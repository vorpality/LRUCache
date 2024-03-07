class BSTNode<K, V> {
    K key;
    V value;
    BSTNode<K, V> left, right;

    public BSTNode(K key, V value) {
        this.key = key;
        this.value = value;
        left = right = null;
    }

    //Getters - Setters
  public K getKey() { return this.key; }

  public V getValue() { return this.value; }
  
  public void setValue(V value){
    this.value = value;
  }
}
