package com.medilabo.patientservice.controller;

import com.medilabo.patientservice.model.Patient;
import com.medilabo.patientservice.service.PatientService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/patients")
public class PatientController {

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }


    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        logger.info("Requête pour obtenir la liste des patients");
        List<Patient> patients = patientService.getAllPatients();
        logger.info("Liste des patients récupérée");
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable UUID id) {
        logger.info("Requête pour obtenir le patient avec l'ID: {}", id);
        Patient patient = patientService.findById(id);
        logger.info("Patient trouvé avec l'ID: {}", id);
        return ResponseEntity.ok(patient);
    }

    @PostMapping
    public ResponseEntity<Patient> create(@Valid @RequestBody Patient newPatient) {
        logger.info("Requête pour créer un nouveau patient");
        Patient patient = patientService.create(newPatient);
        logger.info("Nouveau patient créé avec l'ID: {}", patient.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(patient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable UUID id, @Valid @RequestBody Patient updatedPatient) {
        logger.info("Requête pour mettre à jour le patient avec l'ID: {}", id);
        Patient patient = patientService.update(id, updatedPatient);
        logger.info("Patient mis à jour avec l'ID: {}", id);
        return ResponseEntity.ok(patient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        logger.info("Requête pour supprimer le patient avec l'ID: {}", id);
        patientService.delete(id);
        logger.info("Patient supprimé avec l'ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}
