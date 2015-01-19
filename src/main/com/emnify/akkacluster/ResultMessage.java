package com.emnify.akkacluster;

import java.io.Serializable;

public class ResultMessage implements Serializable {

  private final String answerString;
  private final Double senderCounterValue;

  public ResultMessage(String answerString, Double CounterValue) {
    this.answerString = answerString;
    this.senderCounterValue = CounterValue;
  }

  public String getAnswer() {
    return this.answerString;
  }

  public Double getSenderCounterValue() {
    return this.senderCounterValue;
  }
}
