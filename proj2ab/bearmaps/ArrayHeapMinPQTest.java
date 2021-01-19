package bearmaps;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ArrayHeapMinPQTest {

    /** Adds and removes one item. **/
    @Test
    public void addOne() {
        ArrayHeapMinPQ<String> hp = new ArrayHeapMinPQ<>();
        hp.add("Paul", 100);
        assertEquals("Size should be 1.", 1, hp.size());
        assertEquals("Min item should be \"Paul\" when getSmallesting.",
                "Paul", hp.getSmallest());
        assertEquals("Min item should be \"Paul\" when removing.",
                "Paul", hp.removeSmallest());
    }

    /** Adds and removes four items with ascending priority values. **/
    @Test
    public void addAscending() {
        ArrayHeapMinPQ<String> hp = new ArrayHeapMinPQ<>();
        String[] items = {"Paul", "Josh", "John", "Dan"};
        int[] priorities = {1, 2, 3, 4};
        for (int i = 0; i < items.length; i++) {
            hp.add(items[i], priorities[i]);
        }
        assertEquals("Size should be 4", 4, hp.size());
        for (int i = 0; i < items.length; i++) {
            assertEquals(String.format("%d-th getSmallest call", i),
                    items[i], hp.getSmallest());
            assertEquals(String.format("%d-th removeSmallest call", i),
                    items[i], hp.removeSmallest());
        }
    }

    /** Adds and removes four items with descending priority values. **/
    @Test
    public void addDescending() {
        ArrayHeapMinPQ<String> hp = new ArrayHeapMinPQ<>();
        String[] items = {"Itai", "Zoe", "Shivani", "Omar"};
        int[] priorities = {4, 3, 2, 1};
        for (int i = 0; i < items.length; i++) {
            hp.add(items[i], priorities[i]);
        }
        assertEquals("Size should be 4", 4, hp.size());
        for (int i = 0; i < items.length; i++) {
            assertEquals(String.format("%d-th getSmallest call", i),
                    items[items.length - 1 - i], hp.getSmallest());
            assertEquals(String.format("%d-th removeSmallest call", i),
                    items[items.length - 1 - i], hp.removeSmallest());
        }
    }

    /** Inserts and removes 8 items in no particular order. Each item's
     * priority value is itself. **/
    @Test
    public void addMany() {
        ArrayHeapMinPQ<Integer> hp = new ArrayHeapMinPQ<>();
        int[] items = {2, 9, -7, 3, 1, -1, 10, 8};
        int[] sortedItems = {-7, -1, 1, 2, 3, 8, 9, 10};
        for (int item: items) {
            hp.add(item, item);
        }
        for (int i = 0; i < sortedItems.length; i++) {
            assertEquals(String.format("removing %d-th item", i),
                    (Integer) sortedItems[i], hp.removeSmallest());
        }
    }

    /** Ensures that removeSmallest and getSmallest return null if heap is empty. **/
    @Test
    public void removeSmallestPeekNull() {
        ArrayHeapMinPQ<String> hp = new ArrayHeapMinPQ<>();
        hp.add("Pie", 314);
        assertEquals("Removing min", "Pie", hp.removeSmallest());
        assertNull(hp.getSmallest());
        assertNull(hp.removeSmallest());
    }

    /** Changes the priority value of one item to be higher. **/
    @Test
    public void changePriorityIncreaseOne() {
        ArrayHeapMinPQ<String> hp = new ArrayHeapMinPQ<>();
        String[] items = {"Hashmap", "Linked list", "LLRB tree",
                "Deque", "Graph"};
        int[] priorities = {1, 2, 3, 4, 5};
        for (int i = 0; i < items.length; i++) {
            hp.add(items[i], priorities[i]);
        }
        hp.changePriority("LLRB tree", 4.5);
        String[] expected = {"Hashmap", "Linked list", "Deque",
                "LLRB tree", "Graph"};
        for (int i = 0; i < items.length; i++) {
            assertEquals(String.format("%d-th removeSmallest call", i),
                    expected[i], hp.removeSmallest());
        }
    }

    /** Changes the priority value of one item to be lower. **/
    @Test
    public void changePriorityDecreaseOne() {
        ArrayHeapMinPQ<String> hp = new ArrayHeapMinPQ<>();
        String[] items = {"Hashmap", "Linked list", "LLRB tree",
                "Deque", "Graph"};
        int[] priorities = {1, 2, 3, 4, 5};
        for (int i = 0; i < items.length; i++) {
            hp.add(items[i], priorities[i]);
        }
        hp.changePriority("LLRB tree", 0);
        String[] expected = {"LLRB tree", "Hashmap", "Linked list",
                "Deque", "Graph"};
        for (int i = 0; i < items.length; i++) {
            assertEquals(String.format("%d-th removeSmallest call", i),
                    expected[i], hp.removeSmallest());
        }
    }

    /** Inserts 8 integers with their priorities as themselves.
     * Then changes each of their priorities to be the negation
     * of their original priority. **/
    @Test
    public void changePriorityAll() {
        ArrayHeapMinPQ<Integer> hp = new ArrayHeapMinPQ<>();
        int[] items = {2, 9, -7, 3, 1, -1, 10, 8};
        int[] sortedItems = {-7, -1, 1, 2, 3, 8, 9, 10};
        for (int item: items) {
            hp.add(item, item);
        }
        for (int item: items) {
            hp.changePriority(item, -1 * item);
        }

        /*ArrayHeapMinPQ<Integer> hp1 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < sortedItems.length; ++i) {
            hp1.add(sortedItems[i], -1*items[i]);
        }*/

        for (int i = 0; i < sortedItems.length; i++) {
            assertEquals(String.format("removing %d-th item", i),
                    (Integer) sortedItems[sortedItems.length - 1 - i],
                    hp.removeSmallest());
        }
    }
}

