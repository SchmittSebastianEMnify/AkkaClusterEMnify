package com.emnify.akkacluster;

import java.io.Serializable;

public class StringMessage implements Serializable {

  private String messageString;

  public StringMessage(String messageString) {
    this.messageString = messageString;
  }

  public String getMessage() {
    return this.messageString;
  }
}
