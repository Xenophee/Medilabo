package com.medilabo.riskreportservice.service;


import com.medilabo.riskreportservice.DTO.NoteDTO;
import com.medilabo.riskreportservice.DTO.PatientDTO;
import com.medilabo.riskreportservice.DTO.RiskReportDTO;
import com.medilabo.riskreportservice.enums.RiskLevel;
import com.medilabo.riskreportservice.enums.RiskLevelThreshold;
import com.medilabo.riskreportservice.exception.NotFoundException;
import com.medilabo.riskreportservice.feign.NoteClient;
import com.medilabo.riskreportservice.feign.PatientClient;
import com.medilabo.riskreportservice.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class RiskReportService {

    private final Logger logger = LoggerFactory.getLogger(RiskReportService.class);

    private final PatientClient patientClient;
    private final NoteClient noteClient;

    private static final Set<String> TRIGGER_TERMS = Set.of(
            "Hémoglobine A1C", "Microalbumine", "Taille", "Poids",
            "Fumeur", "Fumeuse", "Anormal", "Cholestérol",
            "Vertiges", "Rechute", "Réaction", "Anticorps"
    );

    @Autowired
    public RiskReportService(PatientClient patientClient, NoteClient noteClient) {
        this.patientClient = patientClient;
        this.noteClient = noteClient;
    }


    /**
     * Assesses the risk level of a patient based on their ID.
     *
     * @param patId the ID of the patient
     * @return a RiskReportDTO containing the patient's ID, risk level label, and risk level description
     * @throws NotFoundException if the patient is not found
     */
    public RiskReportDTO assessRisk(String patId) {

        PatientDTO patient = Optional.ofNullable(patientClient.getPatientById(patId))
                .orElseThrow(() -> new NotFoundException("Patient introuvable."));

        List<NoteDTO> notes = noteClient.getNotesByPatId(patId);

        if (notes.isEmpty()) {
            return new RiskReportDTO(patId, RiskLevel.NONE.getLabel(), RiskLevel.NONE.getDescription());
        }

        int triggerCount = countTriggers(notes);
        RiskLevel riskLevel = calculateRiskLevel(patient, triggerCount);

        return new RiskReportDTO(patId, riskLevel.getLabel(), riskLevel.getDescription());
    }


    /**
     * Counts the number of trigger terms found in the patient's notes.
     *
     * @param notes the list of patient notes
     * @return the count of trigger terms found
     */
    private int countTriggers(List<NoteDTO> notes) {
        return (int) notes.stream()
                .flatMap(note -> TRIGGER_TERMS.stream()
                        .filter(trigger -> Optional.ofNullable(note.note())
                                .map(contentNote -> contentNote.toLowerCase().contains(trigger.toLowerCase()))
                                .orElse(false))
                )
                .count();
    }


    /**
     * Calculates the risk level of a patient based on their age, gender, and the number of trigger terms found.
     *
     * @param patient the patient information
     * @param triggerCount the number of trigger terms found
     * @return the calculated risk level
     */
    private RiskLevel calculateRiskLevel(PatientDTO patient, int triggerCount) {
        int age = DateUtil.calculateAge(patient.birthdate());
        String gender = patient.gender();

        if (triggerCount == 0) return RiskLevel.NONE;

        return Arrays.stream(RiskLevelThreshold.values())
                .filter(threshold -> threshold.matches(age, triggerCount, gender))
                .findFirst()
                .map(RiskLevelThreshold::getRiskLevel)
                .orElse(RiskLevel.NONE);
    }

}

