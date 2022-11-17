package TabuSearch;

import java.io.*;
import java.lang.*;
import java.util.*;

public class Graph implements GraphInterface{

    /**
     * How many points are in the problem.
     */
    private final int dimension;

    /**
     * Matrix containing the dinstances between nodes.
     */
    private final double[][] distanceMatrix;

    /**
     * Map containing the nodes with their x- and y-coordinate.
     */
    //TODO Set to private
    public final HashMap<Integer, Tuple<Double, Double>> nodes;

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
        return null;
    }

    @Override
    public int getNumberOfVertices() {
        return 0;
    }

    @Override
    public int getDistance(int i, int j) {
        return 0;
    }

    @Override
    public Tour getTabuSearchBestTour(int maxNumberOfIterations) {
        return null;
    }

    @Override
    public Tour getOtherHeuristicBestTour(int maxNumberOfIterations) {
        return null;
    }
}
