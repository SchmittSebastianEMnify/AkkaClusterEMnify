package com.emnify.akkacluster.frontend;

import static akka.pattern.Patterns.ask;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.OnSuccess;
import akka.util.Timeout;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.emnify.akkacluster.StringMessage;

/**
 * @author akka-sample-cluster-java example from Typesafe
 *
 */
public class FrontendMain {

  /**
   * @param args
   */
  public static void main(String[] args) {
    // Override the configuration of the port when specified as program argument
    final String port = args.length > 0 ? args[0] : "2551";
    final Config config =
        ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port)
        .withFallback(ConfigFactory.parseString("akka.cluster.roles = [frontend]"))
        .withFallback(ConfigFactory.load());

    ActorSystem system = ActorSystem.create("ClusterSystem", config);

    final ActorRef frontend = system.actorOf(Props.create(StringMessage.class), "frontend");
    final FiniteDuration interval = Duration.create(2, TimeUnit.SECONDS);
    final Timeout timeout = new Timeout(Duration.create(5, TimeUnit.SECONDS));
    final ExecutionContext ec = system.dispatcher();
    final AtomicInteger counter = new AtomicInteger();
    system.scheduler().schedule(
        interval,
        interval,
        () -> ask(frontend, new StringMessage("hello-" + counter.incrementAndGet()), timeout)
        .onSuccess(new OnSuccess<Object>() {
          @Override
          public void onSuccess(Object result) {
            System.out.println(result);
          }
        }, ec), ec);

  }

}
