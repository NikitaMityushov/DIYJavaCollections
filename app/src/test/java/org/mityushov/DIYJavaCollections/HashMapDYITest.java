package org.mityushov.DIYJavaCollections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HashMapDYITest {
    HashMapDYI<Integer, String> map = new HashMapDYI<>();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void size() {
    }

    @Test
    public void isEmpty() {
    }

    @Test
    public void containsKey() {
    }

    @Test
    public void containsValue() {
    }

    @Test
    public void get() {
    }

    @Test
    public void put() {
        final String item1 = "Ignat Zelepopenko";
        final String item2 = "Vasiliy Strelnikov";
        // 1)
        this.map.put(0, item1);
        assertEquals(item1, this.map.get(0));
        assertNull(this.map.get(15));
        assertEquals(1, this.map.size());
        // 2) same hashcode
        this.map.put(17, item2);
        assertEquals(item2, this.map.get(17));
        assertEquals(2, this.map.size());
    }

    @Test
    public void remove() {

    }

    @Test
    public void putAll() {
    }

    @Test
    public void clear() {
    }

    @Test
    public void keySet() {
    }

    @Test
    public void values() {
    }

    @Test
    public void entrySet() {
    }

    @Test
    public void testToString() {
    }
}