public class DLLNode<K,V> {
  long timestamp;
  DLLNode<K,V> previous;
  DLLNode<K,V> next;
  private ListNode<K,V> reference;

  // Constructor for DLLNode
  public DLLNode(ListNode<K,V> ref) {
    this.previous = null;
    this.next = null;
    this.reference = ref;
    renewTimestamp();
  }


  public ListNode<K,V> getReference() { return this.reference; }

  public DLLNode<K,V> getNext() { return this.next; }

  public DLLNode<K,V> getPrevious() { return this.previous; }

  public long getTimestamp() { return this.timestamp; }

  public void setNext(DLLNode<K,V> next) { this.next = next; }

  public void setPrevious(DLLNode<K,V> previous) { this.previous = previous; }

  public void renewTimestamp() {this.timestamp = System.currentTimeMillis();}
    
}
