# Producer-Anwendung

Die Producer-Anwendung besteht aus mehreren Java-Klassen, die für das Erstellen und Senden von Nachrichten an einen Kafka-Server zuständig sind.

Die folgenden Komponenten bilden zusammen die Producer-Anwendung, die Daten an einen Kafka-Server sendet.

## ProducerApplication.java

Die **ProducerApplication** Klasse ist die Einstiegspunkt-Klasse für die Producer-Anwendung. Sie konfiguriert und startet die Spring Boot-Anwendung. Zudem wird ein Kafka-Topic mit dem Namen ``Kafka-Project`` und bestimmten Partitionen und Replikationen erstellt.

## MainView.java

Die **MainView** Klasse stellt die Benutzeroberfläche für die Producer-Anwendung dar. Sie ermöglicht es Benutzern, Informationen wie Name, Nachname, Abteilung, E-Mail und Telefonnummer einzugeben und durch einen Klick auf den ```"Save"-Button``` an den Kafka-Server zu senden.

## TextFieldService.java

Der **TextFieldService** ist ein Service, der zur Erstellung und Konfiguration von Textfeldern für die Benutzeroberfläche verwendet wird. Er erstellt Textfelder mit einem Label und aktiviert die Möglichkeit, den Inhalt zu löschen.

## Department.java

Die **Department** Enumeration definiert verschiedene Abteilungen, die ein Benutzer haben kann.

## AdUserDto.java

Die **AdUserDto** Klasse ist ein Datenübertragungsobjekt (DTO), das die Attribute eines Benutzers wie Nachname, Vorname, E-Mail, Telefonnummer und Abteilung enthält.

## WebhookController.java

Die **WebhookController-Klasse** ist eine Spring Boot-Anwendung und fungiert als **REST-Controller**, um Webhook-Ereignisse von GitHub zu empfangen und zu verarbeiten. Sie verarbeitet eingehende JSON-Payloads, extrahiert relevante Informationen und sendet die verarbeiteten Daten an einen Kafka-Server.

[Mehr Details WebhookController](/details/webhookController.md)