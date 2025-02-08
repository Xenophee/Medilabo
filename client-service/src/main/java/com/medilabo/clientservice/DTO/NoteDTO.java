package com.medilabo.clientservice.DTO;

import jakarta.validation.constraints.NotBlank;

public class NoteDTO {

    private String id;

    @NotBlank
    private String patId;

    @NotBlank
    private String patient;

    @NotBlank
    private String note;

    public NoteDTO() {
    }

//    public NoteDTO(String id, String patId, String patient, String note) {
//        this.id = id;
//        this.patId = patId;
//        this.patient = patient;
//        this.note = note;
//    }

    public NoteDTO(String patId, String patient) {
        this.patId = patId;
        this.patient = patient;
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatId() {
        return patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}