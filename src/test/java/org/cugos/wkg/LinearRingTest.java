package org.cugos.wkg;

import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.*;

public class LinearRingTest {

    @Test
    public void create() {
        LinearRing ring = new LinearRing(
            Arrays.asList(
                Coordinate.create2D(1,1),
                Coordinate.create2D(10,1),
                Coordinate.create2D(10,10),
                Coordinate.create2D(1,1)
            ),
            Dimension.Two
        );
        assertFalse(ring.isEmpty());
        assertEquals(4, ring.getNumberOfCoordinates());
        assertEquals("LINESTRING (1.0 1.0, 10.0 1.0, 10.0 10.0, 1.0 1.0)", ring.toString());
    }

    @Test
    public void createEmpty() {
        LinearRing ring = LinearRing.createEmpty();
        assertTrue(ring.isEmpty());
        assertEquals("LINESTRING EMPTY", ring.toString());
    }

}
