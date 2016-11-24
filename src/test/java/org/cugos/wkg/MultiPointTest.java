package org.cugos.wkg;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * A Test for parsing Points
 * @author Jared Erickson
 */
public class MultiPointTest {

    @Test
    public void create() {
        // Two Dimension without SRID
        MultiPoint mp = new MultiPoint(
            Arrays.asList(
                new Point(new Coordinate(1,1),Dimension.Two),
                new Point(new Coordinate(2,3),Dimension.Two)
            ),
            Dimension.Two
        );
        assertEquals("MULTIPOINT (1.0 1.0, 2.0 3.0)", mp.toString());
        assertEquals(Dimension.Two, mp.getDimension());
        assertNull(mp.getSrid());

        // Two Dimension with SRID
        mp = new MultiPoint(
            Arrays.asList(
                    new Point(new Coordinate(1,1),Dimension.Two, "EPSG:4326"),
                    new Point(new Coordinate(2,3),Dimension.Two, "EPSG:4326")
            ),
            Dimension.Two,
            "EPSG:4326"
        );
        assertEquals("SRID=EPSG:4326;MULTIPOINT (1.0 1.0, 2.0 3.0)", mp.toString());
        assertEquals(Dimension.Two, mp.getDimension());
        assertEquals("EPSG:4326", mp.getSrid());
    }

    @Test
    public void createEmpty() {
        MultiPoint mp = MultiPoint.createEmpty();
        assertTrue(mp.isEmpty());
        assertTrue(mp.getPoints().isEmpty());
        assertEquals("MULTIPOINT EMPTY", mp.toString());
    }

