package org.cugos.wkg;

import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.*;

/**
 * A Test for parsing CircualStrings
 *
 * @author Jared Erickson
 */
public class CircularStringTest {

    @Test
    public void create() {
        CircularString cs = new CircularString(Arrays.asList(
                new Coordinate(1, 1),
                new Coordinate(5, 5),
                new Coordinate(2, 2)
        ), Dimension.Two);
        assertEquals(3, cs.getCoordinates().size());
        assertEquals(Dimension.Two, cs.getDimension());
        assertNull(cs.getSrid());
        assertEquals(Coordinate.create2D(1, 1), cs.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(5, 5), cs.getCoordinates().get(1));
        assertEquals(Coordinate.create2D(2, 2), cs.getCoordinates().get(2));
        assertEquals("CIRCULARSTRING (1.0 1.0, 5.0 5.0, 2.0 2.0)", cs.toString());

        cs = new CircularString(Arrays.asList(
                new Coordinate(1, 1),
                new Coordinate(5, 5),
                new Coordinate(2, 2)
        ), Dimension.Two, "4326");
        assertEquals(3, cs.getCoordinates().size());
        assertEquals(3, cs.getNumberOfCoordinates());
        assertEquals(Dimension.Two, cs.getDimension());
        assertEquals("4326", cs.getSrid());
        assertEquals(Coordinate.create2D(1, 1), cs.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(5, 5), cs.getCoordinates().get(1));
        assertEquals(Coordinate.create2D(2, 2), cs.getCoordinates().get(2));
        assertEquals("SRID=4326;CIRCULARSTRING (1.0 1.0, 5.0 5.0, 2.0 2.0)", cs.toString());
    }

    @Test
    public void createEmpty() {
        CircularString circularString = CircularString.createEmpty();
        assertTrue(circularString.isEmpty());
        assertNull(circularString.getSrid());
        assertTrue(circularString.getCoordinates().isEmpty());
        assertEquals("CIRCULARSTRING EMPTY", circularString.toString());
        assertEquals(0, circularString.getNumberOfCoordinates());
    }

    @Test
    public void empty() {
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read("CIRCULARSTRING EMPTY");

        assertNotNull(geometry);
        assertTrue(geometry instanceof CircularString);
        CircularString line = (CircularString) geometry;
        assertTrue(line.isEmpty());
        assertNull(line.getSrid());
        assertTrue(line.getCoordinates().isEmpty());
        assertEquals("CIRCULARSTRING EMPTY", line.toString());
        assertEquals(0, line.getNumberOfCoordinates());
    }

