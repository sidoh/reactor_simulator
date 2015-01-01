package org.sidoh.reactor_simulator.simulator;

import java.io.Serializable;

public class ReactorResult implements Serializable {
  public double fuelConsumption;
  public double output;
  public float fuelFertility;
  public float coolantTemperature;
  public float fuelHeat;
  public float reactorHeat;
  public ReactorDefinition reactorDefinition;

  public ReactorResult(double fuelConsumption, double output, float fuelFertility, float coolantTemperature, float fuelHeat, float reactorHeat) {
    this.fuelConsumption = fuelConsumption;
    this.output = output;
    this.fuelFertility = fuelFertility;
    this.coolantTemperature = coolantTemperature;
    this.fuelHeat = fuelHeat;
    this.reactorHeat = reactorHeat;
  }

  public ReactorResult() {
  }

  public ReactorResult setFuelConsumption(double fuelConsumption) {
    this.fuelConsumption = fuelConsumption;
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
        "fuelConsumption=" + fuelConsumption +
        ", output=" + output +
        ", fuelFertility=" + fuelFertility +
        ", coolantTemperature=" + coolantTemperature +
        ", fuelHeat=" + fuelHeat +
        ", reactorHeat=" + reactorHeat +
        ", reactorDefinition=" + reactorDefinition +
        '}';
  }
}
