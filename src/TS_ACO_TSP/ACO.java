package TS_ACO_TSP;

import TS_ACO_TSP.Interfaces.ACOInterface;

/**
 * Class simulating ant Ant Colony Optimization.
 *
 * @author Matias Daneels
 * @version 1.0
 */
public class ACO implements ACOInterface {

    /**
     * Constant holding the influence of the length of the ant tour.
     */
    private final double BIGQ = 0.3;

    /**
     * Constant holding the evaporation rate.
     */
    private final double RHO = 0.9;

    /**
     * Constant holding the size of every population.
     */
    private final int antPopSize = 20;


    @Override
    public double getBIGQ(){
        return BIGQ;
    }


    @Override
    public double getRHO(){
        return RHO;
    }

    @Override
    public int getAntPopSize(){
        return antPopSize;
    }

    /**
     * A matrix with the pheromone of the edges.
     */
    private double[][] pheromoneMatrix;

    @Override
    public double[][] getPheromoneMatrix(){
        return pheromoneMatrix;
    }

    /**
     * List holding all the ants of the iteration.
     */
    private Ant[] ants;

    @Override
    public Ant[] getAnts(){
        return ants;
    }

    /**
     * The graph where the ACO takes place.
     */
    private final Graph graph;

    /**
     * The maximum amount of iterations.
     */
    private final int maximumIterations;

    @Override
    public int getMaximumIterations(){
        return maximumIterations;
    }

    @Override
    public Graph getGraph() {
        return graph;
    }

    /**
     * Constructor initializing a new Ant Colony Optimization process in a given graph.
     * @param graph The graph where the Ant Colony Optimization takes place.
     */
    public ACO(Graph graph, int maximumIterations){
        this.graph = graph;
        this.pheromoneMatrix = new double[getDimension()][getDimension()];
        setupPheromone();
        this.ants = new Ant[antPopSize];
        this.maximumIterations = maximumIterations;
    }

    /**
     * The best tour found by any ant.
     */
    private Tour tour;

    /**
     * The tour length of tour. (starts at infinity)
     */
    private int tourLength = (int) Double.POSITIVE_INFINITY;

    /**
     * Returns the tour length of tour.
     */
    public int getTourLength(){
        return tourLength;
    }

    @Override
    public Tour getTour() {
        return tour;
    }

    @Override
    public double getPheremone(int i, int j) {
        if (i<j) return pheromoneMatrix[i-1][j-1];
        else if (i>j) return pheromoneMatrix[j-1][i-1];
        else throw new IllegalArgumentException("i == j is not allowed!");
    }

    /**
     * Set up the pheromone by putting 1 in every slot of the pheromone matrix.
     */
    private void setupPheromone(){
        double initialNumber = 1.0/getDimension();
        for (int i=0; i<getDimension(); i++){
            for (int j=0; j<getDimension(); j++){
                pheromoneMatrix[i][j] = initialNumber;
            }
        }
    }

    /**
     * Generates a population of ants.
     */
    private void generateAnts() throws Exception {
        for (int i=0; i < getAntPopSize(); i++){
            ants[i] = new Ant(this);
        }
    }

    /**
     * Returns the summation of delta for a given edge (i,j).
     * @param i The number of the first node of the edge.
     * @param j The number of the second node of the edge.
     * @return The summation of all the delta tou for all the ants in the generation.
     */
    private double calculateDeltaTou(int i, int j){
        double deltaTou = 0;
        for (Ant ant: getAnts()){
            if (ant.hasVisitedEdge(i, j)){
                deltaTou += getBIGQ()/ant.getTourLength();
            }
        }
        return deltaTou;
    }

    /**
     * Look through the ants and update if there is a better tour.
     */
    private void updateBestTour() {
        for (Ant ant: getAnts()){
            if (ant.getTourLength() < getTourLength()){
                tour = ant.getTour();
                tourLength = ant.getTourLength();
            }
        }
    }

    @Override
    public Tour getSolutionTour() throws Exception {
        for (int n=0; n<maximumIterations; n++) {
            generateAnts();
            updateBestTour();
            updatePheremone();
        }
        return tour;
    }

    /**
     * Update the pheremone matrix.
     */
    private void updatePheremone() {
        for (int i = 0; i < getDimension(); i++) {
            for (int j = i + 1; j < getDimension(); j++) {
                double deltaTou = calculateDeltaTou(i + 1, j + 1); //The argument of calculateDeltaTou are the number of the nodes.
                pheromoneMatrix[i][j] = getRHO() * pheromoneMatrix[i][j] + deltaTou; //Update of the pheromone concentration.
            }
        }
    }

    @Override
    public int getDimension() {
        return graph.getNumberOfVertices();
    }
}
