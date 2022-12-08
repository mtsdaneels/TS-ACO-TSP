package TS_ACO_TSP.Interfaces;

import TS_ACO_TSP.Graph;
import TS_ACO_TSP.Tour;

/**
 * Interface for a heuristic method.
 *
 * @author Matias Daneels
 * @version 1.0
 */
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

    /**
     * Returns the maximum amount of iterations.
     */
    int getMaximumIterations();
}
