package TS_ACO_TSP.Interfaces;

import TS_ACO_TSP.Graph;
import TS_ACO_TSP.Tour;

/**
 * Interface for Ant.
 *
 * @author Matias Daneels
 * @version 1.0
 */
public interface AntInterface {

    /**
     * Returns true if and only if the ant has visited the node.
     * @param node The number of the node.
     * @return True if and only if the ant has visited the node.
     */
    boolean hasVisited(int node);

    /**
     * Returns whether the ant has visited the edge i,j.
     * @param i The number of the first node.
     * @param j The number of the second node.
     * @return Returns true if and only if the ant has visited the edge (i,j) or (j,i)
     */
    boolean hasVisitedEdge(int i, int j);

    /**
     * Returns the tour of the ant.
     */
    Tour getTour();

    /**
     * Returns the length of the tour made by the ant.
     */
    int getTourLength();

    /**
     * Returns the graph where the ant is in.
     */
    Graph getGraph();
}
