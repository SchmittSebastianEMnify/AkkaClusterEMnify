package com.emnify.akkacluster.backend;

import com.emnify.akkacluster.StringMessage;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.FromConfig;

public class BackendSupervisor extends UntypedActor {

  ActorRef backendRouter = getContext().actorOf(
      FromConfig.getInstance().props(Props.create(BackendWorker.class)), "backendRouter");

  /**
   * Forwarding the Messages through a Router
   */
  @Override
  public void onReceive(Object message) throws Exception {
    if (message instanceof StringMessage) {
      backendRouter.forward(message, getContext());
    } else {
      unhandled(message);
    }

  }

}
