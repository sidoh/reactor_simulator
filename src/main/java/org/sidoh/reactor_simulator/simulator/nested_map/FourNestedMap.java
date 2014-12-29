package org.sidoh.reactor_simulator.simulator.nested_map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;

public class FourNestedMap<K1, K2, K3, K4, V> implements Iterable<FourNestedMap.Entry<K1, K2, K3, K4, V>>, Serializable {
  protected final Map<K1, ThreeNestedMap<K2, K3, K4, V>> data = new HashMap<K1, ThreeNestedMap<K2, K3, K4, V>>();
  private final V defaultValue;

  public FourNestedMap(V defaultValue) {
    this.defaultValue = defaultValue;
  }
  public FourNestedMap() {
    this.defaultValue = null;
  }
  public Set<K1> key1Set() {
    if(data.keySet() != null) {
      return data.keySet();
    }
    else return Collections.emptySet();
  }
  public Set<K2> key2Set(K1 k1) {
    if(data.get(k1) != null) {
      return data.get(k1).key1Set();
    } else {
    return Collections.emptySet();
    }
  }
  public Set<K3> key3Set(K1 k1, K2 k2) {
    if(data.get(k1) != null) {
      return data.get(k1).key2Set(k2);
    } else {
    return Collections.emptySet();
    }
  }
  public Set<K4> key4Set(K1 k1, K2 k2, K3 k3) {
    if(data.get(k1) != null) {
      return data.get(k1).key3Set(k2, k3);
    } else {
    return Collections.emptySet();
    }
  }
  public Set<TwoKeyTuple<K1, K2>> key12Set() {
    if(data.keySet().isEmpty()) {
      return Collections.emptySet();
    }
    Set<TwoKeyTuple<K1, K2>> tuples = new HashSet<TwoKeyTuple<K1, K2>>();
    for(K1 k1 : data.keySet()) {
      ThreeNestedMap<K2, K3, K4, V> map1 = data.get(k1);
      for(K2 k2: map1.key1Set()) {
        tuples.add(new TwoKeyTuple<K1, K2>(k1, k2));
      }
    }
    return tuples;
  }
  public Set<ThreeKeyTuple<K1, K2, K3>> key123Set() {
    if(data.keySet().isEmpty()) {
      return Collections.emptySet();
    }
    Set<ThreeKeyTuple<K1, K2, K3>> tuples = new HashSet<ThreeKeyTuple<K1, K2, K3>>();
    for(K1 k1 : data.keySet()) {
      ThreeNestedMap<K2, K3, K4, V> map1 = data.get(k1);
      for(K2 k2: map1.key1Set()) {
        TwoNestedMap<K3, K4, V> map2 = map1.get(k2);
        for(K3 k3: map2.key1Set()) {
          tuples.add(new ThreeKeyTuple<K1, K2, K3>(k1, k2, k3));
        }
      }
    }
    return tuples;
  }
  public Set<FourKeyTuple<K1, K2, K3, K4>> key1234Set() {
    if(data.keySet().isEmpty()) {
      return Collections.emptySet();
    }
    Set<FourKeyTuple<K1, K2, K3, K4>> tuples = new HashSet<FourKeyTuple<K1, K2, K3, K4>>();
    for(K1 k1 : data.keySet()) {
      ThreeNestedMap<K2, K3, K4, V> map1 = data.get(k1);
      for(K2 k2: map1.key1Set()) {
        TwoNestedMap<K3, K4, V> map2 = map1.get(k2);
        for(K3 k3: map2.key1Set()) {
          Map<K4, V> map3 = map2.get(k3);
          for(K4 k4: map3.keySet()) {
            tuples.add(new FourKeyTuple<K1, K2, K3, K4>(k1, k2, k3, k4));
          }
        }
      }
    }
    return tuples;
  }
  public Set<TwoKeyTuple<K2, K3>> key23Set(K1 k1) {
    if(data.keySet().isEmpty()) {
      return Collections.emptySet();
    }
    if(data.get(k1) != null) {
      return data.get(k1).key12Set();
    } else {
      return Collections.emptySet();
    }
  }
  public Set<ThreeKeyTuple<K2, K3, K4>> key234Set(K1 k1) {
    if(data.keySet().isEmpty()) {
      return Collections.emptySet();
    }
    if(data.get(k1) != null) {
      return data.get(k1).key123Set();
    } else {
      return Collections.emptySet();
    }
  }
  public Set<TwoKeyTuple<K3, K4>> key34Set(K1 k1, K2 k2) {
    if(data.keySet().isEmpty()) {
      return Collections.emptySet();
    }
    if(data.get(k1) != null) {
      return data.get(k1).key23Set(k2);
    } else {
      return Collections.emptySet();
    }
  }
  public ThreeNestedMap<K2, K3, K4, V> get(K1 k1) {
    return data.get(k1);
  }
  public TwoNestedMap<K3, K4, V> get(K1 k1, K2 k2) {
    return (data.get(k1) == null || data.get(k1).get(k2) == null) ? null : data.get(k1).get(k2);
  }
  public Map<K4, V> get(K1 k1, K2 k2, K3 k3) {
    return (data.get(k1) == null || data.get(k1).get(k2, k3) == null) ? null : data.get(k1).get(k2, k3);
  }
  public V get(K1 k1, K2 k2, K3 k3, K4 k4) {
    return (data.get(k1) == null || data.get(k1).get(k2, k3, k4) == null) ? defaultValue : data.get(k1).get(k2, k3, k4);
  }

