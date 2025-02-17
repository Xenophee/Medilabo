# Étape 2: Exécution avec une image plus légère (JRE uniquement)
FROM eclipse-temurin:21-jre-alpine

# Copie l’application construite depuis l’étape de build
COPY build/libs/*.jar risk-report.jar

# Lance l’application avec optimisation mémoire
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=50", "-jar", "risk-report.jar"]