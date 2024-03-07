class CacheLRU<K, V> implements Cache<K,V> {
  private final int capacity;
  private HashMap<K, Node> map;
  private Node head, tail;
  private int hits;
  private int misses;

  private long operationsTime;
//Doubly linked list node (for timestamp keeping)
  class Node {
    K key;
    V value;
    Node previous, next;
    long timestamp;

//list Node constructor
    Node(K key, V value) {
      this.key = key;
      this.value = value;
      this.timestamp = System.currentTimeMillis();
    }
  }

//constructor capacity is the maximum size of the Cache.
  public CacheLRU(int capacity) {
    this.capacity = capacity;
    this.map = new HashMap<>(capacity);
  }

//Renew access timestamp(move element to front of the list)
  private void move_to_head(Node node) {
    node.timestamp = System.currentTimeMillis();
    if (head == node) return;

    if (node.previous != null) node.previous.next = node.next;
    if (node.next != null) node.next.previous = node.previous;
    if (tail == node) tail = node.previous;

    node.next = head;
    if (head != null) head.previous = node;
    head = node;
    node.previous = null;

    if (tail == null) tail = node;
  }

//eviction of LRU
/*
  If current size > capacity, removes last used key
*/
  private void rip() {
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
    Node node = map.get(key);
    if (node == null) {
      node = new Node(key, value);
      map.put(key, node);
      move_to_head(node);
      rip();
    } else {
      node.value = value;
      move_to_head(node);
    }
  }

//find key in cache 
/*
If key exists, renews timestamp(moves it to the front of the list) and counts a hit
If key doens't exist, counts a miss
*/
  @Override
  public V lookUp(K key) {
    long startTime = System.nanoTime();
    Node node = map.get(key);
    if (node != null) {
      this.operationsTime += System.nanoTime() - startTime;
      move_to_head(node);
      this.hits++;
      return node.value;
    }
    this.misses++;
    return null;
  }

//getters
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

	@Override
  public void printBucketList() { this.map.printBucketList();}
  @Override
  public void printOperations() { this.map.printOperations();}
  @Override 
  public long getOperationTime() { return this.operationsTime; }

}
