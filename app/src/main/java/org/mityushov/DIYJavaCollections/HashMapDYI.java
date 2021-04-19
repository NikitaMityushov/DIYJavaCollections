package org.mityushov.DIYJavaCollections;

import java.util.*;
import java.util.stream.Collectors;

public class HashMapDYI<K,V> implements Map<K,V> {
    private LinkedList<Entry<K,V>>[] buckets; // list's array
    private int size = 0;
    private final int DEFAULT_CAPACITY = 1 << 4;
    private final int MAX_CAPACITY = 1 << 30;
    private int current_capacity;

    // constructors
    public HashMapDYI() {
        this.current_capacity = this.DEFAULT_CAPACITY;
        this.initializeMap();
    }

    public HashMapDYI(final int capacity) {
        if (capacity <= 0 || capacity > this.MAX_CAPACITY) {
            throw new IllegalArgumentException("Illegal argument in the constructor");
        }
        this.current_capacity = capacity;
        this.initializeMap();
    }
    // static Entry class
    private static class Entry<K,V> implements Map.Entry<K,V> {
        private final K key;
        private V value;

        public Entry(final K key, final V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V v) {
            var tmp = this.value;
            this.value = v;
            return tmp;
        }

        @Override
        public String toString() {
            var builder = new StringBuilder();
            builder.append("[").append(this.getKey()).append(" : ").append(this.getValue()).append("]");
            return builder.toString();
        }
    }
    // public methods
    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean containsKey(Object o) {
        int bucket = findEntryIndex(o);
        if (bucket == -1) {
            return false;
        }

        var list = this.buckets[bucket];
        for (Entry<K, V> tmp : list) {
            if (tmp.getKey().equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(final Object o) {
        return this.values().contains(o);
    }

    @Override
    public V get(final Object o) {
        int bucket = findEntryIndex(o);
        if (bucket == -1) {
            return null;
        }
        var list = this.buckets[bucket];
        int indexOfElementInList = this.getIndexOfKeyInList(list, o);

        return indexOfElementInList != -1 ? list.get(indexOfElementInList).getValue() : null;
    }

    @Override
    public V put(final K k, final V v) {
        int bucket = this.findEntryIndex(k);
        if (bucket == -1) {
            return null;
        }

        var list = this.buckets[bucket];
        int indexOfKeyInList = this.getIndexOfKeyInList(list, k);

        if (indexOfKeyInList == -1) {
            list.add(new Entry<>(k, v));
            this.size++;
        } else {
            list.get(indexOfKeyInList).setValue(v);
        }
        return v;
    }

    @Override
    public V remove(Object o) {
        int bucket = this.findEntryIndex(o);
        if (bucket == -1) {
            return null;
        }
        var list = this.buckets[bucket];
        int indexOfKeyInList = this.getIndexOfKeyInList(list, o);

        if (indexOfKeyInList == -1) {
            return null;
        } else {
            var removeValue = this.get(o);
            list.remove(indexOfKeyInList);
            this.size--;
            return removeValue;
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        map.forEach(this::put);
    }

    @Override
    public void clear() {
        this.current_capacity = this.DEFAULT_CAPACITY;
        this.initializeMap();
        this.size = 0;
    }

    @Override
    public Set<K> keySet() {
        return this.entrySet()  .stream()
                                .map(Map.Entry::getKey)
                                .collect(Collectors.toSet());
    }

    @Override
    public Collection<V> values() {
        return this.entrySet()  .stream()
                                .map(Map.Entry::getValue)
                                .collect(Collectors.toList());
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = new HashSet<>();
        Arrays.stream(this.buckets)
                .filter(list -> !list.isEmpty())
                .forEach(set::addAll);
        return set;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        this.forEach((key, value) -> builder.append("[").append(key).append(" : ").append(value).append("]\n"));
        return builder.toString();
    }
    /**
     * this private method returns bucket index using hashcode of the key
     * @param key
     * @return
     */
    private int findEntryIndex(final Object key) {
        if (key == null) {
            return -1;
        }
        return key.hashCode() % this.current_capacity;
    }
    /**
     * this private method returns index of the key in the list, if present
     * @param list
     * @param o
     * @return
     */
    private int getIndexOfKeyInList(LinkedList<Entry<K,V>> list, Object o) {
        int indexOfElementInList = -1;
        var listIterator = list.listIterator();

        while (listIterator.hasNext()) {
            var tmpIndex = listIterator.nextIndex();
            var tmp = listIterator.next();
            if (tmp.getKey().equals(o)) {
                indexOfElementInList = tmpIndex;
                break;
            }
        }
        return indexOfElementInList;
    }
    /**
     * this private method creates new empty LinkedList in each bucket
     */
    private void initializeMap() {
        this.buckets = new LinkedList[this.current_capacity];

        for (int i = 0; i < this.current_capacity; i++) {
            this.buckets[i] = new LinkedList<>();
        }
    }

}
