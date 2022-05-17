package practica1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CSV {
    public Table readTable(String fileName) throws FileNotFoundException {
        if (fileName == null)
            throw new NullPointerException();

        // Creo el escaner para leer el archivo
        Scanner sc = new Scanner(new File(fileName));

        // Obtengo la primera linea que corresponde a la cabezera de la tabla
        String tableHeader = sc.nextLine();
        Table table = new Table(List.of(tableHeader.split(",")));

        // Recorro el fichero
        String[] row;
        while (sc.hasNext()) {
            //Obtengo la fila en formato String
            row = sc.nextLine().split(",");
            List<Double> data = new LinkedList<>();
            // Convierto los strings a Doubles
            for (String val : row)
                data.add(Double.parseDouble(val));
            table.addRow(new Row(data));
        }
        // Cierro el escaner
        sc.close();

        return table;
    }

    public TableWithLabels readTableWithLabels(String fileName) throws FileNotFoundException {
        if (fileName == null)
            throw new NullPointerException();
        // Creo el escaner para leer el archivo
        Scanner sc = new Scanner(new File(fileName));

        // Obtengo la primera linea que corresponde a la cabezera de la tabla
        String tableHeader = sc.nextLine();
        TableWithLabels table = new TableWithLabels(List.of(tableHeader.split(",")));

        // Recorro el fichero
        String[] row;
        while (sc.hasNext()) {
            //Obtengo la fila en formato String
            row = sc.nextLine().split(",");
            List<Double> data = new LinkedList<>();
            // Convierto los strings a Doubles
            for (int i = 0; i < row.length - 1; i++)
                data.add(Double.parseDouble(row[i]));
            table.addRow(new RowWithLabel(data, row[row.length - 1]));
        }
        // Cierro el escaner
        sc.close();

        return table;

    }
}
