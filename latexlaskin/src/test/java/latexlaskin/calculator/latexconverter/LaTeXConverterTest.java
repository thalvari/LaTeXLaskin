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

/**
 *
 * @author thalvari
 */
public class LaTeXConverterTest {

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
    public void testReplaceSlashes5() {
        List<String> results = new ArrayList();
        results.add("^(- x)/2 + ^( x)/2");
        LaTeXConverter.replaceSlashes(results);
        assertEquals(results.get(0), "\\frac{^(- x)}{2} + \\frac{^( x)}{2}"
                + "");
    }

    @Test
    public void testReplaceSlashes6() {
        List<String> results = new ArrayList();
        results.add("^(-(x - μ)^2/(2 σ^2))/(sqrt(2 π) σ)");
        LaTeXConverter.replaceSlashes(results);
        assertEquals(results.get(0), "\\frac{^(-\\frac{(x - μ)^2}{(2 σ^2)})}{("
                + "sqrt(2 π) σ)}");
    }

    @Test
    public void testReplaceSlashes7() {
        List<String> results = new ArrayList();
        results.add("^(-x^2/2)/sqrt(2 π)");
        LaTeXConverter.replaceSlashes(results);
        assertEquals(results.get(0), "\\frac{^(-\\frac{x^2}{2})}{sqrt(2 π)}");
    }

    @Test
    public void testReplace() {
        List<String> results = new ArrayList();
        results.add("^2");
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
        results.add(" ^(- x) -  ^( x)");
        LaTeXConverter.replace(results);
        assertEquals(results.get(0), "\\mathrm{i} \\mathrm{e}^{(-\\mathrm{i} x)"
                + "} - \\mathrm{i} \\mathrm{e}^{(\\mathrm{i} x)}");
    }

    @Test
    public void testReplace6() {
        List<String> results = new ArrayList();
        results.add("\\frac{1}{2} (π - 2 sin^(-1)(x))");
        LaTeXConverter.replace(results);
        assertEquals(results.get(0), "\\frac{1}{2} (\\pi - 2 \\sin^{(-1)}(x))");
    }

    @Test
    public void testReplace7() {
        List<String> results = new ArrayList();
        results.add("sin^2(x)");
        LaTeXConverter.replace(results);
        assertEquals(results.get(0), "\\sin^{2}(x)");
    }
}
