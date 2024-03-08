/* Note : 
  All actually irrelevant operations have been commented out.
  They have not been removed for future reference.
*/

class CacheLRU<K, V> implements Cache<K,V> {
  private final int capacity;
  private HashMap<K, LinkedValue> map;
  private LinkedValue head, tail;
  private int hits;
  private int misses;

  // Track keepers for structure comparison.
  private long lookupTime = 0;
  private long storeTime = 0;
  private int storeCounter = 0;

//Doubly linked list node (for timestamp keeping) and keeping node immediate connections
  class LinkedValue {
    K key;
    V value;
    LinkedValue previous, next;
    //long timestamp;

// Doubly linked list Node constructor
    LinkedValue(K key,V value) {
      this.key = key;
      this.value = value;
      //this.timestamp = System.currentTimeMillis();
    }
  }

// Cache constructor, capacity corresponds to cache size. 
  public CacheLRU(int capacity) {
    this.capacity = capacity;
    this.map = new HashMap<>(capacity);
  }

// Renew access timestamp(move element to front of the list)
/*
  This method may look a bit complicated.
  It returns if the node in question is already the most recent once.
  If it's the last element in the List, it makes the previous element the new tail.
  Otherwise it connects previous and next elements.
  The checks before reconnecting previous and next elements during one that is not head or tail
  are to avoid taking too many cases for the first values of the list.
  
  Note:
  The actual use of the timestamp variable is actually irrelevant 
  since on each access the element will be moved to the front of the list.
*/
  private void renew(LinkedValue node) {
    //node.timestamp = System.currentTimeMillis();
    if (node == head) {
      return;
    }
    else if (node == tail) {
      tail = node.previous;
    }
    else {
      if (node.previous != null) node.previous.next = node.next;
      if (node.next != null) node.next.previous = node.previous;
    }


    node.next = head;
    if (head != null) head.previous = node;
    head = node;
    node.previous = null;

    if (tail == null) tail = node;
  }

//eviction of LRU
/*
  If current size > capacity, removes last used key (tail of the dll)
*/
  private void evict() {
    if (map.size() > capacity) {
      map.remove(tail.key);
      if (tail.previous != null) {
        tail.previous.next = null;
        tail = tail.previous;
      }
    }
  }

//add new key to cache
/* 
  If key doesn't exist in the HashMap, adds it and if size is over capacity, removes last visited element.
  If key exists, moves it to the front 
*/
  @Override
  public void store(K key, V value) {
    storeCounter ++;
    storeTime -= System.nanoTime();
    LinkedValue node = map.get(key);
    if (node == null) {
      node = new LinkedValue(key,value);
      map.put(key, node);
      renew(node);
      evict();
    } else {
      node.value = value;
      renew(node);
    }
    storeTime+=System.nanoTime();
  }

//find key in cache 
/*
  If key exists, renews timestamp(moves it to the front of the list) and counts a hit
  If key doens't exist, counts a miss
*/
  @Override
  public V lookUp(K key) {
    lookupTime -= System.nanoTime();
    LinkedValue node = map.get(key);
    if (node != null) {
      this.lookupTime += System.nanoTime();
      renew(node);
      this.hits++;
      return node.value;
    }
    this.lookupTime += System.nanoTime();
    this.misses++;
    return null;
  }

// Getters
  @Override
  public double getHitRatio(){
    return (double)this.hits/(this.hits + this.misses);
  }

  @Override
	public long getHits(){
    return this.hits;
  }

  @Override
	public long getMisses(){
    return this.misses;
  }

  @Override
  public long getNumberOfLookUps(){
      return this.hits + this.misses;
  }

  // Comparison printer
  @Override
  public void printOperations() { 
    System.out.println("Avg find speed : " + lookupTime/getNumberOfLookUps() +" ns");
    System.out.println("Avg store speed : " + storeTime/storeCounter + " ns");
    map.printMaxBucketSize();
  }


}
