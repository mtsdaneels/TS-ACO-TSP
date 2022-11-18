package TabuSearch;

import java.util.List;

public class Tour implements TourInterface {

    /**
     * Double linked ist representing the tour.
     */
    private final DLL tour;

    /**
     * The graph where the tour takes place.
     */
    private final Graph graph;

    /**
     * Constructor initializing a tour.
     * @param graph The graph where the tour takes place.
     */
    public Tour(Graph graph){
        tour = new DLL();
        this.graph = graph;
    }

    @Override
    public void addLast(int element){
        tour.insertLast(element);
    }

    @Override
    public void addFirst(int element) {
        tour.insertFirst(element);
    }

    @Override
    public int removeLast() {
        DLL.Node temp = tour.removeLast();
        return temp.element;
    }

    @Override
    public int removeFirst() {
        DLL.Node temp = tour.removeFirst();
        return temp.element;
    }

    @Override
    public int getAmountOfNodes(){
        return tour.size();
    }

    @Override
    public List<Integer> getTour() {
        return tour.getElements();
    }

    //TODO return veranderen naar double, mag dat?
    @Override
    public int getTourLength() {
        int tourLength = 0;
        List<Integer> tour = getTour();
        for (int i=0; i<tour.size()-1; i++){
            tourLength += graph.getDistance(tour.get(i), tour.get(i + 1));
        }
        tourLength += graph.getDistance(tour.get(tour.size()-1), tour.get(0));
        return tourLength;
    }

    /**
     * Makes the tour canonical. The tour will be node 1, node 2, node 3, ...
     */
    protected void makeCanonicalTour(){
        tour.removeAll();
        for (int i = 1; i <= graph.getNumberOfVertices(); i++){
            tour.insertFirst(i);
        }
    }
}
