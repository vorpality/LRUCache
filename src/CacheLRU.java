class CacheLRU<K, V> implements Cache<K,V> {
  private final int capacity;
  private HashMap<K, V> map;
  private DLL stampList;
  private int hits;
  private int misses;


//constructor capacity is the maximum size of the Cache.
  public CacheLRU(int capacity) {
    this.capacity = capacity;
    this.map = new HashMap<>(capacity);
    this.stampList = new DLL();
  }

//add new key to cache
/* 
  If key doesn't exist in the HashMap, adds it and if size is over capacity, removes last visited element.
  If key exists, moves it to the front 
*/
  @Override
  public void store(K key, V value) {
    ListNode<K,V> node = map.get(key);
    if (node == null) {
      if (map.size() == capacity) {
        map.remove(stampList.removeLRU());
      }
      node = map.put(key,value);
      DLLNode<K,V> stamp = stampList.insert(node);
      node.setReference(stamp);
    } else {
      node.setValue(value);
      node.getReference().renewTimestamp();
    }
  }

//find key in cache 
/*
If key exists, renews timestamp(moves it to the front of the list) and counts a hit
If key doens't exist, counts a miss
*/
  @Override
  public V lookUp(K key) {
    ListNode<K,V> node = map.get(key);
    if (node != null) {
      node.getReference().renewTimestamp();
      this.hits++;
      return node.getValue();
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


}
