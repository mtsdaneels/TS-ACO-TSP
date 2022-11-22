package TabuSearch;

import java.util.List;

/**
 * Class representing a tour in a graph.
 * The class uses a double linked list as a representation of the tour. The last element of the double linked list
 * is connected to the first element in the double linked list (if the tour is 1,2,3 then u have a tour 1->2->3->1).
 */
public class Tour implements TourInterface {

    /**
     * Double linked ist representing the tour.
     */
    private final DLL dllList;

    @Override
    public DLL getDLL(){
        return dllList;
    }

    @Override
    public List<Integer> getDllList() {
        return dllList.getElements();
    }

    /**
     * The graph where the tour takes place.
     */
    private final Graph graph;

    @Override
    public Graph getGraph(){
        return graph;
    }

    /**
     * Constructor initializing a tour.
     * @param graph The graph where the tour takes place.
     */
    public Tour(Graph graph){
        dllList = new DLL();
        this.graph = graph;
    }

    @Override
    public void addLast(int element){
        dllList.insertLast(element);
    }

    @Override
    public void addFirst(int element) {
        dllList.insertFirst(element);
    }

    @Override
    public int removeLast() {
        DLL.Node temp = dllList.removeLast();
        return temp.element;
    }

    @Override
    public int removeFirst() {
        DLL.Node temp = dllList.removeFirst();
        return temp.element;
    }

    @Override
    public int getAmountOfNodes(){
        return dllList.size();
    }

    @Override
    public int getTourLength() {
        double tourLength = 0;
        List<Integer> tour = getDllList();
        if (tour.size() == 0){
            return 0;
        }
        for (int i=0; i<tour.size()-1; i++){
            tourLength += graph.getDistance(tour.get(i), tour.get(i + 1));
        }
        tourLength += graph.getDistance(tour.get(tour.size()-1), tour.get(0));
        return (int) tourLength;
    }

    /**
     * Makes the tour canonical. The tour will be node 1, node 2, node 3, ...
     */
    protected void makeCanonicalTour(){
        dllList.removeAll();
        for (int i = 1; i <= graph.getNumberOfVertices(); i++){
            dllList.insertLast(i);
        }
    }

    /**
     * Preforms the given 2-opt move.
     * @param move The move we want to preform.
     */
    protected void makeMove2_opt(Tuple<Integer, Integer> move){
        dllList.makeMove2_opt(move);
    }

    /**
     * Copies all the elements from the given tour.
     * @param tour The tour we want to copy.
     */
    protected void makeDeepCopyOf(Tour tour){
        this.dllList.makeDeepCopyOf(tour.getDLL());
    }

    @Override
    public void printTour(){
        dllList.printTour();
    }

    @Override
    public List<Integer> getElements(){
        return dllList.getElements();
    }
}
