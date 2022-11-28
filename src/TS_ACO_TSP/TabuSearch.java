package TS_ACO_TSP;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

//TODO documentation
public class TabuSearch implements  TabuSearchInterface{

    /**
     * The graph where the tabu search takes place.
     */
    private final Graph graph;

    /**
     * Returns the graph of the tabu search.
     */
    @Override
    public Graph getGraph(){
        return this.graph;
    }

    /**
     * The tour that tabu search chooses.
     */
    private final Tour tour;

    /**
     * Returns the tour.
     */
    @Override
    public Tour getTour(){
        return tour;
    }

    /**
     * The best tour found by tabu search at the moment.
     */
    private Tour bestTour;

    /**
     * Dimension of the problem.
     */
    private final int dimension;

    @Override
    public int getDimension(){
        return dimension;
    }

    /**
     * The length of the current tour.
     */
    private int currentBestTourLength;

    /**
     * Returns the length of the current best tour found.
     */
    private int getCurrentBestTourLength(){
        return currentBestTourLength;
    }

    /**
     * The tabu list of the tabu search.
     */
    List<Tuple<Integer, Integer>> tabuList;

    //TODO bekijk voor efficiëntere manier:
    /**
     * Pointer to the head of the tabu list.
     */
    private int tabuListHead = 1;

    /**
     * Pointer to the tail of the tabu list.
     */
    private int tabuListTail = 0;

    @Override
    public List<Tuple<Integer, Integer>> getTabuList(){
        return tabuList;
    }

    /**
     * Constructor initializing a new tabu search instance.
     * @param graph The graph where the tabu search takes place.
     */
    public TabuSearch(Graph graph){
        this.graph = graph;
        double tabuListFactor = 0.25;
        this.dimension = graph.getNumberOfVertices();
        this.tabuList = Arrays.asList(new Tuple[(int) (graph.getNumberOfVertices()* tabuListFactor)]);
        this.tour = new Tour(this.graph);
        this.bestTour = new Tour(this.graph);
    }

    /**
     * Returns the index of the node in possible ads that is the shortest to the previous node.
     * @param possibleAds List nodes that are a candidate to be the next node.
     * @param prevNode The previous node.
     */
    private int getNextShortestAdd(List<Integer> possibleAds, int prevNode){
        double shortestDistanceNext = Double.POSITIVE_INFINITY;
        int nextIndex = -1;
        for (int j = 0; j < possibleAds.size(); j++){
            if (graph.getDistance(prevNode,possibleAds.get(j)) < shortestDistanceNext){
                shortestDistanceNext = graph.getDistance(prevNode,possibleAds.get(j));
                nextIndex = j;
            }
        }
        return nextIndex;
    }

    /**
     * Make an initial solution in the tour variable using complete greedy algorithm.
     * @throws Exception If the graph of the tabu search has no vertices.
     */
    private void getInitialSolutionGreedy() throws Exception {
        int dimension = graph.getNumberOfVertices();
        if (dimension == 0){
            throw new Exception();
        }
        tour.getDLL().removeAll(); //Clear the tour
        List<Integer> possibleAds = new java.util.ArrayList<>(IntStream.range(1, dimension+1).boxed().toList());
        int nextIndex = (int) (Math.random() * ((dimension - 1))); //Start at a random node
        int nextNode = possibleAds.get(nextIndex);
        tour.addLast(nextNode);
        possibleAds.remove(nextIndex);
        while (!possibleAds.isEmpty()){
            nextIndex = getNextShortestAdd(possibleAds, nextNode);
            nextNode = possibleAds.get(nextIndex);
            tour.addLast(nextNode); //Add the node with the shortest length to the last one
            possibleAds.remove(nextIndex); //Node that gets used is now not possible to add anymore
        }
        bestTour.makeDeepCopyOf(tour);
    }

