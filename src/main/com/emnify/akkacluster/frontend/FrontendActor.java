package com.emnify.akkacluster.frontend;

import static com.emnify.akkacluster.ResultMessage.BACKEND_REGISTRATION;
import akka.actor.ActorRef;
import akka.actor.Terminated;
import akka.actor.UntypedActor;

import com.emnify.akkacluster.MsgFailed;
import com.emnify.akkacluster.StringMessage;

import java.util.ArrayList;
import java.util.List;


/**
 * @author nikos
 *
 */
public class FrontendActor extends UntypedActor {

  List<ActorRef> backends = new ArrayList<ActorRef>();
  int jobCounter = 0;

  @Override
  public void onReceive(Object message) {

    if (message instanceof StringMessage) {
      StringMessage job = (StringMessage) message;
      if (backends.isEmpty()) {
        getSender().tell(new MsgFailed("Service unavailable, try again later", job), getSender());
      } else {
        jobCounter++;
        backends.get(jobCounter % backends.size()).forward(job, getContext());
      }

    } else if (message.equals(BACKEND_REGISTRATION)) {
      getContext().watch(getSender());
      backends.add(getSender());

    } else if (message instanceof Terminated) {
      Terminated terminated = (Terminated) message;
      backends.remove(terminated.getActor());

    } else {
      unhandled(message);
    }
  }

}