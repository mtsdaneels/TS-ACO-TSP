package TS_ACO_TSP;

import TS_ACO_TSP.Interfaces.GraphInterface;

import java.lang.*;
import java.util.*;

/**
 * Class representing a graph.
 *
 * @author Matias Daneels
 * @version 1.0
 */
public class Graph implements GraphInterface {

    /**
     * How many points are in the problem.
     */
    private final int dimension;

    /**
     * Matrix containing the dinstances between nodes.
     */
    protected final int[][] distanceMatrix;

    /**
     * Returns the distance matrix.
     */
    public int[][] getDistanceMatrix(){
        return distanceMatrix;
    }

    /**
     * Map containing the nodes with their x- and y-coordinate.
     */
    private final HashMap<Integer, Tuple<Double, Double>> nodes;

    /**
     * Return the nodes of the graph.
     */
    public HashMap<Integer, Tuple<Double, Double>> getNodes(){
        return nodes;
    }

    /**
     * Constructor initialising variables dimension, distanceMatrix and nodes given a file.
     * @param fileName The name of a file.
     * @throws Exception If the file cannot be found.
     */
    public Graph(String fileName) throws Exception {
        FileReader reader = new FileReader(fileName);
        dimension = reader.getDimension();
        distanceMatrix = reader.getMatrix();
        nodes = reader.getNodes();
    }

    @Override
    public Collection<Integer> getVertices() {
        return nodes.keySet();
    }

    @Override
    public int getNumberOfVertices() {
        return dimension;
    }

    @Override
    public int getDistance(int i, int j) {
        return (int) distanceMatrix[i-1][j-1];
    }

    @Override
    public Tour getTabuSearchBestTour(int maxNumberOfIterations) throws Exception {
        TabuSearch tabuSearch = new TabuSearch(this, maxNumberOfIterations);
        return tabuSearch.getSolutionTour();
    }

    @Override
    public Tour getOtherHeuristicBestTour(int maxNumberOfIterations) throws Exception {
        ACO aco = new ACO(this, maxNumberOfIterations);
        return aco.getSolutionTour();
    }
}
