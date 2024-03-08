//HashNode
class HashNode<K,V> {
  
  K key;
  V value;

  //HashNode constructor
  public HashNode(K key, V value) {
    this.key = key;
    this.value = value;
  }
}

//HashMap for storing the values 
/*
  The idea behind this data struct is :
  There's an array of HashNodes which contains every pair of key/value currently saved in the map.
  The way this is done is via buckets, every hashnode is basically a single linked list which we can traverse 
  like we would a list (node.next)
*/
class HashMap<K, V> {
  // Array of nodes
  private long operations=0;
  private HashNode<K, V>[] table;
  private int capacity;
  private int size;
  private final HashNode<K, V> DELETED = new HashNode<>(null, null);

//constructor, capacity represents the cache maximum size
  public HashMap(int capacity) {
    this.capacity = capacity;
    this.table = new HashNode[capacity];
    this.size = 0;
  }

  private int getBucketIndex(K key, int probe) {
      return (key.hashCode() + probe) % capacity;
  }

  public void put(K key, V value) {
    int probe = 0;
    int bucketIndex = getBucketIndex(key, probe);
    while (table[bucketIndex] != null && table[bucketIndex] != DELETED && !table[bucketIndex].key.equals(key) && probe == 100) {
      probe++;
      bucketIndex = getBucketIndex(key, probe);
    }
    System.out.println("x");
    System.out.println(bucketIndex);
    if (table[bucketIndex] == null || table[bucketIndex] == DELETED || !table[bucketIndex].key.equals(key)) {
      table[bucketIndex] = new HashNode<>(key, value);
      size++;
    } else {
      table[bucketIndex].value = value;
    }
  }

  public V get(K key) {
    int probe = 0;
    int bucketIndex = getBucketIndex(key, probe);
    while (table[bucketIndex] != null && probe < capacity) {
      if (table[bucketIndex] != DELETED && table[bucketIndex].key.equals(key)) {
        return table[bucketIndex].value;
      }
      probe++;
      bucketIndex = getBucketIndex(key, probe);
    }
    return null;
  }



  public void remove(K key) {
      int probe = 0;
      int bucketIndex = getBucketIndex(key, probe);
      while (table[bucketIndex] != null && probe < capacity) {
          if (table[bucketIndex] != DELETED && table[bucketIndex].key.equals(key)) {
              table[bucketIndex] = DELETED;
              size--;
              return;
          }
          probe++;
          bucketIndex = getBucketIndex(key, probe);
      }
  }


public boolean containsKey(K key) {
  int probe = 0;
  int bucketIndex = getBucketIndex(key, probe);
  while (table[bucketIndex] != null && probe < capacity) {
    // If we find a non-deleted entry with the matching key, return true
    if (table[bucketIndex] != DELETED && table[bucketIndex].key.equals(key)) {
        return true;
    }
    probe++;
    bucketIndex = getBucketIndex(key, probe);
  }
  // If we exhaust the table without finding the key, or find a null (indicating the end of a probe sequence), return false
  return false;
}


public int size() {
  return this.size;
}


}