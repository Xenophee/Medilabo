package com.medilabo.patientservice.service;

import com.medilabo.patientservice.exception.AlreadyExistException;
import com.medilabo.patientservice.exception.NotFoundException;
import com.medilabo.patientservice.model.Patient;
import com.medilabo.patientservice.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    private Patient patient;
    private UUID patientId;

    @BeforeEach
    void setUp() {
        patientId = UUID.randomUUID();
        patient = new Patient();
        patient.setId(patientId);
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setBirthdate(LocalDate.parse("2000-01-01"));
    }

    @Test
    public void getAllPatients() {
        when(patientRepository.findAll()).thenReturn(Collections.singletonList(patient));
        assertThat(patientService.getAllPatients()).hasSize(1);
    }

    @Test
    public void findById_existingId() {
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));
        assertThat(patientService.findById(patientId)).isEqualTo(patient);
    }

    @Test
    public void findById_nonExistingId() {
        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> patientService.findById(patientId));
    }

    @Test
    public void create_newPatient() {
        when(patientRepository.existsByFirstNameAndLastNameAndBirthdate(
                patient.getFirstName(), patient.getLastName(), patient.getBirthdate())).thenReturn(false);
        when(patientRepository.save(patient)).thenReturn(patient);
        assertThat(patientService.create(patient)).isEqualTo(patient);
    }

    @Test
    public void create_existingPatient() {
        when(patientRepository.existsByFirstNameAndLastNameAndBirthdate(
                patient.getFirstName(), patient.getLastName(), patient.getBirthdate())).thenReturn(true);
        assertThrows(AlreadyExistException.class, () -> patientService.create(patient));
    }

    @Test
    public void update_existingPatient() {
        when(patientRepository.existsById(patientId)).thenReturn(true);
        when(patientRepository.save(patient)).thenReturn(patient);
        assertThat(patientService.update(patientId, patient)).isEqualTo(patient);
    }

    @Test
    public void update_nonExistingPatient() {
        when(patientRepository.existsById(patientId)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> patientService.update(patientId, patient));
    }

    @Test
    public void delete_existingPatient() {
        when(patientRepository.existsById(patientId)).thenReturn(true);
        doNothing().when(patientRepository).deleteById(patientId);
        assertDoesNotThrow(() -> patientService.delete(patientId));
    }

    @Test
    public void delete_nonExistingPatient() {
        when(patientRepository.existsById(patientId)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> patientService.delete(patientId));
    }
}