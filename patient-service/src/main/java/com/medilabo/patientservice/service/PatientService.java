package com.medilabo.patientservice.service;

import com.medilabo.patientservice.exception.AlreadyExistException;
import com.medilabo.patientservice.exception.NotFoundException;
import com.medilabo.patientservice.model.Patient;
import com.medilabo.patientservice.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class PatientService {

    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient findById(UUID id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Le patient n'a pas été trouvé avec l'ID: {}", id);
                    return new NotFoundException("Le patient demandé n'a pas été trouvé.");
                });
    }

    public Patient create(Patient newPatient) {
        if (patientRepository.existsByFirstNameAndLastNameAndBirthdate(
                newPatient.getFirstName(), newPatient.getLastName(), newPatient.getBirthdate())) {
            logger.warn("Un patient existe déjà avec le même prénom, nom de famille et date de naissance: {} {} {}",
                    newPatient.getFirstName(), newPatient.getLastName(), newPatient.getBirthdate());
            throw new AlreadyExistException("Un patient existe déjà avec le même prénom, nom de famille et date de naissance.");
        }
        return patientRepository.save(newPatient);
    }

    public Patient update(UUID id, Patient updatedPatient) {
        if (!patientRepository.existsById(id)) {
            logger.warn("Le patient n'a pas été trouvé avec l'ID: {}", id);
            throw new NotFoundException("Le patient demandé n'a pas été trouvé.");
        }
        updatedPatient.setId(id);
        return patientRepository.save(updatedPatient);
    }

    public void delete(UUID id) {
        if (!patientRepository.existsById(id)) {
            logger.warn("Le patient n'a pas été trouvé avec l'ID: {}", id);
            throw new NotFoundException("Le patient demandé n'a pas été trouvé.");
        }
        patientRepository.deleteById(id);
    }
}
