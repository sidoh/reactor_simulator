
package org.sidoh.reactor_simulator.simulator.nested_map;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;


public class TwoKeyTuple<K1, K2> implements Serializable{
  private K1 k1;
  private K2 k2;
  public TwoKeyTuple(K1 k1, K2 k2) {
    this.k1 = k1;
    this.k2 = k2;
}

  public List toList() {
    return Lists.newArrayList(k1, k2);
  }
  public Set toSet() {
    return Sets.newHashSet(k1, k2);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if(o == null || getClass() != o.getClass()) {
      return false;
    }
    TwoKeyTuple tuple = (TwoKeyTuple) o;
    return tuple.toList().equals(toList());
  }
  @Override
  public int hashCode() {
    int hash = 1;
    hash = 31 * hash + (k1 != null ? k1.hashCode() : 0);
    hash = 31 * hash + (k2 != null ? k2.hashCode() : 0);
    return hash;
  }
  public K1 getK1() {
    return k1;
  }
  public K2 getK2() {
    return k2;
  }

  public K1 head() {
    return k1;
  }
  public K1 init() {
    return k1;
  }

  public K2 last() {
    return k2;
  }
  public K2 tail() {
    return k2;
  }

}