  public V get(FourKeyTuple tuple) {
    return data.get(tuple.head()).get(tuple.tail());
  }
  public List<K1> key1Sort(Comparator<K1> sort) {
    List<K1> list = Lists.newArrayList(key1Set());
    Collections.sort(list, sort);
    return list;
  }
  public List<TwoKeyTuple<K1, K2>> key12Sort(Comparator<TwoKeyTuple<K1, K2>> sort) {
    List<TwoKeyTuple<K1, K2>> list = Lists.newArrayList(key12Set());
    Collections.sort(list, sort);
    return list;
  }
  public List<ThreeKeyTuple<K1, K2, K3>> key123Sort(Comparator<ThreeKeyTuple<K1, K2, K3>> sort) {
    List<ThreeKeyTuple<K1, K2, K3>> list = Lists.newArrayList(key123Set());
    Collections.sort(list, sort);
    return list;
  }

  public void put(K1 k1, K2 k2, K3 k3, K4 k4, V v) {
    if(data.get(k1) == null) {
      data.put(k1, new ThreeNestedMap<K2, K3, K4, V>());
    }
    data.get(k1).put(k2, k3, k4, v);
  }

  public void clear() {
    data.clear();
  }

  public Collection<V> values() {
    Iterator<Entry<K1, K2, K3, K4, V>> iterator = iterator();
    List<V> values = new ArrayList();
    while(iterator.hasNext()) {
      values.add(iterator.next().getValue());
    }
    return values;
  }

  public boolean containsKey(K1 k1, K2 k2, K3 k3, K4 k4) {
    return data.containsKey(k1) && data.get(k1).containsKey(k2, k3, k4);
  }

  public boolean containsKey(FourKeyTuple key) {
    return data.containsKey(key.getK1()) && data.get(key.getK1()).containsKey(key.tail());
  }

  public boolean containsValue(V v) {
    return values().contains(v);
  }

  public boolean isEmpty() {
    return data.isEmpty();
  }

  public int size() {
    return entrySet().size();
  }

  public Set<Entry<K1, K2, K3, K4, V>> entrySet() {
    Set<FourKeyTuple<K1, K2, K3, K4>> tuples = key1234Set();
    Iterator<FourKeyTuple<K1, K2, K3, K4>> i = tuples.iterator();
    Set<Entry<K1, K2, K3, K4, V>> entries = new HashSet<Entry<K1, K2, K3, K4, V>>();
    while(i.hasNext()) {
      FourKeyTuple<K1, K2, K3, K4> next = i.next();
      entries.add(new Entry<K1, K2, K3, K4, V>(next, get(next)));
    }
    return entries;
  }

  public Iterator<Entry<K1, K2, K3, K4, V>> iterator() {
    return entrySet().iterator();
  }
  public static class Entry<K1, K2, K3, K4, V>  {
    private final FourKeyTuple<K1, K2, K3, K4> keyTuple;
    private final V value;

    public Entry(K1 k1, K2 k2, K3 k3, K4 k4, V v) {
      keyTuple = new FourKeyTuple<K1, K2, K3, K4>(k1, k2, k3, k4);
      value = v;
    }

    public Entry(FourKeyTuple<K1, K2, K3, K4> tuple, V v) {
      keyTuple = tuple;
      value = v;
    }

    public FourKeyTuple<K1, K2, K3, K4> getKeyTuple() {
      return keyTuple;
    }

    public FourKeyTuple<K1, K2, K3, K4> getKey() {
      return keyTuple;
    }
    public K1 getK1() {
      return keyTuple.getK1();
    }
    public K2 getK2() {
      return keyTuple.getK2();
    }
    public K3 getK3() {
      return keyTuple.getK3();
    }
    public K4 getK4() {
      return keyTuple.getK4();
    }


    public V getValue() {
      return value;
    }

    public int hashCode() {
      return keyTuple.hashCode() * 31 + value.hashCode();
    }

    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if(o == null || getClass() != o.getClass()) {
        return false;
      }
      Entry entry = (Entry) o;
      return (getKey().equals(entry.getKey()) && getValue().equals(entry.getValue()));
    }

  }
}
