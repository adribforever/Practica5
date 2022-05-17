package practica1;

import java.util.*;

public class Kmeans implements Algorithm<Table, String, Row>, DistanceClient {
    private int numClusters;
    private int iterations;
    private long seed;
    private Set<Integer> alreadyGenerated;
    private List<Row> centroides;
    private List<List<Row>> clusters;
    private Distance distance;

    public Kmeans(int numberClusters, int iterations, long seed, Distance distance) {
        this.numClusters = numberClusters;
        this.iterations = iterations;
        this.seed = seed;
        this.alreadyGenerated = new HashSet<>();
        this.clusters = new LinkedList<>();
        this.centroides = new LinkedList<>();
        this.distance = distance;
    }

    public void train(Table sample) {
        // Inicializamos los centroides
        initializeCentroides(sample);

        // Inicializamos los clusters
        initializeClusters();

        for (int i = 0; i < iterations; i++) {
            clasificarElementos(sample.getRows());
            recalcularCentroides();

            // Ahora limpiamos e inicializamos los clusters para recalcularlos en base a los nuevos centroides
            this.clusters.clear();
            initializeClusters();
        }
    }

    public String estimate(Row sample) {
        Row initialCentroide = this.centroides.get(0);
        double minCentroide = distance.calculateDistance(sample.getData(), initialCentroide.getData());
        int min = 0;
        for (int i = 1; i < numClusters; i++) {
            Row centroideToCompare = this.centroides.get(i);
            double candidate = distance.calculateDistance(sample.getData(), centroideToCompare.getData());
            if (candidate < minCentroide) {
                minCentroide = candidate;
                min = i;
            }
        }
        return "Centroide " + min;
    }

    private void clasificarElementos(List<Row> rows) {
        for (Row row : rows) {
            double compareTo = distance.calculateDistance(row.getData(), centroides.get(0).getData());
            int goToCluster = 0;
            for (int i = 1; i < this.numClusters; i++) {
                double candidate = distance.calculateDistance(row.getData(), centroides.get(i).getData());
                if (candidate < compareTo) {
                    compareTo = candidate;
                    goToCluster = i;
                }
            }
            this.clusters.get(goToCluster).add(row);
        }

    }

    private void recalcularCentroides() {
        List<Double> newCentroide = new LinkedList<>();
        for (int i = 0; i < this.numClusters; i++) {
            // Obtenemos el cluster y el número de campos de cada Row para recalcular el centroide
            List<Row> cluster = this.clusters.get(i);
            int numFieldsInRow = cluster.get(0).getData().size();

            for (int j = 0; j < numFieldsInRow; j++)
                newCentroide.add(calculateColumnMean(cluster, j));

            // Reemplazamos el centroide anterior por el que acabamos de calcular
            this.centroides.set(i, new Row(newCentroide));

            // Creamos una nueva lista para el row del siguiente centroide a generar.
            newCentroide = new LinkedList<>();
        }
    }

    private Double calculateColumnMean(List<Row> cluster, int numCol) {
        Double sum = 0.0;
        for (Row row : cluster)
            sum+= row.getData().get(numCol);

        return sum/cluster.size();
    }

    private void initializeCentroides(Table sample) {
        Random r = new Random(this.seed);
        int randomIndex;
        for (int i = 0; i < this.numClusters; i++) {
            do {
                randomIndex = r.nextInt(sample.getRows().size());
            } while(alreadyGenerated.contains(randomIndex));
            alreadyGenerated.add(randomIndex);
            this.centroides.add(sample.getRowAt(randomIndex));
        }
    }

    private void initializeClusters() {
        for (int i = 0; i < this.numClusters; i++)
            this.clusters.add(new LinkedList<>());
    }


    public void setDistance(Distance distance) {
        this.distance = distance;
    }
}
