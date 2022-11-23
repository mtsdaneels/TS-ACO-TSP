package TabuSearch;

import java.util.List;

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
