package org.sidoh.reactor_simulator.simulator;

public interface SimulationMonitor {
  public interface Factory {
    public SimulationMonitor create();
  }

  public void offer(MultiblockReactorSimulator simulator);

  public void report(ReactorResult result);
}
