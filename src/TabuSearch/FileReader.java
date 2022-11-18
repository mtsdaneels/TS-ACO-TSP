package TabuSearch;

import java.io.*;
import java.util.*;

import static java.lang.Math.*;

/**
 * Class that reads a tsp file and interprets it as a tps problem.
 */
class FileReader implements FileReaderInterface{

    //Possible forms of distance given the coordinates
    boolean ATT = false;

    boolean EUC_2D = false;

    boolean GEO = false;

    boolean CEIL_2D = false;

    //Possible matrix forms
    boolean FULL_MATRIX =  false;

    boolean UPPER_ROW = false;

    boolean LOWER_ROW = false;

    boolean UPPER_DIAG_ROW = false;

    boolean LOWER_DIAG_ROW = false;

    /**
     * How many points are in the problem.
     */
    private int readerDimension;

    @Override
    public int getDimension(){
        return readerDimension;
    }

    /**
     * Matrix containing the dinstances between nodes.
     */
    private double[][] readerDistanceMatrix;

    @Override
    public double[][] getMatrix(){
        return readerDistanceMatrix;
    }

    /**
     * Map containing the nodes with their x- and y-coordinate.
     */
    private final HashMap<Integer, Tuple<Double, Double>> readerNodes = new HashMap<>();

    @Override
    public HashMap<Integer, Tuple<Double, Double>> getNodes() {
        return readerNodes;
    }

    /**
     * Constructor creating a new fileReader given a file.
     * @param fileName The name of the file.
     * @throws Exception If the file cannot be found.
     */
    public FileReader(String fileName) throws Exception {
        read(fileName);
    }

    /**
     * Read and fill int the information in the distance matrix from a file that uses the NODE_COORD_SECTION representation.
     * @param fileName The name of the file.
     */
    private void readFileCoord_Section(String fileName) throws Exception{
        boolean startOfInput = false;
        Scanner scanner = new Scanner(new File(fileName));
        while (scanner.hasNextLine()){
            ArrayList<String> array = lineSplit(scanner.nextLine());
            if (Objects.equals(array.get(0), "EDGE_WEIGHT_TYPE:") || Objects.equals(array.get(0), "EDGE_WEIGHT_TYPE")) {
                decideInputTypeCoord_Section(array);
            }
            if (Objects.equals(array.get(0), "EOF")) {
                break;
            }
            if (startOfInput) {
                addNode(Integer.parseInt(array.get(0)), Double.parseDouble(array.get(1)),Double.parseDouble(array.get(2)));
            }
            if (Objects.equals(array.get(0), "NODE_COORD_SECTION")) {
                startOfInput = true;
            }
        }
        calculateDistanceMatrix();
    }

    /**
     * Decide which edge weight input type we have.
     * @param array An array of strings made of the line in the input file containing the edge weight type.
     */
    private void decideInputTypeCoord_Section(ArrayList<String> array){
        if (array == null){
            throw new IllegalArgumentException();
        }
        int index = 1;
        if (Objects.equals(array.get(0), "EDGE_WEIGHT_TYPE")){
            index = 2;
        }
        if (Objects.equals(array.get(index), "EUC_2D")){
            EUC_2D = true;
        }
        else if (Objects.equals(array.get(index), "GEO")){
            GEO = true;
        }
        else if (Objects.equals(array.get(index), "CEIL_2D")){
            CEIL_2D = true;
        }
        else if (Objects.equals(array.get(index), "ATT")){
            ATT = true;
        }
    }

    /**
     * Calculate the distance matrix when the coordinates are already known.
     * @throws Exception If the coordinates are not known.
     */
    private void calculateDistanceMatrix() throws Exception {
        if (readerNodes.isEmpty()){
            throw new Exception();
        }
        else if (EUC_2D){
            EuclideanDistance();
        }
        else if (GEO){
            GeoDistance();
        }
        else if (CEIL_2D){
            CeilDistance();
        }
        else if (ATT) {
            AttDistance();
        }
    }

    /**
     * Make the distance matrix using the coordinates of the nodes in a pseudo-euclidean interpretation.
     */
    private void AttDistance(){
        for (int i = 0; i < readerDimension; i++) {
            for (int j = i + 1; j < readerDimension; j++) {
                double xd = readerNodes.get(i+1).x - readerNodes.get(j+1).x;
                double yd = readerNodes.get(i+1).y - readerNodes.get(j+1).y;
                readerDistanceMatrix[i][j] = (int) (sqrt((xd * xd + yd * yd) / 10)) + 1;
                readerDistanceMatrix[j][i] = (int) (sqrt((xd * xd + yd * yd) / 10)) + 1;
            }
        }
    }

    /**
     * Make the distance matrix using the coordinates of the nodes in a 2-euclidean interpretation.
     */
    private void EuclideanDistance(){
        for (int i = 0; i < readerDimension; i++) {
            for (int j = i + 1; j < readerDimension; j++) {
                double xd = readerNodes.get(i+1).x - readerNodes.get(j+1).x;
                double yd = readerNodes.get(i+1).y - readerNodes.get(j+1).y;
                readerDistanceMatrix[i][j] = (double) (sqrt(xd * xd + yd * yd));
                readerDistanceMatrix[j][i] = (double) (sqrt(xd * xd + yd * yd));
            }
        }
    }

