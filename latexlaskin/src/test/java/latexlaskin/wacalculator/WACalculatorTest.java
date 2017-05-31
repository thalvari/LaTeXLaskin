package latexlaskin.wacalculator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.wolfram.alpha.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;
import org.junit.*;

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
        Assert.assertNotNull(calc);
    }

    @Test
    public void testWACalculator2() {
        Assert.assertEquals(calc.getEngine().getAppID(), APPID);
    }

    @Test
    public void testWACalculator3() {
        Assert.assertEquals(calc.getEngine().getFormats()[0], MODE);
    }

    @Test
    public void testQuery() {
        calc.getEngine().setAppID(APPID + "1");
        String input = "1+1";
        List<String> results = calc.query(input);
        Assert.assertNull(results);
        Assert.assertEquals(calc.getError(), "AppID virheellinen.");
    }

    @Test
    public void testQuery2() {
        String input = "@@";
        List<String> results = calc.query(input);
        Assert.assertNull(results);
        Assert.assertEquals(calc.getError(), "Syöte virheellinen.");
    }

    @Test
    public void testQuery3() {
        String input = "\\pi";
        List<String> results = calc.query(input);
        Assert.assertEquals(results.get(0), "3.141592653589793238462643383279502884197"
                + "169399375105820974…");
        Assert.assertNull(calc.getError());
    }

    @Test
    public void testQuery4() {
        String input = "n(n+1)";
        List<String> results = calc.query(input);
        Assert.assertEquals(results.get(0), "1/4 (2 n + 1)^2 - 1/4");
        Assert.assertEquals(results.get(1), "n^2 + n");
    }

    @Test
    public void testQuery5() {
        String input = "\\sum_{i=0}^n i";
        List<String> results = calc.query(input);
        Assert.assertEquals(results.get(0), "1/2 n (n + 1)");
        Assert.assertEquals(results.get(1), "1/8 (2 n + 1)^2 - 1/8");
        Assert.assertEquals(results.get(2), "(n/2 + 1/2) n");
        Assert.assertEquals(results.get(3), "n^2/2 + n/2");
    }

    @Test
    public void testQuery6() {
        String input = "\\log_2 8";
        List<String> results = calc.query(input);
        Assert.assertEquals(results.get(0), "3");
    }

    @Test
    public void testQuery7() {
        String input = "d/dx 2x";
        List<String> results = calc.query(input);
        Assert.assertEquals(results.get(0), "2");
    }

    @Test
    public void testQuery8() {
        String input = "\\int 2x";
        List<String> results = calc.query(input);
        Assert.assertEquals(results.get(0), "x^2 + constant");
    }

    @Test
    public void testQuery9() {
        String input = "\\int_0^1 2x";
        List<String> results = calc.query(input);
        Assert.assertEquals(results.get(0), "1");
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
        Assert.assertTrue(tulos.contains("Ratkaisu WA:n sivuilla:"));
        Assert.assertTrue(tulos.contains(query.toWebsiteURL()));
        Assert.assertTrue(tulos.contains("Ratkaisu XML-tiedostona:"));
        Assert.assertTrue(tulos.contains(calc.getEngine().toURL(query)));
    }

    @Test
    public void testQuery11() {
        String input = "\\sum_{i=0}^{\\infty} 1/i!";
        List<String> results = calc.query(input);
        Assert.assertEquals(results.get(0), "e");
        Assert.assertEquals(results.get(1), "2.718281828459045235360287471352662497757"
                + "247093699959574966…");
    }

    @Test
    public void testQuery12() {
        String input = "\\int_0^1 2 \\pi x";
        List<String> results = calc.query(input);
        Assert.assertEquals(results.get(0), "π");
    }

    @Test
    public void testQuery13() {
        String input = "";
        List<String> results = calc.query(input);
        Assert.assertNull(results);
        Assert.assertEquals(calc.getError(), "Tyhjä syöte.");
    }

    @Test
    public void testQuery14() {
        String input = "e^{ix}";
        List<String> results = calc.query(input);
        Assert.assertEquals(results.get(0), "cos(x) + i sin(x)");
    }

    @Test
    public void testQuery15() {
        String input = "|2x-2|";
        List<String> results = calc.query(input);
        Assert.assertEquals(results.get(0), "2 abs(x - 1)");
        Assert.assertEquals(results.get(1), "abs(2 - 2 x)");
        Assert.assertEquals(results.get(2), "sqrt((2 x - 2)^2)");
    }

    @Test
    public void testQuery16() {
        String input = "((x/2)^2 + 1)/(x^2/2 + 2)";
        List<String> results = calc.query(input);
        Assert.assertEquals(results.get(0), "x^2/(4 (x^2/2 + 2)) + 1/(x^2/2 + 2"
                + ")");
    }
}
