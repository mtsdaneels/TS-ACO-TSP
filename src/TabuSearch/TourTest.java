package TabuSearch;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

//TODO documentation
public class TourTest {

    @Before
    public void setUp(){

    }

    @Test
    public void makeCanonicalTourTest() throws Exception {
        Tour tour = new Tour(new Graph("C:\\Users\\mdane\\IdeaProjects\\TabuSearch\\src\\TabuSearch\\Dataset\\att48.tsp"));
        tour.makeCanonicalTour();
        for (int i=1; i<=48; i++){
            Assert.assertEquals(i, (int) tour.getDllList().get(i-1));
        }
    }

    @Test
    public void getTourLengthTest() throws Exception{
        Tour tour = new Tour(new Graph("C:\\Users\\mdane\\IdeaProjects\\TabuSearch\\src\\TabuSearch\\Dataset\\pcb442.tsp"));
        tour.makeCanonicalTour();
        Assert.assertEquals( 221440, tour.getTourLength());
    }

    @Test
    public void getTourLengthTest1() throws Exception{
        Tour tour = new Tour(new Graph("C:\\Users\\mdane\\IdeaProjects\\TabuSearch\\src\\TabuSearch\\Dataset\\gr666.tsp"));
        tour.makeCanonicalTour();
        Assert.assertEquals(423710, tour.getTourLength());
    }

    @Test
    public void getTourLengthTest2() throws Exception{
        Tour tour = new Tour(new Graph("C:\\Users\\mdane\\IdeaProjects\\TabuSearch\\src\\TabuSearch\\Dataset\\att532.tsp"));
        tour.makeCanonicalTour();
        Assert.assertEquals(309636, tour.getTourLength());
    }
}