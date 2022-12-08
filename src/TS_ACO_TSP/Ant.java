package TS_ACO_TSP;

import TS_ACO_TSP.Interfaces.AntInterface;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Class representing an ant.
 *
 * @author Matias Daneels
 * @version 1.0
 */
public class Ant implements AntInterface {

    /**
     * List representing if an ant has visited a certain node. If index i != 0, it means the ant has visited node i-1.
     */
    private int[] hasVisited;

    @Override
    public boolean hasVisited(int node){
        return 0 != hasVisited[node-1];
    }

    @Override
    public boolean hasVisitedEdge(int i, int j){
        return (Math.abs(hasVisited[i - 1] - hasVisited[j - 1]) == 1)
                || Math.abs(hasVisited[i - 1] - hasVisited[j - 1]) == aco.getDimension() - 1;
    }

    /**
     * Constant used to change the influence of the pheromone when choosing the path.
     */
    private final double ALPHA = 1;

    /**
     * Constant used to change the influence of the length of the edges when choosing the path.
     */
    private final double BETA = 2;

    /**
     * The tour of the ant.
     */
    private Tour tour;

    @Override
    public Tour getTour(){
        return tour;
    }

    /**
     * The length of the tour made by the ant.
     */
    private int tourLength;

    @Override
    public int getTourLength(){
        return tourLength;
    }

    /**
     * The graph where the ant is in.
     */
    private final Graph graph;

    @Override
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

    /**
     * Constructor creating a new ant.
     * @param aco The ant colony optimization the ant is in.
     * @throws Exception
     */
    public Ant(ACO aco) throws Exception {
        this.aco = aco;
        this.graph = aco.getGraph();
        this.hasVisited = new int[aco.getDimension()];
        this.tour = new Tour(this.graph);
        calculateTour();
    }

    /**
     * Get the best next node for the ant.
     * @param possibleAdds A list of the nodes that are still available.
     * @param prevNode The previous node that was added to the tour. (The ant is now in this node)
     * @return The index of the next node in possibleAdds.
     */
    private int getBestNextNode(List<Integer> possibleAdds, int prevNode){
        double bestProbability = Double.NEGATIVE_INFINITY, probability;
        int nextIndex = -1;
        double sommation = 0;
        for (int i: possibleAdds){
            sommation += Math.pow(aco.getPheremone(prevNode, i), ALPHA)
                    * Math.pow( ( 1.0 / graph.getDistance(prevNode, i)) , BETA);
        }
        for (int j=0; j < possibleAdds.size(); j++){
            probability = ( (Math.pow(aco.getPheremone(prevNode, possibleAdds.get(j)), ALPHA)
                    * Math.pow( ( 1.0 / graph.getDistance(prevNode, possibleAdds.get(j)) ), BETA) ) / sommation);
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
