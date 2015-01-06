package org.sidoh.reactor_simulator.simulator;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import restx.security.RestxPrincipal;

public class ReactorDefinition implements Serializable, RestxPrincipal {
  private int xSize;
  private int zSize;
  private int height;
  private String layout;
  private boolean isActivelyCooled;
  private short controlRodInsertion;

  public ReactorDefinition(int xSize, int zSize, int height, String layout, boolean isActivelyCooled, short controlRodInsertion) {
    this.xSize = xSize;
    this.zSize = zSize;
    this.height = height;
    this.layout = layout;
    this.isActivelyCooled = isActivelyCooled;
    this.controlRodInsertion = controlRodInsertion;
  }

  public ReactorDefinition() {
  }

  public ReactorDefinition(String json) {
    ReactorDefinition reactorDefinition = new Gson().fromJson(json, getClass());
    xSize = reactorDefinition.xSize;
    zSize = reactorDefinition.zSize;
    height = reactorDefinition.height;
    layout = reactorDefinition.layout;
    isActivelyCooled = reactorDefinition.isActivelyCooled;
    controlRodInsertion = reactorDefinition.controlRodInsertion;
  }

  public void setActivelyCooled(boolean isActivelyCooled) {
    this.isActivelyCooled = isActivelyCooled;
  }

  public void setLayout(String layout) {
    this.layout = layout;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public void setzSize(int zSize) {
    this.zSize = zSize;
  }

  public void setxSize(int xSize) {
    this.xSize = xSize;
  }

  public int getxSize() {
    return xSize;
  }

  public int getzSize() {
    return zSize;
  }

  public int getHeight() {
    return height;
  }

  public String getLayout() {
    return layout;
  }

  public boolean isActivelyCooled() {
    return isActivelyCooled;
  }

  public short getControlRodInsertion() {
    return controlRodInsertion;
  }

  public void setControlRodInsertion(short controlRodInsertion) {
    this.controlRodInsertion = controlRodInsertion;
  }

  @JsonIgnore
  @Override
  public ImmutableSet<String> getPrincipalRoles() {
    return null;
  }

  @JsonIgnore
  @Override
  public String getName() {
    return "definition";
  }
}
