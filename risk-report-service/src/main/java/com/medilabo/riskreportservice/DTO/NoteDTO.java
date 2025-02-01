package com.medilabo.riskreportservice.DTO;

public record NoteDTO(
        String id,
        String patId,
        String patient,
        String note
) {
}
