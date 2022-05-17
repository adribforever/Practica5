package practica1;

import java.util.List;

public class TableWithLabels extends Table {

    public TableWithLabels(List<String> header) {
        super(header);
    }

    @Override
    public RowWithLabel getRowAt(int numRow) {
        return (RowWithLabel) super.getRows().get(numRow);
    }
}
