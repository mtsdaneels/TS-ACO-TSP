package TS_ACO_TSP;

import java.util.List;
import java.util.stream.IntStream;

public class Ant implements AntInterface{

    /**
     * List representing if an ant has visited a certain node. If index i != 0, it means the ant has visited node i-1.
     */
    private int[] hasVisited;

    /**
     * Returns true if and only if the ant has visited the node.
     * @param node The number of the node.
     * @return True if and only if the ant has visited the node.
     */
    public boolean HasVisited(int node){
        return 0 != hasVisited[node-1];
    }

    /**
     * Returns whether the ant has visited the edge i,j.
     * @param i The number of the first node.
     * @param j The number of the second node.
     * @return Returns true if and only if the ant has visited the edge (i,j) or (j,i)
     */
    public boolean hasVisitedEdge(int i, int j){
        return (Math.abs(hasVisited[i - 1] - hasVisited[j - 1]) == 1)
                || Math.abs(hasVisited[i - 1] - hasVisited[j - 1]) == aco.getDimension() - 1;
    }

    /**
     * Constant used to change the influence of the pheromone when choosing the path.
     */
    private final double ALPHA = 1.0;

    /**
     * Constant used to change the influence of the length of the edges when choosing the path.
     */
    private final double BETA = 2.0;

    /**
     * The tour of the ant.
     */
    private Tour tour;

    /**
     * Returns the tour of the ant.
     */
    public Tour getTour(){
        return tour;
    }

    /**
     * The length of the tour made by the ant.
     */
    private int tourLength;

    /**
     * Returns the length of the tour made by the ant.
     */
    public int getTourLength(){
        return tourLength;
    }

    /**
     * The graph where the ant is in.
     */
    private final Graph graph;

    /**
     * Returns the graph where the ant is in.
     */
    public Graph getGraph(){
        return graph;
    }

    /**
     * The ACO where the ant is in.
     */
    private final ACO aco;

    /**
     * Returns the ACO where the ant is in.
     */
    public ACO getAco(){
        return aco;
    }

    public Ant(ACO aco) throws Exception {
        this.aco = aco;
        this.graph = aco.getGraph();
        this.hasVisited = new int[aco.getDimension()];
        this.tour = new Tour(this.graph);
        calculateTour();
    }

    /**
     * Get the best next node for the ant.
     * @param possibleAds A list of the nodes that are still available.
     * @param prevNode The previous node that was added to the tour. (The ant is now in this node)
     * @return The index of the next node in possibleAds.
     */
    private int getBestNextNode(List<Integer> possibleAds, int prevNode){
        double bestProbability = Double.NEGATIVE_INFINITY, probability;
        int nextIndex = -1;
        double sommation = 0;
        for (int i: possibleAds){
            sommation += Math.pow(aco.getPheremone(prevNode, i), ALPHA)
                    * Math.pow( ( 1.0 / graph.getDistance(prevNode, i)) , BETA);
        }
        if (sommation == 0) sommation = 1;
        for (int j=0; j < possibleAds.size(); j++){
            probability = ( (Math.pow(aco.getPheremone(prevNode, possibleAds.get(j)), ALPHA)
                    * Math.pow( ( 1.0 / graph.getDistance(prevNode, possibleAds.get(j)) ), BETA) ) / sommation);
            if (probability > bestProbability){
                bestProbability = probability;
                nextIndex = j;
            }
        }
        return nextIndex;
    }

    /**
     * Calculate the tour that the ant follows.
     * @throws Exception If the ACO of the ant has no nodes.
     */
    private void calculateTour() throws Exception {
        int counter = 1;
        if (aco.getDimension() == 0){
            throw new Exception();
        }
        tour.getDLL().removeAll(); //Clear the tour
        List<Integer> possibleAds = new java.util.ArrayList<>(IntStream.range(1, aco.getDimension()+1).boxed().toList());
        int nextIndex = (int) (Math.random() * ((aco.getDimension() - 1))); //Start at a random node
        int nextNode = possibleAds.get(nextIndex);
        tour.addLast(nextNode);
        hasVisited[nextNode-1] = counter;
        possibleAds.remove(nextIndex);
        //TODO betere tijdscomplexiteit mogelijk?
        while (!possibleAds.isEmpty()){
            counter++;
            nextIndex = getBestNextNode(possibleAds, nextNode);
            nextNode = possibleAds.get(nextIndex);
            tour.addLast(nextNode); //Add the node with the shortest length to the last one
            hasVisited[nextNode-1] += counter;
            possibleAds.remove(nextIndex); //Node that gets used is now not possible to add anymore
        }
        tourLength = tour.getTourLength();
    }
}