    /**
     * Make an initial solution in the tour variable using complete random choices.
     * @throws Exception If the graph of the tabusearch has no vertices.
     */
    private void getInitialSolutionRandom() throws Exception {
        int dimension = graph.getNumberOfVertices();
        if (dimension == 0){
            throw new Exception();
        }
        List<Integer> possibleAds = new java.util.ArrayList<>(IntStream.range(1, dimension+1).boxed().toList()); //Makes list between 1 and dimension
        int nextIndex = (int) (Math.random() * ((dimension -1)));
        int nextNode = possibleAds.get(nextIndex);
        tour.addLast(nextNode);
        possibleAds.remove(nextIndex);
        for (int i = 1; !possibleAds.isEmpty(); i++){
            nextIndex = (int)(Math.random() * ((dimension-i -1)));
            nextNode = possibleAds.get(nextIndex);
            tour.addLast(nextNode);
            possibleAds.remove(nextIndex);
        }
        bestTour.makeDeepCopyOf(tour);
    }

    /**
     * Returns whether the tabu list contains a move or not.
     * @param i The first node of the move.
     * @param j The second node of the move.
     * @throws IllegalArgumentException If i <= j.
     */
    //TODO is niet volledig efficient
    protected boolean tabuListContains(int i, int j){
        if (i > j ){
            int temp = i;
            i = j;
            j = temp;
        }
        if (i >= j) throw new IllegalArgumentException("i >= j is not allowed");
        int w = tabuListTail;
        while (w != tabuListHead){
            if (tabuList.get(w) == null);
            else if (tabuList.get(w).getX() == i && tabuList.get(w).getY() == j){
                return true;
            }
            w--;
            if (w == -1) w = tabuList.size()-1;
        }
        return false;
    }

    @Override
    public void addToTabuList(Tuple<Integer, Integer> move){ //NOTE tabuList only contains moves where move.getX() < move.getY()
        if (Objects.equals(move.getX(), move.getY())) throw new IllegalArgumentException("i == j is not allowed");
        if (move.getX() < move.getY()) tabuList.set(tabuListTail, move);
        else tabuList.set(tabuListTail, new Tuple<Integer, Integer>(move.getY(), move.getX()));
        tabuListHead++;
        if (tabuListHead == tabuList.size()-1) tabuListHead = 0;
        tabuListTail++;
        if (tabuListTail == tabuList.size()-1) tabuListTail = 0;
    }

    /**
     * Calculate the possible reduction of a two-opt move with given nodes.
     * @param nodeBefI The node before node i.
     * @param nodeI Node i.
     * @param nodeJ Node j.
     * @param nodeAfterJ The node after node j.
     * @return The possible reduction of a two-opt move between node i and node j.
     */
    protected double getCostReduction(DLL.Node nodeBefI, DLL.Node nodeI, DLL.Node nodeJ, DLL.Node nodeAfterJ){
        return graph.getDistance(nodeBefI.getElement(), nodeI.getElement()) +graph.getDistance(nodeJ.getElement(), nodeAfterJ.getElement())
                -graph.getDistance(nodeBefI.getElement(), nodeJ.getElement()) - graph.getDistance(nodeI.getElement(), nodeAfterJ.getElement());
    }

    /**
     * Make a two opt move between node i and node j when node j is the last node of the list.
     * @param nodeBefI The node before node i.
     * @param nodeI Node i.
     * @param nodeJ Node j.
     */
    private void makeMove2_optWithJAtEnd(DLL.Node nodeBefI, DLL.Node nodeI, DLL.Node nodeJ){
        //NOTE Extra check if needed.
        /* if (getIndexOf(nodeI.getElement()) >= getIndexOf(nodeJ.getElement())){
            throw new IllegalArgumentException("getIndex(nodeI) >= getIndex(nodeJ) is not allowed!");
        } */
        //Change of node i-1
        nodeBefI.changeNodeTo(nodeI, nodeJ);
        //Change of node i
        nodeI.changeNodeTo(nodeBefI, null);
        //Change of node j
        nodeJ.changeNodeTo(null, nodeBefI);
        tour.getDLL().setTail(nodeI);
    }

    /**
     * Make a two opt move between node i and node j when node i is the first node of the list.
     * @param nodeI Node i.
     * @param nodeJ Node j.
     * @param nodeAfterJ The node after node j.
     */
    //NOTE The node before node i is not given because it will always be the tail of the list.
    private void makeMove2_optWithIAtBegin(DLL.Node nodeI, DLL.Node nodeJ, DLL.Node nodeAfterJ){
        //NOTE Extra check if needed.
        /* if (getIndexOf(nodeI.getElement()) >= getIndexOf(nodeJ.getElement())){
            throw new IllegalArgumentException("getIndex(nodeI) >= getIndex(nodeJ) is not allowed!");
        } */
        //Change of node i
        nodeI.changeNodeTo(null, nodeAfterJ);
        //Change of node j
        nodeJ.changeNodeTo(nodeAfterJ, null);
        //Change of node j+1
        nodeAfterJ.changeNodeTo(nodeJ, nodeI);
        tour.getDLL().setHead(nodeJ);
    }

