package com.emnify.akkacluster;

import java.io.Serializable;

/**
 * This class holds the message to be processed
 *
 * @author
 * @see BackendWorker
 */
public class StringMessage implements Serializable {

  private static final long serialVersionUID = -3282734986035480356L;
  private final String messageString;

  /**
   * @param messageString
   */
  public StringMessage(String messageString) {
    this.messageString = messageString;
  }

  /**
   * @return messageString
   */
  public String getMessage() {
    return messageString;
  }
}
