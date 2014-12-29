package erogenousbeef.bigreactors.simulator;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class ReactorParser {

  public static final char CONTROL_ROD = 'X';
  private FakeReactorWorld world;

  public ReactorParser(int height, String[] lines) {
    String first = lines[0].replaceAll(" ", "");
    this.world = new FakeReactorWorld(lines.length + 1, height, first.length() + 1);
    for (int i = 0; i < lines.length; i++) {
      String line = lines[i].replaceAll(" ", "");
      for (int j = 0; j < line.length(); j++) {
        char c = line.charAt(j);
        parse(i, j, c, world);
      }

    }
  }

  public static void parse(int i, int j, char c, FakeReactorWorld world) {
    if (c == CONTROL_ROD) {
      world.makeControlRod(i + 1, j + 1);
    } else if (c == 'O') {
      //Air column
    } else {
      String coolant = getCoolant(c);
      world.makeCoolantColumn(i + 1, j + 1, coolant);
    }
  }

  public FakeReactorWorld getWorld() {
    return world;
  }

  private static String getCoolant(char c) {
    return mappings.get(c);
  }

  public static BiMap<Character, String> mappings = HashBiMap.create();

  static {
    mappings.put('X', "entity:controlRod");
    mappings.put('O', "fluid:air");
    mappings.put('E', "fluid:ender");
    mappings.put('C', "fluid:cryotheum");
    mappings.put('D', "block:blockDiamond");
    mappings.put('P', "fluid:pyrotheum");
    mappings.put('G', "block:blockGraphite");
    mappings.put('M', "block:blockManyullyn");
  }
}
