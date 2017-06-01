/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.latexconverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class LaTeXConverterTest {

    @Test
    public void testLaTeXConverter() {
        LaTeXConverter conv = new LaTeXConverter();
        Assert.assertNotNull(conv);
    }

    @Test
    public void testConvertResults() {
        String[] results = {"1/2 n (n + 1)"};
        List<String> teXResults
                = LaTeXConverter.convert(Arrays.asList(results));
        Assert.assertEquals(teXResults.get(0), "\\frac{1}{2} n (n + 1)");
    }

    @Test
    public void testConvertResults2() {
        List<String> teXResults = LaTeXConverter.convert(new ArrayList());
        Assert.assertTrue(teXResults.isEmpty());
    }

    @Test
    public void testConvertResults3() {
        List<String> teXResults = LaTeXConverter.convert(null);
        Assert.assertNull(teXResults);
    }

    @Test
    public void testConvertResults4() {
        String[] results = {
            "1/8 (2 n + 1)^2 - 1/8", "(n/2 + 1/2) n", "n^2/2 + n/2"
        };
        String[] modelTeXResults = {
            "\\frac{1}{8} (2 n + 1)^2 - \\frac{1}{8}",
            "(\\frac{n}{2} + \\frac{1}{2}) n",
            "\\frac{n^2}{2} + \\frac{n}{2}"
        };
        List<String> teXResults
                = LaTeXConverter.convert(Arrays.asList(results));
        for (int i = 0; i < teXResults.size(); i++) {
            Assert.assertEquals(teXResults.get(i), modelTeXResults[i]);
        }
    }

    @Test
    public void testConvertResults5() {
        String[] results = {
            "(x^2/4 + 1)/(x^2/2 + 1)", "x^2/(4 (x^2/2 + 1)) + 1/(x^2/2 + 1)",
            "(4 (x^2/4 + 2))/(x^2/2 + 1)"
        };
        String[] modelTeXResults = {
            "\\frac{(\\frac{x^2}{4} + 1)}{(\\frac{x^2}{2} + 1)}",
            "\\frac{x^2}{(4 (\\frac{x^2}{2} + 1))} + \\frac{1}{(\\frac{x^2}{2}"
            + " + 1)}", "\\frac{(4 (\\frac{x^2}{4} + 2))}{(\\frac{x^2}{2} + 1)}"
        };
        List<String> teXResults
                = LaTeXConverter.convert(Arrays.asList(results));
        for (int i = 0; i < teXResults.size(); i++) {
            Assert.assertEquals(teXResults.get(i), modelTeXResults[i]);
        }
    }
}
