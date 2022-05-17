package practica1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegresionLinealTest {
    private String tableWithoutLabel = "miles_dollars.csv";
     RegresionLineal reg;
    private Table tableTest;
    private CSV csv;

    @BeforeEach
    void init() {
        csv = new CSV();
        reg = new RegresionLineal();
        tableTest = null;
    }

    @Test
    void train() {
        //α=1.255 y β=274.85.
        try {
            tableTest = csv.readTable(tableWithoutLabel);
        } catch (FileNotFoundException err) {
            System.out.println(err.getMessage());
        }
        // Generamos alpha y beta
        reg.train(tableTest);

        // Comprobamos que alpha es correcto
        assertEquals(1.255, reg.getAlpha(), 0.001);

        // Comprobamos que beta es correcto
        assertEquals(274.85, reg.getBeta(), 0.001);

    }

    @Test
    void trainExceptionTest() {
        // tableTest = null
        // Comprobamos que no podemos entrenar con una tabla a null.

        assertThrows(NullPointerException.class, () -> {
            reg.train(tableTest);
        });

        // Comprobamos que no podemos entrenar con una tabla vacía.
        tableTest = new Table(List.of("Miles","Dollars"));
        assertThrows(IllegalArgumentException.class, () -> {
            reg.train(tableTest);
        });

    }

    @Test
    void estimate() {
        try {
            tableTest = csv.readTable(tableWithoutLabel);
        } catch (FileNotFoundException err) {
            System.out.println(err.getMessage());
        }
        // Generamos alpha y beta
        reg.train(tableTest);

        // Comprobamos que para un valor de x la aproximación es correcta para x = 1849.0
        assertEquals(2595.96, reg.estimate(1849.0), 0.01);
    }
}