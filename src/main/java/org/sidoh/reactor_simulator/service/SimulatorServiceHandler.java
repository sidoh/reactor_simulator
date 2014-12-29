package org.sidoh.reactor_simulator.service;

import erogenousbeef.bigreactors.simulator.BigReactorSimulator;
import erogenousbeef.bigreactors.simulator.FakeReactorWorld;
import org.apache.thrift.TException;
import org.sidoh.reactor_simulator.thrift.ReactorDefinition;
import org.sidoh.reactor_simulator.thrift.ReactorResult;
import org.sidoh.reactor_simulator.thrift.SimulatorService;

public class SimulatorServiceHandler implements SimulatorService.Iface {
  private static final int MAX_NUMBER_OF_TICKS = 10000;

  @Override
  public ReactorResult simulateReactor(ReactorDefinition definition) throws TException {
    BigReactorSimulator simulator = new BigReactorSimulator(
        definition.isIsActivelyCooled(),
        MAX_NUMBER_OF_TICKS
    );
    FakeReactorWorld fakeReactorWorld = FakeReactorWorld.makeReactor(
        definition.getLayout(),
        definition.getXSize(),
        definition.getZSize(),
        definition.getHeight()
    );
    BigReactorSimulator.ReactorResult rawResult = simulator.simulate(fakeReactorWorld);

    return new ReactorResult()
        .setCoolantTemperature(rawResult.coolantTemperature)
        .setEfficiency(rawResult.efficiency)
        .setFuelFertility(rawResult.fuelFertility)
        .setFuelHeat(rawResult.fuelHeat)
        .setOutput(rawResult.output)
        .setReactorDefinition(definition)
        .setReactorHeat(rawResult.reactorHeat);
  }
}
