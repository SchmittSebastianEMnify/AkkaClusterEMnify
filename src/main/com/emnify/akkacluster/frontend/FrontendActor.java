package com.emnify.akkacluster.frontend;

import static com.emnify.akkacluster.ResultMessage.BACKEND_REGISTRATION;
import akka.actor.ActorRef;
import akka.actor.Status;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import com.emnify.akkacluster.StringMessage;

import java.util.ArrayList;
import java.util.List;


/**
 * @author nikos
 *
 */
public class FrontendActor extends UntypedActor {
  LoggingAdapter log = Logging.getLogger(getContext().system(), this);

  List<ActorRef> backends = new ArrayList<ActorRef>();
  int jobCounter = 0;

  @Override
  public void onReceive(Object message) throws Exception {
    try {
      if (message instanceof StringMessage) {
        StringMessage job = (StringMessage) message;
        if (backends.isEmpty()) {
          getSender().tell(
              new Status.Failure(new Exception("Service unavailable, try again later")),
              getSender());
        } else {
          jobCounter++;
          backends.get(jobCounter % backends.size()).forward(job, getContext());
        }
        //log.debug(job.getMessage());
      } else if (message.equals(BACKEND_REGISTRATION)) {
        getContext().watch(getSender());
        backends.add(getSender());

      } else if (message instanceof Terminated) {
        Terminated terminated = (Terminated) message;
        backends.remove(terminated.getActor());

      } else {
        unhandled(message);
      }
    } catch (Exception e) {
      getSender().tell(new Status.Failure(e), getSelf());
      throw e;
    }
  }

}