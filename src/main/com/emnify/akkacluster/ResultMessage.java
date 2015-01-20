/**
 * Copyright (C) 2015 Emnify GmbH <http://www.emnify.com>
 */
package com.emnify.akkacluster;

import java.io.Serializable;

/**
 * This class holds the message and Counter resulting from backend processing
 *
 * @author
 * @see BackendWorker
 */
public class ResultMessage implements Serializable {

  private static final long serialVersionUID = 6666437439212966888L;
  private final String answerString;
  private final Double senderCounterValue;

  /** label for watching the workers */
  public static final String BACKEND_REGISTRATION = "BackendRegistration";

  /**
   * @param answerString
   * @param CounterValue
   */
  public ResultMessage(String answerString, Double CounterValue) {
    this.answerString = answerString;
    senderCounterValue = CounterValue;
  }

  /**
   * @return answerString
   */
  public String getAnswer() {
    return answerString;
  }

  /**
   * @return senderCounterValue
   */
  public Double getSenderCounterValue() {
    return senderCounterValue;
  }

  @Override
  public String toString() {
    return "ResultMessage(" + answerString + ", " + senderCounterValue + ")";
  }
}
