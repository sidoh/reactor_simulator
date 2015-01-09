package org.sidoh.reactor_simulator.optimizer;

public class SimulatedAnnealing implements SearchAlgorithm {
  private final int kmax;

  public SimulatedAnnealing(int kmax) {
    this.kmax = kmax;
  }

  @Override
  public String findOptimizedReactor(int x, int z, int height, String seed) {
    final Evaluator evaluator = new Evaluator(x, z, height);

    String state = seed;
    double energy = evaluator.evaluate(state);
    int k = 0;

    while (k < kmax) {
      double t = (k / (double)kmax);

    }

    return state;
  }

  private double P(double oldE, double newE, double t) {
    if (newE > oldE) {
      return newE;
    } else {
      return Math.exp((newE - oldE) / t);
    }
  }
}
