/*
  Bucket Node
*/
public class BucketNode<K,V> {

  private K key;
  private V value;
  private BucketNode <K,V> next;

  //BucketNode constructor
  public BucketNode(K key, V value) {
    this.key = key;
    this.value = value;
    this.next = null;
  }

  public K getKey() { return this.key; }

  public V getValue() { return this.value; }

  public BucketNode<K,V> getNext() { return this.next; }

  public void setNext(BucketNode<K,V> next) { this.next = next; }

  public void setValue(V value){
    this.value = value;
  }
}

