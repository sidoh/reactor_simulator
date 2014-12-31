package org.sidoh.reactor_simulator.service;

import org.sidoh.reactor_simulator.simulator.BigReactorSimulator;
import org.sidoh.reactor_simulator.simulator.FakeReactorWorld;
import org.sidoh.reactor_simulator.simulator.ReactorDefinition;
import org.sidoh.reactor_simulator.simulator.ReactorResult;
import restx.annotations.GET;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;

@Component
@RestxResource
public class SimulatorResource {
  private static final int MAX_NUMBER_OF_TICKS = 10000;

  @PermitAll
  @GET("/simulate")
  public ReactorResult simulate(ReactorDefinition definition) {
    BigReactorSimulator simulator = new BigReactorSimulator(
        definition.isActivelyCooled(),
        MAX_NUMBER_OF_TICKS
    );
    FakeReactorWorld fakeReactorWorld = FakeReactorWorld.makeReactor(
        definition.getLayout(),
        definition.getxSize(),
        definition.getzSize(),
        definition.getHeight()
    );
    ReactorResult rawResult = simulator.simulate(fakeReactorWorld);

    return new ReactorResult()
        .setCoolantTemperature(rawResult.coolantTemperature)
        .setEfficiency(rawResult.efficiency)
        .setFuelFertility(rawResult.fuelFertility)
        .setFuelHeat(rawResult.fuelHeat)
        .setOutput(rawResult.output)
        .setReactorDefinition(definition)
        .setReactorHeat(rawResult.reactorHeat);
  }

  private static void validateReactorDefinition(ReactorDefinition reactorDefinition) {

  }
}
