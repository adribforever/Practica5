package practica1;

import java.util.List;

public class EuclideanDistance implements Distance {
    @Override
    public double calculateDistance(List<Double> p, List<Double> q) {
        double sumatorio = 0;
        for (int i = 0; i < q.size(); i++) {
            sumatorio += Math.pow((q.get(i) - p.get(i)), 2);
        }
        return Math.sqrt(sumatorio);
    }
}
