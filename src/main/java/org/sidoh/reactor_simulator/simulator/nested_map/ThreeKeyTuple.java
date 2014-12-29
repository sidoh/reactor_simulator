
package org.sidoh.reactor_simulator.simulator.nested_map;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;


public class ThreeKeyTuple<K1, K2, K3> implements Serializable{
  private K1 k1;
  private K2 k2;
  private K3 k3;
  public ThreeKeyTuple(K1 k1, K2 k2, K3 k3) {
    this.k1 = k1;
    this.k2 = k2;
    this.k3 = k3;
}

  public List toList() {
    return Lists.newArrayList(k1, k2, k3);
  }
  public Set toSet() {
    return Sets.newHashSet(k1, k2, k3);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if(o == null || getClass() != o.getClass()) {
      return false;
    }
    ThreeKeyTuple tuple = (ThreeKeyTuple) o;
    return tuple.toList().equals(toList());
  }
  @Override
  public int hashCode() {
    int hash = 1;
    hash = 31 * hash + (k1 != null ? k1.hashCode() : 0);
    hash = 31 * hash + (k2 != null ? k2.hashCode() : 0);
    hash = 31 * hash + (k3 != null ? k3.hashCode() : 0);
    return hash;
  }
  public K1 getK1() {
    return k1;
  }
  public K2 getK2() {
    return k2;
  }
  public K3 getK3() {
    return k3;
  }

  public TwoKeyTuple<K1, K2> init() {
    return new TwoKeyTuple<K1, K2>(k1, k2);
  }
  public K1 head() {
    return k1;
  }
  public K3 last() {
    return k3;
  }
  public TwoKeyTuple<K2, K3> tail() {
    return new TwoKeyTuple<K2, K3>(k2, k3);
  }
}
