import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class MyHashMap<K, V> implements Map61B<K, V> {
    static final double LOAD_FACTOR = 0.75;
    static final int INITIAL_SIZE = 16;

    class Node {
        K key;
        V val;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    public MyHashMap(int initialSize, double loadFactor) {
        buckets = (LinkedList<Node>[]) new LinkedList<?>[initialSize];
        lf = loadFactor;
        size = 0;
        keySet = new HashSet<>();

        for (int i = 0; i < buckets.length; ++i) {
            buckets[i] = new LinkedList<>();
        }

        int inisize = initialSize;
    }

    public MyHashMap(int initialSize) {
        this(initialSize, LOAD_FACTOR);
    }

    public MyHashMap() {
        this(INITIAL_SIZE, LOAD_FACTOR);
    }

    @Override
    public Iterator<K> iterator() {
        return keySet.iterator();
    }

    @Override
    public void clear() {
        buckets = (LinkedList<Node>[]) new LinkedList<?>[INITIAL_SIZE];
        size = 0;
        keySet = new HashSet<>();
    }

    @Override
    public boolean containsKey(K key) {
        return keySet.contains(key);
    }

    @Override
    public V get(K key) {
        Node n = getNode(key);

        if (n == null) {
            return null;
        }
        return n.val;
    }

    private Node getNode(K key) {
        if (size != 0) {
            int index = Math.floorMod(key.hashCode(), buckets.length);

            LinkedList<Node> bucket = buckets[index];
            for (Node node : bucket) {
                if (node.key.equals(key)) {
                    return node;
                }
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        Node node = getNode(key);

        if (node != null) {
            node.val = value;
        } else {
            if (((double) size) / buckets.length > lf) {
                resize(buckets.length * 2);
            }
            ++size;
            keySet.add(key);
            int index = Math.floorMod(key.hashCode(), buckets.length);
            buckets[index].add(new Node(key, value));
        }
    }

    private void resize(int newSize) {
        LinkedList<Node>[] oldBuckets = buckets;
        buckets = (LinkedList<Node>[]) new LinkedList<?>[newSize];

        for (int i = 0; i < buckets.length; ++i) {
            buckets[i] = new LinkedList<>();
        }
        size = 0;

        for (LinkedList<Node> lst : oldBuckets) {
            for (Node node : lst) {
                put(node.key, node.val);
            }
        }
    }

    @Override
    public Set<K> keySet() {
        return keySet;
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public V remove(K key, V value) {
        return null;
    }

    LinkedList<Node>[] buckets;
    double lf;
    HashSet<K> keySet;
    int size;
}
