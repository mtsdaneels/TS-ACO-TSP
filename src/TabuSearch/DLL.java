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
    protected void makeDeepCopyOf(DLL dll){
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
        prev.changeNodeTo(node, next);
        next.changeNodeTo(node, prev);
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

    @Override
    public int getIndexOf(int elementOfNode){
        Node solution = head;
        int index = 0;
        Node temp;
        Node prev = null;
        while (solution != null && solution.element != elementOfNode){
            temp = solution;
            solution = solution.getNext(prev);
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

    /**
     * Preform a 2-opt move when the second element is the tail.
     * @param move The move to be performed.
     */
    private void makeMove2_optWithJAtEnd(Tuple<Integer, Integer> move){
        int iValue;
        int jValue;
        if (getIndexOf(move.x) > getIndexOf(move.y)){
            iValue = move.y;
            jValue = move.x;
        }
        else{
            iValue = move.x;
            jValue = move.y;
        }
        Node nodeI = searchWithoutRemove(iValue);
        Node nodeBefI = getNodeAtIndex(getIndexOf(iValue)-1);
        Node nodeJ = searchWithoutRemove(jValue);
        //Change of node i-1
        nodeBefI.changeNodeTo(nodeI, nodeJ);
        //Change of node i
        nodeI.changeNodeTo(nodeBefI, null);
        //Change of node j
        nodeJ.changeNodeTo(null, nodeBefI);
        tail = nodeI;
    }

    /**
     * Preform a 2-opt move when the first element is the head.
     * @param move The move to be performed.
     */
    private void makeMove2_optWithIAtBegin(Tuple<Integer, Integer> move){
        int iValue;
        int jValue;
        if (getIndexOf(move.x) > getIndexOf(move.y)){
            iValue = move.y;
            jValue = move.x;
        }
        else{
            iValue = move.x;
            jValue = move.y;
        }
        Node nodeI = searchWithoutRemove(iValue);
        Node nodeJ = searchWithoutRemove(jValue);
        Node nodeAfterJ = getNodeAtIndex(getIndexOf(jValue)+1);
        //Change of node i
        nodeI.changeNodeTo(null, nodeAfterJ);
        //Change of node j
        nodeJ.changeNodeTo(nodeAfterJ, null);
        //Change of node j+1
        nodeAfterJ.changeNodeTo(nodeJ, nodeI);
        head = nodeJ;
    }

    @Override
    public void makeMove2_opt(Tuple<Integer, Integer> move){
        int iValue;
        int jValue;
        if (getIndexOf(move.x) > getIndexOf(move.y)){
            iValue = move.y;
            jValue = move.x;
        }
        else{
            iValue = move.x;
            jValue = move.y;
        }
        if (getIndexOf(jValue) == size - 1 && getIndexOf(iValue) == 0) return;
        if (getIndexOf(jValue) == size - 1) {
            makeMove2_optWithJAtEnd(move);
            return;
        }
        if (getIndexOf(iValue) == 0){
            makeMove2_optWithIAtBegin(move);
            return;
        }

        Node nodeBefI = getNodeAtIndex(getIndexOf(iValue)-1);
        Node nodeI = searchWithoutRemove(iValue);
        Node nodeAfterJ = getNodeAtIndex(getIndexOf(jValue)+1);
        Node nodeJ = searchWithoutRemove(jValue);

        //Change of node i-1
        nodeBefI.changeNodeTo(nodeI, nodeJ);
        //Change of node i
        nodeI.changeNodeTo(nodeBefI, nodeAfterJ);
        //Change of node j
        nodeJ.changeNodeTo(nodeAfterJ, nodeBefI);
        //Change of node j+1
        nodeAfterJ.changeNodeTo(nodeJ, nodeI);
    }
}
