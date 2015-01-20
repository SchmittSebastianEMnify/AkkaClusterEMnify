package com.emnify.akkacluster.frontend;

import com.emnify.akkacluster.StringMessage;

import static com.emnify.akkacluster.ResultMessage.BACKEND_REGISTRATION;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Status;
import akka.testkit.JavaTestKit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author nikos
 * @see FrontendActor
 */
public class FrontendActorTest {
  static ActorSystem system;

  /**
   * setup before every testcase
   */
  @BeforeClass
  public static void setup() {
    system = ActorSystem.create("FrontendActorTest");
  }

  /**
   * tear down after every testcase
   */
  @AfterClass
  public static void tearDown() {
    JavaTestKit.shutdownActorSystem(system);
    system = null;
  }

  /**
   * no backend available -> Status.Failure response
   */
  @Test
  public void testNoBackendFailure() {
    new JavaTestKit(system) {
      {
        final ActorRef service = system.actorOf(Props.create(FrontendActor.class));
        final ActorRef probe = getRef();

        StringMessage msg = new StringMessage("test");
        service.tell(msg, probe);
        expectMsgClass(Status.Failure.class);

      }
    };
  }

  /**
   * unhandled message type-> no response
   */
  @Test
  public void testUnhandledMsg() {
    new JavaTestKit(system) {
      {
        final ActorRef service = system.actorOf(Props.create(FrontendActor.class));
        final ActorRef probe = getRef();
        // FrontendActor expects a StringMessage, simple strings are not handled
        service.tell("test", probe);
        expectNoMsg();
      }
    };
  }

  /**
   * Register backend -> sending the StringMessage completes successfully
   */
  @Test
  public void testNormalFlow() {

    new JavaTestKit(system) {
      {
        final ActorRef service = system.actorOf(Props.create(FrontendActor.class));
        final ActorRef probe = getRef();

        // register a backend
        service.tell(BACKEND_REGISTRATION, probe);

        // send message successfully
        StringMessage msg = new StringMessage("test");
        service.tell(msg, probe);
        expectMsgEquals(msg);
      }
    };
  }
}
