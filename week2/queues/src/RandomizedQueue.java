package week2.queues.src;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.queue = (Item[]) new Object[0];
        this.size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return this.size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("Cannot enqueue null item");
        }

        Item[] newQueue = (Item[]) new Object[this.queue.length + 1];

        for (int i = 0; i < this.queue.length; i++) {
            newQueue[i] = this.queue[i];
        }

        this.queue = newQueue;

        queue[size] = item;

        this.size++;
    }

    private int getRandomIndex() {
        return StdRandom.uniform(this.size);
    }

    // remove and return a random item
    public Item dequeue() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Empty queue");
        }

        int randomIndex = this.getRandomIndex();
        Item removed = this.queue[randomIndex];

        this.size--;
        this.queue[randomIndex] = this.queue[this.size];
        this.queue[this.size] = null;

        return removed;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Empty queue");
        }

        return this.queue[this.getRandomIndex()];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator(this.queue, this.size);

    }

    private class ListIterator implements Iterator<Item> {

        private final Item[] iteratorQueue;
        private int iteratorIndex = 0;

        public ListIterator(Item[] queue, int size) {

            iteratorQueue = (Item[]) new Object[size];

            for (int i = 0; i < iteratorQueue.length; i++) {
                iteratorQueue[i] = queue[i];
            }

            // Shuffle iterator queue
            for (int j = 1; j < iteratorQueue.length; j++) {
                int swapIndex = StdRandom.uniform(j + 1);

                Item temp = iteratorQueue[j];
                iteratorQueue[j] = iteratorQueue[swapIndex];
                iteratorQueue[swapIndex] = temp;
            }
        }

        @Override
        public boolean hasNext() {
            return (iteratorIndex < iteratorQueue.length);
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more objects to iterate through");
            }

            Item item = iteratorQueue[iteratorIndex];
            iteratorIndex++;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue randomizedQueue = new RandomizedQueue<>();

        randomizedQueue.enqueue(1);
        randomizedQueue.enqueue(2);
        randomizedQueue.enqueue(3);
        randomizedQueue.enqueue(4);

        Iterator iterator = randomizedQueue.iterator();
        while (iterator.hasNext()) {
            StdOut.println(iterator.next() + " ");
        }
    }

}
