package TabuSearch;

import java.util.NoSuchElementException;

/**
 * Class representing a double linked list with integers as the elements.
 * TODO @invar size >= 0
 */
public class DLL implements DLLInterface {

    /**
     * Pointer to the head of the DLL.
     */
    private Node head;

    /**
     * Pointer to the tail of the DLL.
     */
    private Node tail;

    /**
     * The size of the DLL.
     */
    private int size;

    /**
     * Class representing a node from a DLL.
     */
    protected class Node{
        final int element;
        DLL.Node next;
        DLL.Node prev;

        public Node(int element, DLL.Node next, DLL.Node prev){
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }

    /**
     * Constructor initializing a DLL with size 0.
     */
    public DLL(){
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void insertFirst(int element) {
        Node temp = new Node(element, head, null);
        if (head != null){
            head.prev = temp;
        }
        head = temp;
        if (tail == null){
            tail = temp;
        }
        size++;
    }

    @Override
    public void insertLast(int element) {
        Node temp = new Node(element, null, tail);
        if (tail != null){
            tail.next = temp;
        }
        tail = temp;
        if (head == null){
            head = temp;
        }
        size++;
    }

    @Override
    public DLL.Node removeFirst() {
        if (size == 0){
            throw new NoSuchElementException();
        }
        Node temp = head;
        head = head.next;
        head.prev = null;
        size--;
        return temp;
    }

    @Override
    public DLL.Node removeLast() {
        if (size == 0){
            throw new NoSuchElementException();
        }
        Node temp = tail;
        tail = tail.prev;
        tail.next = null;
        size--;
        return temp;
    }

    @Override
    public DLL.Node search(int k) {
        Node temp = head;
        while (temp != null && temp.element != k){
            temp = temp.next;
        }
        return temp;
    }
}
