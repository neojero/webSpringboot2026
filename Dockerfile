FROM eclipse-temurin:21-jdk-jammy

# Définir des variables d'environnement
ENV APP_HOME=/app
#ENV FR_POMPEY_CDA23016_WEBPROJET_URLAPI=http://apiprojet:9000

# Exposer le port
EXPOSE 9001

# Répertoire de travail dans le conteneur
WORKDIR $APP_HOME

# Copie du fichier JAR de votre projet dans le conteneur
COPY target/web2026-0.0.1-SNAPSHOT.jar /app/web2026-0.0.1-SNAPSHOT.jar

# Commande pour exécuter le fichier JAR
CMD ["java", "-jar", "web2026-0.0.1-SNAPSHOT.jar"]