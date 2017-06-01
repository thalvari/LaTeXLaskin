/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.wa;

import com.wolfram.alpha.WAQuery;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class WACalculatorTest {

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
    public void testWACalculator() {
        assertNotNull(calc);
    }

    @Test
    public void testQuery() {
        calc.getEngine().setAppID(APPID + "1");
        String input = "1+1";
        List<String> results = calc.query(input);
        assertNull(results);
        assertEquals(calc.getError(), "AppID virheellinen.");
    }

    @Test
    public void testQuery2() {
        String input = "@@";
        List<String> results = calc.query(input);
        assertNull(results);
        assertEquals(calc.getError(), "Syöte virheellinen.");
    }

    @Test
    public void testQuery3() {
        String input = "";
        List<String> results = calc.query(input);
        assertNull(results);
        assertEquals(calc.getError(), "Tyhjä syöte.");
    }

    @Test
    public void testQuery4() {
        String input = "\\pi";
        List<String> results = calc.query(input);
        assertEquals(results.get(0), "3.141592653589793238462643383279502884197"
                + "169399375105820974…");
        assertNull(calc.getError());
    }

    @Test
    public void testQuery5() {
        String input = "\\sum_{i=0}^n i";
        List<String> results = calc.query(input);
        assertEquals(results.get(0), "∑_(i=0)^n i1/2 n (n + 1)");
        assertEquals(results.get(1), "1/8 (2 n + 1)^2 - 1/8");
        assertEquals(results.get(2), "(n/2 + 1/2) n");
        assertEquals(results.get(3), "n^2/2 + n/2");
    }

    @Test
    public void testQuery6() {
        String input = "e^{ix}";
        List<String> results = calc.query(input);
        assertEquals(results.get(0), "cos(x) +  sin(x)");
    }

    @Test
    public void testQuery7() {
        String input = "d/dx 2x";
        List<String> results = calc.query(input);
        assertEquals(results.get(0), "d/dx(2 x)2");
    }

    @Test
    public void testQuery8() {
        String input = "\\int 2x";
        List<String> results = calc.query(input);
        assertEquals(results.get(0), "∫2 xxx^2 + constant");
    }

    @Test
    public void testQuery9() {
        String input = "\\int_0^1 2x";
        List<String> results = calc.query(input);
        assertEquals(results.get(0), "∫_0^1 2 xx1");
    }

    @Test
    public void testQuery10() {
        calc.setDebug(true);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        String input = "1+1";
        WAQuery query = calc.getEngine().createQuery();
        query.setInput(input);
        calc.query(input);
        String tulos = out.toString();
        assertTrue(tulos.contains("Ratkaisut WA:n sivuilla:"));
        assertTrue(tulos.contains(query.toWebsiteURL()));
        assertTrue(tulos.contains("Ratkaisut XML-tiedostona:"));
        assertTrue(tulos.contains(calc.getEngine().toURL(query)));
    }
}
