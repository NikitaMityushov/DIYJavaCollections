package org.mityushov.DIYJavaCollections;

import sun.misc.Unsafe;
import java.lang.reflect.Constructor;


public class FastIntArray implements AutoCloseable {

    private final long DEFAULT_CAPACITY = 1 << 4; // 16
    private final long MAX_CAPACITY = 1 << 30;
    private final long SCALING_FACTOR = 1 << 1;
    private final long sizeOfElementInBytes = 1 << 2; // 4
    private long size = 0;
    private long currentCapacity;
    private final Unsafe unsafe;
    private long address;
    // private boolean sortedFlag = false;

// constructors
    public FastIntArray() throws Exception {
        this.unsafe = createUnsafeInstance();
        this.address = this.unsafe.allocateMemory(this.sizeOfElementInBytes * this.DEFAULT_CAPACITY);
        this.currentCapacity = DEFAULT_CAPACITY;
    }

    public FastIntArray(long sizeOfArray) throws Exception {
        
        if (sizeOfArray <= 0 || sizeOfArray > this.MAX_CAPACITY) {
            sizeOfArray = this.DEFAULT_CAPACITY;
        }

        this.unsafe = createUnsafeInstance();
        this.address = this.unsafe.allocateMemory(this.sizeOfElementInBytes * sizeOfArray);
        this.currentCapacity = sizeOfArray;
    }

// public methods
    public void add(int element) {

        if (this.size == this.currentCapacity) {
            this.extendArray();
        }
        
        long localAddress = this.address + this.sizeOfElementInBytes * (this.size);
        unsafe.putInt(localAddress, element);
        this.size++;

    }

    public boolean removeFromValue(int element) {
        long index = returnIndexOfElement(element);

        if (index != -1) {
            removeFromIndex(index);
            return true;
        }

        return false;
    }

    public int removeFromIndex(long index) {
        this.checkIfOutOfBounds(index);
        int tmp = this.get(index);

        for (long i = index; i < this.size; i++) {
            long localAddress = this.address + this.sizeOfElementInBytes * i;
            int element = this.get(i + 1);
            unsafe.putInt(localAddress, element);
        }
        this.size--;
        // warning
        if (this.checkIfLess75percentLoading()) {
            this.reduceArray();
        }

        return tmp;
    }

    public int get(long index) {
        
        this.checkIfOutOfBounds(index);

        return unsafe.getInt(this.getAddressOfIndex(index));
    }

    public long size() {
        return this.size;
    }

    public boolean contains(int element) {
        return this.returnIndexOfElement(element) != -1;
    }
/*
    public void sort() {
        //
    }
*/
    @Override
    public void close() {
        this.unsafe.freeMemory(this.address);
    }

    @Override
    public String toString() {
        //
        return "";
    }

// private methods
    private Unsafe createUnsafeInstance() throws Exception {
        Constructor<Unsafe> constructor = Unsafe.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Unsafe unsafe = constructor.newInstance();
        return unsafe;
    }

    private long getAddressOfIndex(long index) {
        return this.address + index * this.sizeOfElementInBytes;
    }

    private void extendArray() {
        //BigInteger??
        long localCapacity = this.currentCapacity * this.SCALING_FACTOR;

        if (localCapacity >= this.MAX_CAPACITY) {
            this.currentCapacity = this.MAX_CAPACITY;
        } else {
            this.currentCapacity *= this.SCALING_FACTOR;
        }

        this.address = unsafe.reallocateMemory(this.address, this.sizeOfElementInBytes * currentCapacity);
    }

    private void reduceArray() {
        this.currentCapacity = this.size << 1;
        this.address = unsafe.reallocateMemory(this.address, this.sizeOfElementInBytes * this.currentCapacity);
    }

    private long returnIndexOfElement(int element) {
        
        for (long i = 0; i <= this.size - 1; i++) {
            if (this.get(i) == element) {
                return i;
            }    
        }

        return -1;
    }

    private void checkIfOutOfBounds(long index) {

        if (index > this.size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }
/*
    private int binarySearch(int element) {
        //
        return 1; // return index of element or -1
    }
*/
    private boolean checkIfLess75percentLoading() {
        
        if (this.size << 1 < this.currentCapacity >> 1) {
            return true;
        }

        return false;
    }

// temporary
/*
    public long getCurrentCapacity() {
        return this.currentCapacity;
    }
*/

}