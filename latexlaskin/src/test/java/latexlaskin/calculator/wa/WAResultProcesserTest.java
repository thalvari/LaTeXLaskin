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

/**
 *
 * @author thalvari
 */
public class WAResultProcesserTest {

    @Test
    public void testWAResultProcesser() {
        WAResultProcesser processer = new WAResultProcesser();
        assertNotNull(processer);
    }

    @Test
    public void testTrimResultsInputApprox() {
        List<String> results = new ArrayList();
        results.add("∫_0^1 2 π xxπ≈3.1416");
        WAResultProcesser.trimResults(results);
        assertEquals(results.get(0), "π");
    }

    @Test
    public void testTrimResultsExtra() {
        List<String> results = new ArrayList();
        results.add("1/2 (4 π n + π),   n∈");
        WAResultProcesser.trimResults(results);
        assertEquals(results.get(0), "1/2 (4 π n + π)");
    }

    @Test
    public void testemoveBadResultsFor() {
        List<String> results = new ArrayList();
        results.add("1/2  (for x≠-2  and x≠2 )");
        results = WAResultProcesser.removeBadResults(results);
        assertTrue(results.isEmpty());
    }
}
