package org.cugos.wkg;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.Assert.*;

public class TriangleTest {

    @Test
    public void create() {
        Triangle triangle = new Triangle(
            new LinearRing(
                Arrays.asList(
                    Coordinate.create2D(0,0),
                    Coordinate.create2D(0,1),
                    Coordinate.create2D(1,1),
                    Coordinate.create2D(0,0)
                ),
                Dimension.Two
            ),
            new ArrayList<LinearRing>(),
            Dimension.Two
        );
        assertEquals("TRIANGLE ((0.0 0.0, 0.0 1.0, 1.0 1.0, 0.0 0.0))", triangle.toString());
        assertEquals(Dimension.Two, triangle.getDimension());
        assertNull(triangle.getSrid());
    }


    @Test
    public void createEmpty() {
        Triangle t = Triangle.createEmpty();
        assertTrue(t.isEmpty());
        assertTrue(t.getOuterLinearRing().getCoordinates().isEmpty());
        assertNull(t.getSrid());
        assertEquals(Dimension.Two, t.getDimension());
        assertEquals("TRIANGLE EMPTY", t.toString());
        assertEquals(0, t.getNumberOfCoordinates());
    }

    @Test
    public void empty() {
        String wkt = "TRIANGLE EMPTY";
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read(wkt);
        assertNotNull(geometry);
        assertTrue(geometry instanceof Triangle);
        Triangle t = (Triangle) geometry;
        assertTrue(t.isEmpty());
        assertTrue(t.getOuterLinearRing().getCoordinates().isEmpty());
        assertNull(t.getSrid());
        assertEquals(Dimension.Two, t.getDimension());
        assertEquals("TRIANGLE EMPTY", t.toString());
        assertEquals(0, t.getNumberOfCoordinates());
    }

    @Test
    public void twoDimensional() {
        String wkt = "TRIANGLE((0 0 ,0 1,1 1,0 0))";
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read(wkt);

        assertNotNull(geometry);
        assertTrue(geometry instanceof Triangle);
        Triangle t = (Triangle) geometry;
        assertNull(t.getSrid());
        assertEquals(Dimension.Two, t.getDimension());
        assertEquals(4, t.getNumberOfCoordinates());
        assertEquals(4, t.getOuterLinearRing().getCoordinates().size());
        assertEquals(Coordinate.create2D(0, 0), t.getOuterLinearRing().getCoordinates().get(0));
        assertEquals(Coordinate.create2D(0, 1), t.getOuterLinearRing().getCoordinates().get(1));
        assertEquals(Coordinate.create2D(1, 1), t.getOuterLinearRing().getCoordinates().get(2));
        assertEquals(Coordinate.create2D(0, 0), t.getOuterLinearRing().getCoordinates().get(3));
        // WKT
        assertEquals("TRIANGLE ((0.0 0.0, 0.0 1.0, 1.0 1.0, 0.0 0.0))", t.toString());
    }

    @Test
    public void twoDimensionalWithSrid() {
        String wkt = "SRID=4326;TRIANGLE((0 0 ,0 1,1 1,0 0))";
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read(wkt);

        assertNotNull(geometry);
        assertTrue(geometry instanceof Triangle);
        Triangle t = (Triangle) geometry;
        assertEquals("4326", t.getSrid());
        assertEquals(Dimension.Two, t.getDimension());
        assertEquals(4, t.getOuterLinearRing().getCoordinates().size());
        assertEquals(Coordinate.create2D(0, 0), t.getOuterLinearRing().getCoordinates().get(0));
        assertEquals(Coordinate.create2D(0, 1), t.getOuterLinearRing().getCoordinates().get(1));
        assertEquals(Coordinate.create2D(1, 1), t.getOuterLinearRing().getCoordinates().get(2));
        assertEquals(Coordinate.create2D(0, 0), t.getOuterLinearRing().getCoordinates().get(3));
        // WKT
        assertEquals("SRID=4326;TRIANGLE ((0.0 0.0, 0.0 1.0, 1.0 1.0, 0.0 0.0))", t.toString());
    }

