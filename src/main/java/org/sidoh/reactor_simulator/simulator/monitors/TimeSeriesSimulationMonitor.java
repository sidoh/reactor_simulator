package org.sidoh.reactor_simulator.simulator.monitors;

import org.sidoh.reactor_simulator.simulator.MultiblockReactorSimulator;
import org.sidoh.reactor_simulator.simulator.ReactorResult;
import org.sidoh.reactor_simulator.simulator.SimulationMonitor;
import org.sidoh.reactor_simulator.util.TimeSeriesMonitor;

public abstract class TimeSeriesSimulationMonitor implements SimulationMonitor {

  public static class Factory<T extends TimeSeriesSimulationMonitor> implements SimulationMonitor.Factory {
    private final Class<T> klass;

    public Factory(Class<T> klass) {
      this.klass = klass;
    }

    @Override
    public T create() {
      try {
        return klass.newInstance();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    public static <T extends TimeSeriesSimulationMonitor> Factory<T> factoryOf(Class<T> klass) {
      return new Factory<>(klass);
    }
  }

  private final TimeSeriesMonitor monitor;

  public TimeSeriesSimulationMonitor() {
    this.monitor = new TimeSeriesMonitor(50);
  }

  @Override
  public void offer(MultiblockReactorSimulator simulator) {
    monitor.offer(extractValue(simulator));
  }

  @Override
  public void report(ReactorResult result) {
    report(monitor, result);
  }

  public abstract double extractValue(MultiblockReactorSimulator simulator);

  public abstract void report(TimeSeriesMonitor monitor, ReactorResult result);
}
