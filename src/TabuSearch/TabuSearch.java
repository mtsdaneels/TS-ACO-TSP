package TabuSearch;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

//TODO documentation
public class TabuSearch implements  TabuSearchInterface{

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

    @Override
    public Tour getBestTour(){
        return bestTour;
    }

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

    @Override
    public int getCurrentBestTourLength(){
        return currentBestTourLength;
    }

    /**
     * The tabu list of the tabu search.
     */
    List<Tuple<Integer, Integer>> tabuList;

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
        int factor = 1; //TODO bepalen factor
        this.tabuList = Arrays.asList(new Tuple[graph.getNumberOfVertices()]);
        this.dimension = graph.getNumberOfVertices();
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
     * @throws Exception If the graph of the tabsearch has no vertices.
     */
    private void getInitialSolutionGreedy() throws Exception {
        double shortestDistanceNext;
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
            possibleAds.remove(nextIndex);
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
        List<Integer> possibleAds = new java.util.ArrayList<>(IntStream.range(1, dimension+1).boxed().toList());
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
    }

    /**
     * Returns whether the tabu list contains a move or not.
     * @param i The first node of the move.
     * @param j The second node of the move.
     * @throws IllegalArgumentException If i <= j.
     */
    private boolean tabuListContains(int i, int j){
        if (i >= j) throw new IllegalArgumentException("i >= j is not allowed");
        int w = tabuListTail;
        while (w != tabuListHead){
            if (tabuList.get(w) == null);
            else if (tabuList.get(w).getX() == i && tabuList.get(w).getY() == j){
                return true;
            }
            w--;
            if (w == -1) w = dimension-1;
        }
        return false;
    }

    @Override
    public void addToTabuList(Tuple<Integer, Integer> move){
        tabuList.set(tabuListTail, move);
        tabuListHead++;
        if (tabuListHead == dimension) tabuListHead = 0;
        tabuListTail++;
        if (tabuListTail == dimension) tabuListTail = 0;
    }

    /**
     * Calculate the cost reduction of a two opt move with the given indexes.
     * @throws IllegalArgumentException If i >= j.
     */
    private double calculateCostReduction(int i, int j){
        if (i >= j) throw new IllegalArgumentException("i >= j is not allowed");
        List<Integer> elements = tour.getDLL().getElements();
        if (i == 0 && j == dimension-1) return Double.NEGATIVE_INFINITY;
        int indexBefI = i -1;
        int indexAfterJ = j +1;
        if (indexBefI == -1) indexBefI = dimension-1;
        if (indexAfterJ == dimension) indexAfterJ = 0;
        if (indexBefI == indexAfterJ) return Double.NEGATIVE_INFINITY;
        if (!(i==1 && j==dimension)){
            return +graph.getDistance(elements.get(indexBefI), elements.get(i)) +graph.getDistance(elements.get(j), elements.get(indexAfterJ))
                    -graph.getDistance(elements.get(indexBefI), elements.get(j)) - graph.getDistance(elements.get(i), elements.get(indexAfterJ));
        }
        return 0;
    }

    /**
     * Calculates the best two-opt move at that point.
     * @return The best two-opt move possible at that point.
     */
    private Tuple<Integer,Integer> getBestTwo_OptMove(){
        Tuple<Integer, Integer> bestMove = null;
        List<Integer> elements = tour.getElements();
        double bestCostReduction = Double.NEGATIVE_INFINITY;
        double posCostReduction;

        for (int i=0; i<dimension-1; i++){
            for (int j=i+1; j<dimension; j++){
                posCostReduction = calculateCostReduction(i, j);
                //TODO aspiration citeria bijvoegen
                int nodeI = elements.get(i);
                int nodeJ = elements.get(j);
                if (nodeI >= nodeJ){
                    int temp = nodeI;
                    nodeI = nodeJ;
                    nodeJ = temp;
                }
                if ((posCostReduction > bestCostReduction && !tabuListContains(nodeI, nodeJ))){ //NOTE always nodeI < nodeJ
                    bestCostReduction = posCostReduction;
                    bestMove = new Tuple<>(nodeI, nodeJ);
                }
            }
        }
        return bestMove;
    }

    /**
     * Preforms a two-opt move.
     * @param move A tuple with the 2 nodes that should get switched in the tour.
     */
    private void makeMove2_opt(Tuple<Integer, Integer> move){
        tour.makeMove2_opt(move);
        addToTabuList(move);
    }

    @Override
    public Tour getSolutionTour() throws Exception {
        getInitialSolutionGreedy();
        Tuple<Integer, Integer> bestMove = getBestTwo_OptMove();
        for (int n=0; n<500; n++){
            makeMove2_opt(bestMove);
            //TODO kan dit sneller berekent worden?
            if (tour.getTourLength() < bestTour.getTourLength()){
                bestTour.makeDeepCopyOf(tour);
                currentBestTourLength = bestTour.getTourLength();
            }
            bestMove = getBestTwo_OptMove();
        }
        return bestTour;
    }
}