package TS_ACO_TSP.Interfaces;

import TS_ACO_TSP.Ant;

/**
 * Interface for Ant Colony Optimization (ACO).
 *
 * @author Matias Daneels
 * @version 1.0
 */
public interface ACOInterface extends HeuristicInterface {

    /**
     * Returns the pheromone on the edge (i,j).
     * @param i The number of the first node of the edge.
     * @param j The number of the second node of the edge.
     * @throws IllegalArgumentException If i == j, this is not allowed.
     */
    double getPheremone(int i, int j);

    /**
     * Returns the pheromone matrix.
     */
    double[][] getPheromoneMatrix();

    /**
     * Returns the ant population size.
     */
    int getAntPopSize();

    /**
     * Returns the RHO.
     */
    double getRHO();

    /**
     * Returns the big q.
     */
    double getBIGQ();

    /**
     * Returns a list of ants of the iteration.
     */
    Ant[] getAnts();
}
