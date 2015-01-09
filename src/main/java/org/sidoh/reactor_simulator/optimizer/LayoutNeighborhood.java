package org.sidoh.reactor_simulator.optimizer;

import java.util.Iterator;

public class LayoutNeighborhood implements Iterable<String> {
  public static final char[] VALID_BLOCKS = { 'C', 'E', 'X', };

  private final String seedLayout;

  public LayoutNeighborhood(String seedLayout) {
    this.seedLayout = seedLayout;
  }

  @Override
  public Iterator<String> iterator() {
    return new LayoutIterator(seedLayout);
  }

  public static LayoutNeighborhood of(String seedLayout) {
    return new LayoutNeighborhood(seedLayout);
  }

  private static class LayoutIterator implements Iterator<String> {
    private final String layout;
    private int layoutIndex;
    private int blockIndex;

    private LayoutIterator(String layout) {
      this.layout = layout;
      this.layoutIndex = 0;
      this.blockIndex = 0;

      if (layout.charAt(0) == VALID_BLOCKS[0]) {
        advancePointers();
      }
    }

    @Override
    public boolean hasNext() {
      return layoutIndex < layout.length();
    }

    @Override
    public String next() {
      StringBuilder b = new StringBuilder(layout.length());
      b.append(layout.substring(0, layoutIndex));
      b.append(VALID_BLOCKS[blockIndex]);
      b.append(layout.substring(layoutIndex+1));
      advancePointers();
      return b.toString();
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }

    private void advancePointers() {
      blockIndex++;

      while (layoutIndex < layout.length() && (blockIndex >= VALID_BLOCKS.length || layout.charAt(layoutIndex) == VALID_BLOCKS[blockIndex])) {
        if (blockIndex >= VALID_BLOCKS.length) {
          blockIndex = 0;
          layoutIndex++;
        } else {
          blockIndex++;
        }
      }
    }
  }
}
