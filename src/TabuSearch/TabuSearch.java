package TabuSearch;

import java.util.Arrays;
import java.util.List;
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
    }

    /**
     * Returns whether the tabu list contains a move or not.
     * @param i The first node of the move.
     * @param j The second node of the move.
     * @throws IllegalArgumentException If i <= j.
     */
    //TODO is niet volledig efficient
    protected boolean tabuListContains(int i, int j){
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
    public void addToTabuList(Tuple<Integer, Integer> move){
        tabuList.set(tabuListTail, move);
        tabuListHead++;
        if (tabuListHead == tabuList.size()-1) tabuListHead = 0;
        tabuListTail++;
        if (tabuListTail == tabuList.size()-1) tabuListTail = 0;
    }

    @Override
    public Tour getSolutionTour() throws Exception {
        getInitialSolutionGreedy();
        for (int n=0; n<10000; n++){
            tour.getDLL().preformBestTwo_OptMove(this);
            //TODO kan dit sneller berekent worden?
            if (tour.getTourLength() < bestTour.getTourLength()){
                bestTour.makeDeepCopyOf(tour);
                currentBestTourLength = bestTour.getTourLength();
            }
        }
        return bestTour;
    }
}