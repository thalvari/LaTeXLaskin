/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.calculator.latexconverter;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class LaTeXConverterTest {

    private static final char IMAGINARY = 63310;
    private static final char NEPER = 63309;

    @Test
    public void testLaTeXConverter() {
        LaTeXConverter conv = new LaTeXConverter();
        assertNotNull(conv);
    }

    @Test
    public void testReplaceSlashes() {
        List<String> results = new ArrayList();
        results.add("1/8 (2 n + 1)^2 - 1/8");
        LaTeXConverter.replaceSlashes(results);
        assertEquals(results.get(0), "\\frac{1}{8} (2 n + 1)^2 - \\frac{1}{8}");
    }

    @Test
    public void testReplaceSlashes2() {
        List<String> results = new ArrayList();
        results.add("(x^2/4 + 1)/(x^2/2 + 1)");
        LaTeXConverter.replaceSlashes(results);
        assertEquals(results.get(0), "\\frac{(\\frac{x^2}{4} + 1)}{(\\frac{x^2}"
                + "{2} + 1)}");
    }

    @Test
    public void testReplaceSlashes3() {
        List<String> results = new ArrayList();
        results.add("x^2/(4 (x^2/2 + 1)) + 1/(x^2/2 + 1)");
        LaTeXConverter.replaceSlashes(results);
        assertEquals(results.get(0), "\\frac{x^2}{(4 (\\frac{x^2}{2} + 1))} + "
                + "\\frac{1}{(\\frac{x^2}{2} + 1)}");
    }

    @Test
    public void testReplaceSlashes4() {
        List<String> results = new ArrayList();
        results.add("(4 (x^2/4 + 2))/(x^2/2 + 1)");
        LaTeXConverter.replaceSlashes(results);
        assertEquals(results.get(0), "\\frac{(4 (\\frac{x^2}{4} + 2))}{(\\frac{"
                + "x^2}{2} + 1)}");
    }

    @Test
    public void testReplace() {
        List<String> results = new ArrayList();
        results.add(NEPER + "^2");
        LaTeXConverter.replace(results);
        assertEquals(results.get(0), "\\mathrm{e}^{2}");
    }

    @Test
    public void testReplace2() {
        List<String> results = new ArrayList();
        results.add("sin(a) cos(b) + cos(a) sin(b)");
        LaTeXConverter.replace(results);
        assertEquals(results.get(0), "\\sin(a) \\cos(b) + \\cos(a) \\sin(b)");
    }

    @Test
    public void testReplace3() {
        List<String> results = new ArrayList();
        results.add("2 abs(x - 1)");
        LaTeXConverter.replace(results);
        assertEquals(results.get(0), "2 \\vert (x - 1) \\vert");
    }

    @Test
    public void testReplace4() {
        List<String> results = new ArrayList();
        results.add("sqrt((2 x - 2)^2)");
        LaTeXConverter.replace(results);
        assertEquals(results.get(0), "\\sqrt{((2 x - 2)^{2})}");
    }

    @Test
    public void testReplace5() {
        List<String> results = new ArrayList();
        results.add(IMAGINARY + " " + NEPER + "^(-" + IMAGINARY + " x) - "
                + IMAGINARY + " " + NEPER + "^(" + IMAGINARY + " x)");
        LaTeXConverter.replace(results);
        assertEquals(results.get(0), "\\mathrm{i} \\mathrm{e}^{(-\\mathrm{i} x)"
                + "} - \\mathrm{i} \\mathrm{e}^{(\\mathrm{i} x)}");
    }
}
