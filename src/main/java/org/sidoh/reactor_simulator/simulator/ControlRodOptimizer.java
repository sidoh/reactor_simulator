package org.sidoh.reactor_simulator.simulator;

public interface ControlRodOptimizer {
  public static class Helpers {
    public static double measure(BigReactorSimulator bigReactorSimulator,
                                 ResultMetric resultMetric,
                                 ReactorDefinition reactorDefinition,
                                 short insertion) {
      final FakeReactorWorld fakeReactorWorld = FakeReactorWorld.makeReactor(
          reactorDefinition.getLayout(),
          reactorDefinition.getxSize(),
          reactorDefinition.getzSize(),
          reactorDefinition.getHeight(),
          insertion
      );

      return resultMetric.measure(bigReactorSimulator.simulate(fakeReactorWorld));
    }
  }

  short optimizeInsertion(ReactorDefinition reactorDefinition);
}
