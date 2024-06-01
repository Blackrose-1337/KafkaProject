# Zielsetzung

Meine Zielsetzungen sind in drei Schritte aufgeteilt. Das Endziel ist es, über ein Git-Commit Informationen an eine Applikation zu übergeben, die diese Informationen als Event an einen Kafka-Broker übermittelt (Producer). Danach soll eine Consumer-Applikation, die sich auf einem Domain-Controller befindet, diese Informationen verarbeiten und daraus einen AD-Benutzer erstellen.

# Step 1

Als erster Schritt wird der Kafka-Server aufgesetzt (bei mir mittels Docker). Es werden eine Producer-Applikation und eine Consumer-Applikation erstellt, die sich in dieser Phase noch in derselben Umgebung befinden. Ziel ist es, dass der Producer ein Event an den Kafka-Broker übergibt und der Consumer, der sich ebenfalls noch in derselben Umgebung befindet, diese Informationen entnehmen und verarbeiten kann (darstellen und bearbeiten).

# Step 2

Nun ist das Ziel, dass die Consumer-Applikation in der Lage ist, die Informationen auch von einem Domain-Controller abzurufen, der sich im selben Netzwerk befindet.

# Step 3

Jetzt fehlt nur noch der letzte Schritt. Ein Git-Commit soll dazu führen, dass die Producer-Applikation die Commit-Informationen an den Kafka-Broker übergibt. Dazu wird ein Webhook von Git zu meiner Applikation eingerichtet, sodass die Producer-Applikation mit diesen Informationen eine Abfrage an die Git-API macht, um die restlichen Informationen zu erhalten und anhand dieser einen neues Event an den Kafka-Broker sendet, um einen AD-Benutzer zu erstellen.
