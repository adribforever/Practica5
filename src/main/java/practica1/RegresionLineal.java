package practica1;

import java.util.List;

public class RegresionLineal implements Algorithm<Table, Double, Double> {
    private double alpha;
    private double beta;
    private double meanX;
    private double meanY;

    // Los datos se proporcionan en una Table.
    public void train(Table data) {
        if (data == null)
            throw new NullPointerException();

        List xValues = data.getColumAt(0);
        List yValues = data.getColumAt(1);

        if (xValues.size() == 0 || yValues.size() == 0)
            throw new IllegalArgumentException();

        meanX = Estadistica.mean(xValues);
        meanY = Estadistica.mean(yValues);
        alpha = calculateAlpha(xValues, yValues);
        beta = calculateBeta();
    }

    // Devuelve una estimación para el «sample»
    public Double estimate(Double sample) {
        return (alpha * sample) + beta;
    }

    private Double calculateAlpha(List<Double> xValueCol, List<Double> yValueCol) {
        Double numerator = 0.0;
        Double denominator = 0.0;
        for (int i = 0; i < xValueCol.size(); i++) {
            numerator += (xValueCol.get(i) - meanX) * (yValueCol.get(i) - meanY);
            denominator += Math.pow((xValueCol.get(i) - meanX), 2);
        }
        return numerator / denominator;
    }

    private Double calculateBeta() {
        return meanY - (alpha * meanX);
    }

    public double getAlpha() {
        return alpha;
    }

    public double getBeta() {
        return beta;
    }
}
