package com.emnify.akkacluster;

import java.io.Serializable;

/**
 * This class holds the message and reason for processing failure
 *
 * @author
 * @see FrontendActor
 */
public class MsgFailed implements Serializable {

  private static final long serialVersionUID = -3193674057359132267L;
  private final String reason;
  private final StringMessage msg;

  /**
   * @param reason
   * @param msg
   */
  public MsgFailed(String reason, StringMessage msg) {
    this.reason = reason;
    this.msg = msg;
  }

  /**
   * @return reason
   */
  public String getReason() {
    return reason;
  }

  /**
   * @return msg
   */
  public StringMessage getMsg() {
    return msg;
  }

  @Override
  public String toString() {
    return "msgFailed(" + reason + ")";
  }
}

