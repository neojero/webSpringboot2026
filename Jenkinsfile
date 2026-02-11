pipeline {
    // depuis m'importe quel agent Jenkins
    agent any
    // variables d'environment
    environment {
        // compte DockerHub paramétré sur le serveur Jenkins
        // dans la rubrique Credentials de l'administration serveur
        registryCredential = 'DockerHubAccount'
    }
    // déclaration des outils que l'agent devra utiliser
    // outils parametre et declare dans jenkins
    tools {
        maven 'maven'
        jdk 'JDK21'
    }

    // déclaration des stages
    stages {
        // nettoyage du workspace
        stage('Clean workspace') {
            steps {
                cleanWs()
            }
        }
        // recopie du depôt dans le workspace
        // option : branch main - url du dépôt - token d'identification
        // dans le compte Github > setting > developper setting > token classic
        // cela permet de créer un token d'authentification valable x jours
        // il faut ensuite le créer dans les crédentials de Jenkins
        stage('Git Checkout') {
            steps {
                // récupération du dépôt GitHub du projet+
                git branch: 'main',
                    credentialsId: 'jenkins_github_PAT',
                    url: 'https://github.com/neojero/webSpringboot2026.git'
            }
        }
        // construction du JAR ou WAR avec maven
        stage('Build Maven') {
            steps {
                // package du projet -Dspring.profiles.active=jenkins
                bat 'mvn clean verify'
            }
        }
        // Generation rapport Test
        stage('Generate Allure Report') {
            steps {
                bat 'mvn allure:report'
            }
        }

        // Construction de l'image Docker à partir du Dockerfile
        stage('Build Docker Image') {
            steps {
                script {
                    docker.build('neojero/webspringboot2026:latest', '-f Dockerfile .')
                }
            }
        }
        // push de l'image dans le dockerHub
        stage('Push to Docker Hub') {
            steps {
                // il faut également dans le crédentials de Jenkins fournir les infos de connexion
                script {
                    docker.withRegistry('', registryCredential) {
                        docker.image('neojero/webspringboot2026:latest').push()
                    }
                }
            }
        }
        // déploiement du multi containeur avec docker compose
        stage('Deploy with Docker Compose') {
                    steps {
                        // initialise le conteneur docker
                        script {
                            // construit les services
                            bat 'docker-compose up -d --build --force-recreate --remove-orphans'
                        }
                    }
        }
        // verifie que le multi-conteneur fonctionne
        stage('Check Docker Containers') {
            steps {
                timeout(time: 120, unit: 'SECONDS') {
                    script {
                        try {
                            def containers = bat(script: 'docker ps --format "{{.Names}}"', returnStdout: true).trim()
                            echo "Containers running: ${containers}"
                            if (!containers.contains('mysql_server') || !containers.contains('apiprojet') || !containers.contains('webprojet') || !containers.contains('phpmyadmin')) {
                                error 'One or more Docker containers are not running.'
                            }
                        } catch (Exception e) {
                            echo "Error checking Docker containers: ${e.message}"
                            error 'Failed to check Docker containers.'
                        }
                    }
                }
            }
        }
    }
    // après réalisation du pipeline, notification du résultat sur Discord
    post {
        always {
            allure([
                includeProperties: false,
                jdk: '',
                properties: [],
                reportBuildPolicy: 'ALWAYS',
                results: [[path: 'target/allure-results']]
            ])

            sh """
            curl -H "Content-Type: application/json" \
            -X POST \
            -d '{"content":"Build ${env.JOB_NAME} #${env.BUILD_NUMBER} - ${currentBuild.currentResult}"}' \
            https://discord.com/api/webhooks/TON_WEBHOOK
            """
        }
    }
}