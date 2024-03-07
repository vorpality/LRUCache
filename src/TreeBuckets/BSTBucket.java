public class BSTBucket<K extends Comparable<K>, V> {
    private BSTNode<K, V> root;

    public boolean isEmpty() {
        return root == null;
    }

    public void add(K key, V value) {
        root = insert(root, key, value);
    }

    private BSTNode<K, V> insert(BSTNode<K, V> node, K key, V value) {
        if (node == null) {
            return new BSTNode<>(key, value);
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = insert(node.left, key, value);
        } else if (cmp > 0) {
            node.right = insert(node.right, key, value);
        } else {
            node.value = value; // Update value if key already exists
        }
        return node;
    }

    public BSTNode<K, V> get(K key) {
        BSTNode<K, V> node = getNode(root, key);
        return node == null ? null : node;
    }

    private BSTNode<K, V> getNode(BSTNode<K, V> node, K key) {
        while (node != null) {
            int cmp = key.compareTo(node.key);
            if (cmp < 0) node = node.left;
            else if (cmp > 0) node = node.right;
            else return node; // Key found
        }
        return null; // Key not found
    }

    public boolean containsKey(K key) {
        return getNode(root, key) != null;
    }

    public void remove(K key) {
        root = delete(root, key);
    }

    private BSTNode<K, V> delete(BSTNode<K, V> node, K key) {
        if (node == null) return null;
        
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = delete(node.left, key);
        } else if (cmp > 0) {
            node.right = delete(node.right, key);
        } else {
            if (node.right == null) return node.left;
            if (node.left == null) return node.right;

            BSTNode<K, V> t = node;
            node = min(t.right);
            node.right = deleteMin(t.right);
            node.left = t.left;
        }
        return node;
    }

    private BSTNode<K, V> min(BSTNode<K, V> node) {
        if (node.left == null) return node;
        else return min(node.left);
    }

    private BSTNode<K, V> deleteMin(BSTNode<K, V> node) {
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        return node;
    }
}
