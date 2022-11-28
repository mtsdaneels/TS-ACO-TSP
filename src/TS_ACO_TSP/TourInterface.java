package TS_ACO_TSP;

import java.util.List;

/**
 * Interface for Tour.
 * //TODO more documentation
 */
public interface TourInterface {

    /**
     * Returns an ordered list representing a tour of the graph. It should always contain all elements of the graph
     * exactly once in a specific order.
     */
    List<Integer> getDllList();

    /**
     * Returns the length of the tour. This is the sum of all distances between nodes i and j if j follows i in the list
     * given by getTour() or if i and j are the first and last element.
     */
    int getTourLength();

    //TODO: Extra aan interface, mag dit??
    /**
     * Adds an element to the back of the tour.
     * @param element The element we want to add.
     */
    void addLast(int element);

    /**
     * Adds an element to the front of the tour.
     * @param element The element we want to add.
     */
    void addFirst(int element);

    /**
     * Removes and returns the last element of the tour.
     * @return The last element of the tour.
     */
    int removeLast();

    /**
     * Removes and returns the first element of the tour.
     * @return The first element of the tour.
     */
    int removeFirst();

    /**
     * Returns the size of the tour
     * @return The amount of nodes in the tour.
     */
    int getAmountOfNodes();

    /**
     * Print the tour.
     */
    void printTour();

    /**
     * Returns the graph where the tour takes place.
     */
    Graph getGraph();

    /**
     * Returns the DLL.
     */
    DLL getDLL();

    /**
     * Returns a list containing the nodes of the tour (in order).
     */
    List<Integer> getElements();

    /**
     * Returns the first node of the tour.
     */
    DLL.Node getFirstNode();

    /**
     * Returns the last node of the tour.
     */
    DLL.Node getLastNode();
}