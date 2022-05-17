package practica1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KNNTest {
    private String tableWithLabels = "iris.csv";
    private KNN knn;
    private TableWithLabels tableTest;
    private CSV csv;
    private DistanceFactory factory;


    @BeforeEach
    void init() {
        csv = new CSV();
        factory = new DistanceFactory();
        knn = new KNN(factory.getDistance(DistanceType.EUCLIDEAN));
        tableTest = null;
    }

    @Test
    void estimate() {
        try {
            tableTest = csv.readTableWithLabels(tableWithLabels);
        } catch (FileNotFoundException err) {
            System.out.println(err.getMessage());
        }

        knn.train(tableTest);

        List<Double> sampleTest = List.of(4.9,3.0,1.4,0.2);

        // Comprobamos que para sampleTest recibimos tipo Iris-setosa
        assertEquals("Iris-setosa", knn.estimate(sampleTest));

        sampleTest = List.of(5.6,2.7,4.2,1.3);

        // Comprobamos que para sapleTest recibimos tipo Iris-versicolor
        assertEquals("Iris-versicolor", knn.estimate(sampleTest));
    }
}