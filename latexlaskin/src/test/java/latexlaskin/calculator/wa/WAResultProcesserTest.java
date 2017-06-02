/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.calculator.wa;

import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class WAResultProcesserTest {

    private static final char EQUALS = 63449;
    private static final char NEPER = 63309;
    private static final char IMAGINARY = 63310;

    @Test
    public void testWAResultProcesser() {
        WAResultProcesser processer = new WAResultProcesser();
        assertNotNull(processer);
    }

    @Test
    public void testProcess() {
        String[] results = {
            "∑_(i=0)^n i" + EQUALS + "1/2 n (n + 1)"
        };
        List<String> processedResults
                = WAResultProcesser.process(Arrays.asList(results));
        assertEquals(processedResults.get(0), "1/2 n (n + 1)");
    }

    @Test
    public void testProcess2() {
        String[] results = {
            "1/8 (2 n + 1)^2 - 1/8", "(n/2 + 1/2) n"
        };
        List<String> processedResults
                = WAResultProcesser.process(Arrays.asList(results));
        assertEquals(processedResults.get(0), results[0]);
        assertEquals(processedResults.get(1), results[1]);
    }

    @Test
    public void testProcess3() {
        String[] results = {
            "∑_(i=0)^∞ 1/(i!)" + EQUALS + NEPER
        };
        List<String> processedResults
                = WAResultProcesser.process(Arrays.asList(results));
        assertEquals(processedResults.get(0), "e");
    }

    @Test
    public void testProcess4() {
        String[] results = {
            "∫_0^1 2 π xx" + EQUALS + "π≈3.1416"
        };
        List<String> processedResults
                = WAResultProcesser.process(Arrays.asList(results));
        assertEquals(processedResults.get(0), "π");
    }

    @Test
    public void testProcess5() {
        String[] results = {
            "cos(x) + " + IMAGINARY + " sin(x)"
        };
        List<String> processedResults
                = WAResultProcesser.process(Arrays.asList(results));
        assertEquals(processedResults.get(0), "cos(x) + i sin(x)");
    }

    @Test
    public void testProcess6() {
        String[] results = {
            "1/2  (for x≠-2 " + IMAGINARY + " and x≠2 " + IMAGINARY + ")"
        };
        List<String> processedResults
                = WAResultProcesser.process(Arrays.asList(results));
        assertTrue(processedResults.isEmpty());
    }
}
