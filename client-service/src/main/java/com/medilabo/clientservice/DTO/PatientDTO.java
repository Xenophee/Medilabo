package com.medilabo.clientservice.DTO;

import com.medilabo.clientservice.validation.annotation.ValidAddress;
import com.medilabo.clientservice.validation.annotation.ValidPhone;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class PatientDTO {

    private UUID id;

    @NotBlank(message = "Le renseignement du nom est obligatoire.")
    @Size(min = 2, max = 70, message = "Le nom doit contenir entre 2 et 70 caractères.")
    private String lastName;

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





    public PatientDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    public String formattedBirthdate() {
        return birthdate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public String inputBirthdate() {
        return birthdate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}