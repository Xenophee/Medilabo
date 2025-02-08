package com.medilabo.clientservice.DTO;

import java.util.List;

public class PatientDetailsDTO {

    private PatientDTO patient;
    private List<NoteDTO> notes;
    private RiskReportDTO riskReport;

    public PatientDetailsDTO() {
    }

    public PatientDetailsDTO(PatientDTO patient, List<NoteDTO> notes, RiskReportDTO riskReport) {
        this.patient = patient;
        this.notes = notes;
        this.riskReport = riskReport;
    }

    public PatientDTO getPatient() {
        return patient;
    }

    public void setPatient(PatientDTO patient) {
        this.patient = patient;
    }

    public List<NoteDTO> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteDTO> notes) {
        this.notes = notes;
    }

    public RiskReportDTO getRiskReport() {
        return riskReport;
    }

    public void setRiskReport(RiskReportDTO riskReport) {
        this.riskReport = riskReport;
    }
}
