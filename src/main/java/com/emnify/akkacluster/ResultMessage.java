/**
 * Copyright (C) 2015 Emnify GmbH <http://www.emnify.com>
 */
package com.emnify.akkacluster;

import com.emnify.akkacluster.backend.BackendWorker;

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
  private final Long senderCounterValue;


  /**
   * @param answerString
   * @param CounterValue
   */
  public ResultMessage(String answerString, Long counterValue) {
    this.answerString = answerString;
    senderCounterValue = counterValue;
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
  public Long getSenderCounterValue() {
    return senderCounterValue;
  }

  @Override
  public String toString() {
    return "ResultMessage(" + answerString + ", " + senderCounterValue + ")";
  }

}
