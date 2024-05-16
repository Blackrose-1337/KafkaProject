# Docker Compose

Die Docker Compose-Datei definiert die verschiedenen Dienste und deren Konfigurationen, die in der Anwendungsinfrastruktur verwendet werden.
Diese Docker-Container bilden die Grundlage für Ihre Anwendungsinfrastruktur und ermöglichen die Bereitstellung und Verwaltung von PostgreSQL-Datenbanken sowie die Kommunikation über Apache Kafka. Hier ist eine Zusammenfassung der Dienste, die in der Docker Compose-Datei definiert sind:

## pgAdmin

Ein Container für die pgAdmin-Oberfläche, die zur Verwaltung von PostgreSQL-Datenbanken verwendet wird. Die folgenden Umgebungsvariablen wurden konfiguriert:
        PGADMIN_DEFAULT_EMAIL: Die Standard-E-Mail-Adresse für die Anmeldung bei pgAdmin.
        PGADMIN_DEFAULT_PASSWORD: Das Standardpasswort für die Anmeldung bei pgAdmin.
        PGADMIN_LISTEN_PORT: Der Port, auf dem pgAdmin lauscht, um auf die Oberfläche zuzugreifen. Hier ist es auf Port 5050 konfiguriert.

## PostgreSQL as db

Ein Container für die PostgreSQL-Datenbank. Die folgenden Umgebungsvariablen wurden konfiguriert:
        POSTGRES_USER: Der Benutzername für den Zugriff auf die PostgreSQL-Datenbank.
        POSTGRES_PASSWORD: Das Passwort für den Benutzer zur Authentifizierung.
        POSTGRES_DB: Der Name der zu verwendenden PostgreSQL-Datenbank.
        Der Dienst wird auf Port 5432 verfügbar gemacht.

## zookeeper

Ein Container für den Zookeeper, der von Apache Kafka für die Koordination verwendet wird. Es sind keine zusätzlichen Umgebungsvariablen konfiguriert. Der Dienst wurde auf Port 2181 verfügbar gemacht.

## kafka
Ein Container für Apache Kafka, der auf Port 9092 verfügbar gemacht wurde. Folgende Umgebungsvariablen wurden konfiguriert:
        KAFKA_ADVERTISED_LISTENERS: Diese Umgebungsvariable definiert, wie Kafka-Clients den Broker erreichen können. Sie spezifiziert die Kommunikationswege, die Kafka verwendet, um sich selbst im Netzwerk bekannt zu machen. Dabei wird zwischen verschiedenen Netzwerktypen unterschieden, wie intern und extern. In diesem Fall werden die Bezeichnungen INSIDE und OUTSIDE verwendet, um die Netzwerkbereiche zu kennzeichnen, in denen die Broker erreichbar sind.
        KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: Mapping von Listenernamen auf Sicherheitsprotokolle.
        KAFKA_LISTENERS: Definiert die Listener, auf die Kafka-Broker lauschen sollen.
        KAFKA_INTER_BROKER_LISTENER_NAME: Der Name des Listeners, der für die Kommunikation zwischen Brokern verwendet wird.
        KAFKA_ZOOKEEPER_CONNECT: Die Verbindungsinformationen für Zookeeper.
        KAFKA_CREATE_TOPICS: Definiert die Kafka-Themen, die beim Starten automatisch erstellt werden sollen.
        Darüber hinaus wird das Volume /var/run/docker.sock eingebunden, um den Zugriff auf den Docker-Daemon innerhalb des Containers zu ermöglichen.