    /**
     * Make the distance matrix using the coordinates of the nodes in a 2-euclidean interpretation rounding up to the next integer.
     */
    private void CeilDistance(){
        for (int i = 0; i < readerDimension; i++) {
            for (int j = i + 1; j < readerDimension; j++) {
                double xd = readerNodes.get(i+1).x - readerNodes.get(j+1).x;
                double yd = readerNodes.get(i+1).y - readerNodes.get(j+1).y;
                readerDistanceMatrix[i][j] = (int) (sqrt(xd * xd + yd * yd)) + 1;
                readerDistanceMatrix[j][i] = (int) (sqrt(xd * xd + yd * yd)) + 1;
            }
        }
    }

    /**
     * Make the distance matrix using the coordinates of the nodes in a geographic interpretation.
     */
    private void GeoDistance(){
        double[] latitude = new double[readerDimension];
        double[] longitude = new double[readerDimension];
        int deg;
        double min;
        double PI = 3.141592;
        double RadiusEarth = 6378.388;
        for (int u = 0; u < readerDimension; u++){
            deg = (int) Double.parseDouble(String.valueOf(readerNodes.get(u+1).x));
            min = readerNodes.get(u+1).x - deg;
            latitude[u] = PI * (deg + 5.0 * min / 3.0 ) / 180.0;
            deg = (int) Double.parseDouble(String.valueOf(readerNodes.get(u+1).y));
            min = readerNodes.get(u+1).y - deg;
            longitude[u] = PI * (deg + 5.0 * min / 3.0 ) / 180.0;
        }
        for (int v = 0; v < readerDimension; v++){
            for (int w = v+1; w < readerDimension; w++){
                double q1 = cos( longitude[v] - longitude[w] );
                double q2 = cos( latitude[v] - latitude[w] );
                double q3 = cos( latitude[v] + latitude[w] );
                int dij = (int) ( RadiusEarth * acos( 0.5*((1.0+q1)*q2 - (1.0-q1)*q3) ) + 1.0);
                readerDistanceMatrix[v][w] = dij;
                readerDistanceMatrix[w][v] = dij;
            }
        }
    }

    /**
     * Read and interpret the information from a file that uses the EDGE_WEIGHT_SECTION representation.
     * @param fileName The name of the file.
     * @throws Exception If the file cannot be found.
     */
    private void readFileEdge_Weight_Section(String fileName) throws Exception {
        boolean startOfInput = false;
        boolean startOfMatrix = false;
        ArrayList<Double> numbers = new ArrayList<Double>();
        Scanner scanner = new Scanner(new File(fileName));
        while (scanner.hasNextLine()) {
            ArrayList<String> array = lineSplit(scanner.nextLine());
            if (Objects.equals(array.get(0), "EDGE_WEIGHT_FORMAT:") || Objects.equals(array.get(0), "EDGE_WEIGHT_FORMAT")) {
                decideInputTypeEdgeWeight_Section(array);
            }
            if (Objects.equals(array.get(0), "EOF")) {
                break;
            }
            if (startOfInput) {
                addNode(Integer.parseInt(array.get(0)), Double.parseDouble(array.get(1)),Double.parseDouble(array.get(2)));
            }
            if (Objects.equals(array.get(0), "DISPLAY_DATA_SECTION")) {
                startOfInput = true;
                startOfMatrix = false;
            }
            if (startOfMatrix){
                for (String number: array){
                    numbers.add(Double.parseDouble(number));
                }
            }
            if (Objects.equals(array.get(0), "EDGE_WEIGHT_SECTION")) {
                startOfMatrix = true;
            }
        }
        fillMatrix(numbers);
    }

    /**
     * Decide which edge weight format we have.
     * @param array An array of strings made of the line in the input file containing the edge weight format.
     */
    private void decideInputTypeEdgeWeight_Section(ArrayList<String> array){
        int index = 1;
        if (Objects.equals(array.get(0), "EDGE_WEIGHT_FORMAT")){
            index = 2;
        }
        if (Objects.equals(array.get(index), "FULL_MATRIX")){
            FULL_MATRIX = true;
        }
        else if (Objects.equals(array.get(index), "UPPER_ROW")){
            UPPER_ROW = true;
        }
        else if (Objects.equals(array.get(index), "LOWER_ROW")){
            LOWER_ROW = true;
        }
        else if (Objects.equals(array.get(index), "UPPER_DIAG_ROW")){
            UPPER_DIAG_ROW = true;
        }
        else if (Objects.equals(array.get(index), "LOWER_DIAG_ROW")){
            LOWER_DIAG_ROW = true;
        }
    }

