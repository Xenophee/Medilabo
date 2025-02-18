package com.medilabo.riskreportservice.service;

import com.medilabo.riskreportservice.DTO.NoteDTO;
import com.medilabo.riskreportservice.DTO.PatientDTO;
import com.medilabo.riskreportservice.DTO.RiskReportDTO;
import com.medilabo.riskreportservice.enums.RiskLevel;
import com.medilabo.riskreportservice.exception.NotFoundException;
import com.medilabo.riskreportservice.feign.NoteClient;
import com.medilabo.riskreportservice.feign.PatientClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


class RiskReportServiceTest {

    @Mock
    private PatientClient patientClient;

    @Mock
    private NoteClient noteClient;

    @InjectMocks
    private RiskReportService riskReportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void assessRisk_NoTriggers_ReturnsNone() {
        PatientDTO patient = new PatientDTO(UUID.randomUUID(), "TestNone", "FirstName", LocalDate.of(1980, 1, 1), "M", "Address", "Phone");
        List<NoteDTO> notes = List.of(new NoteDTO(UUID.randomUUID().toString(), patient.id().toString(), patient.firstName(), "Le patient se sent bien."));

        when(patientClient.getPatientById(anyString())).thenReturn(patient);
        when(noteClient.getNotesByPatId(anyString())).thenReturn(notes);

        RiskReportDTO result = riskReportService.assessRisk("1");

        assertThat(result.label()).isEqualTo(RiskLevel.NONE.getLabel());
    }


    @Test
    void assessRisk_PatientNotFound_ThrowsNotFoundException() throws Exception {
        when(patientClient.getPatientById(anyString())).thenReturn(null);

        assertThrows(NotFoundException.class, () -> riskReportService.assessRisk("5"));
    }
}