package org.mityushov.DIYJavaCollections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
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
        List<Integer> tmp = new ArrayList<>();
        tmp.add(1);
        tmp.add(4);
        tmp.add(7);
        
        assertTrue(this.list.containsAll(tmp));
        tmp.add(20);
        assertFalse(this.list.containsAll(tmp));
        tmp.remove((Object) 20);
        assertTrue(this.list.containsAll(tmp));
        this.list.clear();
        assertFalse((this.list.containsAll(tmp)));
    }

    @Test
    public void addAll() {
        int s = this.list.size();
        List<Integer> tmp = new ArrayList<>();
        tmp.add(1);
        tmp.add(4);
        tmp.add(7);

        this.list.addAll(tmp);
        assertEquals(s + 3, this.list.size());

        List<Integer> tmp1 = new ArrayList<>();
        tmp1.add(35);
        tmp1.add(36);
        tmp1.add(37);
        this.list.addAll(tmp1);

        assertEquals(s + 6, this.list.size());
        assertTrue(this.list.containsAll(tmp));
        assertEquals(Optional.of(37), Optional.ofNullable(this.list.get(this.list.size() - 1)));
        assertEquals(Optional.of(36), Optional.ofNullable(this.list.get(this.list.size() - 2)));
        assertEquals(Optional.of(35), Optional.ofNullable(this.list.get(this.list.size() - 3)));
        assertEquals(Optional.of(7), Optional.ofNullable(this.list.get(this.list.size() - 4)));
        assertEquals(Optional.of(4), Optional.ofNullable(this.list.get(this.list.size() - 5)));
        assertEquals(Optional.of(1), Optional.ofNullable(this.list.get(this.list.size() - 6)));
    }

    @Test
    public void AddAllFromIndex() {
        int s = this.list.size();
        List<Integer> tmp = new ArrayList<>();
        tmp.add(1);
        tmp.add(4);
        tmp.add(7);

        this.list.addAll(2, tmp);
        assertEquals(s + 3, this.list.size());

        assertEquals(Optional.of(1), Optional.ofNullable(this.list.get(2)));
        assertEquals(Optional.of(4), Optional.ofNullable(this.list.get(3)));
        assertEquals(Optional.of(7), Optional.ofNullable(this.list.get(4)));
        assertEquals(Optional.of(2), Optional.ofNullable(this.list.get(5)));
    }

    @Test
    public void removeAll() {
        int s = this.list.size();
        List<Integer> tmp = new ArrayList<>();
        tmp.add(1);
        tmp.add(4);
        tmp.add(7);
        tmp.add(20);

        this.list.removeAll(tmp);
        assertEquals(s - 3, this.list.size());

        tmp.remove((Object) 20);

        assertFalse(this.list.containsAll(tmp));
    }

    @Test
    public void retainAll() {
        int s = this.list.size();
        List<Integer> tmp = new ArrayList<>();
        tmp.add(1);
        tmp.add(4);
        tmp.add(7);
        tmp.add(20);

        this.list.retainAll(tmp);
        assertEquals(3, this.list.size());

        tmp.remove((Object) 20);

        assertTrue(this.list.containsAll(tmp));
        assertFalse(this.list.contains(2));
        assertFalse(this.list.contains(3));
        assertFalse(this.list.contains(6));
    }

    @Test
    public void clear() {
        this.list.clear();
        assertTrue(this.list.isEmpty());
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
        for (int i = 0; i < this.list.size(); i++) {
            assertEquals(i, this.list.indexOf(i));
        }
    }

    @Test
    public void lastIndexOf() {
    }

    @Test
    public void listIterator() {
        // iterator 1 with default constructor
        var iterator1 = this.list.listIterator();
        assertEquals(0, iterator1.nextIndex());
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