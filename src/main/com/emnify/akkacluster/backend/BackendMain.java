package com.emnify.akkacluster.backend;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorSystem;
import akka.actor.Props;

public class BackendMain {

  /**
   * Starts the Backend-Node with the preset IP in the application.conf file
   * 
   * @param args
   */
  public static void main(String[] args) {
    Config config = ConfigFactory.load("backend");
    ActorSystem system = ActorSystem.create("clusterSystem", config);

    system.actorOf(Props.create(BackendSupervisor.class), "backendSupervisor");
  }

}