    /**
     * Fill the matrix given all the numbers.
     * @param numbers The numbers that need to be filled in, int the matrix.
     * @throws Exception If there are no numbers in the input.
     */
    private void fillMatrix(ArrayList<Double> numbers) throws Exception {
        if (numbers.isEmpty()){
            throw new Exception();
        }
        else if (FULL_MATRIX){
            fillMatrixFull_Matrix(numbers);
        }
        else if (UPPER_ROW){
            fillMatrixUpper_Row(numbers);
        }
        else if (LOWER_ROW){
            fillMatrixLower_Row(numbers);
        }
        else if (UPPER_DIAG_ROW) {
            fillMatrixUpper_Diag_Row(numbers);
        }
        else if (LOWER_DIAG_ROW){
            fillMatrixLower_Diag_Row(numbers);
        }
    }

    /**
     * Fill the matrix when the input is a full matrix.
     * @param numbers The numbers that need to be filled in, in the matrix.
     */
    private void fillMatrixFull_Matrix(ArrayList<Double> numbers){
        for (int i = 0; i < readerDimension; i++){
            for (int j = 0; j < readerDimension; j++){
                readerDistanceMatrix[i][j] = numbers.get(i * readerDimension + j);
            }
        }
    }

    /**
     * Fill the matrix when the input is a matrix upper row or lower col.
     * @param numbers The numbers that need to be filled in, in the matrix.
     */
    private void fillMatrixUpper_Row(ArrayList<Double> numbers){
        int counter = 0;
        for (int i = 0; i < readerDimension-1; i++){
            for (int j = i + 1; j < readerDimension; j++){
                readerDistanceMatrix[i][j] = numbers.get(counter);
                readerDistanceMatrix[j][i] = numbers.get(counter);
                counter++;
            }
        }
    }

    /**
     * Fill in the matrix when the input is a matrix lower row or upper col.
     * @param numbers The numbers that need to be filled in, in the matrix.
     */
    private void fillMatrixLower_Row(ArrayList<Double> numbers){
        int counter = 0;
        for (int i = 1; i < readerDimension; i++){
            for (int j = 0; j < i; j++){
                readerDistanceMatrix[i][j] = numbers.get(counter);
                readerDistanceMatrix[j][i] = numbers.get(counter);
                counter++;
            }
        }
    }

    /**
     * Fill in the matrix when the input is a matrix upper diag row or lower diag col.
     * @param numbers The numbers that need to be filled in, in the matrix.
     */
    private void fillMatrixUpper_Diag_Row(ArrayList<Double> numbers){
        int counter = 0;
        for (int i = 0; i < readerDimension; i++){
            for (int j = i; j < readerDimension; j++){
                if (i != j){
                    readerDistanceMatrix[i][j] = numbers.get(counter);
                    readerDistanceMatrix[j][i] = numbers.get(counter);
                }
                counter++;
            }
        }
    }

    /**
     * Fill in the matrix when the input is matrix lower diag row or upper diag col.
     * @param numbers The numbers that need to be filled in, in the matrix.
     */
    private void fillMatrixLower_Diag_Row(ArrayList<Double> numbers){
        int counter = 0;
        for (int i = 0; i < readerDimension; i++){
            for (int j = 0; j < i+1; j++) {
                if (i != j) {
                    readerDistanceMatrix[i][j] = numbers.get(counter);
                    readerDistanceMatrix[j][i] = numbers.get(counter);
                }
                counter++;
            }
        }
    }


    /**
     * Split a given line.
     * @param s The line that needs to be split.
     * @return An Array of strings with the words of the line.
     */
    private ArrayList<String> lineSplit(String s){
        String[] arrayOfNextLine = s.split("\t");
        ArrayList<String> array = new ArrayList<String>();
        for (String i : arrayOfNextLine) {
            String[] secondArray = i.split(" ");
            for (String j: secondArray){
                if (!(Objects.equals(j, ""))) {
                    array.add(j);
                }
            }
        }
        return array;
    }

    /**
     * Add a node.
     * @param number The number of the node.
     * @param x The x-coordinate of the node.
     * @param y The y-coordinate of the node.
     */
    private void addNode(int number, double x, double y){
        readerNodes.put(number, new Tuple<Double, Double>(x, y));
    }


    /**
     * Read and interpret the information from a file.
     * @param fileName The name of the file.
     * @throws Exception Gets thrown when the given file cannot be found.
     */
    private void read(String fileName) throws Exception {
        Scanner scanner = new Scanner(new File(fileName));
        while (scanner.hasNextLine()){
            String nextLine = scanner.nextLine();
            String[] arrayOfLine = nextLine.split(" ");
            if (Objects.equals(arrayOfLine[0], "DIMENSION")){
                readerDimension = Integer.parseInt(arrayOfLine[2]);
                readerDistanceMatrix = new double[readerDimension][readerDimension];
            }
            else if (Objects.equals(arrayOfLine[0], "DIMENSION:")){
                readerDimension = Integer.parseInt(arrayOfLine[1]);
                readerDistanceMatrix = new double[readerDimension][readerDimension];
            }
            else if (Objects.equals(arrayOfLine[0], "NODE_COORD_SECTION")){
                readFileCoord_Section(fileName);
                return;
            }
            else if (Objects.equals(arrayOfLine[0], "EDGE_WEIGHT_SECTION")){
                readFileEdge_Weight_Section(fileName);
                return;
            }
        }
    }
}
