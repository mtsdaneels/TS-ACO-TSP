package TS_ACO_TSP;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

//TODO veel andere testen
public class FileReaderTest {

    @Test
    public void getDimensionTest() throws Exception {
        FileReader fileReader;
        fileReader = new FileReader("C:\\Users\\mdane\\IdeaProjects\\TS_ACO_TSP\\src\\TS_ACO_TSP\\Dataset\\att48.tsp");
        Assert.assertEquals(48, fileReader.getDimension());
        fileReader = new FileReader("C:\\Users\\mdane\\IdeaProjects\\TS_ACO_TSP\\src\\TS_ACO_TSP\\Dataset\\a280.tsp");
        Assert.assertEquals(280, fileReader.getDimension());
        fileReader = new FileReader("C:\\Users\\mdane\\IdeaProjects\\TS_ACO_TSP\\src\\TS_ACO_TSP\\Dataset\\bayg29.tsp");
        Assert.assertEquals(29, fileReader.getDimension());
        fileReader = new FileReader("C:\\Users\\mdane\\IdeaProjects\\TS_ACO_TSP\\src\\TS_ACO_TSP\\Dataset\\ch150.tsp");
        Assert.assertEquals(150, fileReader.getDimension());
    }

    @Test
    public void getNodesTest() throws Exception{
        FileReader fileReader;
        fileReader = new FileReader("C:\\Users\\mdane\\IdeaProjects\\TS_ACO_TSP\\src\\TS_ACO_TSP\\Dataset\\att48.tsp");
        Assert.assertEquals(6734, (int) (double) fileReader.getNodes().get(1).getX());
        List<Integer> list = Arrays.asList(6734,2233,5530,401,3082,7608,7573,7265,6898, 1112, 5468, 5989, 4706, 4612, 6347, 6107, 7611, 7462, 7732, 5900, 4483, 6101, 5199, 1633, 4307, 675, 7555, 7541, 3177, 7352, 7545, 3245, 6426, 4608, 23, 7248, 7762, 7392, 3484, 6271, 4985, 1916, 7280, 7509, 10, 6807, 5185, 3023);
        for (int i=1; i<=48; i++){
            Assert.assertEquals((int) list.get(i-1), (int) (double) fileReader.getNodes().get(i).getX());
        }
        list = Arrays.asList(1453,10,1424,841,1644,4458,3716,1268,1885,2049,2606,2873,2674,2035,2683,669,5184,3590,4723,3561,3369,1110,2182,2809,2322,1006,4819,3981,756,4506,2801,3305,3173,1198,2216,3779,4595,2244,2829,2135,140,1569,4899,3239,2676,2993,3258,1942);
        for (int i=1; i<=48; i++){
            Assert.assertEquals((int) list.get(i-1), (int) (double) fileReader.getNodes().get(i).getY());
        }
    }
}
