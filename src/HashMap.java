
//HashMap for storing the values 
/*
  The idea behind this data struct is :
  There's an array of HashNodes which contains every pair of key/value currently saved in the map.
  The way this is done is via buckets, every hashnode is basically a single linked list which we can traverse 
  like we would a list (node.next)
*/

class HashMap<K, V> {

  //debugging variables 
  private long operations=0;

  // Array of nodes
  private Bucket<K, V>[] node_list;
  
  private int capacity;
  private int size;

//constructor, capacity represents the cache maximum size
  public HashMap(int capacity) {
    this.capacity = capacity;
    this.node_list = new List[capacity];
    this.size = 0;
  }

  private int getBucketIndex(K key) {
    int hashCode = key.hashCode() % this.capacity;
    return hashCode;
  }

  public void put(K key, V value) {
    int bucketIndex = getBucketIndex(key);
    List<K, V> head = node_list[bucketIndex];

    while (head != null) {
      operations++;
      if (head.key.equals(key)) {
        head.value = value;
        return;
      }
      head = head.next;
    }

    size++;
    head = node_list[bucketIndex];
    List<K, V> newNode = new List<K, V>(key, value);
    newNode.next = head;
    node_list[bucketIndex] = newNode;
  }

  public V get(K key) {
    int bucketIndex = getBucketIndex(key);
    List<K, V> head = node_list[bucketIndex];

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

    //Find the bucket that would contain the key
    int bucketIndex = getBucketIndex(key);
    List<K, V> head = node_list[bucketIndex];
    List<K, V> prev = null;

    // Search for the key the respective bucket
    while (head != null) {
        if (head.key.equals(key))
            break;

        prev = head;
        head = head.next;
    }

    if (head == null) return null;

    // Remove 
    size--;
    if (prev != null) {
        prev.next = head.next;
    } else {
        node_list[bucketIndex] = head.next;
    }

    return head.value;
}

public boolean containsKey(K key) {
    int bucketIndex = getBucketIndex(key);
    List<K, V> head = node_list[bucketIndex];
    while (head != null) {
        if (head.key.equals(key)) {
            return true;
        }
        head = head.next;
    }
    return false;
}

//Debugging
public void printBucketList() {
    System.out.println("HashMap Bucket Distribution:");
    for (int i = 0; i < node_list.length; i++) {
        System.out.print("Bucket " + i + ": ");
        List<K, V> head = node_list[i];
        while (head != null) {
            System.out.print(head.key + " : " + head.value + " || ");
            head = head.next;
        }
        System.out.println();
    }
}

public void printOperations(){
  System.out.println("operations : " + operations);
}

}