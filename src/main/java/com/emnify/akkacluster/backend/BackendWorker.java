package com.emnify.akkacluster.backend;

import com.emnify.akkacluster.ResultMessage;
import com.emnify.akkacluster.StringMessage;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * @author
 *
 */
public class BackendWorker extends UntypedActor {

  private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
  private Long messageCounter = 0L;

  /**
   * If StringMessage: Message logging and returning a new String with the increased Worker-intern
   * messageCounter
   *
   * @param message
   * @throws Exception
   */
  @Override
  public final void onReceive(final Object message) throws Exception {
    if (message instanceof StringMessage) {
      StringMessage stringMessage = (StringMessage) message;
      messageCounter++;
      log.info("Received Message Number " + messageCounter + " was: " + stringMessage.getMessage()
          + " from: " + getSender());
      ResultMessage resultMessage = new ResultMessage("Got it!", messageCounter);
      getSender().tell(resultMessage, getSelf());
    } else {
      unhandled(message);
    }
  }
}
