package org.sidoh.reactor_simulator.simulator.monitors;

import org.sidoh.reactor_simulator.simulator.MultiblockReactorSimulator;
import org.sidoh.reactor_simulator.simulator.ReactorResult;
import org.sidoh.reactor_simulator.util.TimeSeriesMonitor;

public class OutputMonitor extends TimeSeriesSimulationMonitor {
  @Override
  public double extractValue(MultiblockReactorSimulator simulator) {
    return simulator.getEnergyGeneratedLastTick();
  }

  @Override
  public void report(TimeSeriesMonitor monitor, ReactorResult result) {
    result.setOutput(monitor.getLastMean());
  }
}
