# Etapa de construcción
FROM maven:3.8.5-openjdk-17 AS builder

WORKDIR /app

# Copia todos los pom.xml primero
COPY pom.xml /app/
COPY rest-input-adapter/pom.xml /app/rest-input-adapter/
COPY application/pom.xml /app/application/
COPY cli-input-adapter/pom.xml /app/cli-input-adapter/
COPY common/pom.xml /app/common/
COPY domain/pom.xml /app/domain/
COPY maria-output-adapter/pom.xml /app/maria-output-adapter/
COPY mongo-output-adapter/pom.xml /app/mongo-output-adapter/

# Copia el código fuente de todos los módulos
COPY rest-input-adapter/src /app/rest-input-adapter/src/
COPY application/src /app/application/src/
COPY cli-input-adapter/src /app/cli-input-adapter/src/
COPY common/src /app/common/src/
COPY domain/src /app/domain/src/
COPY maria-output-adapter/src /app/maria-output-adapter/src/
COPY mongo-output-adapter/src /app/mongo-output-adapter/src/

# Construye primero los módulos base (common y domain)
RUN mvn clean install -pl common,domain -am -DskipTests

# Ahora construye el resto de la aplicación
RUN mvn clean package -pl rest-input-adapter -am -DskipTests

# Etapa de runtime
FROM eclipse-temurin:17-jre-focal

WORKDIR /app

# Copia el JAR final
COPY --from=builder /app/rest-input-adapter/target/*.jar app.jar

# Variables de entorno para la JVM
ENV JAVA_OPTS="-Xms512m -Xmx512m"

# Puerto de la aplicación
EXPOSE 3000

# Ejecuta la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]