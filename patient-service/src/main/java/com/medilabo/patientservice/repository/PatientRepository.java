package com.medilabo.patientservice.repository;

import com.medilabo.patientservice.model.Patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;


@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    boolean existsByFirstNameAndLastNameAndBirthdate(String firstName, String lastName, LocalDate birthdate);
}
