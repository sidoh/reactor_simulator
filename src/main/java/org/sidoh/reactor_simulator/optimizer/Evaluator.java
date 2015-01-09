package org.sidoh.reactor_simulator.optimizer;

import org.sidoh.reactor_simulator.simulator.BigReactorSimulator;
import org.sidoh.reactor_simulator.simulator.FakeReactorWorld;
import org.sidoh.reactor_simulator.simulator.ReactorResult;

final class Evaluator {
  private final int x;
  private final int z;
  private final int height;

  Evaluator(int x, int z, int height) {
    this.x = x;
    this.z = z;
    this.height = height;
  }

  double evaluate(String layout) {
    FakeReactorWorld fakeReactorWorld = FakeReactorWorld.makeReactor(layout, x, z, height, (short)0);
    BigReactorSimulator simulator = new BigReactorSimulator(false, 10000);
    ReactorResult result = simulator.simulate(fakeReactorWorld);
    return result.output / result.fuelConsumption;
  }
}
