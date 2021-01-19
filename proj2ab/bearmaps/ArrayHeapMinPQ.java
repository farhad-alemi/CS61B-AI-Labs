package bearmaps;

import java.util.ArrayList;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T>{

    private class Node {
        Node(T item, double priority) {
            this.item = item;
            this.priority = priority;
        }

        T item() {
            return item;
        }

        double priority() {
            return priority;
        }

        T item;
        double priority;
    }

    @Override
    public void add(T item, double priority) {
        int index = contents.size();
        setNode(index, new Node(item, priority));
        bubbleUp(index);
    }

    @Override
    public boolean contains(T item) {
        //fixme
        return false;
    }

    @Override
    public T getSmallest() {
        return (getNode(1) != null) ? getNode(1).item() : null;
    }

    @Override
    public T removeSmallest() {
        if (contents.size() <= 1) {
            return null;
        }
        Node minNode;

        swap(1, contents.size() - 1);
        minNode = removeNode(contents.size() - 1);
        bubbleDown(1);
        return (minNode != null) ? minNode.item() : null;
    }

    @Override
    public int size() {
        return contents.size() - 1;
    }

    @Override
    public void changePriority(T item, double priority) {
        boolean found = false;
        int index = contents.size(), smallestChild;

        if (contents.size() > 1/* && priority >= getNode(1).priority()*/) {
            for (int i = 1; i < contents.size(); ++i) {
                if (item.equals(getNode(i).item())) {
                    index = i;
                    found = true;
                    setNode(index, new Node(item, priority));
                    break;
                }
            }
            if (found) {
                if (getNode(getRightOf(index)) == null
                        && getNode(getLeftOf(index)) == null) {
                    bubbleUp(index);
                } else if (index == 1) {
                    bubbleDown(index);
                }
                smallestChild = min(getRightOf(index), getLeftOf(index));
                if (index == min(index, smallestChild)) {
                    bubbleUp(index);
                } else {
                    bubbleDown(index);
                }
            }
        }
    }

    public ArrayHeapMinPQ() {
        contents = new ArrayList<>();
        contents.add(null);
    }

    /** Bubbles up the node currently at the given index until no longer
     *  needed. */
    private void bubbleUp(int index) {
        if (contents.size() <= 2) {
            return;
        }
        while (min(index, getParentOf(index)) == index && index > 1) {
            swap(index, getParentOf(index));
            index = getParentOf(index);
        }
    }

    /** Bubbles down the node currently at the given index until no longer
     *  needed. */
    private void bubbleDown(int index) {
        if (contents.size() <= 2) {
            return;
        }
        int smallestChild = min(getRightOf(index), getLeftOf(index));
        while (min(index, smallestChild) != index) {
            swap(index, smallestChild);
            index = smallestChild;
            if (getNode(getRightOf(index)) != null && getNode(getLeftOf(index)) != null) {
                smallestChild = min(getRightOf(index), getLeftOf(index));
            }
        }
    }

    /** Swap the nodes at the two indices. */
    private void swap(int index1, int index2) {
        Node node1 = getNode(index1);
        Node node2 = getNode(index2);
        this.contents.set(index1, node2);
        this.contents.set(index2, node1);
    }

    /** Returns the node at index INDEX. */
    private Node getNode(int index) {
        if (index >= contents.size()) {
            return null;
        } else {
            return contents.get(index);
        }
    }

    /** Sets the node at INDEX to N */
    private void setNode(int index, Node n) {
        // In the case that the ArrayList is not big enough
        // add null elements until it is the right size
        while (index + 1 > contents.size()) {
            contents.add(null);
        }
        contents.set(index, n);
    }

    /** Returns and removes the node located at INDEX. */
    private Node removeNode(int index) {
        if (index >= contents.size()) {
            return null;
        } else {
            return contents.remove(index);
        }
    }

    /** Returns the index of the left child of the node at i. */
    private int getLeftOf(int i) {
        return 2 * i;
    }

    /** Returns the index of the right child of the node at i. */
    private int getRightOf(int i) {
        return 2 * i + 1;
    }

    /** Returns the index of the node that is the parent of the
     *  node at i. */
    private int getParentOf(int i) {
        return i / 2;
    }

    /** Returns the index of the node with smaller priority. If one
     * node is null, then returns the index of the non-null node.
     * Precondition: at least one of the nodes is not null. */
    private int min(int index1, int index2) {
        if (getNode(index1) == null) {
            return index2;
        } else if (getNode(index2) == null) {
            return index1;
        } else {
            return (getNode(index1).priority() < getNode(index2).priority())
                    ? index1 : index2;
        }
    }

    ArrayList<Node> contents;
    int size = 0;
}
