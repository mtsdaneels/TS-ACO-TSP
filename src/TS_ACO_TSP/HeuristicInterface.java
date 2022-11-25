package TS_ACO_TSP;

public interface HeuristicInterface {

    /**
     * Returns the tour.
     */
    Tour getTour();

    /**
     * Returns the graph.
     */
    Graph getGraph();

    /**
     * Returns the solution recommended by the heuristic.
     * @throws Exception If the graph of the problem has no nodes.
     */
    Tour getSolutionTour() throws Exception;

    /**
     * Returns the dimension of the problem.
     */
    int getDimension();
}
