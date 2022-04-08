package week2.queues.src;

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    private Node first = null;
    private Node last = null;
    private int size = 0;

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("Cannot add null items");
        }

        Node node = new Node();
        node.item = item;
        this.size++;

        if (this.size == 1) {
            this.first = node;
            this.last = node;
        } else {
            Node tmpFirst = this.first;
            this.first = node;
            node.next = tmpFirst;
            tmpFirst.previous = node;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("Cannot add null items");
        }

        Node node = new Node();
        node.item = item;
        this.size++;

        if (this.size == 1) {
            this.first = node;
            this.last = node;
        } else {
            Node tmpLast = this.last;
            this.last = node;
            node.next = null;
            node.previous = tmpLast;
            tmpLast.next = node;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size() == 0) {
            throw new NoSuchElementException("Deque is empty");
        }

        Item firstItem = this.first.item;
        this.first = this.first.next;
        this.size--;

        if (this.size == 0) {
            this.last = null;
        } else {
            this.first.previous = null;
        }

        return firstItem;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size() == 0) {
            throw new NoSuchElementException("Deque is empty");
        }

        Item lastItem = this.last.item;
        this.last = this.last.previous;
        this.size--;

        if (this.size == 0) {
            this.first = null;
        } else {
            this.last.next = null;
        }

        return lastItem;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more objects to iterate through");
            }

            Item item = current.item;
            current = current.next;

            return item;
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque deque = new Deque<>();

        deque.addFirst(1);
        deque.addLast(2);
        deque.addLast(3);

        deque.addFirst(0);
        deque.addFirst(-1);
        deque.addFirst(-2);

        Iterator iterator = deque.iterator();

        while (iterator.hasNext()) {
            StdOut.println(iterator.next() + " ");
        }
    }

}
