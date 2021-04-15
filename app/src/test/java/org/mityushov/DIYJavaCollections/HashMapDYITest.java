package org.mityushov.DIYJavaCollections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class HashMapDYITest {
    HashMapDYI<Integer, String> map = new HashMapDYI<>();
    final String item1 = "Ignat Zelepopenko";
    final String item2 = "Vasiliy Strelnikov";

    @Before
    public void setUp() throws Exception {
        this.map.put(0, item1);
        this.map.put(17, item2);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void size() {
        assertEquals(2, this.map.size());
        this.map.put(12, "Her s bugra");
        assertEquals(3, this.map.size());
    }

    @Test
    public void isEmpty() {
        assertFalse(this.map.isEmpty());
        this.map.clear();
        assertTrue(this.map.isEmpty());
        assertNull(this.map.get(0));
        assertNull(this.map.get(17));
    }

    @Test
    public void containsKey() {
        assertTrue(this.map.containsKey(0));
        assertTrue(this.map.containsKey(17));
        assertFalse(this.map.containsKey(18));
        String item3 = "her s bugra";
        this.map.put(18, item3);
        assertTrue(this.map.containsKey(18));
    }

    @Test
    public void containsValue() {
        assertTrue(this.map.containsValue(item1));
        assertTrue(this.map.containsValue(item2));
        String item3 = "her s bugra";
        assertFalse(this.map.containsValue(item3));
        this.map.put(18, item3);
        assertTrue(this.map.containsValue(item3));
    }

    @Test
    public void get() {
        assertEquals(item1, this.map.get(0));
        assertEquals(item2, this.map.get(17));
        String item3 = "her s bugra";
        this.map.put(18, item3);
        assertEquals(item3, this.map.get(18));
    }

    @Test
    public void put() {
        final String item3 = "Raja Naingollan";
        final String item4 = "Franchesko Totti";
        final String item5 = "Danielle De Rossi";

        // 1)
        this.map.put(10, item4);
        assertEquals(item1, this.map.get(0));
        assertNull(this.map.get(15));
        assertEquals(3, this.map.size());
        // 2) same hashcode
        this.map.put(4, item3);
        assertEquals(item2, this.map.get(17));
        assertEquals(4, this.map.size());
        assertEquals(item4, this.map.get(10));
        // 3) rewrite key
        this.map.put(4, item5);
        assertEquals(item5, this.map.get(4));
        assertEquals(4, this.map.size());


    }

    @Test
    public void remove() {
        assertEquals(item1, this.map.remove(0));
        assertEquals(1, this.map.size());
        assertEquals(item2, this.map.remove(17));
        assertEquals(0, this.map.size());
        assertNull(this.map.remove(18));
        String item3 = "her s bugra";
        this.map.put(18, item3);
        assertEquals(item3, this.map.remove(18));
    }

    @Test
    public void putAll() {
    }

    @Test
    public void clear() {
        this.map.clear();
        assertTrue(this.map.isEmpty());
    }

    @Test
    public void keySet() {
    }

    @Test
    public void values() {
        Collection<String> values = this.map.values();
        assertFalse(values.isEmpty());
        var record = values.stream().map(Object::toString).collect(Collectors.joining("\n"));
        assertTrue(record.contains(item1));
        assertTrue(record.contains(item2));

        this.map.clear();
        values = this.map.values();
        assertTrue(values.isEmpty());

    }

    @Test
    public void entrySet() {
        // 1) set with items
        var entrySet = this.map.entrySet();
        assertFalse(entrySet.isEmpty());
        entrySet.forEach(System.out :: println);
        var record = entrySet.stream().map(Object::toString).collect(Collectors.joining("\n"));
        assertTrue(record.contains("1"));
        assertTrue(record.contains("17"));
        assertTrue(record.contains(item1));
        assertTrue(record.contains(item2));
        // 2) empty set
        this.map.clear();
        entrySet = this.map.entrySet();
        assertTrue(entrySet.isEmpty());
    }

    @Test
    public void testToString() {
    }
}