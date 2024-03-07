public class List<K, V> {
  private ListNode<K, V> head;
  private ListNode<K, V> tail;

  public List() {
    head = null;
    tail = null;
  }

  public boolean isEmpty() {
    return head == null;
  }

  public void insertAtFront(K key, V value) {
    ListNode<K, V> n = new ListNode<>(key, value); 

    if (isEmpty()) {
      head = tail = n;
    } else {
      n.setNext(head);
      head = n;
    }
  }

  public ListNode<K,V> insertAtBack(K key, V value) {
    ListNode<K, V> n = new ListNode<>(key, value);

    if (isEmpty()) {
      head = tail = n;
    } else {
      tail.setNext(n);
      tail = n;
    }
    return n;
  }

  public boolean containsKey(K key) {
    ListNode<K, V> current = head;
    while (current != null) {
      if (current.getKey().equals(key)) { 
        return true;
      } 
      current = current.getNext();
    }
    return false;
  }

  public void remove(K key) {
    ListNode<K, V> current = head;
    ListNode<K, V> previous = null;
      
    if (!isEmpty() && head.getKey().equals(key)) {
      head = head.getNext();
      if (head == null) tail = null;
      return;
    }

    while (current != null && !current.getKey().equals(key)) {
      previous = current;
      current = current.getNext();
    }

    if (current == null) return;

    if (current == tail) {
      tail = previous;
    }

    if (previous != null) {
      previous.setNext(current.getNext());
    }
  }

  public ListNode<K,V> findEntry(K key) {
    ListNode<K, V> current = head;
    while (current != null) {
      if (current.getKey().equals(key)) {
        return current;
      }
      current = current.getNext();
    }
    return null;
  }

  public void printList(){
  ListNode<K, V> current = head;
    while (current != null) {
      System.out.println(" Key : " + current.getKey() + " Value : " + current.getValue());
      current = current.getNext();
    }
    return;
  }
}
