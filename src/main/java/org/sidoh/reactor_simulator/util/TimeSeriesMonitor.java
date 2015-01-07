package org.sidoh.reactor_simulator.util;

import java.util.Deque;

import com.google.common.collect.Lists;

public class TimeSeriesMonitor {
  private final int windowSize;
  private final Deque<Double> window;

  private double mean;
  private double m2;

  public TimeSeriesMonitor(int windowSize) {
    this.windowSize = windowSize;
    this.mean = 0;
    this.window = Lists.newLinkedList();
  }

  public void offer(final double value) {
    if (window.size() > windowSize) {
      final double evictedValue = window.removeFirst();
      final double evictedDelta = evictedValue - mean;

      mean -= (evictedDelta / window.size());
      m2 -= evictedDelta * (evictedValue - mean);
    }

    window.addLast(value);

    final double delta = value - mean;
    mean += (delta / window.size());
    m2 += delta*(value - mean);
  }

  public double getLastStandardDeviation() {
    return Math.sqrt(getLastVariance());
  }

  public double getLastVariance() {
    return m2/window.size();
  }

  public double getLastMean() {
    return mean;
  }

  public int getWindowSize() {
    return window.size();
  }

  public int getMaxWindowSize() {
    return windowSize;
  }
}
