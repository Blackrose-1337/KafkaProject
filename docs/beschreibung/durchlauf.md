# Durchlauf

## Vorbereitung
1. Docker Compose initialisieren
2. Ngrok starten
3. Producer Applikation starten
4. Auf Domain Controller die Consumer-Applikation als Administrator ausführen. (Zur überwachung aus einem Admin Powershell-Terminal starten.)
5. AdUser commit vorbereiten.

## Durchführung

Es wird ein Commit auf dem eingerichteten Repository gepusht.

![Anzeige des Commtis](../bilder/commitAdUser.jpg)

Ngrok leitet den Webhook an den Producer weiter, welcher auf dem Port **8080** definiert ist.

![Webansicht von Ngrok das als Monitoring tool dient.](../bilder/webDashboardNgrok_00.jpg)
![Webansicht von Ngrok das als Monitoring tool dient.](../bilder/webDashboardNgrok_01.jpg)

Die Producer-Applikation verarbeitet die Informationen des Webhooks, anhand dieser Informationen holt sie sich die Json-Datei vor dem Commit und nach dem Commit, um die beiden miteinander zu vergleichen, um aus den neuen Benutzern für Kafka ein neues Event für jeden neuen Benutzer zu generieren.

![Log Informationen von der Producer-Applikation, dass ein neuer AD-User hinzugefügt wurde und weiteren Informationen.](../bilder/logProducerFromCommitreaction.jpg)


Die Consumer-Applikation registriert das neue Ereignis auf dem Kafka-Stream, ruft das Ereignis ab und verwendet diese Informationen, um einen Powershell-Befehl auszuführen, der den neuen Benutzer erstellt.

![Log Informationen vom der Consumer-Applikation über den neu angelegten AD-User auf dem DC](../bilder/consumerLogCreatedAdUser.jpg)

Kontrolle zeigt den neu generierten AD-User auf dem Domain-Controller

![Eigenschaften des AD-User auf dem DC, zur Kontrolle überprüft](../bilder/eigenschaftenErstellterAdUser.jpg)