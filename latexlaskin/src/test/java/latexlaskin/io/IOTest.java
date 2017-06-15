/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.io;

import java.io.File;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author thalvari
 */
public class IOTest {

    private File file;

    @Before
    public void setUp() {
        file = new File("temp");
    }

    @After
    public void tearDown() {
        file.delete();
    }

    @Test
    public void testIO() {
        IO io = new IO();
        assertNotNull(io);
    }

    @Test
    public void testReadWrite() {
        IO.writeAppID(file, "1");
        assertEquals(IO.readAppID(file), "1");
    }

    @Test
    public void testRead2() {
        assertNull(IO.readAppID(file));
    }
}
