/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.latexconverter;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import latexlaskin.wacalculator.WACalculator;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author thalvari
 */
public class LaTeXConverterTest {

    private static final String APPID = "WJ628E-G3H5VTERP4";
    private static final String MODE = "plaintext";

    private WACalculator calc;

    @Before
    public void setUp() {
        Logger.getLogger("com.wolfram.alpha.net.URLFetcher")
                .setLevel(Level.OFF);
        calc = new WACalculator(APPID, MODE, false);
    }

    @Test
    public void testConvertResults() {
        String[] results = {
            "2 abs(x - 1)", "abs(2 - 2 x)", "sqrt((2 x - 2)^2)"
        };
        List<String> teXResults
                = LaTeXConverter.convert(Arrays.asList(results));
        for (int i = 0; i < teXResults.size(); i++) {
            assertEquals(teXResults.get(i),
                    LaTeXConverter.convert(results[i]));
        }
    }

    @Test
    public void testConvertResult() {
        // TODO
    }
}
