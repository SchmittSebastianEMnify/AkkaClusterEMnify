package com.emnify.akkacluster;

import java.io.Serializable;

public class ResultMessage implements Serializable {

  private final String answerString;
  private final Long senderCounterValue;

  public ResultMessage(String answerString, Long CounterValue) {
    this.answerString = answerString;
    this.senderCounterValue = CounterValue;
  }

  public String getAnswer() {
    return this.answerString;
  }

  public Long getSenderCounterValue() {
    return this.senderCounterValue;
  }
}
