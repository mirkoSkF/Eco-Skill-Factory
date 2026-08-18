# ============================================================
# STAGE 1 - COMPILAZIONE
# ============================================================

FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copiamo prima il pom per sfruttare la cache Docker
COPY pom.xml .

RUN mvn dependency:go-offline -B

# Copiamo il sorgente
COPY src ./src

# Compilazione del progetto
RUN mvn clean package -DskipTests


# ============================================================
# STAGE 2 - ESECUZIONE
# ============================================================

FROM eclipse-temurin:17-jre

WORKDIR /app

# Copia il JAR generato dallo stage precedente
COPY --from=build /app/target/*.jar app.jar

# Porta utilizzata da Spring Boot
EXPOSE 8095

# Avvio dell'applicazione
ENTRYPOINT ["java", "-jar", "app.jar"]
