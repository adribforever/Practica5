package practica1;

import java.util.List;

public class Row {

    private List<Double> data;

    public Row(List<Double> row) {
        this.data = row;
    }

    public List<Double> getData() {
        return data;
    }

    public String toString() {
        String res = "";
        for (Double val : data) {
            res += String.format("%-15.2f",val);
        }
        return res;
    }
}
