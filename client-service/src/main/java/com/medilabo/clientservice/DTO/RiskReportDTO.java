package com.medilabo.clientservice.DTO;

public record RiskReportDTO(
        String patId,
        String label,
        String description) { }
