package org.sidoh.reactor_simulator.simulator.nested_map;

public class FourNestedCountingMap<K1, K2, K3, K4> extends FourNestedMap<K1, K2, K3, K4, Long> {

  public FourNestedCountingMap(Long defaultValue){
    super(defaultValue);
  }

  public Long incrementAndGet(K1 k1, K2 k2, K3 k3, K4 k4, Long amount){
    Long previous = get(k1, k2, k3, k4);
    long newV = previous.longValue() + amount.longValue();
    put(k1, k2, k3, k4, newV);
    return newV;
  }
}
