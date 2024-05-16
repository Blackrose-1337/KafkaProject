# Consumer-Anwendung

Die Consumer-Anwendung ist für den Empfang und die Verarbeitung von Nachrichten aus einem Kafka-Topic verantwortlich.

Die Consumer-Anwendung wird mithilfe von Spring Boot erstellt und konfiguriert. Sie verwendet Spring Kafka zum Konsumieren von Nachrichten aus einem Kafka-Topic und Jackson zur Deserialisierung von JSON-Nachrichten in Java-Objekte.

Die Verwendung von Powershell ermöglicht die Interaktion mit dem Active Directory, um Benutzerkonten basierend auf den empfangenen Daten zu erstellen.

## ConsumerApplication.java

Die Hauptklasse der Anwendung, die die Konfiguration und den Einstiegspunkt enthält. Sie verwendet die @KafkaListener-Annotation, um auf Nachrichten aus dem Kafka-Topic "topic1" zu lauschen. Empfangene Nachrichten werden deserialisiert und an einen Powershell-Service übergeben, um entsprechende Aktionen im Active Directory auszuführen.

## PowershellService.java

Diese Klasse bietet Methoden zum Ausführen von Powershell-Befehlen. Sie erstellt Powershell-Befehle zum Erstellen von Benutzern im Active Directory und überprüft die Verfügbarkeit von SAM-Accountnamen.

## Department.java

Die Department Enumeration definiert verschiedene Abteilungen, die ein Benutzer haben kann.

## AdUserDto

Eine einfache Datenübertragungsobjektklasse, die die Datenstruktur eines Benutzers repräsentiert, der im Active Directory erstellt werden soll.




