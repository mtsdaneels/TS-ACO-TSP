package TabuSearch;

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
    protected final double[][] distanceMatrix;

    /**
     * Returns the distance matrix.
     */
    public double[][] getDistanceMatrix(){
        return distanceMatrix;
    }

    /**
     * Map containing the nodes with their x- and y-coordinate.
     */
    //TODO Set to private
    public final HashMap<Integer, Tuple<Double, Double>> nodes;

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

    //TODO return veranderen naar double, mag dat?
    @Override
    public int getDistance(int i, int j) {
        return (int) distanceMatrix[i-1][j-1];
    }

    //TODO
    @Override
    public Tour getTabuSearchBestTour(int maxNumberOfIterations) {
        return null;
    }

    //TODO
    @Override
    public Tour getOtherHeuristicBestTour(int maxNumberOfIterations) {
        return null;
    }
}
