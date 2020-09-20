package cache.product.service;

import java.util.HashMap;
import java.util.Map;

public class LRUCache  implements Cache{
    /**
     * Least Recently Used Algorithm
     * 가장 최근에 사용된 적이 없는 캐시의 메모리부터 대체
     */
    private class Node {
        private Object key;
        private Object val;
        private Node prev;
        private Node next;

        public Node(Object key, Object val){
            this.key = key;
            this.val = val;
            this.prev = null;
            this.next = null;
        }
    }

    private Map<Object, Node> map = new HashMap<>();
    private int capacity;
    private Node head;
    private Node tail;

    public LRUCache(int capacity){
        this.map = new HashMap<>();
        this.capacity = capacity;
        head = new Node(0, 0);
        tail = new Node(0, 0);

        head.next = tail;
        tail.prev = head;
    }

    /**
     * 가장 최근에 사용한 값을 맨앞으로 이동
     */
    private void insertToHead(Node node){
        this.head.next.prev = node;
        node.next = this.head.next;
        node.prev = this.head;
        this.head.next = node;
        map.put(node.key, node);
    }

    private void remove(Node node){
        node.prev.next = node.next;
        node.next.prev = node.prev;
        map.remove(node.key);
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public Object get(Object key) {
        if(!map.containsKey(key)){
            return null;
        }
        Node node = map.get(key);
        remove(node);
        insertToHead(node);
        return node.val;
    }

    @Override
    public void put(Object key, Object value) {
        Node newNode = new Node(key, value);
        if(map.containsKey(key)){
            remove(map.get(key));
        } else {
            if(map.size() >= capacity){
                remove(tail.prev);
            }
        }
        insertToHead(newNode);
    }

    @Override
    public void remove(Object key) {
        if(map.containsKey(key)){
            remove(map.get(key));
        }
    }

    @Override
    public void removeAll(){
        map.clear();
    }
}
