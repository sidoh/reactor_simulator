package org.sidoh.reactor_simulator.service;

import com.google.common.base.Optional;
import org.sidoh.reactor_simulator.simulator.BigReactorSimulator;
import restx.server.JettyWebServer;
import restx.server.WebServer;

public class SimulatorServer {
  public static final String WEB_INF_LOCATION = "src/main/webapp/WEB-INF/web.xml";
  public static final String WEB_APP_LOCATION = "src/main/webapp";

  public static void main(String[] args) throws Exception {
    int port = Integer.valueOf(Optional.fromNullable(System.getenv("PORT")).or("8081"));
    WebServer server = new JettyWebServer(WEB_INF_LOCATION, WEB_APP_LOCATION, port, "0.0.0.0");

    System.setProperty("restx.mode", "prod");
    System.setProperty("restx.app.package", "hello");

    BigReactorSimulator.init();
    server.startAndAwait();
  }
}
