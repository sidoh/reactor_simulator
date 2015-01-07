package org.sidoh.reactor_simulator.simulator.monitors;

import org.sidoh.reactor_simulator.simulator.MultiblockReactorSimulator;
import org.sidoh.reactor_simulator.simulator.ReactorResult;
import org.sidoh.reactor_simulator.util.TimeSeriesMonitor;

public class FuelFertilityMonitor extends TimeSeriesSimulationMonitor {
  @Override
  public double extractValue(MultiblockReactorSimulator simulator) {
    return simulator.getFuelFertility();
  }

  @Override
  public void report(TimeSeriesMonitor monitor, ReactorResult result) {
    result.setFuelFertility((float)monitor.getLastMean());
  }
}
