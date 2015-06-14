package org.sidoh.reactor_simulator.service;

import org.sidoh.reactor_simulator.simulator.BigReactorSimulator;
import org.sidoh.reactor_simulator.simulator.LinearControlRodOptimizer;
import org.sidoh.reactor_simulator.simulator.ReactorDefinition;
import org.sidoh.reactor_simulator.simulator.ResultMetrics;
import restx.annotations.GET;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;

@Component
@RestxResource
public class OptimizeInsertionResource {
  @PermitAll
  @GET("/optimize_insertion")
  public Short optimizeInsertion(ReactorDefinition definition) {
    SimulatorServer.validateReactorDefinition(definition);

    BigReactorSimulator simulator = new BigReactorSimulator(
        definition.isActivelyCooled(),
        SimulatorServer.MAX_NUMBER_OF_TICKS
    );

    return new LinearControlRodOptimizer(
        ResultMetrics.efficiency(),
        simulator
    ).optimizeInsertion(definition);
  }
}
