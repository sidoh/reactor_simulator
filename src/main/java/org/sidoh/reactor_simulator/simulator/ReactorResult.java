package org.sidoh.reactor_simulator.simulator;

import java.io.Serializable;

public class ReactorResult implements Serializable {
  private double fuelConsuption;
  public double output;
  public float fuelFertility;
  public float coolantTemperature;
  public float fuelHeat;
  public float reactorHeat;
  public ReactorDefinition reactorDefinition;

  public ReactorResult(double fuelConsuption, double output, float fuelFertility, float coolantTemperature, float fuelHeat, float reactorHeat) {
    this.fuelConsuption = fuelConsuption;
    this.output = output;
    this.fuelFertility = fuelFertility;
    this.coolantTemperature = coolantTemperature;
    this.fuelHeat = fuelHeat;
    this.reactorHeat = reactorHeat;
  }

  public ReactorResult() {
  }

  public ReactorResult setFuelConsuption(double fuelConsuption) {
    this.fuelConsuption = fuelConsuption;
    return this;
  }

  public ReactorResult setReactorDefinition(ReactorDefinition reactorDefinition) {
    this.reactorDefinition = reactorDefinition;
    return this;
  }

  public ReactorResult setOutput(double output) {
    this.output = output;
    return this;
  }

  public ReactorResult setFuelFertility(float fuelFertility) {
    this.fuelFertility = fuelFertility;
    return this;
  }

  public ReactorResult setCoolantTemperature(float coolantTemperature) {
    this.coolantTemperature = coolantTemperature;
    return this;
  }

  public ReactorResult setFuelHeat(float fuelHeat) {
    this.fuelHeat = fuelHeat;
    return this;
  }

  public ReactorResult setReactorHeat(float reactorHeat) {
    this.reactorHeat = reactorHeat;
    return this;
  }

  @Override
  public String toString() {
    return "ReactorResult{" +
        "fuelConsuption=" + fuelConsuption +
        ", output=" + output +
        ", fuelFertility=" + fuelFertility +
        ", coolantTemperature=" + coolantTemperature +
        ", fuelHeat=" + fuelHeat +
        ", reactorHeat=" + reactorHeat +
        ", reactorDefinition=" + reactorDefinition +
        '}';
  }
}
