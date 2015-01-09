package org.sidoh.reactor_simulator.optimizer;

import org.sidoh.reactor_simulator.simulator.BigReactorSimulator;

public class HillClimber implements SearchAlgorithm {
  @Override
  public String findOptimizedReactor(int x, int z, int height, String seed) {
    final Evaluator evaluator = new Evaluator(x, z, height);

    String bestLayout = seed;
    double bestEval = evaluator.evaluate(bestLayout);

    for (int i = 0; i < 1000; i++) {
      for (String neighbor : LayoutNeighborhood.of(bestLayout)) {
        double neighborEval = evaluator.evaluate(neighbor);

        if (neighborEval > bestEval) {
          bestLayout = neighbor;
          bestEval = neighborEval;

          System.out.println(bestEval);
          System.out.println(bestLayout);
        }
      }
    }

    return bestLayout;
  }

  public static void main(String[] args) {
    BigReactorSimulator.init();
    HillClimber c = new HillClimber();
    c.findOptimizedReactor(13, 13, 13, "EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEXEEEEEEEEEXXXEEEEEEECCCCCEEEEEEEXXXEEEEEEEEEXEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
  }
}