    /**
     * Make a two-opt move between node i and node j. Index of node i must be smaller that index of node j.
     * @param nodeBefI The node before node i.
     * @param nodeI Node i.
     * @param nodeJ Node j.
     * @param nodeAfterJ The node after node j.
     */
    private void makeMove2_opt(DLL.Node nodeBefI, DLL.Node nodeI, DLL.Node nodeJ, DLL.Node nodeAfterJ){
        if (nodeJ == tour.getLastNode() && nodeI == tour.getFirstNode()) return; //NOTE This does not change the tour, so we do not preform this one.
        if (nodeJ == tour.getLastNode()){
            makeMove2_optWithJAtEnd(nodeBefI, nodeI, nodeJ);
            return;
        }
        if (nodeI == tour.getFirstNode()){
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
     * Calculate and preform the best two-opt move.
     */
    private void preformBestTwo_OptMove(){
        //Declaring local variables
        double bestCostReduction = Double.NEGATIVE_INFINITY, posCostReduction = Double.NEGATIVE_INFINITY;
        Tuple<Integer, Integer> bestMove = new Tuple<Integer, Integer>(-1,-1);
        DLL.Node temp, nodeJ, nodeAfterJ, nodeBefI = null, nodeI = tour.getFirstNode();
        FourTuple fourTuple = new FourTuple(null, null, null, null);

        //Checking every two-opt
        for (int i=0; i<getDimension()-2; i++){
            nodeJ = tour.getNext(nodeI,nodeBefI);
            nodeAfterJ = tour.getNext(nodeJ,nodeI);
            for (int j=i+1; j<= getDimension()-1; j++){
                if (i==0 && j== getDimension()-1) break;
                else if (i==0) posCostReduction = getCostReduction(tour.getLastNode(), nodeI, nodeJ, nodeAfterJ);
                else if (j==getDimension()-1) posCostReduction = getCostReduction(nodeBefI, nodeI, nodeJ, tour.getFirstNode());
                else posCostReduction = getCostReduction(nodeBefI, nodeI, nodeJ, nodeAfterJ);
                //TODO aspiration criteria
                if (posCostReduction > bestCostReduction && !tabuListContains(nodeI.getElement(), nodeJ.getElement())){
                    bestCostReduction = posCostReduction;
                    bestMove = new Tuple<Integer, Integer>(nodeI.getElement(), nodeJ.getElement());
                    if (i==0) fourTuple = new FourTuple(tour.getLastNode(), nodeI, nodeJ, nodeAfterJ);
                    else if (j==getDimension()-1) fourTuple = new FourTuple(nodeBefI, nodeI, nodeJ, nodeAfterJ);
                    else fourTuple = new FourTuple(nodeBefI, nodeI, nodeJ, nodeAfterJ);
                }
                if (j == getDimension()-1) break;
                temp = nodeJ;
                nodeJ = nodeAfterJ;
                nodeAfterJ = tour.getNext(nodeJ,temp);
            }
            temp = nodeI;
            nodeI = tour.getNext(nodeI,nodeBefI);
            nodeBefI = temp;
        }
        makeMove2_opt(fourTuple.getNodeBefI(), fourTuple.getNodeI(), fourTuple.getNodeJ(), fourTuple.getNodeAfterJ());
        addToTabuList(bestMove);
    }

    @Override
    public Tour getSolutionTour() throws Exception {
        getInitialSolutionGreedy();
        for (int n=0; n<1000; n++){
            preformBestTwo_OptMove();
            //TODO kan dit sneller berekent worden?
            if (tour.getTourLength() < bestTour.getTourLength()){
                bestTour.makeDeepCopyOf(tour);
                currentBestTourLength = bestTour.getTourLength();
            }
        }
        return bestTour;
    }
}