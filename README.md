# AkkaClusterEMnify
````
backend.sh                     // Starts the Backend
frontend.sh                    // Starts the Frontend
pom.xml                        // Maven Configuration
README.md                      // this
/src                           
 /main                         
  /com                         
   /emnify                     
    /akkacluster               
     /backend                  
       BackendMain.java        // Main to start the Backend
       BackendSupervisor.java  // Akka Actor for internal routing and supervision
       BackendWorker.java      // Akka Actor for simple Message Processing
     /frontend                 
       FrontendActor.java      // Akka Actor with Scheduler to send periodical Messages to the Backend
       FrontendMain.java       // Main to start the Frontend
     ResultMessage.java        // Akka Message, including a new String and Long Value
     StringMessage.java        // Akka Message, including a simple String
 /resources                    
   application.conf          // General Akka config
   backend.conf              // Additional Backend specific Akka config
   frontend.conf             // Additional Frontend specific Akka config
````

Simple Akka Cluster Example.

It sends a simple String from one or multiple Frontends to one or multiple Backends.

The String is logged, a Counter Variable is increased and send back to the Frontend with a new String.

The Frontend logs the received String, Long-Value and the corresponding sender-actor.

The Frontend uses an adaptive router with mixed metrics to determine which Backend-System the message is send to.

The Backend uses a dynamic round-robin router with at least 2 workers to internally process the messages with multiple workers.
