package org.sidoh.reactor_simulator.service;

import org.sidoh.reactor_simulator.simulator.BigReactorSimulator;
import org.sidoh.reactor_simulator.simulator.FakeReactorWorld;
import org.sidoh.reactor_simulator.simulator.LinearControlRodOptimizer;
import org.sidoh.reactor_simulator.simulator.ReactorDefinition;
import org.sidoh.reactor_simulator.simulator.ReactorResult;
import org.sidoh.reactor_simulator.simulator.ResultMetrics;
import restx.annotations.GET;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;

import static com.google.common.base.Preconditions.checkArgument;

@Component
@RestxResource
public class SimulatorResource {
  private static final int MAX_NUMBER_OF_TICKS = 100000;

  @PermitAll
  @GET("/simulate")
  public ReactorResult simulate(ReactorDefinition definition) {
    validateReactorDefinition(definition);

    BigReactorSimulator simulator = new BigReactorSimulator(
        definition.isActivelyCooled(),
        MAX_NUMBER_OF_TICKS
    );
    FakeReactorWorld fakeReactorWorld = FakeReactorWorld.makeReactor(
        definition.getLayout(),
        definition.getxSize(),
        definition.getzSize(),
        definition.getHeight(),
        definition.getControlRodInsertion()
    );
    ReactorResult rawResult = simulator.simulate(fakeReactorWorld);

    return new ReactorResult()
        .setCoolantTemperature(rawResult.coolantTemperature)
        .setFuelConsumption(rawResult.fuelConsumption)
        // for display purposes
        .setFuelFertility(rawResult.fuelFertility * 100)
        .setFuelHeat(rawResult.fuelHeat)
        .setOutput(rawResult.output)
        .setReactorDefinition(definition)
        .setReactorHeat(rawResult.reactorHeat);
  }

  @PermitAll
  @GET("/optimize_insertion")
  public short optimizeInsertion(ReactorDefinition definition) {
    validateReactorDefinition(definition);

    BigReactorSimulator simulator = new BigReactorSimulator(
        definition.isActivelyCooled(),
        MAX_NUMBER_OF_TICKS
    );

    return new LinearControlRodOptimizer(
        ResultMetrics.efficiency(),
        simulator
    ).optimizeInsertion(definition);
  }

  private static void validateReactorDefinition(ReactorDefinition reactorDefinition) {
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
