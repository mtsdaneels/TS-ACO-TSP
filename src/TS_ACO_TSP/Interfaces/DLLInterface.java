package TS_ACO_TSP.Interfaces;

import TS_ACO_TSP.DLL;

import java.util.List;

/**
 * Interfaces for a double linked list with integers as its elements.
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
     * Set the head of the list to the given node.
     */
    void setHead(DLL.Node node);

    /**
     * Set the tail of the list to the given node.
     */
    void setTail(DLL.Node node);

    /**
     * Returns the next node when given the previous.
     * @param node The node we want to know the next from.
     * @param prev The previous node.
     * @return The next node.
     */
    DLL.Node getNext(DLL.Node node, DLL.Node prev);

}
