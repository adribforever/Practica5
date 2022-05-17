package practica1;

import java.util.List;

public class RowWithLabel extends Row {
    String label;

    public RowWithLabel(List<Double> row, String label) {
        super(row);
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public String toString() {
        String res = "";
        for (Double val : super.getData()) {
            res += String.format("%-15.2f",val);
        }
        res += String.format("%-15s", label);
        return res;
    }
}
