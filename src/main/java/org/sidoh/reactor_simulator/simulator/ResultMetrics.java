package org.sidoh.reactor_simulator.simulator;

public class ResultMetrics {
  private ResultMetrics() { }

  public static ResultMetric output() {
    return new OutputMetric();
  }

  public static ResultMetric efficiency() {
    return new EfficiencyMetric();
  }

  private static class OutputMetric implements ResultMetric {
    @Override
    public double measure(ReactorResult result) {
      return result.output;
    }
  }

  private static class EfficiencyMetric implements ResultMetric {
    @Override
    public double measure(ReactorResult result) {
      return result.output / result.fuelConsumption;
    }
  }
}
