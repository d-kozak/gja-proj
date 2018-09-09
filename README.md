# System for monitoring and executing distributed tasks

The system consists of a server node and client node. Both are spring boot web apps. The server node has a GUI implemented using JSF. 
The server and client communicate using rest endpoints. When client node starts, it tries to register itself at the server, but this can also
be done manually from the server. Then the server then send requests to the client to start executing a certain command. The server then shows statistics about the execution 
of the tasks on client nodes. 


## Authors
* [dkozak](https://github.com/d-kozak) 
* [phodoval](https://github.com/phodoval)
* [Dzinekk](https://github.com/Dzinekk)
* [Pheat](https://github.com/Pheat)
