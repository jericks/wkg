package org.cugos.wkg;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TinTest {

    @Test
    public void create() {
        Tin tin = new Tin(
            Arrays.asList(
                new Triangle(
                    new LinearRing(
                        Arrays.asList(
                            Coordinate.create2D(0,0),
                            Coordinate.create2D(1,0),
                            Coordinate.create2D(0,1),
                            Coordinate.create2D(0,0)
                        ),
                        Dimension.Two
                    ),
                    new ArrayList<LinearRing>(),
                    Dimension.Two
                ),
                new Triangle(
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
                )
            ),
            Dimension.Two
        );
        assertEquals("TIN (((0.0 0.0, 1.0 0.0, 0.0 1.0, 0.0 0.0)), ((0.0 0.0, 0.0 1.0, 1.0 1.0, 0.0 0.0)))", tin.toString());
        assertEquals(Dimension.Two, tin.getDimension());
        assertNull(tin.getSrid());
    }

    @Test
    public void createEmpty() {
        Tin t = Tin.createEmpty();
        assertTrue(t.isEmpty());
        assertTrue(t.getTriangles().isEmpty());
        assertNull(t.getSrid());
        assertEquals(Dimension.Two, t.getDimension());
        assertEquals("TIN EMPTY", t.toString());
        assertEquals(0, t.getNumberOfCoordinates());
    }

    @Test
    public void empty() {
        String wkt = "TIN EMPTY";
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read(wkt);
        assertNotNull(geometry);
        assertTrue(geometry instanceof Tin);
        Tin t = (Tin) geometry;
        assertTrue(t.isEmpty());
        assertTrue(t.getTriangles().isEmpty());
        assertNull(t.getSrid());
        assertEquals(Dimension.Two, t.getDimension());
        assertEquals("TIN EMPTY", t.toString());
        assertEquals(0, t.getNumberOfCoordinates());
    }

    @Test
    public void twoDimensional() {
        String wkt = "TIN (((0 0, 1 0, 0 1, 0 0)), ((0 0, 0 1, 1 1, 0 0)))";
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read(wkt);

        assertNotNull(geometry);
        assertTrue(geometry instanceof Tin);
        Tin t = (Tin) geometry;
        assertNull(t.getSrid());
        assertEquals(Dimension.Two, t.getDimension());
        assertEquals(8, t.getNumberOfCoordinates());
        assertEquals(8, t.getCoordinates().size());
        assertEquals(2, t.getTriangles().size());
        // 0
        Triangle triangle = t.getTriangles().get(0);
        assertNull(triangle.getSrid());
        assertEquals(Dimension.Two, triangle.getDimension());
        assertEquals(4, triangle.getOuterLinearRing().getCoordinates().size());
        assertEquals(0, triangle.getInnerLinearRings().size());
        assertEquals(Coordinate.create2D(0, 0), triangle.getOuterLinearRing().getCoordinates().get(0));
        assertEquals(Coordinate.create2D(1, 0), triangle.getOuterLinearRing().getCoordinates().get(1));
        assertEquals(Coordinate.create2D(0, 1), triangle.getOuterLinearRing().getCoordinates().get(2));
        assertEquals(Coordinate.create2D(0, 0), triangle.getOuterLinearRing().getCoordinates().get(3));
        // 1
        triangle = t.getTriangles().get(1);
        assertNull(triangle.getSrid());
        assertEquals(Dimension.Two, triangle.getDimension());
        assertEquals(4, triangle.getOuterLinearRing().getCoordinates().size());
        assertEquals(0, triangle.getInnerLinearRings().size());
        assertEquals(Coordinate.create2D(0, 0), triangle.getOuterLinearRing().getCoordinates().get(0));
        assertEquals(Coordinate.create2D(0, 1), triangle.getOuterLinearRing().getCoordinates().get(1));
        assertEquals(Coordinate.create2D(1, 1), triangle.getOuterLinearRing().getCoordinates().get(2));
        assertEquals(Coordinate.create2D(0, 0), triangle.getOuterLinearRing().getCoordinates().get(3));
        // WKT
        assertEquals("TIN (((0.0 0.0, 1.0 0.0, 0.0 1.0, 0.0 0.0)), ((0.0 0.0, 0.0 1.0, 1.0 1.0, 0.0 0.0)))", t.toString());
    }

    @Test
    public void twoDimensionalWithSrid() {
        String wkt = "SRID=4326;TIN (((0 0, 1 0, 0 1, 0 0)), ((0 0, 0 1, 1 1, 0 0)))";
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read(wkt);

        assertNotNull(geometry);
        assertTrue(geometry instanceof Tin);
        Tin t = (Tin) geometry;
        assertEquals("4326", t.getSrid());
        assertEquals(Dimension.Two, t.getDimension());
        assertEquals(2, t.getTriangles().size());
        // 0
        Triangle triangle = t.getTriangles().get(0);
        assertEquals("4326", triangle.getSrid());
        assertEquals(Dimension.Two, triangle.getDimension());
        assertEquals(4, triangle.getOuterLinearRing().getCoordinates().size());
        assertEquals(0, triangle.getInnerLinearRings().size());
        assertEquals(Coordinate.create2D(0, 0), triangle.getOuterLinearRing().getCoordinates().get(0));
        assertEquals(Coordinate.create2D(1, 0), triangle.getOuterLinearRing().getCoordinates().get(1));
        assertEquals(Coordinate.create2D(0, 1), triangle.getOuterLinearRing().getCoordinates().get(2));
        assertEquals(Coordinate.create2D(0, 0), triangle.getOuterLinearRing().getCoordinates().get(3));
        // 1
        triangle = t.getTriangles().get(1);
        assertEquals("4326", triangle.getSrid());
        assertEquals(Dimension.Two, triangle.getDimension());
        assertEquals(4, triangle.getOuterLinearRing().getCoordinates().size());
        assertEquals(0, triangle.getInnerLinearRings().size());
        assertEquals(Coordinate.create2D(0, 0), triangle.getOuterLinearRing().getCoordinates().get(0));
        assertEquals(Coordinate.create2D(0, 1), triangle.getOuterLinearRing().getCoordinates().get(1));
        assertEquals(Coordinate.create2D(1, 1), triangle.getOuterLinearRing().getCoordinates().get(2));
        assertEquals(Coordinate.create2D(0, 0), triangle.getOuterLinearRing().getCoordinates().get(3));
        // WKT
        assertEquals("SRID=4326;TIN (((0.0 0.0, 1.0 0.0, 0.0 1.0, 0.0 0.0)), ((0.0 0.0, 0.0 1.0, 1.0 1.0, 0.0 0.0)))", t.toString());
    }

    @Test
    public void twoDimensionalMeasured() {
        String wkt = "TIN M (((0 0 22, 1 0 23, 0 1 24, 0 0 22)), ((0 0 22, 0 1 34, 1 1 35, 0 0 22)))";
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read(wkt);

        assertNotNull(geometry);
        assertTrue(geometry instanceof Tin);
        Tin t = (Tin) geometry;
        assertNull(t.getSrid());
        assertEquals(Dimension.TwoMeasured, t.getDimension());
        assertEquals(2, t.getTriangles().size());
        // 0
        Triangle triangle = t.getTriangles().get(0);
        assertNull(triangle.getSrid());
        assertEquals(Dimension.TwoMeasured, triangle.getDimension());
        assertEquals(4, triangle.getOuterLinearRing().getCoordinates().size());
        assertEquals(0, triangle.getInnerLinearRings().size());
        assertEquals(Coordinate.create2DM(0, 0, 22), triangle.getOuterLinearRing().getCoordinates().get(0));
        assertEquals(Coordinate.create2DM(1, 0, 23), triangle.getOuterLinearRing().getCoordinates().get(1));
        assertEquals(Coordinate.create2DM(0, 1, 24), triangle.getOuterLinearRing().getCoordinates().get(2));
        assertEquals(Coordinate.create2DM(0, 0, 22), triangle.getOuterLinearRing().getCoordinates().get(3));
        // 1
        triangle = t.getTriangles().get(1);
        assertNull(triangle.getSrid());
        assertEquals(Dimension.TwoMeasured, triangle.getDimension());
        assertEquals(4, triangle.getOuterLinearRing().getCoordinates().size());
        assertEquals(0, triangle.getInnerLinearRings().size());
        assertEquals(Coordinate.create2DM(0, 0, 22), triangle.getOuterLinearRing().getCoordinates().get(0));
        assertEquals(Coordinate.create2DM(0, 1, 34), triangle.getOuterLinearRing().getCoordinates().get(1));
        assertEquals(Coordinate.create2DM(1, 1, 35), triangle.getOuterLinearRing().getCoordinates().get(2));
        assertEquals(Coordinate.create2DM(0, 0, 22), triangle.getOuterLinearRing().getCoordinates().get(3));
        // WKT
        assertEquals("TIN M (((0.0 0.0 22.0, 1.0 0.0 23.0, 0.0 1.0 24.0, 0.0 0.0 22.0)), ((0.0 0.0 22.0, 0.0 1.0 34.0, 1.0 1.0 35.0, 0.0 0.0 22.0)))", t.toString());
    }

    @Test
    public void threeDimensional() {
        String wkt = "TIN Z (((0 0 22, 1 0 23, 0 1 24, 0 0 22)), ((0 0 22, 0 1 34, 1 1 35, 0 0 22)))";
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read(wkt);

        assertNotNull(geometry);
        assertTrue(geometry instanceof Tin);
        Tin t = (Tin) geometry;
        assertNull(t.getSrid());
        assertEquals(Dimension.Three, t.getDimension());
        assertEquals(2, t.getTriangles().size());
        // 0
        Triangle triangle = t.getTriangles().get(0);
        assertNull(triangle.getSrid());
        assertEquals(Dimension.Three, triangle.getDimension());
        assertEquals(4, triangle.getOuterLinearRing().getCoordinates().size());
        assertEquals(0, triangle.getInnerLinearRings().size());
        assertEquals(Coordinate.create3D(0, 0, 22), triangle.getOuterLinearRing().getCoordinates().get(0));
        assertEquals(Coordinate.create3D(1, 0, 23), triangle.getOuterLinearRing().getCoordinates().get(1));
        assertEquals(Coordinate.create3D(0, 1, 24), triangle.getOuterLinearRing().getCoordinates().get(2));
        assertEquals(Coordinate.create3D(0, 0, 22), triangle.getOuterLinearRing().getCoordinates().get(3));
        // 1
        triangle = t.getTriangles().get(1);
        assertNull(triangle.getSrid());
        assertEquals(Dimension.Three, triangle.getDimension());
        assertEquals(4, triangle.getOuterLinearRing().getCoordinates().size());
        assertEquals(0, triangle.getInnerLinearRings().size());
        assertEquals(Coordinate.create3D(0, 0, 22), triangle.getOuterLinearRing().getCoordinates().get(0));
        assertEquals(Coordinate.create3D(0, 1, 34), triangle.getOuterLinearRing().getCoordinates().get(1));
        assertEquals(Coordinate.create3D(1, 1, 35), triangle.getOuterLinearRing().getCoordinates().get(2));
        assertEquals(Coordinate.create3D(0, 0, 22), triangle.getOuterLinearRing().getCoordinates().get(3));
        // WKT
        assertEquals("TIN Z (((0.0 0.0 22.0, 1.0 0.0 23.0, 0.0 1.0 24.0, 0.0 0.0 22.0)), ((0.0 0.0 22.0, 0.0 1.0 34.0, 1.0 1.0 35.0, 0.0 0.0 22.0)))", t.toString());
    }

    @Test
    public void threeDimensionalMeasured() {
        String wkt = "TIN ZM (((0 0 22 99, 1 0 23 98, 0 1 24 97, 0 0 22 99)), ((0 0 22 99, 0 1 34 45, 1 1 35 47, 0 0 22 99)))";
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read(wkt);

        assertNotNull(geometry);
        assertTrue(geometry instanceof Tin);
        Tin t = (Tin) geometry;
        assertNull(t.getSrid());
        assertEquals(Dimension.ThreeMeasured, t.getDimension());
        assertEquals(2, t.getTriangles().size());
        // 0
        Triangle triangle = t.getTriangles().get(0);
        assertNull(triangle.getSrid());
        assertEquals(Dimension.ThreeMeasured, triangle.getDimension());
        assertEquals(4, triangle.getOuterLinearRing().getCoordinates().size());
        assertEquals(0, triangle.getInnerLinearRings().size());
        assertEquals(Coordinate.create3DM(0, 0, 22, 99), triangle.getOuterLinearRing().getCoordinates().get(0));
        assertEquals(Coordinate.create3DM(1, 0, 23, 98), triangle.getOuterLinearRing().getCoordinates().get(1));
        assertEquals(Coordinate.create3DM(0, 1, 24, 97), triangle.getOuterLinearRing().getCoordinates().get(2));
        assertEquals(Coordinate.create3DM(0, 0, 22, 99), triangle.getOuterLinearRing().getCoordinates().get(3));
        // 1
        triangle = t.getTriangles().get(1);
        assertNull(triangle.getSrid());
        assertEquals(Dimension.ThreeMeasured, triangle.getDimension());
        assertEquals(4, triangle.getOuterLinearRing().getCoordinates().size());
        assertEquals(0, triangle.getInnerLinearRings().size());
        assertEquals(Coordinate.create3DM(0, 0, 22, 99), triangle.getOuterLinearRing().getCoordinates().get(0));
        assertEquals(Coordinate.create3DM(0, 1, 34, 45), triangle.getOuterLinearRing().getCoordinates().get(1));
        assertEquals(Coordinate.create3DM(1, 1, 35, 47), triangle.getOuterLinearRing().getCoordinates().get(2));
        assertEquals(Coordinate.create3DM(0, 0, 22, 99), triangle.getOuterLinearRing().getCoordinates().get(3));
        // WKT
        assertEquals("TIN ZM (((0.0 0.0 22.0 99.0, 1.0 0.0 23.0 98.0, 0.0 1.0 24.0 97.0, 0.0 0.0 22.0 99.0)), ((0.0 0.0 22.0 99.0, 0.0 1.0 34.0 45.0, 1.0 1.0 35.0 47.0, 0.0 0.0 22.0 99.0)))", t.toString());
    }
}
