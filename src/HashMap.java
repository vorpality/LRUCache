public class HashMap<K, V> {
  private List<K, V>[] buckets;
  private int capacity;
  private int size;

  private long operations = 0;

  public HashMap(int capacity) {
    this.capacity = capacity;
    this.buckets = (List<K, V>[]) new List[capacity];
    this.size = 0;
  }

  private int getBucketIndex(K key) {
    int hashCode = key.hashCode();
    return Math.abs(hashCode) % capacity;
  }

  public ListNode<K,V> put(K key, V value) {
    int bucketIndex = getBucketIndex(key);
    if (buckets[bucketIndex] == null) {
      buckets[bucketIndex] = new List<>();
    }
    size++;
    return buckets[bucketIndex].insertAtBack(key, value);
  }

  public ListNode<K,V> get(K key) {
    int bucketIndex = getBucketIndex(key);
    if (buckets[bucketIndex] == null) {
        return null;
    }
    return buckets[bucketIndex].findEntry(key);
  }

  public void remove(ListNode<K,V> node) {
    K key = node.getKey();
    int bucketIndex = getBucketIndex(key);
    if (buckets[bucketIndex] == null) {
      return;
    }
    ListNode<K,V> toBeRemoved = buckets[bucketIndex].findEntry(key);
    if (toBeRemoved != null) {
      buckets[bucketIndex].remove(key);
      size--;
    }
    return;
  }

  public int size() {
      return this.size;
  }

  // Debugging methods
  public void printBucketList() {
    System.out.println("HashMap Bucket Distribution:");
    for (int i = 0; i < capacity; i++) {
      System.out.print("Bucket " + i + ": ");
      if (buckets[i] != null) {
        buckets[i].printList();
      } else {
        System.out.print("empty");
      }
      System.out.println(); // New line for each bucket
    }
  }

  public void printOperations() {
    System.out.println("Operations: " + operations);
  }
}
