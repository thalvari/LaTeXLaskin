/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.calculator;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author thalvari
 */
public class CalculatorTest {

    private static final String APPID = "WJ628E-G3H5VTERP4";

    private Calculator calc;

    @Before
    public void setUp() {
        calc = new Calculator(APPID);
        calc.setDebug(false);
    }

    @Test
    public void testCalculator() {
        assertNotNull(calc);
    }

    @Test
    public void testQuery() {
        String input = "";
        List<String> results = calc.query(input);
        assertNull(results);
    }

    @Test
    public void testQuery2() {
        calc.setDebug(true);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        String input = "\\int_0^1 2 \\pi x";
        calc.query(input);
        String result = out.toString();
        assertTrue(result.contains("Prosessoidut ratkaisut:"));
        assertTrue(result.contains("π"));
    }

    @Test
    public void testQuery3() {
        String input = "\\sum_{i=0}^n i";
        List<String> results = calc.query(input);
        assertEquals(results.get(0), "\\frac{1}{2} n (n + 1)");
        assertEquals(results.get(1), "\\frac{1}{8} (2 n + 1)^2 - \\frac{1}{8}");
        assertEquals(results.get(2), "(\\frac{n}{2} + \\frac{1}{2}) n");
        assertEquals(results.get(3), "\\frac{n^2}{2} + \\frac{n}{2}");
        assertNull(calc.getError());
    }

    @Test
    public void testQuery4() {
        String input = "e^{ix}";
        List<String> results = calc.query(input);
        assertEquals(results.get(0), "cos(x) + \\mathrm{i} sin(x)");
        assertNull(calc.getError());
    }

    @Test
    public void testQuery5() {
        String input = "((x/2)^2 + 1)/(x^2/2 + 2)";
        List<String> results = calc.query(input);
        assertEquals(results.get(0), "\\frac{x^2}{(4 (\\frac{x^2}{2} + 2))} + "
                + "\\frac{1}{(\\frac{x^2}{2} + 2)}");
        assertNull(calc.getError());
    }

    @Test
    public void testSetDebug() {
        assertFalse(calc.getWACalc().isDebug());
    }
}