    @Test
    public void empty() {
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read("MULTIPOINT EMPTY");
        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiPoint);
        MultiPoint mp = (MultiPoint) geometry;
        assertTrue(mp.isEmpty());
        assertTrue(mp.getPoints().isEmpty());
        assertEquals("MULTIPOINT EMPTY", mp.toString());
    }

    @Test
    public void twoDimensional1() {
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read("MULTIPOINT (10 40, 40 30, 20 20, 30 10)");

        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiPoint);
        MultiPoint mp = (MultiPoint) geometry;
        assertEquals(4, mp.getNumberOfCoordinates());
        assertEquals(4, mp.getPoints().size());
        // 0
        Coordinate c = mp.getPoints().get(0).getCoordinate();
        assertEquals(Dimension.Two, c.getDimension());
        assertEquals(10, c.getX(), 0.01);
        assertEquals(40, c.getY(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        assertTrue(Double.isNaN(c.getZ()));
        // 1
        c = mp.getPoints().get(1).getCoordinate();
        assertEquals(Dimension.Two, c.getDimension());
        assertEquals(40, c.getX(), 0.01);
        assertEquals(30, c.getY(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        assertTrue(Double.isNaN(c.getZ()));
        // 3
        c = mp.getPoints().get(2).getCoordinate();
        assertEquals(Dimension.Two, c.getDimension());
        assertEquals(20, c.getX(), 0.01);
        assertEquals(20, c.getY(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        assertTrue(Double.isNaN(c.getZ()));
        // 4
        c = mp.getPoints().get(3).getCoordinate();
        assertEquals(Dimension.Two, c.getDimension());
        assertEquals(30, c.getX(), 0.01);
        assertEquals(10, c.getY(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        assertTrue(Double.isNaN(c.getZ()));
        // WKT
        assertEquals("MULTIPOINT (10.0 40.0, 40.0 30.0, 20.0 20.0, 30.0 10.0)", mp.toString());
    }

    @Test
    public void twoDimensional2() {
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read("MULTIPOINT ((10 40), (40 30), (20 20), (30 10))");

        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiPoint);
        MultiPoint mp = (MultiPoint) geometry;
        assertEquals(4, mp.getPoints().size());
        // 0
        Coordinate c = mp.getPoints().get(0).getCoordinate();
        assertEquals(Dimension.Two, c.getDimension());
        assertEquals(10, c.getX(), 0.01);
        assertEquals(40, c.getY(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        assertTrue(Double.isNaN(c.getZ()));
        // 1
        c = mp.getPoints().get(1).getCoordinate();
        assertEquals(Dimension.Two, c.getDimension());
        assertEquals(40, c.getX(), 0.01);
        assertEquals(30, c.getY(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        assertTrue(Double.isNaN(c.getZ()));
        // 3
        c = mp.getPoints().get(2).getCoordinate();
        assertEquals(Dimension.Two, c.getDimension());
        assertEquals(20, c.getX(), 0.01);
        assertEquals(20, c.getY(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        assertTrue(Double.isNaN(c.getZ()));
        // 4
        c = mp.getPoints().get(3).getCoordinate();
        assertEquals(Dimension.Two, c.getDimension());
        assertEquals(30, c.getX(), 0.01);
        assertEquals(10, c.getY(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        assertTrue(Double.isNaN(c.getZ()));
        // WKT
        assertEquals("MULTIPOINT (10.0 40.0, 40.0 30.0, 20.0 20.0, 30.0 10.0)", mp.toString());
    }

    @Test
    public void twoDimensionalMeasured() {
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read("MULTIPOINT M ((10 40 1), (40 30 2), (20 20 3), (30 10 4))");

        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiPoint);
        MultiPoint mp = (MultiPoint) geometry;
        assertEquals(4, mp.getPoints().size());
        // 0
        Coordinate c = mp.getPoints().get(0).getCoordinate();
        assertEquals(Dimension.TwoMeasured, c.getDimension());
        assertEquals(10, c.getX(), 0.01);
        assertEquals(40, c.getY(), 0.01);
        assertEquals(1, c.getM(), 0.01);
        assertTrue(Double.isNaN(c.getZ()));
        // 1
        c = mp.getPoints().get(1).getCoordinate();
        assertEquals(Dimension.TwoMeasured, c.getDimension());
        assertEquals(40, c.getX(), 0.01);
        assertEquals(30, c.getY(), 0.01);
        assertEquals(2, c.getM(), 0.01);
        assertTrue(Double.isNaN(c.getZ()));
        // 3
        c = mp.getPoints().get(2).getCoordinate();
        assertEquals(Dimension.TwoMeasured, c.getDimension());
        assertEquals(20, c.getX(), 0.01);
        assertEquals(20, c.getY(), 0.01);
        assertEquals(3, c.getM(), 0.01);
        assertTrue(Double.isNaN(c.getZ()));
        // 4
        c = mp.getPoints().get(3).getCoordinate();
        assertEquals(Dimension.TwoMeasured, c.getDimension());
        assertEquals(30, c.getX(), 0.01);
        assertEquals(10, c.getY(), 0.01);
        assertEquals(4, c.getM(), 0.01);
        assertTrue(Double.isNaN(c.getZ()));
        // WKT
        assertEquals("MULTIPOINT M (10.0 40.0 1.0, 40.0 30.0 2.0, 20.0 20.0 3.0, 30.0 10.0 4.0)", mp.toString());
    }

    @Test
    public void threeDimensional() {
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read("MULTIPOINT Z ((10 40 1), (40 30 2), (20 20 3), (30 10 4))");

        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiPoint);
        MultiPoint mp = (MultiPoint) geometry;
        assertEquals(4, mp.getPoints().size());
        // 0
        Coordinate c = mp.getPoints().get(0).getCoordinate();
        assertEquals(Dimension.Three, c.getDimension());
        assertEquals(10, c.getX(), 0.01);
        assertEquals(40, c.getY(), 0.01);
        assertEquals(1, c.getZ(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        // 1
        c = mp.getPoints().get(1).getCoordinate();
        assertEquals(Dimension.Three, c.getDimension());
        assertEquals(40, c.getX(), 0.01);
        assertEquals(30, c.getY(), 0.01);
        assertEquals(2, c.getZ(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        // 3
        c = mp.getPoints().get(2).getCoordinate();
        assertEquals(Dimension.Three, c.getDimension());
        assertEquals(20, c.getX(), 0.01);
        assertEquals(20, c.getY(), 0.01);
        assertEquals(3, c.getZ(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        // 4
        c = mp.getPoints().get(3).getCoordinate();
        assertEquals(Dimension.Three, c.getDimension());
        assertEquals(30, c.getX(), 0.01);
        assertEquals(10, c.getY(), 0.01);
        assertEquals(4, c.getZ(), 0.01);
        assertTrue(Double.isNaN(c.getM()));
        // WKT
        assertEquals("MULTIPOINT Z (10.0 40.0 1.0, 40.0 30.0 2.0, 20.0 20.0 3.0, 30.0 10.0 4.0)", mp.toString());
    }

    @Test
    public void threeDimensionalMeasured() {
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read("MULTIPOINT ZM ((10 40 1 4), (40 30 2 3), (20 20 3 2), (30 10 4 1))");

        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiPoint);
        MultiPoint mp = (MultiPoint) geometry;
        assertEquals(4, mp.getPoints().size());
        // 0
        Coordinate c = mp.getPoints().get(0).getCoordinate();
        assertEquals(Dimension.ThreeMeasured, c.getDimension());
        assertEquals(10, c.getX(), 0.01);
        assertEquals(40, c.getY(), 0.01);
        assertEquals(1, c.getZ(), 0.01);
        assertEquals(4, c.getM(), 0.01);
        // 1
        c = mp.getPoints().get(1).getCoordinate();
        assertEquals(Dimension.ThreeMeasured, c.getDimension());
        assertEquals(40, c.getX(), 0.01);
        assertEquals(30, c.getY(), 0.01);
        assertEquals(2, c.getZ(), 0.01);
        assertEquals(3, c.getM(), 0.01);
        // 3
        c = mp.getPoints().get(2).getCoordinate();
        assertEquals(Dimension.ThreeMeasured, c.getDimension());
        assertEquals(20, c.getX(), 0.01);
        assertEquals(20, c.getY(), 0.01);
        assertEquals(3, c.getZ(), 0.01);
        assertEquals(2, c.getM(), 0.01);
        // 4
        c = mp.getPoints().get(3).getCoordinate();
        assertEquals(Dimension.ThreeMeasured, c.getDimension());
        assertEquals(30, c.getX(), 0.01);
        assertEquals(10, c.getY(), 0.01);
        assertEquals(4, c.getZ(), 0.01);
        assertEquals(1, c.getM(), 0.01);
        // WKT
        assertEquals("MULTIPOINT ZM (10.0 40.0 1.0 4.0, 40.0 30.0 2.0 3.0, 20.0 20.0 3.0 2.0, 30.0 10.0 4.0 1.0)", mp.toString());
    }

}
