package com.emnify.akkacluster.frontend;

import com.emnify.akkacluster.ResultMessage;
import com.emnify.akkacluster.StringMessage;

import akka.actor.ActorRef;
import akka.actor.Status;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.FromConfig;


/**
 * @author nikos
 *
 */
public class FrontendActor extends UntypedActor {
  private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

  private ActorRef router = getContext().actorOf(FromConfig.getInstance().props(), "router");

  @Override
  public final void onReceive(final Object message) throws Exception {
    try {
      if (message instanceof StringMessage) {
        StringMessage job = (StringMessage) message;
        router.tell(job, getSelf());
      } else if (message instanceof ResultMessage) {
        ResultMessage result = (ResultMessage) message;
        log.info("Answer: " + result.getAnswer() + " - " + result.getSenderCounterValue() + " from: "
            + getSender());
      } else {
        unhandled(message);
      }
    } catch (Exception e) {
      getSender().tell(new Status.Failure(e), getSelf());
      throw e;
    }
  }
}
