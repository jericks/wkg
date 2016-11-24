package org.cugos.wkg;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * A Test for parsing Points
 * @author Jared Erickson
 */
public class PointTest {

    @Test
    public void empty() {
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read("POINT EMPTY");
        assertNotNull(geometry);
        assertTrue(geometry instanceof Point);
        Point point = (Point) geometry;
        assertEquals(Dimension.Two, point.getDimension());
        assertTrue(point.isEmpty());
        assertTrue(point.getCoordinate().isEmpty());
        assertTrue(point.getCoordinate().getDimension() == Dimension.Two);
        assertTrue(Double.isNaN(point.getCoordinate().getX()));
        assertTrue(Double.isNaN(point.getCoordinate().getY()));
        assertTrue(Double.isNaN(point.getCoordinate().getM()));
        assertTrue(Double.isNaN(point.getCoordinate().getZ()));
        assertNull(point.getSrid());
        assertEquals(0, point.getNumberOfCoordinates());
        assertEquals("POINT EMPTY", point.toString());
    }

    @Test
    public void twoDimensional() {
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read("POINT (1 2)");
        assertNotNull(geometry);
        assertTrue(geometry instanceof Point);
        Point point = (Point) geometry;
        assertEquals(Dimension.Two, point.getDimension());
        assertFalse(point.getCoordinate().isEmpty());
        assertTrue(point.getCoordinate().getDimension() == Dimension.Two);
        assertEquals(1, point.getCoordinate().getX(), 0.001);
        assertEquals(2, point.getCoordinate().getY(), 0.001);
        assertTrue(Double.isNaN(point.getCoordinate().getM()));
        assertTrue(Double.isNaN(point.getCoordinate().getZ()));
        assertNull(point.getSrid());
        assertEquals("POINT (1.0 2.0)", point.toString());
        assertEquals(1, point.getNumberOfCoordinates());
    }

    @Test
    public void twoDimensionalMeasured() {
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read("POINT M (1 2 3)");
        assertNotNull(geometry);
        assertTrue(geometry instanceof Point);
        Point point = (Point) geometry;
        assertEquals(Dimension.TwoMeasured, point.getDimension());
        assertFalse(point.getCoordinate().isEmpty());
        assertTrue(point.getCoordinate().getDimension() == Dimension.TwoMeasured);
        assertEquals(1, point.getCoordinate().getX(), 0.001);
        assertEquals(2, point.getCoordinate().getY(), 0.001);
        assertEquals(3, point.getCoordinate().getM(), 0.001);
        assertTrue(Double.isNaN(point.getCoordinate().getZ()));
        assertNull(point.getSrid());
        assertEquals("POINT M (1.0 2.0 3.0)", point.toString());
    }

    @Test
    public void threeDimensional() {
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read("POINT Z (1 2 3)");
        assertNotNull(geometry);
        assertTrue(geometry instanceof Point);
        Point point = (Point) geometry;
        assertEquals(Dimension.Three, point.getDimension());
        assertFalse(point.getCoordinate().isEmpty());
        assertTrue(point.getCoordinate().getDimension() == Dimension.Three);
        assertEquals(1, point.getCoordinate().getX(), 0.001);
        assertEquals(2, point.getCoordinate().getY(), 0.001);
        assertEquals(3, point.getCoordinate().getZ(), 0.001);
        assertTrue(Double.isNaN(point.getCoordinate().getM()));
        assertNull(point.getSrid());
        assertEquals("POINT Z (1.0 2.0 3.0)", point.toString());
    }

    @Test
    public void threeDimensionalMeasured() {
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read("POINT ZM (1 2 3 4)");
        assertNotNull(geometry);
        assertTrue(geometry instanceof Point);
        Point point = (Point) geometry;
        assertEquals(Dimension.ThreeMeasured, point.getDimension());
        assertFalse(point.getCoordinate().isEmpty());
        assertTrue(point.getCoordinate().getDimension() == Dimension.ThreeMeasured);
        assertEquals(1, point.getCoordinate().getX(), 0.001);
        assertEquals(2, point.getCoordinate().getY(), 0.001);
        assertEquals(3, point.getCoordinate().getZ(), 0.001);
        assertEquals(4, point.getCoordinate().getM(), 0.001);
        assertNull(point.getSrid());
        assertEquals("POINT ZM (1.0 2.0 3.0 4.0)", point.toString());
    }

