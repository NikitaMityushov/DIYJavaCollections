package org.mityushov.DIYJavaCollections;
// 18.04.21
// 1) lastIndexOf(Object)
// 2) subList(int, int)
// 3) toArray()
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
/**
 * doubly linked list
 * implements List API
 * @param <E>
 * created by Mityushov Nikita
 */
public class LinkedListDIY<E> implements List<E> {
    private int size = 0;
    private Node<E> first;
    private Node<E> last;
    /**
     *
     * @param <E>
     */
    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;
        
        Node(E item, Node<E> next, Node<E> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }

        @Override
        public String toString() {
            return item.toString();
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean contains(Object o) {

        for (E e : this) {
            if (o.equals(e)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new InternalIterator(this.first);
    }
 // 1
    @Override
    public Object[] toArray() {
        return new Object[0];
    }
// 2
    @Override
    public <T> T[] toArray(T[] ts) {
        return null;
    }

    @Override
    public boolean add(final E element) {
        if (isEmpty()) {
            first = last = new Node<>(element, null, null);
        } else {
            last.next = new Node<>(element, null, last);
            last = last.next;
        }
        this.size++;
        return true;
    }
    /**
     * remove first object o encountered in the list
     * @return boolean
     */
    @Override
    public boolean remove(final Object o) {
        return this.removeIf(x -> x.equals(o));
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object item : collection) {
            if (!this.contains(item)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        collection.forEach(this::add);
        return true;
    }

    @Override
    public boolean addAll(int i, Collection<? extends E> collection) {
        var iterator = this.listIterator(i);
        collection.forEach(iterator::add);
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        collection.forEach(this::remove);
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return this.removeIf(item -> !collection.contains(item));
    }

    @Override
    public void clear() {
        this.size = 0;
        this.first = null;
        this.last = null;
    }

    @Override
    public E get(int i) {
        var iterator = this.listIterator(i);
        return iterator.next();
    }

    @Override
    public E set(int i, E e) {
        var iterator = this.listIterator(i);
        var tmp = this.get(i);
        iterator.set(e);
        return tmp;
    }

    @Override
    public void add(int i, E e) {
        var iterator = this.listIterator(i);
        iterator.add(e);
    }

    @Override
    public E remove(int i) {
        var item = this.get(i);
        ListIterator<E> iterator = this.listIterator(i);
        iterator.remove();
        return item;
    }

    @Override
    public int indexOf(Object o) {
        ListIterator<E> iterator = this.listIterator();
        while (iterator.hasNext()) {
            var item = iterator.next();
            if (item.equals(o)) {
                return iterator.previousIndex();
            }
        }
        return -1;
    }
// 9
    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new InternalListIterator();
    }

    @Override
    public ListIterator<E> listIterator(int i) {
        return new InternalListIterator(i) {
        };
    }
// 10
    @Override
    public List<E> subList(int i, int i1) {
        return null;
    }
    /**
     * private class for iterator() method of Iterable interface
     */
    private class InternalIterator implements Iterator<E> {
        private Node<E> current;
        private Node<E> prev; // for remove() method

        InternalIterator(final Node<E> first) {
            this.current = first;
        }

        @Override
        public boolean hasNext() {
            return this.current != null;
        }

        @Override
        public E next() {
            var tmp = this.current.item;
            this.prev = this.current;
            this.current = this.current.next;
            return tmp;
        }
        /**
         * @Override default void remove() method of Iterator
         * this method works only with remove(Object o) method
         */
        @Override
        public void remove() {
            var tmp = this.prev;

            if (tmp.prev == null) {
                LinkedListDIY.this.removeFirst();
                return;
            }
            if (tmp.next == null) {
                LinkedListDIY.this.removeLast();
                return;
            }

            tmp.next.prev = tmp.prev;
            tmp.prev.next = tmp.next;

            LinkedListDIY.this.size--;
        }
    }
    /**
     *
     */
    private class InternalListIterator implements ListIterator<E> {
        private int index;
        private Node<E> current;

        InternalListIterator() {
            this.index = 0;
            this.current = LinkedListDIY.this.first;
        }

        InternalListIterator(int i) {
            this.index = i;
            Node<E> tmp = LinkedListDIY.this.first;

            if (LinkedListDIY.this.size <= index || index < 0 || tmp == null) { // ???
                throw new IndexOutOfBoundsException();
            }

            for (int idx = 0; idx < i; idx++) {
                tmp = tmp.next;
            }

            this.current = tmp;
        }

        @Override
        public boolean hasNext() {
            return LinkedListDIY.this.size > this.index;
        }

        @Override
        public E next() {
            var tmp = this.current.item;
            this.current = this.current.next;
            this.index++;
            return tmp;
        }

        @Override
        public boolean hasPrevious() {
            return this.index > 0;
        }

        @Override
        public E previous() {
            var tmp = this.current.item;
            this.current = this.current.prev;
            this.index--;
            return tmp;
        }

        @Override
        public int nextIndex() {
            return this.index;
        }

        @Override
        public int previousIndex() {
            return this.hasPrevious() ? this.index - 1 : this.index;
        }

        @Override
        public void remove() {
            Node<E> tmp = this.current;

            if (tmp.prev == null) {
                LinkedListDIY.this.removeFirst();
                return;
            }
            if (tmp.next == null) {
                LinkedListDIY.this.removeLast();
                return;
            }

            tmp.next.prev = tmp.prev;
            tmp.prev.next = tmp.next;

            this.current = this.current.next;
            LinkedListDIY.this.size--;
        }

        @Override
        public void set(E e) {
            this.current.item = e;
        }

        @Override
        public void add(E element) {
            Node<E> newItem;
            // add first
            if (this.current.prev == null) {
                newItem = new Node<>(element, this.current, null);
                this.current.prev = newItem;
                LinkedListDIY.this.first = newItem;
                // add inside
            } else {
                newItem = new Node<>(element, this.current, this.current.prev);
                this.current.prev.next = this.current.prev = newItem;
            }

            LinkedListDIY.this.size++;
        }
    }

    private void removeFirst() {
        if (this.size == 1) {
            this.clear();
        } else {
            this.first = this.first.next;
            this.first.prev = null;
            this.size--;
        }
    }

    private void removeLast() {
        if (this.size == 1) {
            this.clear();
        } else {
            this.last = this.last.prev;
            this.last.next = null;
            this.size--;
        }
    }
}