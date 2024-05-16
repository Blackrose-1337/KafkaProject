# Kafka Projekt

Dieses kleine Projekt dient dazu, mittels einer Webapplikation Informationen ein AD-Benutzer zu übermitteln, die auf dem DC-Server neu angelegt werden sollen.

Eine Springboot-Application (Producer) stellt die Webapplikation zur Verfügung und nimmt die eingegebenen Informationen um diese an einen Kafka-Stream weiterzugeben, welcher mittels Docker-Composition zur Verfügung gestellt wird. Eine weitere Springboot-Applikation (Consumer), die sich auf dem DC-Server befindet, registriert das neue Event im Kafka-Stream, entnimmt diese Information und versucht mit den übergebenen Informationen mittels Powershell-Befehlen einen neuen Ad-User zu erstellen.