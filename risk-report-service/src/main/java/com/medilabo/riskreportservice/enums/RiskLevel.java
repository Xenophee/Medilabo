package com.medilabo.riskreportservice.enums;

import lombok.Getter;

@Getter
public enum RiskLevel {
    NONE("Aucun risque", "Le patient ne présente aucun risque de diabète."),
    BORDERLINE("Risque limité", "Le patient présente un risque limité de diabète, mais une surveillance est nécessaire."),
    EARLY_ONSET("Apparition précoce", "Le patient présente des signes d'apparition précoce de diabète."),
    IN_DANGER("Risque élevé", "Le patient présente un risque élevé de diabète, intervention nécessaire.");

    private final String label;
    private final String description;

    RiskLevel(String label, String description) {
        this.label = label;
        this.description = description;
    }
}

