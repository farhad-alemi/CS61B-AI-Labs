import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K,V>{

    private int size;
    private Node root;

    private class Node {
        Node l;
        Node r;

        K key;
        V val;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    public BSTMap() {
        size = 0;
        clear();
    }
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        return getHelp(key, root);
    }
    private V getHelp(K key, Node n) {
        if (n == null) {
            return null;
        }
        int cmp = key.compareTo(n.key);

        if (cmp < 0) {
            return getHelp(key, n.l);
        } else if (cmp > 0) {
            return getHelp(key, n.r);
        } else {
            return n.val;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        root = putHelp(key, value, root);
    }

    Node putHelp(K key, V val, Node n) {
        if (n == null) {
            ++size;
            return new Node (key, val);
        }
        int cmp = key.compareTo(n.key);
        if (cmp < 0) {
            n.l = putHelp(key, val, n.l);
        } else if (cmp > 0) {
            n.r = putHelp(key, val, n.r);
        } else {
            n.val = val;
        }
        return n;
    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public V remove(K key, V value) {
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }
}