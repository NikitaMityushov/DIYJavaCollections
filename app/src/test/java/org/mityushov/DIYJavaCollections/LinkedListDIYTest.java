package org.mityushov.DIYJavaCollections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class LinkedListDIYTest {
    private final LinkedListDIY<Integer> list = new LinkedListDIY<>();

    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < 11; i++) {
            list.add(i);
        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void size() {
        assertEquals(11, list.size() );
    }

    @Test
    public void isEmpty() {
        assertFalse(list.isEmpty());
    }

    @Test
    public void contains() {
        assertFalse(list.contains(11));
        assertTrue(list.contains(8));
    }

    @Test
    public void iterator() {
    }

    @Test
    public void toArray() {
    }

    @Test
    public void testToArray() {
    }

    @Test
    public void addElementToTheEnd() {
        assertEquals(11, this.list.size());
        this.list.add(11);
        assertEquals(12, this.list.size());
    }

    @Test
    public void remove() {
        this.list.remove(0);
        // 1) remove first
        assertEquals(10, this.list.size());
        assertEquals(Optional.of(1), Optional.ofNullable(this.list.get(0)));
        // 2) remove last
        this.list.remove(this.list.size() - 1);
        assertEquals(9, this.list.size());
        assertEquals(Optional.of(9), Optional.ofNullable(this.list.get(this.list.size() - 1)));
        // 3) remove inside
        this.list.remove(5);
        assertEquals(8, this.list.size());
        assertEquals(Optional.of(7), Optional.ofNullable(this.list.get(5)));
        // 3) remove object
        assertTrue(this.list.remove((Object) 8));
        assertEquals(7, this.list.size());
        // assertEquals(Optional.of(9), Optional.ofNullable(this.list.get(6)));
        assertFalse(this.list.remove((Object) 20));

    }

    @Test
    public void containsAll() {
    }

    @Test
    public void addAll() {
    }

    @Test
    public void testAddAll() {
    }

    @Test
    public void removeAll() {
    }

    @Test
    public void retainAll() {
    }

    @Test
    public void clear() {
    }

    @Test
    public void get() {
        assertEquals(Optional.of(8), Optional.ofNullable(this.list.get(8)));
    }

    @Test
    public void set() {
        this.list.set(10, 345);
        assertEquals(Optional.of(345), Optional.ofNullable(this.list.get(10)));
    }

    @Test
    public void AddToIndex() {
        this.list.add(0, 512);
        assertEquals(Optional.of(512), Optional.ofNullable(this.list.get(0)));
        assertEquals(12, this.list.size());
        this.list.add(1, 515);
        assertEquals(Optional.of(515), Optional.ofNullable(this.list.get(1)));
        assertEquals(13, this.list.size());
    }

    @Test
    public void removeFromIndex() {
        this.remove();
    }

    @Test
    public void indexOf() {
    }

    @Test
    public void lastIndexOf() {
    }

    @Test
    public void listIterator() {
        // iterator 1 with default constructor
        var iterator1 = this.list.listIterator();
        assertEquals(1, iterator1.nextIndex());
        assertEquals(0, iterator1.previousIndex());
        assertFalse(iterator1.hasPrevious());
        assertTrue(iterator1.hasNext());

        // check next() and hasNext()
        for (int i = 0; i < this.list.size() + 1; i++) {
            if (iterator1.hasNext()) {
                int tmp = iterator1.next();
                assertEquals(i, tmp);
            }
        }
        // iterator 2 with args constructor
        var iterator2 = this.list.listIterator(this.list.size() - 1);
        // check previous() and hasPrevious()
        for (int i = this.list.size() - 1; i >= -1; i--) {
            if (iterator2.hasPrevious()) {
                var tmp = iterator2.previous();
                assertEquals(Optional.of(i), Optional.of(tmp));
            }
        }
    }

    @Test
    public void ListIteratorFromIndex() {
    }

    @Test
    public void subList() {
    }
}