This is a Java Web Service that has a one-way operation.
In this case, there is a request message but there is not a response message.
The client can send the message and continue processing without waiting.

Sleeps are introduced in the server code to allow the human observation
of this message exchange pattern.

The service is defined by the Java code with annotations
(code-first approach, also called bottom-up approach).

The service runs in a standalone HTTP server.


## Instructions using Maven:

To compile: 
```
mvn compile
```

To run using exec plugin: 
```
mvn exec:java
```

To generate launch scripts for Windows and Linux:
  (appassembler:assemble is attached to install phase)
```
mvn install
```

To run using appassembler plugin:
- On Windows:
  ```
  target\appassembler\bin\bye-ws http://localhost:8080/bye-ws/endpoint
  ```
- On Linux:
  ```
  ./target/appassembler/bin/bye-ws http://localhost:8080/bye-ws/endpoint
  ```


When running, the web service awaits connections from clients.
You can check if the service is running using your web browser 
to see the generated WSDL file:

```
http://localhost:8080/bye-ws/endpoint?WSDL
```

This address is defined in HelloMain when the publish() method is called.

To call the service you will need a web service client,
including code generated from the WSDL.


## To configure the Maven project in Eclipse:


'File', 'Import...', 'Maven'-'Existing Maven Projects'
'Select root directory' and 'Browse' to the project base folder.
Check that the desired POM is selected and 'Finish'.


----
[SD Faculty](mailto:leic-sod@disciplinas.tecnico.ulisboa.pt)
