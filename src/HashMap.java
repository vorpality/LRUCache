/*
  The idea behind this data struct is :
  There's an array of HashNodes which contains every pair of key/value currently saved in the map.
  The way this is done is via buckets, every hashnode is basically a single linked list which we can traverse 
  like we would a list (node.next)
*/

// HashNode 
class HashNode<K,V> {
  
  K key;
  V value;
  int trailingSize;
  HashNode <K,V> next;

  // HashNode Constructor
  public HashNode(K key, V value) {
    this.key = key;
    this.value = value;
    this.trailingSize = 0;
  }
}

// HashMap for storing the values 
class HashMap<K, V> {
  // Array of nodes
  private HashNode<K, V>[] node_list;
  private int capacity;
  private int size;

  // Constructor, capacity represents the cache maximum size
  public HashMap(int capacity) {
    this.capacity = capacity;
    this.node_list = new HashNode[capacity];
    this.size = 0;
  }

  // Hashing function, used java.object hashCode()
  private int getBucketIndex(K key) {
    int hashCode = key.hashCode() % this.capacity; // M is total size of cache.
    return hashCode;
  }

  // Inserting operation
  public void put(K key, V value) {
    int bucketIndex = getBucketIndex(key);
    // Instead of an actual list, the bucket is the head of a list pointing to the other elements
    HashNode<K, V> head = node_list[bucketIndex]; 

    // Searches for key, if it exists in the Bucket, updates the value.
    while (head != null) {
      if (head.key.equals(key)) {
        head.value = value;
        return;
      }
      head = head.next;
    }
    // If not, adds new element as head which points to previous head.
    size++;
    HashNode<K, V> previousHead = node_list[bucketIndex];
    HashNode<K, V> newNode = new HashNode<K, V>(key, value);
    newNode.next = previousHead;
    //Size calculation (for bucket maths)
    if (previousHead != null) {
      newNode.trailingSize = previousHead.trailingSize+1;
    }
    node_list[bucketIndex] = newNode;
  }


  // Selecting operation
  public V get(K key) {
    int bucketIndex = getBucketIndex(key);
    HashNode<K, V> head = node_list[bucketIndex];

    while (head != null) {
      if (head.key.equals(key)) {
        return head.value;
      }
      head = head.next;
    }

    return null;
  }

  // Removing operation
  public V remove(K key) {
    int bucketIndex = getBucketIndex(key);
    HashNode<K, V> head = node_list[bucketIndex];
    HashNode<K, V> prev = null;

    // If reached the end or still looking, iterate
    // Checking order saves the null comparison
    while (head != null && !head.key.equals(key)) {
        prev = head;
        head = head.next;
    }

    if (head == null) return null;
    size--;

    // Remove the node 

    //(Link previous node to next unless we're removing head because prev == null in that case)
    // If removing head
    if (prev == null) {
      node_list[bucketIndex] = head.next;
    }
    // If not head
    else {
        prev.next = head.next;
    } 

    return head.value;
}

  // Checks if key exists in map. 
  public boolean containsKey(K key) {
    int bucketIndex = getBucketIndex(key);
    HashNode<K, V> head = node_list[bucketIndex];
    while (head != null) {
      if (head.key.equals(key)) {
        return true;
      }
      head = head.next;
    }
    return false;
  }

  /* Note :
    Some form of the method get() could have been used for all 
    put(), remove(), containsKey() methods, but it's been avoided
    for clarity
  */

  // Size getter
  public int size(){
    return this.size;
  }
  
  // Calculates max bucket size over the iteration.
  // Used for calculating the worth of switching to 
  // a BST type structure instead of a List type structure.
  public void printMaxBucketSize() {
    int maxBucketSize = 0;
    for (int i = 0; i < node_list.length; i++) {
      if (node_list[i] != null && node_list[i].trailingSize > maxBucketSize)  maxBucketSize = node_list[i].trailingSize;
    }
    System.out.println("Max bucket size is : " + maxBucketSize + " elements.");
  }
}