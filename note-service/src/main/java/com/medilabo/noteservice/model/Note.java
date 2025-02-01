package com.medilabo.noteservice.model;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection = "notes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Note {

    @Id
    private String id;

    @NotBlank
    private String patId;
    @NotBlank
    private String patient;
    @NotBlank
    private String note;

    public Note(String patId, String patient, String note) {}
}
