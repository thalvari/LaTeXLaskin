/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.calculator.wa;

import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author thalvari
 */
public class WASupportedTest {

    private WASupported supp;

    @Before
    public void setUp() {
        supp = new WASupported();
    }

    @Test
    public void testWASupported() {
        assertNotNull(supp);
    }
}
