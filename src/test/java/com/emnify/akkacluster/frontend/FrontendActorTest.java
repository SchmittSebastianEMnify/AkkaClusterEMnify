package com.emnify.akkacluster.frontend;

import com.emnify.akkacluster.ResultMessage;
import com.emnify.akkacluster.StringMessage;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author nikos
 * @see FrontendActor
 */
public class FrontendActorTest {
  static ActorSystem fsystem;
  static ActorSystem bsystem;

  /**
   * setup before every test case
   */
  @BeforeClass
  public static void setup() {
    fsystem = ActorSystem.create("ClusterSystem", ConfigFactory.load("frontend"));
    bsystem = ActorSystem.create("ClusterSystem", ConfigFactory.load("backend"));
  }

  /**
   * tear down after every test case
   */
  @AfterClass
  public static void tearDown() {
    JavaTestKit.shutdownActorSystem(fsystem);
    fsystem = null;
    JavaTestKit.shutdownActorSystem(bsystem);
    bsystem = null;
  }



  /**
   * unhandled message type
   */
  @Test
  public void testUnhandledMsg() {
    new JavaTestKit(fsystem) {
      {
        final ActorRef service = fsystem.actorOf(Props.create(FrontendActor.class), "frontend");
        final ActorRef probe = getRef();
        // FrontendActor expects a StringMessage, simple strings are not handled
        service.tell("unhandled test", probe);
        expectNoMsg();
      }
    };
  }

  /**
   * Normal flow, send a StringMessage and a ResultMessage
   */
  @Test
  public void testNormalFlow() {

    new JavaTestKit(fsystem) {
      {
        final ActorRef service = fsystem.actorOf(Props.create(FrontendActor.class), "frontend");
        final ActorRef probe = getRef();
        service.tell(new StringMessage("normal test"), probe);
        service.tell(new ResultMessage("Got it!", 1L), probe);
        expectNoMsg();
      }
    };
  }
}
