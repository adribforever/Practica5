package practica1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVTest {
    private String tableWithoutLabel = "miles_dollars.csv";
    private String tableWithLabel = "iris.csv";
    private CSV csv;
    private Table tableTest;

    @BeforeEach
    void init() {
        csv = new CSV();
        tableTest = null;
    }

    @Test
    void readTable() {
        try {
            tableTest = csv.readTable(tableWithoutLabel);
        } catch (FileNotFoundException err) {
            System.out.println(err.getMessage());
        }
        // La tabla no es nula
        assertNotEquals(null, tableTest);

        // El número de filas es correcto
        assertEquals(25, tableTest.getRows().size());

        // El número de columnas es correcto
        assertEquals(2, tableTest.getRowAt(0).getData().size());

        // Las cabezeras son correctas
        List<String> headerTest = new LinkedList<>();
        headerTest.add("Miles");
        headerTest.add("Dollars");
        assertEquals(headerTest, tableTest.getHeader());

    }

    @Test
    void readTableExceptionTest() {
       // Lanza excepción cuando se le pasa null.
        assertThrows(NullPointerException.class, () -> {
           tableTest = csv.readTable(null);
        });

        // Lanza excepción cuando no encuentra el archivo.
        assertThrows(FileNotFoundException.class, () -> {
            tableTest = csv.readTable("archivoInexistente");
        });
    }

    @Test
    void readTableWithLabels() {
        try {
            tableTest = csv.readTableWithLabels(tableWithLabel);
        } catch (FileNotFoundException err) {
            System.out.println(err.getMessage());
        }
        // La tabla no es nula
        assertNotEquals(null, tableTest);

        // El número de filas es correcto
        assertEquals(150, tableTest.getRows().size());

        // El número de columnas es correcto
        assertEquals(4, tableTest.getRowAt(0).getData().size());

        // Las cabezeras son correctas
        List<String> headerTest = new LinkedList<>();
        headerTest.add("sepal length");
        headerTest.add("sepal width");
        headerTest.add("petal length");
        headerTest.add("petal width");
        headerTest.add("class");
        assertEquals(headerTest, tableTest.getHeader());
    }

    @Test
    void readTableWithLabelsExceptionsTest() {
        // Lanza excepción cuando se le pasa null.
        assertThrows(NullPointerException.class, () -> {
            tableTest = csv.readTableWithLabels(null);
        });

        // Lanza excepción cuando no encuentra el archivo.
        assertThrows(FileNotFoundException.class, () -> {
            tableTest = csv.readTableWithLabels("archivoInexistente");
        });
    }
}