    @Test
    public void twoDimensionalMeasured() {
        String wkt = "TRIANGLE M ((0 0 0,0 1 0,1 1 0,0 0 0))";
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read(wkt);

        assertNotNull(geometry);
        assertTrue(geometry instanceof Triangle);
        Triangle t = (Triangle) geometry;
        assertEquals(Dimension.TwoMeasured, t.getDimension());
        assertEquals(4, t.getOuterLinearRing().getCoordinates().size());
        assertEquals(Coordinate.create2DM(0, 0, 0), t.getOuterLinearRing().getCoordinates().get(0));
        assertEquals(Coordinate.create2DM(0, 1, 0), t.getOuterLinearRing().getCoordinates().get(1));
        assertEquals(Coordinate.create2DM(1, 1, 0), t.getOuterLinearRing().getCoordinates().get(2));
        assertEquals(Coordinate.create2DM(0, 0, 0), t.getOuterLinearRing().getCoordinates().get(3));
        // WKT
        assertEquals("TRIANGLE M ((0.0 0.0 0.0, 0.0 1.0 0.0, 1.0 1.0 0.0, 0.0 0.0 0.0))", t.toString());
    }

    @Test
    public void threeDimensional() {
        String wkt = "TRIANGLE Z ((0 0 0,0 1 0,1 1 0,0 0 0))";
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read(wkt);

        assertNotNull(geometry);
        assertTrue(geometry instanceof Triangle);
        Triangle t = (Triangle) geometry;
        assertEquals(Dimension.Three, t.getDimension());
        assertEquals(4, t.getOuterLinearRing().getCoordinates().size());
        assertEquals(Coordinate.create3D(0, 0, 0), t.getOuterLinearRing().getCoordinates().get(0));
        assertEquals(Coordinate.create3D(0, 1, 0), t.getOuterLinearRing().getCoordinates().get(1));
        assertEquals(Coordinate.create3D(1, 1, 0), t.getOuterLinearRing().getCoordinates().get(2));
        assertEquals(Coordinate.create3D(0, 0, 0), t.getOuterLinearRing().getCoordinates().get(3));
        // WKT
        assertEquals("TRIANGLE Z ((0.0 0.0 0.0, 0.0 1.0 0.0, 1.0 1.0 0.0, 0.0 0.0 0.0))", t.toString());
    }

    @Test
    public void threeDimensionalMeasured() {
        String wkt = "TRIANGLE ZM ((0 0 0 1,0 1 0 2,1 1 0 3,0 0 0 1))";
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read(wkt);

        assertNotNull(geometry);
        assertTrue(geometry instanceof Triangle);
        Triangle t = (Triangle) geometry;
        assertEquals(Dimension.ThreeMeasured, t.getDimension());
        assertEquals(4, t.getOuterLinearRing().getCoordinates().size());
        assertEquals(Coordinate.create3DM(0, 0, 0, 1), t.getOuterLinearRing().getCoordinates().get(0));
        assertEquals(Coordinate.create3DM(0, 1, 0, 2), t.getOuterLinearRing().getCoordinates().get(1));
        assertEquals(Coordinate.create3DM(1, 1, 0, 3), t.getOuterLinearRing().getCoordinates().get(2));
        assertEquals(Coordinate.create3DM(0, 0, 0, 1), t.getOuterLinearRing().getCoordinates().get(3));
        // WKT
        assertEquals("TRIANGLE ZM ((0.0 0.0 0.0 1.0, 0.0 1.0 0.0 2.0, 1.0 1.0 0.0 3.0, 0.0 0.0 0.0 1.0))", t.toString());
    }

    @Test
    public void threeDimensionalWithoutZ() {
        String wkt = "TRIANGLE((0 0 0,0 1 0,1 1 0,0 0 0))";
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read(wkt);

        assertNotNull(geometry);
        assertTrue(geometry instanceof Triangle);
        Triangle t = (Triangle) geometry;
        assertEquals(Dimension.Three, t.getDimension());
        assertEquals(4, t.getOuterLinearRing().getCoordinates().size());
        assertEquals(Coordinate.create3D(0, 0, 0), t.getOuterLinearRing().getCoordinates().get(0));
        assertEquals(Coordinate.create3D(0, 1, 0), t.getOuterLinearRing().getCoordinates().get(1));
        assertEquals(Coordinate.create3D(1, 1, 0), t.getOuterLinearRing().getCoordinates().get(2));
        assertEquals(Coordinate.create3D(0, 0, 0), t.getOuterLinearRing().getCoordinates().get(3));
        // WKT
        assertEquals("TRIANGLE Z ((0.0 0.0 0.0, 0.0 1.0 0.0, 1.0 1.0 0.0, 0.0 0.0 0.0))", t.toString());
    }

}
