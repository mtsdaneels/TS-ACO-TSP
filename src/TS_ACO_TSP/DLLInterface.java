package TS_ACO_TSP;

import java.util.List;

/**
 * Interface for a double linked list with integers as its elements.
 */
public interface DLLInterface {

    /**
     * Returns the size of the DLL.
     */
    int size();

    /**
     * Returns whether the DLL is empty.
     */
    boolean isEmpty();

    /**
     * Inserts a new node in the first position of the DLL.
     */
    void insertFirst(int element);

    /**
     * Inserts a new node in the last position of the DLL.
     */
    void insertLast(int element);

    /**
     * Removes and returns the first node of the DLL.
     * @return The first node of the DLL.
     */
    DLL.Node removeFirst();

    /**
     * Removes and returns the last node of the DLL.
     * @return The last node of the DLL.
     */
    DLL.Node removeLast();

    /**
     * Removes and returns the first node in the DLL with element k.
     * @param k The element.
     * @return The first node in the DLL with element k.
     */
    DLL.Node search(int k);

    /**
     * Returns a list of the elements that are in the DLL.
     */
    List<Integer> getElements();

    /**
     * Returns the head.
     */
    DLL.Node getHead();

    /**
     * Returns the tail.
     */
    DLL.Node getTail();

    /**
     * Returns the size.
     */
    int getSize();

    /**
     * Returns the node at a given index in the list.
     * @param index The index where we want to get the node.
     */
    DLL.Node getNodeAtIndex(int index);

    /**
     * Removes and returns the node from the DLL.
     * @param node The node we want to remove.
     */
    DLL.Node remove(DLL.Node node);

    /**
     * Returns the node with element k, returns null if k not in the DLL.
     * @param k The element.
     */
    DLL.Node searchWithoutRemove(int k);

    /**
     * Returns the index of the first node with the given element.
     * @param elementOfNode The element of the node.
     */
    int getIndexOf(int elementOfNode);

    /**
     * Remove all the elements from the list
     */
    void removeAll();

    /**
     * Searches and preforms the best possible two opt move for the given tabu search. The move gets added to the tabu list
     * of the tabu search.
     * @param tabuSearch The tabu search where the two opt takes place.
     */
    void preformBestTwo_OptMove(TabuSearch tabuSearch);
}
