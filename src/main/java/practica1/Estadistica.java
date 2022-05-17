package practica1;

import java.util.Collection;
import java.util.List;

public class Estadistica {
    public static double mean(Collection<Double> listOfValues) {
        double sum = 0;
        for (Double value : listOfValues)
            sum += value;
        return sum / listOfValues.size();
    }

}
