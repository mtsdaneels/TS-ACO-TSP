package TabuSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Class representing a double linked list with integers as the elements.
 * TODO @invar size >= 0?
 */
public class DLL implements DLLInterface {

    /**
     * Pointer to the head of the DLL.
     */
    private Node head;

    @Override
    public Node getHead(){
        return head;
    }

    /**
     * Pointer to the tail of the DLL.
     */
    private Node tail;
    @Override
    public Node getTail(){
        return tail;
    }

    /**
     * The size of the DLL.
     */
    private int size;

    @Override
    public int getSize(){
        return size;
    }

    /**
     * Class representing a node from a DLL.
     */
    public static class Node{
        /**
         * The element of the node.
         */
        final int element;

        /**
         * Returns the element.
         */
        public int getElement(){
            return element;
        }

        /**
         * First node that is linked.
         */
        DLL.Node Node1;

        /**
         * Returns first node that is linked.
         */
        public Node getNode1() {
            return Node1;
        }

        /**
         * Second node that is linked.
         */
        DLL.Node Node2;

        /**
         * Returns second node that is linked.
         */
        public Node getNode2(){
            return Node2;
        }

        /**
         * Set Node1 to the given node.
         * @param newNode The new Node1.
         */
        public void setNode1(DLL.Node newNode){
            Node1 = newNode;
        }

        /**
         * Set Node2 tp the given node.
         * @param newNode The new Node2.
         */
        public void setNode2(DLL.Node newNode){
            Node2 = newNode;
        }

        /**
         * Initializes a new node with given element and 2 linked nodes.
         * @param element The element of the node.
         * @param Node1 The first node that is linked.
         * @param Node2 The second node that is linked.
         */
        public Node(int element, DLL.Node Node1, DLL.Node Node2){
            this.element = element;
            this.Node1 = Node1;
            this.Node2 = Node2;
        }

        /**
         * Returns the next node when given the previous.
         * @param prev The previous node.
         */
        public DLL.Node getNext(DLL.Node prev){
            if (Node1 != prev){
                return Node1;
            }
            return Node2;
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
            if (head.Node2 == null){
                head.Node2 = temp;
            }
            else{
                head.Node1 = temp;
            }
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
            if (tail.Node1 == null){
                tail.Node1 = temp;
            }
            else{
                tail.Node2 = temp;
            }
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
        if (!(size == 1)) {
            if (head.Node1 == null) {
                if (head.Node2.Node1 == head) {
                    head.Node2.Node1 = null;
                } else {
                    head.Node2.Node2 = null;
                }
                head = head.Node2;
            } else {
                if (head.Node1.Node1 == head) {
                    head.Node1.Node1 = null;
                } else {
                    head.Node1.Node2 = null;
                }
                head = head.Node1;
            }
            size--;
        }
        else{
            removeAll();
        }
        return temp;
    }

    @Override
    public DLL.Node removeLast() {
        if (size == 0){
            throw new NoSuchElementException();
        }
        Node temp = tail;
        if (!(size == 1)) {
            if (tail.Node1 == null) {
                if (tail.Node2.Node1 == tail) {
                    tail.Node2.Node1 = null;
                } else {
                    tail.Node2.Node2 = null;
                }
                tail = tail.Node2;
            } else {
                if (tail.Node1.Node1 == tail) {
                    tail.Node1.Node1 = null;
                } else {
                    tail.Node1.Node2 = null;
                }
                tail = tail.Node1;
            }
            size--;
        }
        else{
            removeAll();
        }
        return temp;
    }

    @Override
    public DLL.Node search(int k) {
        Node solution = head;
        Node temp;
        Node prev = null;
        while (solution != null && solution.element != k){
            temp = solution;
            solution = solution.getNext(prev);
            prev = temp;
        }
        return solution;
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

    @Override
    public DLL.Node getNodeAtIndex(int index){
        if (index >= size){
            throw new IllegalArgumentException();
        }
        int x = 0;
        DLL.Node nodeAtX = head;
        DLL.Node prev = null;
        DLL.Node temp;
        while (x < size){
            if (x == index){
                return nodeAtX;
            }
            temp = nodeAtX;
            nodeAtX = nodeAtX.getNext(prev);
            prev = temp;
            x++;
        }
        assert false: "Should not happen";
        return null;
    }

    /**
     * Removes all the elements from the DLL.
     */
    public void removeAll(){
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Print the tour.
     */
    protected void printTour(){
        System.out.println(getElements());
    }
}
