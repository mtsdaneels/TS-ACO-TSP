package TS_ACO_TSP;

import TS_ACO_TSP.Interfaces.DLLInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Class representing a double linked list with integers as the elements.
 */
public class DLL implements DLLInterface {

    /**
     * Pointer to the head of the DLL.
     */
    protected Node head;

    @Override
    public Node getHead(){
        return head;
    }

    @Override
    public void setHead(Node node){
        head = node;
    }

    /**
     * Pointer to the tail of the DLL.
     */
    protected Node tail;

    @Override
    public Node getTail(){
        return tail;
    }

    @Override
    public void setTail(Node node){
        tail = node;
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
         * Swaps one of the 2 nodes with another one.
         * @param oneToChange The one to swap.
         * @param ChangeTo The new node.
         */
        public void changeNodeTo(Node oneToChange, Node ChangeTo){
            if (Node1 == oneToChange){
                Node1 = ChangeTo;
            }
            else if (Node2 == oneToChange){
                Node2 = ChangeTo;
            }
        }
    }

    @Override
    public DLL.Node getNext(DLL.Node node, DLL.Node prev){
        if (node == tail) {
            return head;
        }
        if (node.Node1 != prev){
            return node.Node1;
        }
        return node.Node2;
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

    /**
     * Copies all the elements from the given dll.
     * @param dll The dll we want to copy.
     */
    public void makeDeepCopyOf(DLL dll){
        int sizeOfDll = dll.size;
        this.removeAll();
        List<Integer> elements = dll.getElements();
        for (int i=0; i < sizeOfDll; i++){
            insertLast(elements.get(i));
        }
    }

    @Override
    public void insertFirst(int element) {
        Node temp = new Node(element, head, null);
        if (head != null){
            head.changeNodeTo(null, temp);
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
            tail.changeNodeTo(null, temp);
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
                head.Node2.changeNodeTo(head, null);
                head = head.Node2;
            } else {
                head.Node1.changeNodeTo(head, null);
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
                tail.Node2.changeNodeTo(tail, null);
                tail = tail.Node2;
            } else {
                tail.Node1.changeNodeTo(tail, null);
                tail = tail.Node1;
            }
            size--;
        }
        else{
            removeAll();
        }
        return temp;
    }

    //TODO Function only gets used in tests
    @Override
    public DLL.Node search(int k) {
        if (size == 0){
            return null;
        }
        Node node = searchWithoutRemove(k);
        return remove(node);
    }

    @Override
    public DLL.Node remove(Node node){
        if (node == null){
            throw new IllegalArgumentException("Node cannot be null!");
        }
        if (node == head) {
            return removeFirst();
        }
        else if (node == tail){
            return removeLast();
        }
        Node prev = node.Node1;
        Node next = node.Node2;
        prev.changeNodeTo(node, node.Node2);
        next.changeNodeTo(node, node.Node1);
        size--;
        return node;
    }

    @Override
    public DLL.Node searchWithoutRemove(int k){
        Node solution = head;
        Node temp;
        Node prev = null;
        while (solution != null && solution.element != k){
            temp = solution;
            solution = getNext(solution, prev);
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

    //TODO Function only gets used in tests
    @Override
    public DLL.Node getNodeAtIndex(int index){
        if (index >= size || index < 0){
            throw new IllegalArgumentException("Index must be between 0 and the size of the list");
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
            nodeAtX = getNext(nodeAtX,prev);
            prev = temp;
            x++;
        }
        assert false: "Should not happen";
        return null;
    }

    //TODO Fuction only gets used in tests
    @Override
    public int getIndexOf(int elementOfNode){
        Node solution = head;
        int index = 0;
        Node temp;
        Node prev = null;
        while (solution != null && solution.element != elementOfNode){
            temp = solution;
            solution = getNext(solution,prev);
            prev = temp;
            index++;
        }
        if (solution != null){
            return index;
        }
        else{
            throw new NoSuchElementException("No element with given value in DLL!");
        }
    }

    @Override
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