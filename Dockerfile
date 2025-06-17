# Dockerfile.dev — imagen para desarrollo usando el JAR ya construido

# 1) Imagen base con Java 21
FROM eclipse-temurin:21-jdk

# 2) Directorio de trabajo
WORKDIR /app

# 3) Copia el JAR compilado
COPY target/Prueba_Classora-0.0.1-SNAPSHOT.jar app.jar

# 4) Expone el puerto de dev (8082)
EXPOSE 8082

# 5) Variables de entorno por defecto para dev
ENV SPRING_PROFILES_ACTIVE=dev \
    SERVER_PORT=8082 \
    APP_SECURITY_USER=classoraUser \
    APP_SECURITY_PASS=classoraPass

# 6) Arranque de la aplicación
ENTRYPOINT ["sh","-c","java -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -Dserver.port=${SERVER_PORT} -jar app.jar"]
