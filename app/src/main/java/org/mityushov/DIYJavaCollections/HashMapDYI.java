package org.mityushov.DIYJavaCollections;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

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
        private K key;
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
    }


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
        return false;
    }

    @Override
    public boolean containsValue(Object o) {
        return false;
    }

    @Override
    public V get(final Object o) {
        if (o == null) {
            return null;
        }
        int bucket = findEntryIndex(o);
        var list = this.buckets[bucket];
        int indexOfElementInList = -1;

        var listIterator = list.listIterator();

        while (listIterator.hasNext()) {
            indexOfElementInList = listIterator.nextIndex();
            var tmp = listIterator.next();
            if (tmp.getKey().equals(o)) {
                break;
            }
        }

        return indexOfElementInList != -1 ? list.get(indexOfElementInList).getValue() : null;
    }

    @Override
    public V put(final K k, final V v) {
        int bucket = this.findEntryIndex(k);
        this.buckets[bucket].add(new Entry<>(k, v));
        this.size++;
        return v;
    }

    @Override
    public V remove(Object o) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        return ""; // дописать
    }

    private int findEntryIndex(final Object key) {
        return key.hashCode() % this.current_capacity;
    }

    private void initializeMap() {
        this.buckets = new LinkedList[this.current_capacity];

        for (int i = 0; i < this.current_capacity; i++) {
            this.buckets[i] = new LinkedList<>();
        }
    }

}
