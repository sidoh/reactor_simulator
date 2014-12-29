package erogenousbeef.bigreactors.simulator;

import java.util.List;

import com.google.common.collect.BiMap;
import com.google.common.collect.Lists;
import erogenousbeef.bigreactors.common.multiblock.tileentity.TileEntityReactorControlRod;
import erogenousbeef.bigreactors.simulator.nested_map.ThreeNestedMap;
import erogenousbeef.core.common.CoordTriplet;
import net.minecraft.tileentity.TileEntity;

public class FakeReactorWorld implements IFakeReactorWorld {

  public static class Factory {
    private final int xSize;
    private final int zSize;
    private final int height;

    public Factory(int xSize, int ySize, int height) {
      this.xSize = xSize;
      this.zSize = ySize;
      this.height = height;
    }

    public FakeReactorWorld create(String reactorLayout) {
      return makeReactor(reactorLayout, xSize, zSize, height);
    }
  }

  private ThreeNestedMap<Integer, Integer, Integer, Value> worldValues = new ThreeNestedMap<Integer, Integer, Integer, Value>();
  private final CoordTriplet maxDims;
  private final List<TileEntity> parts = Lists.newArrayList();
  private int numRods = 0;

  public FakeReactorWorld(int x, int y, int z) {
    this.maxDims = new CoordTriplet(x - 1, z - 1, y - 1);
  }

  public static FakeReactorWorld makeReactor(String reactorLayout, int xSize, int zSize, int height) {
    FakeReactorWorld fakeReactorWorld = new FakeReactorWorld(xSize, zSize, height);
    reactorLayout = reactorLayout.replaceAll(" ", "");
    reactorLayout = reactorLayout.replaceAll("\n", "");
    for (int i = 0; i < reactorLayout.length(); i++) {
      int x = i % (xSize - 2);
      int z = i / (xSize - 2);
      ReactorParser.parse(x, z, reactorLayout.charAt(i), fakeReactorWorld);
    }
    return fakeReactorWorld;
  }

  public void makeControlRod(int x, int z) {
    int maxY = maxDims.y;
    List<TileEntityReactorFuelRodSimulator> fuel = Lists.newArrayList();
    for (int i = 1; i < maxY; i++) { //From above reactor floor to below control rod
      TileEntityReactorFuelRodSimulator part = new TileEntityReactorFuelRodSimulator();
      setEntity(x, i, z, part);
      fuel.add(part);
    }
    TileEntityReactorControlRod controlRod = new TileEntityReactorControlRod();
    setEntity(x, maxY, z, controlRod);
    numRods++;
  }

  public int getNumRods() {
    return numRods;
  }

  @Override
  public String display() {
    String result = "";
    int y = 1;
    for (int i = 1; i < maxDims.x - 1; i++) {
      for (int j = 1; j < maxDims.z - 1; j++) {
        if (this.getTileEntity(i, y, j) != null) {
          result += "X ";
        } else if (this.isAirBlock(i, y, j)) {
          result += "O ";
        } else {
          String blockName = this.getBlockName(i, y, j);
          BiMap<String, Character> inverse = ReactorParser.mappings.inverse();
          Character c = inverse.get(blockName);
          result += c + " ";
        }
      }
      result += "\n";
    }
    return result;
  }

  private void setEntity(int x, int y, int z, TileEntity part) {
    if (x < maxDims.x && z < maxDims.z && y <= maxDims.y) {
      parts.add(part);
      part.xCoord = x;
      part.yCoord = y;
      part.zCoord = z;
      worldValues.put(x, y, z, new Value(part));
    } else {
      throw new IllegalArgumentException(x + "," + z + ", height:" + y + " invalid for " + maxDims);
    }
  }

  public void makeCoolantColumn(int x, int z, String coolant) {
    if (x < maxDims.x && z < maxDims.z) {
      int maxY = maxDims.y;
      for (int i = 1; i < maxY - 1; i++) { //From above reactor floor to below control rod
        worldValues.put(x, i, z, new Value(coolant));
      }
    } else {
      throw new IllegalArgumentException(x + "," + z + " invalid for " + maxDims);
    }
  }

  @Override
  public TileEntity getTileEntity(int x, int y, int z) {
    Value value = worldValues.get(x, y, z);
    return value == null ? null : value.getEntity();
  }

  @Override
  public boolean isAirBlock(int x, int y, int z) {
    return !worldValues.containsKey(x, y, z);
  }

  @Override
  public String getBlockName(int x, int y, int z) {
    Value value = worldValues.get(x, y, z);
    return value == null ? null : value.getBlockOrFluid();
  }

  @Override
  public CoordTriplet getMaxCoord() {
    return maxDims;
  }

  @Override
  public CoordTriplet getMinCoord() {
    return new CoordTriplet(0, 0, 0);
  }

  @Override
  public List<TileEntity> getParts() {
    return parts;
  }

  private static class Value {
    private TileEntity entity = null;
    private String blockOrFluid = "";

    private Value(TileEntity entity) {
      this.entity = entity;
    }

    private Value(String blockOrFluid) {
      this.blockOrFluid = blockOrFluid;
    }

    public TileEntity getEntity() {
      return entity;
    }

    public String getBlockOrFluid() {
      return blockOrFluid;
    }
  }
}
