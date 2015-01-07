package org.sidoh.reactor_simulator.simulator.monitors;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;
import org.sidoh.reactor_simulator.simulator.SimulationMonitor;

public class MonitorUtils {
  private MonitorUtils() { }

  public static List<SimulationMonitor> instantiate(Collection<? extends SimulationMonitor.Factory> factories) {
    List<SimulationMonitor> monitors = Lists.newArrayList();
    for (SimulationMonitor.Factory factory : factories) {
      monitors.add(factory.create());
    }
    return monitors;
  }
}
