package practica1;

import java.util.List;

public class KNN implements Algorithm<TableWithLabels, String, List<Double>>, DistanceClient {
    private TableWithLabels data;
    private Distance distance;

    public KNN(Distance distance) {
        this.distance = distance;
    }


    public void train(TableWithLabels data) {
        this.data = data;
    }

    public String estimate(List<Double> sample) {
        RowWithLabel result = data.getRowAt(0);
        double min = this.distance.calculateDistance(sample, data.getRowAt(0).getData());
        for (int i = 1; i < data.getRows().size(); i++) {
            double distanceEstimate = this.distance.calculateDistance(sample, data.getRowAt(i).getData());
            if (distanceEstimate < min) {
                result = data.getRowAt(i);
                min = distanceEstimate;
            }
        }
        return result.getLabel();
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }
}
