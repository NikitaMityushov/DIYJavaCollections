package org.mityushov.DIYJavaCollections;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)

public class AppTest {
    ArrayCollection<Integer> coll = new ArrayCollection<Integer>();
    private final int elements = 100000;

    @Benchmark
    public void AddNewElementsInArrayTest() {
        for (int i = 0; i < elements; i++) {
            coll.add(i);
        }
    }

    @Benchmark
    public void removeElementsFromArrayTest() {
        
    }
}
