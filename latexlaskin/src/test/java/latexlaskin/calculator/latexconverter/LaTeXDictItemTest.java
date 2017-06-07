/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.calculator.latexconverter;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author thalvari
 */
public class LaTeXDictItemTest {

    private LaTeXDictItem item;
    private LaTeXDictItem otherItem;

    @Before
    public void setUp() {
        item = new LaTeXDictItem("\\sin");
        otherItem = new LaTeXDictItem("\\sqrt{", "}");
    }

    @Test
    public void testGetTag() {
        assertEquals(item.getTag(), "\\sin");
    }

    @Test
    public void testGetClosingTag() {
        assertEquals(otherItem.getClosingTag(), "}");
    }

    @Test
    public void testHasClosingTag() {
        assertEquals(item.hasClosingTag(), false);
    }

    @Test
    public void testHasClosingTag2() {
        assertEquals(otherItem.hasClosingTag(), true);
    }
}
