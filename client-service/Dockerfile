# Étape 1: Build avec JDK et Gradle
FROM eclipse-temurin:21-jdk-alpine AS builder

# Installer Node.js et NPM dans l'image de build
RUN apk add --no-cache nodejs npm

WORKDIR /app

# Copie les fichiers Gradle et le code source
COPY . .

# Installe les dépendances NPM (ici, Tailwind)
RUN npm install

# Compile les fichiers CSS avec Tailwind
RUN npm run build:css

# Construit l’application avec Gradle
RUN ./gradlew build --no-daemon

# Étape 2: Exécution avec une image plus légère (JRE uniquement)
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copie l’application construite depuis l’étape de build (JAR)
COPY --from=builder /app/build/libs/*.jar app.jar

# Lance l’application avec optimisation mémoire
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=50", "-jar", "app.jar"]
