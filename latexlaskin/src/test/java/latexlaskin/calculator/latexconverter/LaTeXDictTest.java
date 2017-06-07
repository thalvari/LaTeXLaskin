/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.calculator.latexconverter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author thalvari
 */
public class LaTeXDictTest {

    @Test
    public void testLaTeXDict() {
        LaTeXDict dict = new LaTeXDict();
        assertNotNull(dict);
    }

    @Test
    public void testGetRealKeyLength() {
        assertEquals(LaTeXDict.getRealKeyLength("π"), 1);
    }

    @Test
    public void testGetRealKeyLength2() {
        assertEquals(LaTeXDict.getRealKeyLength("cos("), 3);
    }

    @Test
    public void testGetKeys() {
        assertEquals(LaTeXDict.getKeys().size(), 12);
    }

    @Test
    public void testGetItem() {
        assertNotNull(LaTeXDict.getItem("^"));
    }
}
