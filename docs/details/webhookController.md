# WebhookController

## Empfangen von Webhook-Ereignissen
Über den Endpunkt /webhook empfängt die Klasse POST-Anfragen mit JSON-Payloads, die Informationen über GitHub-Commits enthalten. 

## Verarbeiten des Payloads
Die Methode handleWebhook extrahiert Informationen wie Commit-IDs, Commit-Nachrichten und Listen von hinzugefügten, entfernten und geänderten Dateien aus der Payload. 

## Dateiinhalte abrufen    
  Die Methode getFileContent ruft die Inhalte der geänderten Dateien von GitHub anhand der Commit-IDs ab. Diese Inhalte werden als Base64-codierte Strings abgerufen und dekodiert. 

## Vergleichen und Verarbeiten von JSON-Daten
Die Methode compareAndProcessJson vergleicht die JSON-Daten der Dateien vor und nach den Änderungen. Neue oder geänderte Benutzerinformationen werden an einen Kafka-Server gesendet. 

## Konfiguration und Initialisierung 
Die Klasse nutzt Spring Boot-Anmerkungen wie @SpringBootApplication und @RestController, um die Anwendung zu konfigurieren und zu starten. Verschiedene Konfigurationswerte wie GitHub-URL, Token und Kafka-Topic werden über @Value aus den Anwendungseigenschaften geladen.