    @Test
    public void twoDimensional() {
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read("CIRCULARSTRING (101 234,345 567)");

        assertNotNull(geometry);
        assertTrue(geometry instanceof CircularString);
        CircularString line = (CircularString) geometry;
        assertNull(line.getSrid());
        assertEquals(2, line.getCoordinates().size());
        // 0
        Coordinate c = line.getCoordinates().get(0);
        assertEquals(Dimension.Two, c.getDimension());
        assertEquals(101, c.getX(), 0.01);
        assertEquals(234, c.getY(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        assertTrue(Double.isNaN(c.getZ()));
        // 1
        c = line.getCoordinates().get(1);
        assertEquals(Dimension.Two, c.getDimension());
        assertEquals(345, c.getX(), 0.01);
        assertEquals(567, c.getY(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        assertTrue(Double.isNaN(c.getZ()));
        // WKT
        assertEquals("CIRCULARSTRING (101.0 234.0, 345.0 567.0)", line.toString());
    }

    @Test
    public void twoDimensionalWithSrid() {
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read("SRID=4326;CIRCULARSTRING (101 234,345 567)");

        assertNotNull(geometry);
        assertTrue(geometry instanceof CircularString);
        CircularString line = (CircularString) geometry;
        assertEquals(2, line.getCoordinates().size());
        assertEquals("4326", line.getSrid());
        // 0
        Coordinate c = line.getCoordinates().get(0);
        assertEquals(Dimension.Two, c.getDimension());
        assertEquals(101, c.getX(), 0.01);
        assertEquals(234, c.getY(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        assertTrue(Double.isNaN(c.getZ()));
        // 1
        c = line.getCoordinates().get(1);
        assertEquals(Dimension.Two, c.getDimension());
        assertEquals(345, c.getX(), 0.01);
        assertEquals(567, c.getY(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        assertTrue(Double.isNaN(c.getZ()));
        // WKT
        assertEquals("SRID=4326;CIRCULARSTRING (101.0 234.0, 345.0 567.0)", line.toString());
    }

    @Test
    public void twoDimensionalMeasured() {
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read("CIRCULARSTRING M (101 234 5,345 567 6)");

        assertNotNull(geometry);
        assertTrue(geometry instanceof CircularString);
        CircularString line = (CircularString) geometry;
        assertNull(line.getSrid());
        assertEquals(2, line.getCoordinates().size());
        // 0
        Coordinate c = line.getCoordinates().get(0);
        assertEquals(Dimension.TwoMeasured, c.getDimension());
        assertEquals(101, c.getX(), 0.01);
        assertEquals(234, c.getY(), 0.01);
        assertEquals(5, c.getM(), 0.01);
        assertTrue(Double.isNaN(c.getZ()));
        // 1
        c = line.getCoordinates().get(1);
        assertEquals(Dimension.TwoMeasured, c.getDimension());
        assertEquals(345, c.getX(), 0.01);
        assertEquals(567, c.getY(), 0.01);
        assertEquals(6, c.getM(), 0.01);
        assertTrue(Double.isNaN(c.getZ()));
        // WKT
        assertEquals("CIRCULARSTRING M (101.0 234.0 5.0, 345.0 567.0 6.0)", line.toString());
    }

    @Test
    public void threeDimensional() {
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read("CIRCULARSTRING Z (101.1 234.2 5.4, 345.6 567.7 6.8)");

        assertNotNull(geometry);
        assertTrue(geometry instanceof CircularString);
        CircularString line = (CircularString) geometry;
        assertNull(line.getSrid());
        assertEquals(2, line.getCoordinates().size());
        // 0
        Coordinate c = line.getCoordinates().get(0);
        assertEquals(Dimension.Three, c.getDimension());
        assertEquals(101.1, c.getX(), 0.01);
        assertEquals(234.2, c.getY(), 0.01);
        assertEquals(5.4, c.getZ(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        // 1
        c = line.getCoordinates().get(1);
        assertEquals(Dimension.Three, c.getDimension());
        assertEquals(345.6, c.getX(), 0.01);
        assertEquals(567.7, c.getY(), 0.01);
        assertEquals(6.8, c.getZ(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        // WKT
        assertEquals("CIRCULARSTRING Z (101.1 234.2 5.4, 345.6 567.7 6.8)", line.toString());
    }

    @Test
    public void threeDimensionalMeasured() {
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read("CIRCULARSTRING ZM (101.1 234.2 5.4 1.2, 345.6 567.7 6.8 99.2)");

        assertNotNull(geometry);
        assertTrue(geometry instanceof CircularString);
        CircularString line = (CircularString) geometry;
        assertNull(line.getSrid());
        assertEquals(2, line.getCoordinates().size());
        // 0
        Coordinate c = line.getCoordinates().get(0);
        assertEquals(Dimension.ThreeMeasured, c.getDimension());
        assertEquals(101.1, c.getX(), 0.01);
        assertEquals(234.2, c.getY(), 0.01);
        assertEquals(5.4, c.getZ(), 0.01);
        assertEquals(1.2, c.getM(), 0.01);
        // 1
        c = line.getCoordinates().get(1);
        assertEquals(Dimension.ThreeMeasured, c.getDimension());
        assertEquals(345.6, c.getX(), 0.01);
        assertEquals(567.7, c.getY(), 0.01);
        assertEquals(6.8, c.getZ(), 0.01);
        assertEquals(99.2, c.getM(), 0.01);
        // WKT
        assertEquals("CIRCULARSTRING ZM (101.1 234.2 5.4 1.2, 345.6 567.7 6.8 99.2)", line.toString());
    }

}
