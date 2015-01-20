package com.emnify.akkacluster.backend;

import com.emnify.akkacluster.ResultMessage;
import com.emnify.akkacluster.StringMessage;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class BackendWorker extends UntypedActor {

  private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
  private Long MessageCounter = 0L;

  /**
   * If StringMessage: Message logging and returning a new String with the increased Worker-intern
   * MessageCounter
   */
  @Override
  public void onReceive(Object message) throws Exception {
    if (message instanceof StringMessage) {
      StringMessage stringMessage = (StringMessage) message;
      MessageCounter++;
      log.info("Received Message Number " + MessageCounter + " was: " + stringMessage.getMessage()
          + " from: " + getSender());
      ResultMessage resultMessage = new ResultMessage("Got it!", MessageCounter);
      getSender().tell(resultMessage, getSelf());
    } else {
      unhandled(message);
    }
  }
}
