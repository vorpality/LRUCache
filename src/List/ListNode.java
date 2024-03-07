/*
  Single linked list for hash buckets.
*/

public class ListNode<K,V> {

  private K key;
  private V value;
  private ListNode <K,V> next;
  private DLLNode<K,V> reference;

  //ListNode constructor
  public ListNode(K key, V value) {
    this.key = key;
    this.value = value;
    this.next = null;
    this.reference = null;
  }

  public K getKey() { return this.key; }

  public V getValue() { return this.value; }

  public ListNode<K,V> getNext() { return this.next; }

  public void setNext(ListNode<K,V> next) { this.next = next; }

  public void setReference(DLLNode<K,V> ref) { this.reference = ref; }

  public void setValue(V value){
    this.value = value;
    this.reference.renewTimestamp();
  }

  public DLLNode<K,V> getReference() {return this.reference;}
}

