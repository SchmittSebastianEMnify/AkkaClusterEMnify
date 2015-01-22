package com.emnify.akkacluster.frontend;

import com.emnify.akkacluster.ResultMessage;
import com.emnify.akkacluster.StringMessage;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UnhandledMessage;
import akka.testkit.JavaTestKit;
import org.junit.After;
import org.junit.Before;
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
  @Before
  public void setup() {
    fsystem = ActorSystem.create("ClusterSystem", ConfigFactory.load("frontend"));
    bsystem = ActorSystem.create("ClusterSystem", ConfigFactory.load("backend"));
  }

  /**
   * tear down after every test case
   */
  @After
  public void tearDown() {
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
        fsystem.eventStream().subscribe(getRef(), UnhandledMessage.class);

        // FrontendActor expects a StringMessage, simple strings are not handled
        service.tell("unhandled test", probe);
        expectMsgClass(UnhandledMessage.class);
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
        System.out.println(service);
        fsystem.eventStream().subscribe(getRef(), UnhandledMessage.class);
        service.tell(new StringMessage("normal test"), probe);
        service.tell(new ResultMessage("Got it!", 1L), probe);
        expectNoMsg();
      }
    };
  }
}
