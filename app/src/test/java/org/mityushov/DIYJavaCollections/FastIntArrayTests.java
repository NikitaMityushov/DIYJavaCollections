package org.mityushov.DIYJavaCollections;

import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;


@State(Scope.Thread)
@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class FastIntArrayTests {
    FastIntArray arr;
    final int NUM_OF_ELEM = 100000;

    @Setup
    public void setup() {
        try {
            this.arr = new FastIntArray();
        }  catch (Exception e) {
            e.printStackTrace();
            System.out.println("Все пропало, допрыгался с Unsafe");
        }
    }

    @Benchmark
    public void addNewElementsTest() {

        for (int i = 0; i < NUM_OF_ELEM; i++) {
            this.arr.add(i);
        }

        this.arr.close();

    }
    
}
