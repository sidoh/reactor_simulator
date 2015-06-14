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
    double max = Helpers.measure(bigReactorSimulator, resultMetric, reactorDefinition, (short)99);
    short bestInsertion = 99;

    for (short insertion = 98; insertion >= 0; insertion--) {
      final double value = Helpers.measure(
          bigReactorSimulator, resultMetric, reactorDefinition, insertion
      );

      if (value > max) {
        max = value;
        bestInsertion = insertion;
      } else {
        break;
      }
    }

    return bestInsertion;
  }
}
