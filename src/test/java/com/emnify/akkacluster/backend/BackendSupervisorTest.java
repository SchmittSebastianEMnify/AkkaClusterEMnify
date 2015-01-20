package com.emnify.akkacluster.backend;

import com.emnify.akkacluster.ResultMessage;
import com.emnify.akkacluster.StringMessage;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

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
public class BackendSupervisorTest {
  static ActorSystem system;

  /**
   * setup before every testcase
   */
  @BeforeClass
  public static void setup() {
    Config config = ConfigFactory.load("backend");
    system = ActorSystem.create("clusterSystem", config);
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
        final ActorRef service =
            system.actorOf(Props.create(BackendSupervisor.class), "backendSupervisor");
        final ActorRef probe = getRef();

        StringMessage msg = new StringMessage("ok");
        service.tell(msg, probe);
        expectMsgClass(ResultMessage.class);
        service.tell(msg, probe);
        expectMsgClass(ResultMessage.class);
        service.tell(msg, probe);
        expectMsgClass(ResultMessage.class);
        service.tell(msg, probe);
        expectMsgClass(ResultMessage.class);
        service.tell(msg, probe);
        expectMsgClass(ResultMessage.class);
        service.tell(msg, probe);
        expectMsgClass(ResultMessage.class);
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
