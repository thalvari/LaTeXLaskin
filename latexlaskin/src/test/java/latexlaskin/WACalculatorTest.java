/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin;

import com.wolfram.alpha.WAQuery;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

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
    public void testWACalculator2() {
        assertEquals(calc.getEngine().getAppID(), APPID);
    }

    @Test
    public void testWACalculator3() {
        assertEquals(calc.getEngine().getFormats()[0], MODE);
    }

    @Test
    public void testQuery() {
        calc.getEngine().setAppID(APPID + "1");
        String input = "1+1";
        calc.query(input);
        assertEquals(calc.getError(), "AppID virheellinen.");
    }

    @Test
    public void testQuery2() {
        String input = "@@";
        calc.query(input);
        assertEquals(calc.getError(), "Syöte virheellinen.");
    }

    @Test
    public void testQuery3() {
        String input = "\\pi";
        List<String> results = calc.query(input);
        assertNull(calc.getError());
        assertEquals(results.get(0), "3.141592653589793238462643383279502884197"
                + "169399375105820974…");
        assertEquals(results.size(), 1);
    }

    @Test
    public void testQuery4() {
        String input = "n(n+1)";
        List<String> results = calc.query(input);
        assertEquals(results.get(0), "1/4 (2 n + 1)^2 - 1/4");
        assertEquals(results.get(1), "n^2 + n");
        assertEquals(results.size(), 2);
    }

    @Test
    public void testQuery5() {
        String input = "\\sum_{i=0}^n i";
        List<String> results = calc.query(input);
        assertEquals(results.get(0), "1/2 n (n + 1)");
        assertEquals(results.get(1), "1/8 (2 n + 1)^2 - 1/8");
        assertEquals(results.get(2), "n^2/2 + n/2");
        assertEquals(results.size(), 3);
    }

    @Test
    public void testQuery6() {
        String input = "\\log_2 8";
        List<String> results = calc.query(input);
        assertEquals(results.get(0), "3");
    }

    @Test
    public void testQuery7() {
        String input = "d/dx 2x";
        List<String> results = calc.query(input);
        assertEquals(results.get(0), "2");
    }

    @Test
    public void testQuery8() {
        String input = "\\int 2x";
        List<String> results = calc.query(input);
        assertEquals(results.get(0), "x^2 + constant");
    }

    @Test
    public void testQuery9() {
        String input = "\\int_0^1 2x";
        List<String> results = calc.query(input);
        assertEquals(results.get(0), "1");
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
        assertTrue(tulos.contains("Ratkaisu WA:n sivuilla:"));
        assertTrue(tulos.contains(query.toWebsiteURL()));
        assertTrue(tulos.contains("Ratkaisu XML-tiedostona:"));
        assertTrue(tulos.contains(calc.getEngine().toURL(query)));
    }

    @Test
    public void testQuery11() {
        String input = "\\sum_{i=0}^{\\infty} 1/i!";
        List<String> results = calc.query(input);
        assertEquals(results.get(0), "e");
        assertEquals(results.get(1), "2.718281828459045235360287471352662497757"
                + "247093699959574966…");
    }

    @Test
    public void testQuery12() {
        String input = "\\int_0^1 2 \\pi x";
        List<String> results = calc.query(input);
        assertEquals(results.get(0), "π");
    }
}
