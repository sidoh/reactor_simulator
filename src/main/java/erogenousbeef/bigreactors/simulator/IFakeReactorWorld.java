package erogenousbeef.bigreactors.simulator;

import java.util.List;

import erogenousbeef.core.common.CoordTriplet;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public interface IFakeReactorWorld {

  public TileEntity getTileEntity(int x, int y, int z);

  boolean isAirBlock(int x, int y, int z);

  String getBlockName(int x, int y, int z);

  CoordTriplet getMaxCoord();

  CoordTriplet getMinCoord();

  List<TileEntity> getParts();

  int getNumRods();

  String display();
}
