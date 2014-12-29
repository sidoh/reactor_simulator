package org.sidoh.reactor_simulator.simulator.nested_map;

public class ThreeNestedCountingMap <K1, K2, K3> extends ThreeNestedMap<K1, K2, K3, Long> {

  public ThreeNestedCountingMap(Long defaultValue){
    super(defaultValue);
  }

  public Long incrementAndGet(K1 k1, K2 k2, K3 k3, Long amount){
    Long previous = get(k1, k2, k3);
    long newV = previous.longValue() + amount.longValue();
    put(k1, k2, k3, newV);
    return newV;
  }
}
