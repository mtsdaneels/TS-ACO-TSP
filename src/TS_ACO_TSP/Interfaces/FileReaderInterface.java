package TS_ACO_TSP.Interfaces;

import TS_ACO_TSP.Tuple;

import java.util.HashMap;

/**
 * Interface for FileReader.
 *
 * @author Matias Daneels
 * @version 1.0
 */
public interface FileReaderInterface {

    /**
     * Returns the dimension of the problem.
     */
    int getDimension();

    /**
     * Returns the matrix representing the distances between nodes.
     */
    int[][] getMatrix();

    /**
     * Returns the nodes of the problem.
     */
    HashMap<Integer, Tuple<Double, Double>> getNodes();
}

