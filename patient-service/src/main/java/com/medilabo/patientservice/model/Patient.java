package com.medilabo.patientservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;


@Entity
@Table(name = "Patients")
@Setter
@Getter
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "last_name")
    @NotBlank
    @Size(min = 2, max = 70)
    private String lastName;

    @Column(name = "first_name")
    @NotBlank
    @Size(min = 2, max = 70)
    private String firstName;

    @NotNull
    private LocalDate birthdate;

    @NotBlank
    @Pattern(regexp = "^[FM]$")
    private String gender;

    @Size(min = 5, max = 150)
    private String address;

    @Size(min = 10, max = 15)
    private String phone;
}
