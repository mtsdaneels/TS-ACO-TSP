package TabuSearch;

import java.util.List;

public interface TourInterface {

    /**
     * Returns an ordered list representing a tour of the graph. It should always contain all elements of the graph
     * exactly once in a specific order.
     */
    List<Integer> getTour();

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
}