package TabuSearch;

import java.util.HashMap;

public interface FileReaderInterface {

    /**
     * Returns the dimension of the problem.
     */
    int getDimension();

    /**
     * Returns the matrix representing the distances between nodes.
     */
    double[][] getMatrix();

    /**
     * Returns the nodes of the problem.
     */
    HashMap<Integer, Tuple<Double, Double>> getNodes();
}

