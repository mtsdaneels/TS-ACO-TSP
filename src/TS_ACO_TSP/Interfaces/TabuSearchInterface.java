package TS_ACO_TSP.Interfaces;

import TS_ACO_TSP.Tuple;

import java.util.List;

/**
 * Interface for TabuSearch.
 *
 * @author Matias Daneels
 * @version 1.0
 */
public interface TabuSearchInterface extends HeuristicInterface {

    /**
     * Returns the tabu list.
     */
    List<Tuple<Integer, Integer>> getTabuList();

    /**
     * Adds a move to the tabu list.
     */
    void addToTabuList(Tuple<Integer, Integer> move);
}
