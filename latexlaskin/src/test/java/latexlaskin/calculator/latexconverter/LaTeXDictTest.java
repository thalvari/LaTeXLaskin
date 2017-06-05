/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.calculator.latexconverter;

import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author thalvari
 */
public class LaTeXDictTest {

    private LaTeXDict dict;

    @Before
    public void setUp() {
        dict = new LaTeXDict();
    }

    @Test
    public void testLaTeXDictionary() {
        assertNotNull(dict);
    }
}
