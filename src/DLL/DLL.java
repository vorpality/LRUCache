public class DLL<K,V>{
    private DLLNode<K,V> head;
    private DLLNode<K,V> tail;
    

    public DLL() {
    head = null;
    tail = null;
    }

    public boolean isEmpty() {
        return head == null;
    }

  // Adds a node 
  public DLLNode<K,V> insert(ListNode<K,V> node) {
    DLLNode<K,V> newNode = new DLLNode<>(node);

    if (isEmpty()) {
      head = tail = newNode;
    } else {
      newNode.next = head;
      head.previous = newNode;
      head = newNode;
    }
    return newNode;
  }

  public void remove(DLLNode<K,V> node) {
    if (node.previous != null) {
      node.previous.next = node.next;
    } else {
      head = node.next;
    }

    if (node.next != null) {
      node.next.previous = node.previous;
    } else {
      tail = node.previous;
    }
  }

  public void renew(DLLNode<K,V> node) {
    if (node != head) {
      remove(node);
      insert(node.getReference());
    }
  }

  public ListNode<K,V> removeLRU() {
    if (tail != null) {
      DLLNode<K,V> lastElement = tail;
      remove(tail);
      return lastElement.getReference();
    }
    return null;
  }
}
