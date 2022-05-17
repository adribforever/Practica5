package practica1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KmeansTest {
    private String testFile = "test.csv";
    private String testFile2 = "test2.csv";
    private long seed = 253627;
    private int numClusters = 3;
    private int numIterations = 1000;
    private CSV csv;
    private Kmeans kmeans;
    private Table tableTest;
    private Row rowTest;
    private DistanceFactory factory;

    @BeforeEach
    void init() {
        csv = new CSV();
        factory = new DistanceFactory();
        kmeans = new Kmeans(numClusters,numIterations, seed, factory.getDistance(DistanceType.EUCLIDEAN));
    }

    @Test
    void estimateEuclidean() {
        try {
            tableTest = csv.readTable(testFile);
            kmeans.train(tableTest);

            // Comprobamos que clasifica correctamente el row (2,4) en el primer centroide
            rowTest = new Row(List.of(2.0,4.0));
            assertEquals("Centroide 0", kmeans.estimate(rowTest));

            // Comprobamos que clasifica correctamente el row (30,25) en el segundo centroide
            rowTest = new Row(List.of(30.0,25.0));
            assertEquals("Centroide 1", kmeans.estimate(rowTest));

            // Comprobamos que clasifica correctamente el row (150,190) en el tercer centroide
            rowTest = new Row(List.of(150.0,190.0));
            assertEquals("Centroide 2", kmeans.estimate(rowTest));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void estimateManhattan() {
        // Cambiamos el algoritmo de calculo de distancia
        kmeans.setDistance(factory.getDistance(DistanceType.MANHATTAN));

        try {
            tableTest = csv.readTable(testFile);
            kmeans.train(tableTest);

            // Comprobamos que clasifica correctamente el row (2,4) en el primer centroide
            rowTest = new Row(List.of(2.0,4.0));
            assertEquals("Centroide 0", kmeans.estimate(rowTest));

            // Comprobamos que clasifica correctamente el row (30,25) en el segundo centroide
            rowTest = new Row(List.of(30.0,25.0));
            assertEquals("Centroide 1", kmeans.estimate(rowTest));

            // Comprobamos que clasifica correctamente el row (150,190) en el tercer centroide
            rowTest = new Row(List.of(150.0,190.0));
            assertEquals("Centroide 2", kmeans.estimate(rowTest));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void estimateEuclideanWithTestFile2() {
        try {
            tableTest = csv.readTable(testFile2);
            kmeans.train(tableTest);

            // Comprobamos que clasifica correctamente el row (2,4) en el primer centroide
            rowTest = new Row(List.of(2.0,4.0));
            assertEquals("Centroide 1", kmeans.estimate(rowTest));

            // Comprobamos que clasifica correctamente el row (30,25) en el segundo centroide
            rowTest = new Row(List.of(30.0,25.0));
            assertEquals("Centroide 0", kmeans.estimate(rowTest));

            // Comprobamos que clasifica correctamente el row (150,190) en el tercer centroide
            rowTest = new Row(List.of(150.0,190.0));
            assertEquals("Centroide 0", kmeans.estimate(rowTest));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void estimateManhattanWithTestFile2() {
        // Cambiamos el algoritmo de calculo de distancia
        kmeans.setDistance(factory.getDistance(DistanceType.MANHATTAN));

        try {
            tableTest = csv.readTable(testFile2);
            kmeans.train(tableTest);

            // Comprobamos que clasifica correctamente el row (2,4) en el primer centroide
            rowTest = new Row(List.of(2.0,4.0));
            assertEquals("Centroide 1", kmeans.estimate(rowTest));

            // Comprobamos que clasifica correctamente el row (30,25) en el segundo centroide
            rowTest = new Row(List.of(30.0,25.0));
            assertEquals("Centroide 0", kmeans.estimate(rowTest));

            // Comprobamos que clasifica correctamente el row (150,190) en el tercer centroide
            rowTest = new Row(List.of(150.0,190.0));
            assertEquals("Centroide 0", kmeans.estimate(rowTest));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}