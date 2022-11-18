package TabuSearch;

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
}
