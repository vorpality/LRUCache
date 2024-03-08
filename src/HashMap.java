//HashNode
class HashNode<K,V> {
  
  K key;
  V value;
  int trailingSize;
  HashNode <K,V> next;

  //HashNode constructor
  public HashNode(K key, V value) {
    this.key = key;
    this.value = value;
    this.trailingSize = 0;
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
  private HashNode<K, V>[] node_list;
  private int capacity;
  private int size;
  private int maxBucketSize = 0;
//constructor, capacity represents the cache maximum size
  public HashMap(int capacity) {
    this.capacity = capacity;
    this.node_list = new HashNode[capacity];
    this.size = 0;
  }

  private int getBucketIndex(K key) {
    int hashCode = key.hashCode() % this.capacity;
    return hashCode;
  }

  public void put(K key, V value) {
    int bucketIndex = getBucketIndex(key);
    HashNode<K, V> head = node_list[bucketIndex];

    while (head != null) {
      if (head.key.equals(key)) {
        head.value = value;
        return;
      }
      head = head.next;
     
    }

    size++;
    head = node_list[bucketIndex];
    HashNode<K, V> newNode = new HashNode<K, V>(key, value);
    newNode.next = head;
    if (head != null) newNode.trailingSize = head.trailingSize+1 ;
    node_list[bucketIndex] = newNode;
  }

  public V get(K key) {
    int bucketIndex = getBucketIndex(key);
    HashNode<K, V> head = node_list[bucketIndex];

    while (head != null) {
      operations++;
      if (head.key.equals(key)) {
        return head.value;
      }
      head = head.next;
    }

    return null;
  }

  public int size() {
    return this.size;
  }

  public V remove(K key) {
    int bucketIndex = getBucketIndex(key);
    HashNode<K, V> head = node_list[bucketIndex];
    HashNode<K, V> prev = null;

    // Search for the key in its linked list ("chain")
    while (head != null) {
        if (head.key.equals(key))
            break;

        prev = head;
        head = head.next;
    }

    // If the key was not found
    if (head == null) return null;

    // Reduce the size of the hashmap
    size--;

    // Remove the node
    if (prev != null) {
        prev.next = head.next; // Bypass the head node if it's not at the start
    } else {
        // Move the bucket's head to the next node
        node_list[bucketIndex] = head.next;
    }

    return head.value;
}

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

public void printBucketList() {
    System.out.println("HashMap Bucket Distribution:");
    for (int i = 0; i < node_list.length; i++) {
        System.out.print("Bucket " + i + ": ");
        HashNode<K, V> head = node_list[i];
        while (head != null) {
            System.out.print("(" + head.key + ", " + head.value + ") ");
            head = head.next;
        }
        System.out.println(); // Move to the next line after printing all nodes in the bucket
    }
}

public void printOperations(){
  System.out.println("operations : " + operations);
}

public void printMaxBucketSize() {
  for (int i = 0; i < node_list.length; i++) {
    if (node_list[i] != null && node_list[i].trailingSize > maxBucketSize)  maxBucketSize = node_list[i].trailingSize;
  }
  System.out.println("Max bucket size is : " + maxBucketSize + " elements.");
}
}