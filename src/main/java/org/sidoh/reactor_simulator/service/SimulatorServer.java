package org.sidoh.reactor_simulator.service;

import com.google.common.base.Optional;
import org.sidoh.reactor_simulator.simulator.BigReactorSimulator;
import org.sidoh.reactor_simulator.simulator.ReactorDefinition;
import restx.server.JettyWebServer;
import restx.server.WebServer;

import static com.google.common.base.Preconditions.checkArgument;

public class SimulatorServer {
  public static final String WEB_INF_LOCATION = "src/main/webapp/WEB-INF/web.xml";
  public static final String WEB_APP_LOCATION = "src/main/webapp";
  public static final int MAX_NUMBER_OF_TICKS = 100000;


  public static void main(String[] args) throws Exception {
    int port = Integer.valueOf(Optional.fromNullable(System.getenv("PORT")).or("8081"));
    WebServer server = new JettyWebServer(WEB_INF_LOCATION, WEB_APP_LOCATION, port, "0.0.0.0");

    System.setProperty("restx.mode", "prod");
    System.setProperty("restx.app.package", "hello");

    BigReactorSimulator.init();
    server.startAndAwait();
  }

  public static void validateReactorDefinition(ReactorDefinition reactorDefinition) {
    checkArgument(reactorDefinition.getxSize() >= 3, "xSize should be at least 3");
    checkArgument(reactorDefinition.getzSize() >= 3, "zSize should be at least 3");
    checkArgument(reactorDefinition.getHeight() >= 3, "zSize should be at least 3");

    checkArgument(reactorDefinition.getxSize() <= 32, "xSize should be no larger than 32");
    checkArgument(reactorDefinition.getzSize() <= 32, "zSize should be no larger than 32");
    checkArgument(reactorDefinition.getHeight() <= 48, "height should be no larger than 48");

    checkArgument(reactorDefinition.getControlRodInsertion() <= 100, "insertion should be in the range 0-100");
    checkArgument(reactorDefinition.getControlRodInsertion() >= 0, "insertion should be in the range 0-100");

    final int layoutSize = (reactorDefinition.getxSize() - 2) * (reactorDefinition.getzSize() - 2);
    checkArgument(
        reactorDefinition.getLayout().length() == layoutSize,
        String.format(
            "layout size for a %dx%d reactor should be %d",
            reactorDefinition.getxSize(),
            reactorDefinition.getzSize(),
            layoutSize
        )
    );
  }
}
