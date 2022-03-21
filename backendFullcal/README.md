Per lanciare correttamente il backend è necessario avere in esecuzione l'applicazione "scheduler" sulla porta 8090 e il database postgres con le tabelle richieste.

## Lanciare l'applicazione scheduler

Fare riferimento al readme del progetto [scheduler](http://dev.kidf.eu/streambase/streambase-aws-deploy/blob/master/README.md#docker-containers).

Riassumo qua i passi

1. Far partire i container docker
2. Lanciare il progetto

## Lanciare database

Questa applicazione necessita di un database.
Per convenienza è presente un file docker-compose che oltre a creare il database crea anche le tabelle necessarie all'applicazione.

Per lanciarlo è sufficiente eseguire da CLI:

`docker-compose up`

## Lanciare l'applicazione

Questa applicazione è stata sviluppata utilizzando springboot. Fare riferimento alla documentazione in caso di dubbi.

Per far partire l'applicazione è necessario eseguire il comando

`./mvnw spring-boot:run`

oppure direttamente dall'IDE
La prima volta verranno anche scaricate le librerie necessarie.