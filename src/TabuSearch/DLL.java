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
        if (index >= size || index < 0){
            throw new IllegalArgumentException("index must be between 0 and the size of the list");
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
     * Calculate the possible reduction of a two-opt move with given nodes.
     * @param tabuSearch The tabu search where the reduction would take place.
     * @param nodeBefI The node before node i.
     * @param nodeI Node i.
     * @param nodeJ Node j.
     * @param nodeAfterJ The node after node j.
     * @return The possible reduction of a two-opt move between node i and node j.
     */
    private double getCostReduction(TabuSearch tabuSearch, Node nodeBefI, Node nodeI, Node nodeJ, Node nodeAfterJ){
        Graph graph = tabuSearch.getGraph();
        return +graph.getDistance(nodeBefI.getElement(), nodeI.getElement()) +graph.getDistance(nodeJ.getElement(), nodeAfterJ.getElement())
                -graph.getDistance(nodeBefI.getElement(), nodeJ.getElement()) - graph.getDistance(nodeI.getElement(), nodeAfterJ.getElement());
    }

    /**
     * Check if a given tabu search contains a move with i and j.
     * @param tabuSearch The tabu search.
     * @param i The first node of the move.
     * @param j The second node of the move.
     * @return If the move created with i and j is in the tabu list of the tabu search, return true, else return false.
     */
    private boolean tabuListContains(TabuSearch tabuSearch, int i, int j){
        if (i == j) throw new IllegalArgumentException("i == j is not allowed");
        if (i<j){
            return tabuSearch.tabuListContains(i, j);
        }
        else{
            return tabuSearch.tabuListContains(j, i);
        }
    }

    /**
     * Adds a move (with i and j) to the tabu list of the given tabu search.
     * @param tabuSearch The tabu search with the tabu list.
     * @param i The element of the first node of the move.
     * @param j The element of the second node of the move.
     */
    private void addToTabuList(TabuSearch tabuSearch, int i, int j){
        if (i == j) throw new IllegalArgumentException("i == j is not allowed");
        if (i<j){
            tabuSearch.addToTabuList(new Tuple<Integer, Integer>(i, j));
        }
        else{
            tabuSearch.addToTabuList(new Tuple<Integer, Integer>(j, i));
        }
    }

    //TODO functie kleiner maken
    /**
     * Preform the two opt move that reduces the length of the tour by the most.
     * @param tabuSearch The tabu search where the two opt takes place.
     */
    public void preformBestTwo_OptMove(TabuSearch tabuSearch){
        //Declaring local variables
        double bestCostReduction = Double.NEGATIVE_INFINITY, posCostReduction = Double.NEGATIVE_INFINITY;
        Tuple<Integer, Integer> bestMove = new Tuple<Integer, Integer>(-1,-1);
        Node temp, nodeJ, nodeAfterJ, nodeBefI = null, nodeI = head;
        Node bestMoveNodeJ=null, bestMoveNodeAfterJ=null, bestMoveNodeBefI=null, bestMoveNodeI=null;

        //Checking every two-opt
        for (int i=0; i<tabuSearch.getDimension()-2; i++){
            nodeJ = nodeI.getNext(nodeBefI);
            nodeAfterJ = nodeJ.getNext(nodeI);
            for (int j=i+1; j<=tabuSearch.getDimension()-1; j++){
                if (i==0 && j==tabuSearch.getDimension()-1) break;
                else if (i==0) posCostReduction = getCostReduction(tabuSearch, tail, nodeI, nodeJ, nodeAfterJ);
                else if (j==tabuSearch.getDimension()-1) posCostReduction = getCostReduction(tabuSearch, nodeBefI, nodeI, nodeJ, head);
                else posCostReduction = getCostReduction(tabuSearch, nodeBefI, nodeI, nodeJ, nodeAfterJ);
                //TODO aspiration criteria
                if (posCostReduction > bestCostReduction && !tabuListContains(tabuSearch, nodeI.getElement(), nodeJ.getElement())){
                    bestCostReduction = posCostReduction;
                    bestMove = new Tuple<Integer, Integer>(nodeI.getElement(), nodeJ.getElement());
                    if (i==0){
                        bestMoveNodeBefI = tail;
                        bestMoveNodeI = nodeI;
                        bestMoveNodeJ = nodeJ;
                        bestMoveNodeAfterJ = nodeAfterJ;
                    }
                    else if (j==tabuSearch.getDimension()-1){
                        bestMoveNodeBefI = nodeBefI;
                        bestMoveNodeI = nodeI;
                        bestMoveNodeJ = nodeJ;
                        bestMoveNodeAfterJ = head;
                    }
                    else{
                        bestMoveNodeBefI = nodeBefI;
                        bestMoveNodeI = nodeI;
                        bestMoveNodeJ = nodeJ;
                        bestMoveNodeAfterJ = nodeAfterJ;
                    }
                }
                if (j == tabuSearch.getDimension()-1) break;
                temp = nodeJ;
                nodeJ = nodeAfterJ;
                nodeAfterJ = nodeJ.getNext(temp);
            }
            temp = nodeI;
            nodeI = nodeI.getNext(nodeBefI);
            nodeBefI = temp;
        }
        makeMove2_opt(bestMoveNodeBefI, bestMoveNodeI, bestMoveNodeJ, bestMoveNodeAfterJ);
        addToTabuList(tabuSearch, bestMove.getX(), bestMove.getY());
    }

    /**
     * Make a two opt move between node i and node j.
     * @param nodeBefI The node before node i.
     * @param nodeI Node i.
     * @param nodeJ Node j.
     * @param nodeAfterJ The node after node j.
     */
    private void makeMove2_opt(Node nodeBefI, Node nodeI, Node nodeJ, Node nodeAfterJ){
        if (nodeJ == tail && nodeI == head) return; //NOTE This does not change the tour, so we do not preform this one.
        if (nodeJ == tail){
            makeMove2_optWithJAtEnd(nodeBefI, nodeI, nodeJ);
            return;
        }
        if (nodeI == head){
            makeMove2_optWithIAtBegin(nodeI, nodeJ, nodeAfterJ);
            return;
        }
        //Change of node i-1
        nodeBefI.changeNodeTo(nodeI, nodeJ);
        //Change of node i
        nodeI.changeNodeTo(nodeBefI, nodeAfterJ);
        //Change of node j
        nodeJ.changeNodeTo(nodeAfterJ, nodeBefI);
        //Change of node j+1
        nodeAfterJ.changeNodeTo(nodeJ, nodeI);
    }

    /**
     * Make a two opt move between node i and node j when node i is the first node of the list.
     * @param nodeI Node i.
     * @param nodeJ Node j.
     * @param nodeAfterJ The node after node j.
     */
    //NOTE The node before node i is not given because it will always be the tail of the list.
    private void makeMove2_optWithIAtBegin(Node nodeI, Node nodeJ, Node nodeAfterJ){
        if (getIndexOf(nodeI.getElement()) >= getIndexOf(nodeJ.getElement())){
            throw new IllegalArgumentException("getIndex(nodeI) >= getIndex(nodeJ) is not allowed!");
        }
        //Change of node i
        nodeI.changeNodeTo(null, nodeAfterJ);
        //Change of node j
        nodeJ.changeNodeTo(nodeAfterJ, null);
        //Change of node j+1
        nodeAfterJ.changeNodeTo(nodeJ, nodeI);
        head = nodeJ;
    }

    /**
     * Make a two opt move between node i and node j when node j is the last node of the list.
     * @param nodeBefI The node before node i.
     * @param nodeI Node i.
     * @param nodeJ Node j.
     */
    //NOTE The node after node j is not given because it will always be the head of the list.
    private void makeMove2_optWithJAtEnd(Node nodeBefI, Node nodeI, Node nodeJ){
        if (getIndexOf(nodeI.getElement()) >= getIndexOf(nodeJ.getElement())){
            throw new IllegalArgumentException("getIndex(nodeI) >= getIndex(nodeJ) is not allowed!");
        }
        //Change of node i-1
        nodeBefI.changeNodeTo(nodeI, nodeJ);
        //Change of node i
        nodeI.changeNodeTo(nodeBefI, null);
        //Change of node j
        nodeJ.changeNodeTo(null, nodeBefI);
        tail = nodeI;
    }
}
