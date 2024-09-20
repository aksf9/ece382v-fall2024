# ece382v-fall2024
distributed systems

1. Run  [Server](src/main/java/org/ece382vfall2024/asg1/server/Server.java)
2. Run [Client](src/main/java/org/ece382vfall2024/asg1/client/Client.java)
3. Multiple instances of client can be executed at the same time.
4. Server listen for TCP connection on 1024 and UDP connection on 5001.
5. Server is always on.
6. Server load initial product inventory from [Input.txt](src/main/resources/Input.txt)
7. Once a client is up, it will present user an option to select UDP or TCP mode [Type setmode T or setmode U or quit to end]
8. After selection of mode, client will have few options to perform.
   
     1. purchase <user-name> <product-name> <quantity> --> username is String, product name is product name and quantity is integer.
     2. cancel <order-id> --> order id is kept as double
     3. search <user-name> --> username is String
     4. list

9. After execution of selected option, client will again present user options to select the mode.
10. Order cache is shared across clients.
