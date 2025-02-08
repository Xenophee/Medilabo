package com.medilabo.patientservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabo.patientservice.exception.NotFoundException;
import com.medilabo.patientservice.model.Patient;
import com.medilabo.patientservice.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PatientService patientService;

    @Autowired
    private ObjectMapper objectMapper;

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
        patient.setGender("M");
    }

    @Test
    void getAllPatients() throws Exception {

        when(patientService.getAllPatients()).thenReturn(Collections.singletonList(patient));

        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"));

        verify(patientService).getAllPatients();
    }

    @Test
    void getPatientById_existingId() throws Exception {

        when(patientService.findById(patientId)).thenReturn(patient);

        mockMvc.perform(get("/patients/{id}", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));

        verify(patientService).findById(patientId);
    }

    @Test
    void getPatientById_nonExistingId() throws Exception {

        when(patientService.findById(any(UUID.class))).thenThrow(new NotFoundException(any(String.class)));

        mockMvc.perform(get("/patients/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound());

    }

    @Test
    void createPatient() throws Exception {

        when(patientService.create(any(Patient.class))).thenReturn(patient);

        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));

        verify(patientService).create(any(Patient.class));
    }

    @Test
    void update_existingId() throws Exception {
        patient.setFirstName("Jane");

        when(patientService.update(eq(patientId), any(Patient.class))).thenReturn(patient);

        mockMvc.perform(put("/patients/{id}", patientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"));

        verify(patientService).update(eq(patientId), any(Patient.class));
    }

    @Test
    void update_nonExistingId() throws Exception {

        when(patientService.update(any(UUID.class), any(Patient.class))).thenThrow(new NotFoundException("Patient not found"));

        mockMvc.perform(put("/patients/{id}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_existingId() throws Exception {
        mockMvc.perform(delete("/patients/{id}", patientId))
                .andExpect(status().isNoContent());

        verify(patientService).delete(patientId);
    }

    @Test
    void delete_nonExistingId() throws Exception {
        doThrow(new NotFoundException("Patient non trouvé")).when(patientService).delete(any(UUID.class));

        mockMvc.perform(delete("/patients/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}
