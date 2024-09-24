# ece382v-fall2024
distributed systems

1. Run  [Server](src/main/java/org/ece382vfall2024/asg1/server/Server.java)
2. Run [Client](src/main/java/org/ece382vfall2024/asg1/client/Client.java)
3. Multiple instances of client can be executed at the same time.
4. Server listen for TCP connection on 1024 and UDP connection on 5001.
5. Server is always on.
6. Server loads initial inventory from [Input.txt](src/main/resources/Input.txt)
7. Once a client is up, first command should be to setmode T or setmode U
8. After selection of mode, client will have few options to perform.
   
     1. purchase <user-name> <product-name> <quantity> --> username is String, product name is product name and quantity is integer.
     2. cancel <order-id> --> order id is kept as double
     3. search <user-name> --> username is String
     4. list
9. Order cache is shared across clients.

