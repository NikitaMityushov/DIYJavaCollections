package org.mityushov.DIYJavaCollections;

import java.util.Collection;
import java.util.Iterator;

public class ArrayCollection<T> implements Collection<T> {
    private final int DEFAULT_CAPACITY = 1 << 4; // 16
    // private final int MAX_CAPACITY = 1 << 29;
    private final int SCALING_FACTOR = 1 << 1;


    private T[] array;
    private int size;
    
    // constructors
    @SuppressWarnings("unchecked")
    public ArrayCollection() {
        this.array = (T[]) new Object[DEFAULT_CAPACITY];
    }

    @SuppressWarnings("unchecked")
    public ArrayCollection(int capacity) {
        this.array = (T[]) new Object[capacity];
    }

    //methods
    @Override
    @SuppressWarnings("unchecked")
    public boolean add(final T t) {
        if (array.length == this.size) { 
            final T[] oldArray = this.array;
            this.array = (T[]) new Object[this.size * this.SCALING_FACTOR];
            System.arraycopy(oldArray, 0, this.array, 0, oldArray.length);
        }
        this.array[this.size++] = t;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (final T item : c) {
            this.add(item);
        }
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void clear() {
        this.array = (T[]) new Object[DEFAULT_CAPACITY];
        this.size = 0;

    }

    @Override
    public boolean contains(final Object o) {
        for (int i = 0; i < this.size; i++) {
            if (array[i].equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        for (final Object item : c) {
            if (!this.contains(item)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<T> iterator() {        
        return new ElementsIterator();
    }

    @Override
    public boolean remove(final Object o) {
        for (int i = 0; i < this.size; i++) {
            if (this.array[i].equals(o) && i != this.size - 1) {
                System.arraycopy(this.array, i + 1, this.array, i, this.size - i - 1);
                this.size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        for (final Object item : c) {
            this.remove(item);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        for (final Object item : this) {
            if (!c.contains(item)) {
                this.remove(item);
            }
        }
        return true;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object[] toArray() {
        final T[] newArray = (T[]) new Object[this.size];
        System.arraycopy(this.array, 0, newArray, 0, this.size);
        return newArray;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] arg0) {
        return (T[]) this.toArray();
    }

    private class ElementsIterator implements Iterator<T> {
        
        private int size;

        @Override
        public boolean hasNext() {
            return ArrayCollection.this.size > size;
        }

        @Override
        public T next() {
            return ArrayCollection.this.array[size++];
        }

    }
    
}
