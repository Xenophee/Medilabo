# Étape 1: Build avec JDK et Gradle
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

# Copie les fichiers Gradle et le code source
COPY . .

# Construit l’application avec Gradle
RUN ./gradlew build -x test --no-daemon

# Étape 2: Exécution avec une image plus légère (JRE uniquement)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copie l’application construite depuis l’étape de build
COPY --from=builder /app/build/libs/*.jar app.jar

# Lance l’application avec optimisation mémoire
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=50", "-jar", "app.jar"]
