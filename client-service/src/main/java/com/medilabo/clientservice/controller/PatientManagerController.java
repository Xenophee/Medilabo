package com.medilabo.clientservice.controller;

import com.medilabo.clientservice.DTO.NoteDTO;
import com.medilabo.clientservice.DTO.PatientDTO;
import com.medilabo.clientservice.DTO.PatientDetailsDTO;
import com.medilabo.clientservice.DTO.RiskReportDTO;
import com.medilabo.clientservice.exception.AlreadyExistException;
import com.medilabo.clientservice.feign.NoteClient;
import com.medilabo.clientservice.feign.PatientClient;
import com.medilabo.clientservice.feign.RiskReportClient;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class PatientManagerController {

    private static final Logger logger = LoggerFactory.getLogger(PatientManagerController.class);

    private static final String PATIENTS_LIST_VIEW = "views/patients-list";
    private static final String PATIENT_DETAILS_VIEW = "views/patient-details";
    private static final String PATIENT_CREATION_VIEW = "views/add-patient";
    private static final String NOTE_CREATION_VIEW = "views/add-note";
    private static final String PATIENT_UPDATE_VIEW = "views/update-patient";
    private static final String REDIRECT_TO_PATIENTS_LIST = "redirect:/patients";
    private static final String REDIRECT_TO_PATIENT_DETAILS = "redirect:/patients/{id}";


    private final PatientClient patientClient;
    private final NoteClient noteClient;
    private final RiskReportClient riskReportClient;


    @Autowired
    public PatientManagerController(PatientClient patientClient, NoteClient noteClient, RiskReportClient riskReportClient) {
        this.patientClient = patientClient;
        this.noteClient = noteClient;
        this.riskReportClient = riskReportClient;
    }


    @GetMapping("/patients")
    public String getAllPatients(Model model) {
        logger.info("Requête pour obtenir la liste des patients");
        List<PatientDTO> patients = patientClient.getAllPatients();
        model.addAttribute("patients", patients);
        logger.info("Liste des patients récupérée : {}", patients);
        return PATIENTS_LIST_VIEW;
    }

    @GetMapping("/patients/{id}")
    public String getPatientDetails(Model model, @PathVariable String id) {
        logger.info("Requête pour obtenir les détails du patient avec l'ID : {}", id);

        PatientDTO patient = patientClient.getPatientById(id);
        List<NoteDTO> notes = noteClient.getNotesByPatientId(id);
        RiskReportDTO riskReport = riskReportClient.getRiskReportByPatientId(id);

        PatientDetailsDTO patientDetails = new PatientDetailsDTO(patient, notes, riskReport);

        model.addAttribute("patientDetails", patientDetails);
        logger.info("Détails du patient récupérés : {}", patientDetails);
        return PATIENT_DETAILS_VIEW;
    }


    @GetMapping("/patients/new")
    public String getPatientCreationForm(Model model) {
        logger.info("Requête pour obtenir le formulaire de création d'un nouveau patient");
        model.addAttribute("patient", new PatientDTO());
        return PATIENT_CREATION_VIEW;
    }

    @PostMapping("/patients/new")
    public String createPatient(@ModelAttribute("patient") @Valid PatientDTO newPatient, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        logger.info("Requête pour créer un nouveau patient : {}", newPatient);

        if (result.hasErrors()) {
            logger.warn("Données du patient non valide pour l'enregistrement : {}", newPatient);
            model.addAttribute("errorMessage", "Données du patient non valide.");
            return PATIENT_CREATION_VIEW;
        }

        try {
            PatientDTO patient = patientClient.create(newPatient);
            logger.info("Nouveau patient créé : {}", patient);
            redirectAttributes.addFlashAttribute("successMessage", "Le patient a été enregistré avec succès.");
            return REDIRECT_TO_PATIENTS_LIST;
        } catch (AlreadyExistException e) {
            logger.error("Erreur lors de la création du patient : {}", e.getMessage());
            model.addAttribute("errorMessage", "Le patient existe déjà.");
            return PATIENT_CREATION_VIEW;
        }

//        logger.info("Nouveau patient créé : {}", patient);
//        return REDIRECT_TO_PATIENTS_LIST;
    }


    @GetMapping("/patients/{id}/update")
    public String getPatientUpdateForm(Model model, @PathVariable String id) {
        logger.info("Requête pour obtenir le formulaire de mise à jour du patient avec l'ID : {}", id);
        PatientDTO patient = patientClient.getPatientById(id);
        model.addAttribute("patient", patient);
        return PATIENT_UPDATE_VIEW;
    }


    @PostMapping("/patients/{id}/update")
    public String updatePatient(@PathVariable String id, @ModelAttribute("patient") @Valid PatientDTO updatedPatient, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        logger.info("Requête pour mettre à jour les informations du patient avec l'ID : {}", id);

        if (result.hasErrors()) {
            logger.warn("Données du patient non valide pour la mise à jour : {}", updatedPatient);
            model.addAttribute("errorMessage", "Données du patient non valide.");
            return PATIENT_UPDATE_VIEW;
        }

        PatientDTO patient = patientClient.update(id, updatedPatient);
        logger.info("Informations du patient mises à jour : {}", patient);
        redirectAttributes.addFlashAttribute("successMessage", "Le patient a été modifié avec succès.");
        return REDIRECT_TO_PATIENT_DETAILS.replace("{id}", id);
    }




    @GetMapping("/patients/{id}/notes/new")
    public String getNoteCreationForm(Model model, @PathVariable String id) {
        logger.info("Requête pour obtenir le formulaire de création d'une note pour le patient avec l'ID : {}", id);
        PatientDTO patient = patientClient.getPatientById(id);
        model.addAttribute("note", new NoteDTO(id, patient.getLastName()));
        model.addAttribute("patientId", id);
        model.addAttribute("patientName", patient.getLastName() + " " + patient.getFirstName());
        return NOTE_CREATION_VIEW;
    }

    @PostMapping("/patients/{id}/notes/new")
    public String createNote(@ModelAttribute("note") @Valid NoteDTO newNote) {
        logger.info("Requête pour créer une nouvelle note : {}", newNote);
        NoteDTO note = noteClient.addNoteToPatient(newNote);
        logger.info("Nouvelle note créée : {}", note);
        return REDIRECT_TO_PATIENT_DETAILS.replace("{id}", newNote.getPatId());
    }
}
