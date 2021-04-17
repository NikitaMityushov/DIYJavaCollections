package org.mityushov.DIYJavaCollections;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

public class HashMapDIY_JMH_Test {
    HashMapDYI<Integer, String> map = new HashMapDYI<>();

    @Benchmark
    public void measureName() {
        for (int i = 0; i < 10000; i++) {
            this.map.put(i, String.valueOf(i));
        }
    }
}
