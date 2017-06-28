/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.calculator.wa;

import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author thalvari
 */
public class WACalculatorTest {

    private WACalculator waCalc;

    @Before
    public void setUp() {
        waCalc = new WACalculator();
    }

    @Test
    public void testQueryBadAppID() {
        waCalc.setAppID(waCalc.getAppID() + "1");
        String input = "1+1";
        List<String> results = waCalc.query(input);
        assertNull(results);
        assertEquals(waCalc.getError(), "AppID virheellinen.");
    }

    @Test
    public void testQueryBadInput() {
        String input = "@@";
        List<String> results = waCalc.query(input);
        assertNull(results);
        assertEquals(waCalc.getError(), "Syöte virheellinen.");
    }

    @Test
    public void testQueryEmptyInput() {
        String input = "";
        List<String> results = waCalc.query(input);
        assertNull(results);
        assertEquals(waCalc.getError(), "Tyhjä syöte.");
    }

    @Test
    public void testQueryNotSupported() {
        String input = "1";
        List<String> results = waCalc.query(input);
        assertNull(results);
        assertEquals(waCalc.getError(), "Ei tuettuja ratkaisuja.");
    }

    @Test
    public void testQueryNetworkError() {
        waCalc.setAppID(" ");
        String input = "1+1";
        List<String> results = waCalc.query(input);
        assertNull(results);
        assertEquals(waCalc.getError(), "Tapahtui verkkovirhe.");
    }

    @Test
    public void testQuerySupportedPodIds() {
        String input = "\\sum_{i=0}^n i";
        List<String> results = waCalc.query(input);
        assertEquals(results.get(0), "∑_(i=0)^n i1/2 n (n + 1)");
        assertEquals(results.get(1), "1/8 (2 n + 1)^2 - 1/8");
        assertEquals(results.get(2), "(n/2 + 1/2) n");
        assertEquals(results.get(3), "n^2/2 + n/2");
    }

    @Test
    public void testQuerySupportedFirstPodTitles() {
        String input = "d/dx 2x";
        List<String> results = waCalc.query(input);
        assertEquals(results.get(0), "d/dx(2 x)2");
    }

    @Test
    public void testQuerySupportedExclusivePodTitles() {
        String input = "x^2 - 9 = 0";
        List<String> results = waCalc.query(input);
        assertEquals(results.get(0), "x-3");
        assertEquals(results.get(1), "x3");
    }

    @Test
    public void testQueryWarnings() {
        String input = "lolcatt";
        List<String> results = waCalc.query(input);
        assertNull(results);
        assertEquals(waCalc.getError(), "Syöte virheellinen.");
    }
}
