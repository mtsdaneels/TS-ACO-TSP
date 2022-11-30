package TS_ACO_TSP.Tests;

import TS_ACO_TSP.Graph;
import TS_ACO_TSP.Tour;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for testing Tour.
 *
 * @author Matias Daneels
 * @version 1.0
 */
public class TourTest {

    @Before
    public void setUp(){

    }

    @Test
    public void makeCanonicalTourTest() throws Exception {
        Tour tour = new Tour(new Graph("C:\\Users\\mdane\\IdeaProjects\\TS_ACO_TSP\\src\\TS_ACO_TSP\\Dataset\\att48.tsp"));
        tour.makeCanonicalTour();
        for (int i=1; i<=48; i++){
            Assert.assertEquals(i, (int) tour.getDllList().get(i-1));
        }
    }

    @Test
    public void getTourLengthTest() throws Exception{
        Tour tour = new Tour(new Graph("C:\\Users\\mdane\\IdeaProjects\\TS_ACO_TSP\\src\\TS_ACO_TSP\\Dataset\\pcb442.tsp"));
        tour.makeCanonicalTour();
        Assert.assertEquals( 221440, tour.getTourLength());
    }

    @Test
    public void getTourLengthTest1() throws Exception{
        Tour tour = new Tour(new Graph("C:\\Users\\mdane\\IdeaProjects\\TS_ACO_TSP\\src\\TS_ACO_TSP\\Dataset\\gr666.tsp"));
        tour.makeCanonicalTour();
        Assert.assertEquals(423710, tour.getTourLength());
    }

    @Test
    public void getTourLengthTest2() throws Exception{
        Tour tour = new Tour(new Graph("C:\\Users\\mdane\\IdeaProjects\\TS_ACO_TSP\\src\\TS_ACO_TSP\\Dataset\\att532.tsp"));
        tour.makeCanonicalTour();
        Assert.assertEquals(309636, tour.getTourLength());
    }
}