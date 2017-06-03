/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.calculator.wa;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class WAResultProcesserTest {

    private static final char EQUALS = 63449;
    private static final char IMAGINARY = 63310;

    @Test
    public void testWAResultProcesser() {
        WAResultProcesser processer = new WAResultProcesser();
        assertNotNull(processer);
    }

    @Test
    public void testTrimResults() {
        List<String> results = new ArrayList();
        results.add("∑_(i=0)^n i" + EQUALS + "1/2 n (n + 1)");
        WAResultProcesser.trimResults(results);
        assertEquals(results.get(0), "1/2 n (n + 1)");
    }

    @Test
    public void testTrimResults2() {
        List<String> results = new ArrayList();
        results.add("∫_0^1 2 π xx" + EQUALS + "π≈3.1416");
        WAResultProcesser.trimResults(results);
        assertEquals(results.get(0), "π");
    }

    @Test
    public void testemoveBadResults() {
        List<String> results = new ArrayList();
        results.add("1/2  (for x≠-2 " + IMAGINARY + " and x≠2 " + IMAGINARY
                + ")");
        results = WAResultProcesser.removeBadResults(results);
        assertTrue(results.isEmpty());
    }
}
