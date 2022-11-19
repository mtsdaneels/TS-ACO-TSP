package TabuSearch;

import java.util.ArrayList;
import java.util.List;
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
        DLL.Node Node1;
        DLL.Node Node2;

        public Node(int element, DLL.Node next, DLL.Node prev){
            this.element = element;
            this.Node1 = next;
            this.Node2 = prev;
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
            head.Node2 = temp;
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
            tail.Node1 = temp;
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
        head = head.Node1;
        head.Node2 = null;
        size--;
        return temp;
    }

    @Override
    public DLL.Node removeLast() {
        if (size == 0){
            throw new NoSuchElementException();
        }
        Node temp = tail;
        tail = tail.Node2;
        tail.Node1 = null;
        size--;
        return temp;
    }

    @Override
    public DLL.Node search(int k) {
        Node temp = head;
        while (temp != null && temp.element != k){
            temp = temp.Node1;
        }
        return temp;
    }

    @Override
    public List<Integer> getElements(){
        List<Integer> list = new ArrayList<>();
        Node pastNode = null;
        Node temp = head;
        while (temp != null){
           list.add(temp.element);
           if (temp.Node1 != pastNode){
               pastNode = temp;
               temp = temp.Node1;
           }
           else{
               pastNode = temp;
               temp = temp.Node2;
           }
        }
        return list;
    }

    /**
     * Removes all the elements from the DLL.
     */
    protected void removeAll(){
        head = null;
        tail = null;
        size = 0;
    }
}
