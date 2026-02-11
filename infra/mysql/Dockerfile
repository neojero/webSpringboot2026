# depuis une image mysql
FROM mysql:latest

# exposition du port 3306 du conteneur
EXPOSE 3306

# Copie les fichiers de script dans le conteneur
# permettra au lancement du conteneur de creer la bdd avec les données
COPY dump/* /docker-entrypoint-initdb.d/