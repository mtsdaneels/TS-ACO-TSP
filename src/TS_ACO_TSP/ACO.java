package TS_ACO_TSP;

public class ACO implements ACOInterface{

    /**
     * Constant holding the influence of the length of the ant tour.
     */
    private final double BIGQ = 1.0;

    /**
     * Returns the big q.
     */
    public double getBIGQ(){
        return BIGQ;
    }

    /**
     * Constant holding the evaporation rate.
     */
    private final double RHO = 0.8;

    /**
     * Returns the RHO.
     */
    public double getRHO(){
        return RHO;
    }

    /**
     * Constant holding the size of every population.
     */
    private final int antPopSize = 20;

    /**
     * Returns the ant population size.
     */
    public int getAntPopSize(){
        return antPopSize;
    }

    /**
     * A matrix with the pheromone of the edges.
     */
    private double[][] pheromoneMatrix;

    /**
     * Returns the pheromone matrix.
     */
    public double[][] getPheromoneMatrix(){
        return pheromoneMatrix;
    }

    /**
     * List holding all the ants of the iteration.
     */
    private Ant[] ants;

    /**
     * Returns a list of ants of the iteration.
     */
    public Ant[] getAnts(){
        return ants;
    }

    /**
     * The graph where the ACO takes place.
     */
    private final Graph graph;

    @Override
    public Graph getGraph() {
        return graph;
    }

    /**
     * Constructor initializing a new Ant Colony Optimization process in a given graph.
     * @param graph The graph where the Ant Colony Optimization takes place.
     */
    public ACO(Graph graph){
        this.graph = graph;
        this.pheromoneMatrix = new double[getDimension()][getDimension()];
        setupPheromone();
        this.ants = new Ant[antPopSize];
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

    /**
     * Returns the pheromone on the edge (i,j).
     * @param i The number of the first node of the edge.
     * @param j The number of the second ndoe of the edge.
     * @throws IllegalArgumentException If i == j, this is not allowed.
     */
    public double getPheremone(int i, int j) {
        if (i<j) return pheromoneMatrix[i-1][j-1];
        else if (i>j) return pheromoneMatrix[j-1][i-1];
        else throw new IllegalArgumentException("i == j is not allowed!");
    }

    /**
     * Set up the pheromone by putting 1 in every slot of the pheromone matrix.
     */
    private void setupPheromone(){
        for (int i=0; i<getDimension(); i++){
            for (int j=0; j<getDimension(); j++){
                pheromoneMatrix[i][j] = 1;
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
            if (ant.getTourLength() < getTourLength()){
                tour = ant.getTour();
                tourLength = ant.getTourLength();
            }
        }
        return deltaTou;
    }

    /**
     * Calculate the final tour with the information gained by simulating the ants.
     * @return The tour that was found best by the Ant Colony Optimization.
     * @throws Exception
     */
    private Tour calculateFinalTour() throws Exception {
        return tour;
        //Ant finalAnt = new Ant(this);
        //return finalAnt.getTour();
    }

    @Override
    public Tour getSolutionTour() throws Exception {
        for (int n=0; n<5; n++) {
            generateAnts();
            for (int i = 0; i < getDimension(); i++) {
                for (int j = i + 1; j < getDimension(); j++) {
                    double deltaTou = calculateDeltaTou(i + 1, j + 1); //NOTE The argument of calculateDeltaTou are the number of the nodes.
                    pheromoneMatrix[i][j] = getRHO() * pheromoneMatrix[i][j] + deltaTou; //NOTE Update of the pheromone concentration.
                }
            }
        }
        return calculateFinalTour();
    }

    @Override
    public int getDimension() {
        return graph.getNumberOfVertices();
    }
}
