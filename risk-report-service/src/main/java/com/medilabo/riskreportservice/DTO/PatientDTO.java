package com.medilabo.riskreportservice.DTO;

import java.time.LocalDate;
import java.util.UUID;

public record PatientDTO(
        UUID id,
        String lastName,
        String firstName,
        LocalDate birthdate,
        String gender,
        String address,
        String phone
) {
}
