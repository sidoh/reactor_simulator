package org.sidoh.reactor_simulator.simulator;

import erogenousbeef.bigreactors.common.BigReactors;
import erogenousbeef.bigreactors.common.multiblock.helpers.CoolantContainer;
import net.minecraftforge.fluids.Fluid;

public class CoolantContainerSimulator extends CoolantContainer {
  public CoolantContainerSimulator() {
    super();
  }

  @Override
  public Fluid getCoolantType() {
    return new Fluid("water");
  }

  @Override
  public Fluid getVaporType() {
    return BigReactors.fluidSteam;
  }

  @Override
  protected int getFluidAmount(int idx) {
    if (idx == COLD) {
      return Integer.MAX_VALUE;
    } else {
      return super.getFluidAmount(idx);
    }
  }

  @Override
  protected int addFluidToStack(int idx, int fluidAmount) {
    return 0;
  }
}