    @Test
    public void twoDimensionalWithSrid() {
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read("SRID=4326;POINT (1 2)");
        assertNotNull(geometry);
        assertTrue(geometry instanceof Point);
        Point point = (Point) geometry;
        assertEquals(Dimension.Two, point.getDimension());
        assertFalse(point.getCoordinate().isEmpty());
        assertTrue(point.getCoordinate().getDimension() == Dimension.Two);
        assertEquals(1, point.getCoordinate().getX(), 0.001);
        assertEquals(2, point.getCoordinate().getY(), 0.001);
        assertTrue(Double.isNaN(point.getCoordinate().getM()));
        assertTrue(Double.isNaN(point.getCoordinate().getZ()));
        assertEquals("4326", point.getSrid());
        assertEquals("SRID=4326;POINT (1.0 2.0)", point.toString());
    }

    @Test
    public void threeDimensionalWithoutZ() {
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read("POINT (1 2 5)");
        assertNotNull(geometry);
        assertTrue(geometry instanceof Point);
        Point point = (Point) geometry;
        assertEquals(Dimension.Three, point.getDimension());
        assertFalse(point.getCoordinate().isEmpty());
        assertTrue(point.getCoordinate().getDimension() == Dimension.Three);
        assertEquals(1, point.getCoordinate().getX(), 0.001);
        assertEquals(2, point.getCoordinate().getY(), 0.001);
        assertEquals(5, point.getCoordinate().getZ(), 0.001);
        assertTrue(Double.isNaN(point.getCoordinate().getM()));
        assertNull(point.getSrid());
        assertEquals("POINT Z (1.0 2.0 5.0)", point.toString());
    }

    @Test
    public void threeDimensionalMeasuredWithoutZM() {
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read("POINT (1 2 5 4)");
        assertNotNull(geometry);
        assertTrue(geometry instanceof Point);
        Point point = (Point) geometry;
        assertEquals(Dimension.ThreeMeasured, point.getDimension());
        assertFalse(point.getCoordinate().isEmpty());
        assertTrue(point.getCoordinate().getDimension() == Dimension.ThreeMeasured);
        assertEquals(1, point.getCoordinate().getX(), 0.001);
        assertEquals(2, point.getCoordinate().getY(), 0.001);
        assertEquals(5, point.getCoordinate().getZ(), 0.001);
        assertEquals(4, point.getCoordinate().getM(), 0.001);
        assertNull(point.getSrid());
        assertEquals("POINT ZM (1.0 2.0 5.0 4.0)", point.toString());
    }

    @Test
    public void createEmpty() {
        Point p = Point.createEmpty();
        assertTrue(p.isEmpty());
        assertEquals("POINT EMPTY", p.toString());
        assertNull(p.getSrid());
        assertEquals(p.getDimension(), Dimension.Two);
        Coordinate c = p.getCoordinate();
        assertTrue(Double.isNaN(c.getX()));
        assertTrue(Double.isNaN(c.getY()));
        assertTrue(Double.isNaN(c.getZ()));
        assertTrue(Double.isNaN(c.getM()));
        assertEquals(Dimension.Two, c.getDimension());
        assertTrue(c.isEmpty());
        assertEquals("Coordinate { x = NaN y = NaN }", c.toString());
    }

    @Test
    public void parseMultiple() {
        WKTReader reader = new WKTReader();
        // 1
        Geometry geometry = reader.read("POINT (1 2)");
        assertEquals("POINT (1.0 2.0)", geometry.toString());
        // 2
        geometry = reader.read("POINT M (1 2 4.5)");
        assertEquals("POINT M (1.0 2.0 4.5)", geometry.toString());
        // 3
        geometry = reader.read("POINT (-122.014487 46.982752)");
        assertEquals("POINT (-122.014487 46.982752)", geometry.toString());
    }
}
