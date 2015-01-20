package com.emnify.akkacluster.backend;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Status;
import akka.testkit.JavaTestKit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.CORBA.INVALID_TRANSACTION;

/**
 * @author nikos
 *
 */
public class BackendWorkerTest {
  static ActorSystem system;

  /**
   * setup before every testcase
   */
  @BeforeClass
  public static void setup() {
    system = ActorSystem.create("BackendWorkerTest");
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
   * simple String message -> returned as response
   */
  @Test
  public void PingPong() {
    new JavaTestKit(system) {
      {
        final ActorRef service = system.actorOf(Props.create(BackendWorker.class));
        final ActorRef probe = getRef();

        String msg = "test";
        service.tell(msg, probe);
        expectMsgEquals(msg);

      }
    };
  }

  /**
   * unhandled message type-> no response
   */
  @Test
  public void UnhandledMsg() {
    new JavaTestKit(system) {
      {
        final ActorRef service = system.actorOf(Props.create(BackendWorker.class));
        final ActorRef probe = getRef();

        service.tell(1, probe);
        expectNoMsg();
      }
    };
  }

  /**
   * Invalid message Type -> Status.Failure response
   */
  @Test
  public void FailedStatus() {
    new JavaTestKit(system) {
      {
        final ActorRef service = system.actorOf(Props.create(BackendWorker.class));
        final ActorRef probe = getRef();

        service.tell(new INVALID_TRANSACTION(), probe);
        expectMsgClass(Status.Failure.class);
      }
    };
  }
}
