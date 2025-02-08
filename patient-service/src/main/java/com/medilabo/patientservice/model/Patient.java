package com.medilabo.patientservice.model;

import com.medilabo.patientservice.validation.annotation.ValidAddress;
import com.medilabo.patientservice.validation.annotation.ValidPhone;
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
    @NotBlank(message = "Le renseignement du nom est obligatoire.")
    @Size(min = 2, max = 70, message = "Le nom doit contenir entre 2 et 70 caractères.")
    private String lastName;

    @Column(name = "first_name")
    @NotBlank(message = "Le renseignement du prénom est obligatoire.")
    @Size(min = 2, max = 70, message = "Le prénom doit contenir entre 2 et 70 caractères.")
    private String firstName;

    @NotNull(message = "Le renseignement de la date de naissance est obligatoire.")
    @PastOrPresent(message = "La date de naissance ne peut pas être dans le futur.")
    private LocalDate birthdate;

    @NotBlank(message = "Le renseignement du genre est obligatoire.")
    @Pattern(regexp = "^[FM]$", message = "Le genre doit être M ou F.")
    private String gender;

    @ValidAddress
    private String address;

    @ValidPhone
    private String phone;
}
