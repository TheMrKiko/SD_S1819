# gRPC server

This is the gRPC server. 

The server depends on the contract module, where the protocol buffers shared between server and client are defined.
The server needs to know the interface to provide an implementation for it.


## Instructions for using Maven

Make sure that you installed the contract module first.

To compile and run the server:

```
mvn compile exec:java
```


## To configure the Maven project in Eclipse

'File', 'Import...', 'Maven'-'Existing Maven Projects'

'Select root directory' and 'Browse' to the project base folder.

Check that the desired POM is selected and 'Finish'.


----

[SD Faculty](mailto:leic-sod@disciplinas.tecnico.ulisboa.pt)


Na aula adicionámos a cifra do lado do servidor e do cliente, mas não conseguimos fazer a leitura da chave.
Não fizemos a alínea secreta.