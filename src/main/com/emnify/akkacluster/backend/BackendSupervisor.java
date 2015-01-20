package com.emnify.akkacluster.backend;

import com.emnify.akkacluster.StringMessage;

import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy.Directive;
import akka.actor.UntypedActor;
import akka.japi.Function;
import akka.routing.FromConfig;
import scala.concurrent.duration.Duration;

public class BackendSupervisor extends UntypedActor {

  ActorRef backendRouter = getContext().actorOf(
      FromConfig.getInstance().props(Props.create(BackendWorker.class)), "backendRouter");

  private static SupervisorStrategy strategy = new OneForOneStrategy(10,
      Duration.create("1 minute"), new Function<Throwable, Directive>() {
        @Override
        public Directive apply(Throwable t) {
          if (t instanceof ArithmeticException) {
            return akka.actor.SupervisorStrategy.resume();
          } else if (t instanceof NullPointerException) {
            return akka.actor.SupervisorStrategy.restart();
          } else if (t instanceof IllegalArgumentException) {
            return akka.actor.SupervisorStrategy.stop();
          } else {
            return akka.actor.SupervisorStrategy.escalate();
          }
        }
      });

  @Override
  public SupervisorStrategy supervisorStrategy() {
    return strategy;
  }



  @Override
  public void onReceive(Object message) throws Exception {
    if (message instanceof StringMessage) {
      backendRouter.forward(message, getContext());
    } else {
      unhandled(message);
    }

  }

}
