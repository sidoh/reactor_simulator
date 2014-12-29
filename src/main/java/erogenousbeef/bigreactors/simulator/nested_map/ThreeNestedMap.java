package erogenousbeef.bigreactors.simulator.nested_map;

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

public class ThreeNestedMap<K1, K2, K3, V> implements Iterable<ThreeNestedMap.Entry<K1, K2, K3, V>>, Serializable {
  protected final Map<K1, TwoNestedMap<K2, K3, V>> data = new HashMap<K1, TwoNestedMap<K2, K3, V>>();
  private final V defaultValue;

  public ThreeNestedMap(V defaultValue) {
    this.defaultValue = defaultValue;
  }
  public ThreeNestedMap() {
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
  public Set<TwoKeyTuple<K1, K2>> key12Set() {
    if(data.keySet().isEmpty()) {
      return Collections.emptySet();
    }
    Set<TwoKeyTuple<K1, K2>> tuples = new HashSet<TwoKeyTuple<K1, K2>>();
    for(K1 k1 : data.keySet()) {
      TwoNestedMap<K2, K3, V> map1 = data.get(k1);
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
      TwoNestedMap<K2, K3, V> map1 = data.get(k1);
      for(K2 k2: map1.key1Set()) {
        Map<K3, V> map2 = map1.get(k2);
        for(K3 k3: map2.keySet()) {
          tuples.add(new ThreeKeyTuple<K1, K2, K3>(k1, k2, k3));
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
  public TwoNestedMap<K2, K3, V> get(K1 k1) {
    return data.get(k1);
  }
  public Map<K3, V> get(K1 k1, K2 k2) {
    return (data.get(k1) == null || data.get(k1).get(k2) == null) ? null : data.get(k1).get(k2);
  }
  public V get(K1 k1, K2 k2, K3 k3) {
    return (data.get(k1) == null || data.get(k1).get(k2, k3) == null) ? defaultValue : data.get(k1).get(k2, k3);
  }

  public V get(ThreeKeyTuple tuple) {
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

  public void put(K1 k1, K2 k2, K3 k3, V v) {
    if(data.get(k1) == null) {
      data.put(k1, new TwoNestedMap<K2, K3, V>());
    }
    data.get(k1).put(k2, k3, v);
  }

  public void clear() {
    data.clear();
  }

  public Collection<V> values() {
    Iterator<Entry<K1, K2, K3, V>> iterator = iterator();
    List<V> values = new ArrayList();
    while(iterator.hasNext()) {
      values.add(iterator.next().getValue());
    }
    return values;
  }

  public boolean containsKey(K1 k1, K2 k2, K3 k3) {
    return data.containsKey(k1) && data.get(k1).containsKey(k2, k3);
  }

  public boolean containsKey(ThreeKeyTuple key) {
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

  public Set<Entry<K1, K2, K3, V>> entrySet() {
    Set<ThreeKeyTuple<K1, K2, K3>> tuples = key123Set();
    Iterator<ThreeKeyTuple<K1, K2, K3>> i = tuples.iterator();
    Set<Entry<K1, K2, K3, V>> entries = new HashSet<Entry<K1, K2, K3, V>>();
    while(i.hasNext()) {
      ThreeKeyTuple<K1, K2, K3> next = i.next();
      entries.add(new Entry<K1, K2, K3, V>(next, get(next)));
    }
    return entries;
  }

  public Iterator<Entry<K1, K2, K3, V>> iterator() {
    return entrySet().iterator();
  }
  public static class Entry<K1, K2, K3, V>  {
    private final ThreeKeyTuple<K1, K2, K3> keyTuple;
    private final V value;

    public Entry(K1 k1, K2 k2, K3 k3, V v) {
      keyTuple = new ThreeKeyTuple<K1, K2, K3>(k1, k2, k3);
      value = v;
    }

    public Entry(ThreeKeyTuple<K1, K2, K3> tuple, V v) {
      keyTuple = tuple;
      value = v;
    }

    public ThreeKeyTuple<K1, K2, K3> getKeyTuple() {
      return keyTuple;
    }

    public ThreeKeyTuple<K1, K2, K3> getKey() {
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
