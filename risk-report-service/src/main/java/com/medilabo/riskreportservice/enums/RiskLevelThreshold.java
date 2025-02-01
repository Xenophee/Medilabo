package com.medilabo.riskreportservice.enums;


import com.medilabo.riskreportservice.service.RiskReportService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public enum RiskLevelThreshold {

    BORDERLINE(2, 5, 30, null, RiskLevel.BORDERLINE), // Risque limité pour adultes (peu importe le genre)
    EARLY_ONSET_MALE(5, Integer.MAX_VALUE, 0, "M", RiskLevel.EARLY_ONSET),  // Risque d'apparition précoce pour les hommes
    EARLY_ONSET_FEMALE(7, Integer.MAX_VALUE, 0, "F", RiskLevel.EARLY_ONSET),  // Risque d'apparition précoce pour les femmes
    EARLY_ONSET_ADULT(8, Integer.MAX_VALUE, 30, null, RiskLevel.EARLY_ONSET),  // Risque d'apparition précoce pour les adultes
    IN_DANGER_MALE(3, Integer.MAX_VALUE, 0, "M", RiskLevel.IN_DANGER),  // Risque pour les hommes
    IN_DANGER_FEMALE(4, Integer.MAX_VALUE, 0, "F", RiskLevel.IN_DANGER),  // Risque pour les femmes
    IN_DANGER_ADULT(6, 7, 30, null, RiskLevel.IN_DANGER);  // Risque pour les adultes

    private final Logger logger = LoggerFactory.getLogger(RiskReportService.class);

    private final int minTriggers;
    private final int maxTriggers;
    private final int minAge;
    private final String gender; // "M", "F" ou null si applicable à tous
    @Getter
    private final RiskLevel riskLevel;


    RiskLevelThreshold(int minTriggers, int maxTriggers, int minAge, String gender, RiskLevel riskLevel) {
        this.minTriggers = minTriggers;
        this.maxTriggers = maxTriggers;
        this.minAge = minAge;
        this.gender = gender;
        this.riskLevel = riskLevel;
    }

    public boolean matches(int age, long triggerCount, String gender) {
        logger.info("Correspondance du patient avec âge: {}, nombre de déclencheurs: {}, genre: {}", age, triggerCount, gender);

        boolean genderMatches = this.gender == null || this.gender.equalsIgnoreCase(gender);
        logger.info("Correspondance du genre: {}", genderMatches);

        boolean triggersMatch = (triggerCount >= this.minTriggers && triggerCount <= this.maxTriggers);
        logger.info("Correspondance du nombre de déclencheurs: {}", triggersMatch);

        boolean ageMatch = (age >= this.minAge);
        logger.info("Correspondance de l'âge: {}", ageMatch);

        return genderMatches && triggersMatch && ageMatch;
    }
}

