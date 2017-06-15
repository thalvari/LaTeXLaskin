/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.calculator;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author thalvari
 */
public class CalculatorTest {

    private Calculator calc;

    @Before
    public void setUp() {
        calc = new Calculator(false);
    }

    @Test
    public void testQuery() {
        String input = "";
        List<String> results = calc.query(input);
        assertNull(results);
        assertNotNull(calc.getError());
    }

    @Test
    public void testQuery2() {
        calc.setDebug(true);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStream));
        String input = "\\int_0^1 2 \\pi x / 2";
        calc.query(input);
        String out = outStream.toString();
        assertTrue(out.contains("---"));
        assertTrue(out.contains("http://www.wolframalpha.com/input/?i=%5Cint_0%"
                + "5E1+2+%5Cpi+x+%2F+2"));
        assertTrue(out.contains("http://api.wolframalpha.com/v2/query?appid="
                + calc.getAppID() + "&input=%5Cint_0%5E1+2+%5Cpi+x+%2F+2&format"
                + "=plaintext&async=false&reinterpret=true"));
        assertTrue(out.contains("π/2"));
        assertTrue(out.contains("\\frac{π}{2}"));
        assertTrue(out.contains("\\frac{\\pi}{2}"));
    }

    @Test
    public void testQuery3() {
        String input = "\\sum_{i=0}^n i";
        List<String> results = calc.query(input);
        assertEquals(results.get(0), "\\frac{1}{2} n (n + 1)");
        assertEquals(results.get(1), "\\frac{1}{8} (2 n + 1)^{2} - \\frac{1}{8}"
                + "");
        assertEquals(results.get(2), "(\\frac{n}{2} + \\frac{1}{2}) n");
        assertEquals(results.get(3), "\\frac{n^{2}}{2} + \\frac{n}{2}");
        assertNull(calc.getError());
    }

    @Test
    public void testQuery4() {
        String input = "e^{ix}";
        List<String> results = calc.query(input);
        assertEquals(results.get(0), "\\cos(x) + \\mathrm{i} \\sin(x)");
    }

    @Test
    public void testQuery5() {
        String input = "((x/2)^2 + 1)/(x^2/2 + 2)";
        List<String> results = calc.query(input);
        assertEquals(results.get(0), "\\frac{x^{2}}{(4 (\\frac{x^{2}}{2} + 2))}"
                + " + \\frac{1}{(\\frac{x^{2}}{2} + 2)}");
    }

    @Test
    public void testQuery6() {
        String input = "(1-e^2x)/(1+e^{2x})";
        List<String> results = calc.query(input);
        assertEquals(results.get(0), "\\frac{1}{2} (\\mathrm{e}^{2} x - 1) ("
                + "\\tanh(x) - 1)");
        assertEquals(results.get(1), "-\\frac{(\\mathrm{e}^{2} x - 1)}{("
                + "\\mathrm{e}^{(2 x)} + 1)}");
        assertEquals(results.get(2), "\\frac{1}{(\\mathrm{e}^{(2 x)} + 1)} - "
                + "\\frac{(\\mathrm{e}^{2} x)}{(\\mathrm{e}^{(2 x)} + 1)}");
    }

    @Test
    public void testSetAppID() {
        calc.setAppID("1");
        assertEquals(calc.getAppID(), "1");
    }
}
