package practica1;

public class DistanceFactory implements Factory {
    @Override
    public Distance getDistance(DistanceType distanceType) {
        Distance res = null;
        switch (distanceType) {
            case EUCLIDEAN:
                res = new EuclideanDistance();
                break;
            case MANHATTAN:
                res = new ManhattanDistance();
                break;
        };

        return res;
    }
}
