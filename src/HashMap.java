
//HashMap for storing the values 
/*
  The idea behind this data struct is :
  There's an array of HashNodes which contains every pair of key/value currently saved in the map.
  The way this is done is via buckets, every hashnode is basically a single linked list which we can traverse 
  like we would a list (node.next)
*/

public class HashMap<K, V> {
  private HashBucket<K, V>[] buckets;
  private int capacity;
  private int size;
  private long operations = 0;

  public HashMap(int capacity) {
    this.capacity = capacity;
    this.size = 0;
    this.buckets = new HashBucket[capacity];
  }

  private HashBucket<K, V> getBucket(int bucketIndex) {
    if (buckets[bucketIndex] == null) {
        buckets[bucketIndex] = new HashBucket<>();
    }
    return buckets[bucketIndex];
  }


  private int getBucketIndex(K key) {
    int hashCode = key.hashCode() % this.capacity;
    return hashCode;
  }

  public void put(K key, V value) {
    int bucketIndex = getBucketIndex(key);
    HashBucket<K, V> bucket = getBucket(bucketIndex);
    bucket.add(key, value);
    operations++;
    size++;
  }

  public V get(K key) {
    int bucketIndex = getBucketIndex(key);
    HashBucket<K, V> bucket = buckets[bucketIndex];
    if (bucket == null) {
      return null;
    }
    BucketNode<K, V> node = bucket.get(key);
    return node != null ? node.getValue() : null;
  }

  public void remove(K key) {
    int bucketIndex = getBucketIndex(key);
    HashBucket<K, V> bucket = buckets[bucketIndex];
    if (bucket == null) {
      return;
    }
    bucket.remove(key);
    operations++;
    size--;
  }

  public int size() {
    return this.size;
  }


  public boolean containsKey(K key) {
    return (get(key) != null);
  }

//Debugging


public void printOperations(){
  System.out.println("operations : " + operations);
}

}