# ============================================================
# STAGE 1 - COMPILAZIONE MAVEN
# ============================================================

FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copia il pom.xml per sfruttare la cache Docker
COPY pom.xml .

# Scarica le dipendenze prima di copiare il sorgente
RUN mvn dependency:go-offline -B

# Copia il progetto
COPY src ./src

# Compila il progetto SENZA eseguire i test
RUN mvn clean package -DskipTests


# ============================================================
# STAGE 2 - ESECUZIONE SPRING BOOT
# ============================================================

FROM eclipse-temurin:17-jre

WORKDIR /app

# Copia il JAR prodotto da Maven
COPY --from=build /app/target/*.jar app.jar

# Porta utilizzata da Spring Boot
EXPOSE 8095

# Avvio dell'applicazione
ENTRYPOINT ["java", "-jar", "app.jar"]
