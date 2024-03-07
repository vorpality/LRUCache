public class HashBucket<K, V> {
  private BucketNode<K, V> head;

  public HashBucket() {
    head = null;
  }

  public boolean isEmpty() {
    return head == null;
  }

  public void add(K key, V value) {
    BucketNode<K, V> n = new BucketNode<>(key, value); 

    if (isEmpty()) {
      head = n;
    } else {
      n.setNext(head);
      head = n;
    }
  }

  public BucketNode<K,V> get(K key) {
    BucketNode<K, V> current = head;
    while (current != null) {
      if (current.getKey().equals(key)) {
        return current;
      }
      current = current.getNext();
    }
    return null;
  }


  public boolean containsKey(K key) {
    BucketNode<K, V> current = head;
    while (current != null) {
      if (current.getKey().equals(key)) { 
        return true;
      } 
      current = current.getNext();
    }
    return false;
  }

  public void remove(K key) {
    BucketNode<K, V> current = head;
    BucketNode<K, V> previous = null;
    
    if (isEmpty()) return;

    if (head.getKey().equals(key)) {
      head = head.getNext();
      return;
    }

    while (current != null){
    if (current.getKey().equals(key)) {
      previous.setNext(current.getNext());
    }
      previous = current;
      current = current.getNext();
    }
  }



  public void printBucket(){
  BucketNode<K, V> current = head;
    while (current != null) {
      System.out.println(" Key : " + current.getKey() + " Value : " + current.getValue());
      current = current.getNext();
    }
    return;
  }
}
