package practica1;

import java.util.LinkedList;
import java.util.List;

public class Table {
    private List<String> header;

    private List<Row> rows;
    public Table(List<String> header) {
        this.header = header;
        this.rows = new LinkedList<>();
    }

    public List<Row> getRows() {
        return rows;
    }

    public List<String> getHeader() {
        return header;
    }

    public Row getRowAt(int numRow) {
        return rows.get(numRow);
    }

    public List<Double> getColumAt(int numColum) {
        List<Double> res = new LinkedList<>();
        for (Row row:rows) {
            res.add(row.getData().get(numColum));
        }
        return res;
    }

    public void addRow(Row row) {
        rows.add(row);
    }

    @Override
    public String toString() {
        String res = "";
        for (String val :header) {
            res+= String.format("%-15s",val);
        }
        res+="\n";
        for (Row row : rows) {
            res+=String.format("%s\n",row);
        }
        return res;
    }
}
