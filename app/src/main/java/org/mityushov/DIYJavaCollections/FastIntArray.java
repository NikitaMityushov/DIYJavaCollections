package org.mityushov.DIYJavaCollections;

import sun.misc.Unsafe;
import java.lang.reflect.Constructor;

/**
 *
 */

public class FastIntArray implements AutoCloseable {

    private final long DEFAULT_CAPACITY = 1 << 4; // 16
    private final long MAX_CAPACITY = 1 << 29;
    private final long SCALING_FACTOR = 1 << 1;
    private final long sizeOfElementInBytes = 1 << 2; // 4
    private long size = 0;
    private long currentCapacity;
    private final Unsafe unsafe;
    private long address;
    private boolean sortedFlag = false;
    private boolean isFilled = false;

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
    public boolean add(int element) {
        boolean canExtend = true;

        if (this.size == this.currentCapacity) { // ! strange ==, why >=
            canExtend = this.extendArray();
        }
        
        if (canExtend) {
            long localAddress = this.address + this.sizeOfElementInBytes * (this.size);
            unsafe.putInt(localAddress, element);
            this.size++;
            // maybe add check if_sorted ??
            /*
                if (element < this.get(this.size - 2)) this.sortedFlag = false;
            */
            this.sortedFlag = false;
            return true;
        } else {
            return false;
        }

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
        if (this.sortedFlag) {
            return this.binarySearch(element) != -1;
        } else {
            return this.returnIndexOfElement(element) != -1;
        }
    }

    public void sort() {
        if (!this.sortedFlag && this.size > 0) {
            // this.bubbleSort();
            this.quickSort(0, this.size - 1);
            this.sortedFlag = true;
        }
    }

    @Override
    public void close() {
        this.unsafe.freeMemory(this.address);
    }

    @Override
    public String toString() {
        // посчитать символы комментариев, вставить в размер билдера!!
        StringBuilder builder;

        if (this.size < 100) {
            builder = new StringBuilder((int) this.size);
            builder.append("[");
            
            for (long i = 0; i < this.size; i++) {
                
                if (i % 10 == 0) {
                    builder.append("\n");
                }

                builder.append(this.get(i) + " ");
                
            }

        } else {
            builder = new StringBuilder(100);
            builder.append("Size of array is " + this.size + " elements.\nFirst 50 elements:\n[");
            
            for (long i = 0; i < 50; i++) {
                
                if (i % 10 == 0) {
                    builder.append("\n");
                }

                builder.append(this.get(i) + " ");            
            }

            builder.append("\n]\n...\nLast 50 elements:\n[");

            for (long i = this.size, j = 0; i > this.size - 50; i--, j++) {
                
                if (j % 10 == 0) {
                    builder.append("\n");
                }

                builder.append(this.get(i) + " ");
            }
        }

        builder.append("\n]");

        return builder.toString();
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

    private boolean extendArray() {
        //BigInteger??
        if (!isFilled) {

            // BigInteger localCapacity = BigInteger.valueOf(this.currentCapacity)
            //                                             .multiply(BigInteger.valueOf(this.SCALING_FACTOR));
    
            // int compareInt = localCapacity.compareTo(BigInteger.valueOf(this.MAX_CAPACITY));
            long localCapacity = this.currentCapacity * this.SCALING_FACTOR;
    
    // перелать!!! ->
            //if (compareInt == 1 || compareInt == 0) {
                if (localCapacity >= this.MAX_CAPACITY) {
                this.currentCapacity = this.MAX_CAPACITY;
                this.isFilled = true;
                this.address = unsafe.reallocateMemory(this.address, this.sizeOfElementInBytes * this.currentCapacity);
            // } else if (compareInt == 0) {
              //  this.isFilled = true;
              //  this.currentCapacity = localCapacity.longValue();
              //  this.address = unsafe.reallocateMemory(this.address, this.sizeOfElementInBytes * this.currentCapacity);
            } else {
                // this.currentCapacity = localCapacity.longValue();
                this.currentCapacity *= this.SCALING_FACTOR;
                this.address = unsafe.reallocateMemory(this.address, this.sizeOfElementInBytes * this.currentCapacity);
            }
    
            
            return true;
        }

        return false; 
        
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

    private long binarySearch(int element) {
        long low = 0;
        long up = this.size - 1;
        long middle;

        do {
            middle = low + (up - low) / 2;
            long midElem = this.get(middle);

            if (element == midElem) {
                return middle;
            }

            if (element < midElem) {
                up = middle - 1;
            }

            if (element > midElem) {
                low = middle + 1;
            }

        } while (low <= up);

            return -1;
    }

    private void quickSort(long low, long up) {

        if (low >= up) {
            return;
        }

        long middle = low + (up - low) / 2;
        long pivot = this.get(middle);

        long i = low;
        long j = up;

        while(i <= j) {
            while(this.get(i) < pivot) {
                i++;
            }

            while(this.get(j) > pivot) {
                j--;
            }

            if (i <= j) {
                int tmp = this.get(i);
                this.setValue(i, this.get(j));
                this.setValue(j, tmp);
                i++;
                j--;
            }
        }

        if (low < j) {
            this.quickSort(low, j);
        }

        if (up > i) {
            this.quickSort(i, up);
        }
            
    }
/*
    private void bubbleSort() {
        for (long i = 0; i < size - 1; i++) {
            for (long j = 0; j < this.size - 1 - i; j++) {
                if (this.get(j) > this.get(j + 1)) {
                    int tmp = this.get(j);
                    this.setValue(j, this.get(j + 1));
                    this.setValue(j + 1, tmp);
                }
            }
        }
    }
*/

    private void checkIfOutOfBounds(long index) {

        if (index > this.size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private boolean checkIfLess75percentLoading() {
        
        if (this.size << 1 < this.currentCapacity >> 1) {
            return true;
        }

        return false;
    }

    private void setValue(long index, int element) {
        checkIfOutOfBounds(index);
        long localAddress = this.address + this.sizeOfElementInBytes * index;
        unsafe.putInt(localAddress, element);
    }

    /* for tests
    public long getCurrentCapacity() {
        return this.currentCapacity;
    }
    */

}