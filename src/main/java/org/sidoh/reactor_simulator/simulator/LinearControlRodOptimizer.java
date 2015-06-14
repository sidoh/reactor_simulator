package org.sidoh.reactor_simulator.simulator;

public class LinearControlRodOptimizer implements ControlRodOptimizer {
  private final ResultMetric resultMetric;
  private final BigReactorSimulator bigReactorSimulator;

  public LinearControlRodOptimizer(ResultMetric resultMetric, BigReactorSimulator bigReactorSimulator) {
    this.resultMetric = resultMetric;
    this.bigReactorSimulator = bigReactorSimulator;
  }

  @Override
  public short optimizeInsertion(ReactorDefinition reactorDefinition) {
    double max = 0;
    short bestInsertion = 0;

    for (short insertion = 0; insertion <= 100; insertion++) {
      final double value = measure(reactorDefinition, insertion);
      if (value > max) {
        max = value;
        bestInsertion = insertion;
      }
    }

    return bestInsertion;
  }

  private double measure(ReactorDefinition reactorDefinition, short insertion) {
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
