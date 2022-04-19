package org.cugos.wkg;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class MultiLineStringTest {

    @Test
    public void create() {
        // Two Dimensional with no SRID
        MultiLineString multiLineString = new MultiLineString(
            Arrays.asList(
                new LineString(Arrays.asList(
                    new Coordinate(0,0),
                    new Coordinate(1,1),
                    new Coordinate(1,2)
                ), Dimension.Two),
                new LineString(Arrays.asList(
                    new Coordinate(2,3),
                    new Coordinate(3,2),
                    new Coordinate(5,4)
                ), Dimension.Two)
            ),
            Dimension.Two
        );
        assertEquals(multiLineString.toString(), "MULTILINESTRING ((0.0 0.0, 1.0 1.0, 1.0 2.0), (2.0 3.0, 3.0 2.0, 5.0 4.0))");
        assertEquals(2, multiLineString.getLineStrings().size());
        assertEquals(Dimension.Two, multiLineString.getDimension());
        assertNull(multiLineString.getSrid());
    }

    @Test
    public void createEmpty() {
        MultiLineString mls = MultiLineString.createEmpty();
        assertTrue(mls.isEmpty());
        assertTrue(mls.getLineStrings().isEmpty());
        assertEquals("MULTILINESTRING EMPTY", mls.toString());
    }

    @Test
    public void empty() {
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read("MULTILINESTRING EMPTY");
        assertNotNull(geometry);
        assertTrue(geometry.isEmpty());
        assertTrue(geometry instanceof MultiLineString);
        MultiLineString mls = (MultiLineString) geometry;
        assertTrue(mls.getLineStrings().isEmpty());
        assertEquals("MULTILINESTRING EMPTY", mls.toString());
    }

    @Test
    public void twoDimensional() {
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read("MULTILINESTRING((0 0,1 1,1 2),(2 3,3 2,5 4))");
        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiLineString);
        MultiLineString mls = (MultiLineString) geometry;
        assertEquals(Dimension.Two, mls.getDimension());
        assertEquals(2, mls.getLineStrings().size());
        assertEquals(6, mls.getNumberOfCoordinates());
        // 0
        LineString line = mls.getLineStrings().get(0);
        assertEquals(3, line.getCoordinates().size());
        assertEquals(Coordinate.create2D(0, 0), line.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(1, 1), line.getCoordinates().get(1));
        assertEquals(Coordinate.create2D(1, 2), line.getCoordinates().get(2));
        // 1
        line = mls.getLineStrings().get(1);
        assertEquals(3, line.getCoordinates().size());
        assertEquals(Coordinate.create2D(2, 3), line.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(3, 2), line.getCoordinates().get(1));
        assertEquals(Coordinate.create2D(5, 4), line.getCoordinates().get(2));
        // WKT
        assertEquals("MULTILINESTRING ((0.0 0.0, 1.0 1.0, 1.0 2.0), (2.0 3.0, 3.0 2.0, 5.0 4.0))", mls.toString());
    }

    @Test
    public void twoDimensionalMeasured() {
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read("MULTILINESTRING M ((0 0 0,1 1 0,1 2 1),(2 3 1,3 2 1,5 4 1))");

        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiLineString);
        MultiLineString mls = (MultiLineString) geometry;
        assertEquals(Dimension.TwoMeasured, mls.getDimension());
        assertEquals(2, mls.getLineStrings().size());
        // 0
        LineString line = mls.getLineStrings().get(0);
        assertEquals(3, line.getCoordinates().size());
        assertEquals(Coordinate.create2DM(0, 0, 0), line.getCoordinates().get(0));
        assertEquals(Coordinate.create2DM(1, 1, 0), line.getCoordinates().get(1));
        assertEquals(Coordinate.create2DM(1, 2, 1), line.getCoordinates().get(2));
        // 1
        line = mls.getLineStrings().get(1);
        assertEquals(3, line.getCoordinates().size());
        assertEquals(Coordinate.create2DM(2, 3, 1), line.getCoordinates().get(0));
        assertEquals(Coordinate.create2DM(3, 2, 1), line.getCoordinates().get(1));
        assertEquals(Coordinate.create2DM(5, 4, 1), line.getCoordinates().get(2));
        // WKT
        assertEquals("MULTILINESTRING M ((0.0 0.0 0.0, 1.0 1.0 0.0, 1.0 2.0 1.0), (2.0 3.0 1.0, 3.0 2.0 1.0, 5.0 4.0 1.0))", mls.toString());
    }

    @Test
    public void threeDimensional() {
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read("MULTILINESTRING Z ((0 0 0,1 1 0,1 2 1),(2 3 1,3 2 1,5 4 1))");

        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiLineString);
        MultiLineString mls = (MultiLineString) geometry;
        assertEquals(Dimension.Three, mls.getDimension());
        assertEquals(2, mls.getLineStrings().size());
        // 0
        LineString line = mls.getLineStrings().get(0);
        assertEquals(3, line.getCoordinates().size());
        assertEquals(Coordinate.create3D(0, 0, 0), line.getCoordinates().get(0));
        assertEquals(Coordinate.create3D(1, 1, 0), line.getCoordinates().get(1));
        assertEquals(Coordinate.create3D(1, 2, 1), line.getCoordinates().get(2));
        // 1
        line = mls.getLineStrings().get(1);
        assertEquals(3, line.getCoordinates().size());
        assertEquals(Coordinate.create3D(2, 3, 1), line.getCoordinates().get(0));
        assertEquals(Coordinate.create3D(3, 2, 1), line.getCoordinates().get(1));
        assertEquals(Coordinate.create3D(5, 4, 1), line.getCoordinates().get(2));
        // WKT
        assertEquals("MULTILINESTRING Z ((0.0 0.0 0.0, 1.0 1.0 0.0, 1.0 2.0 1.0), (2.0 3.0 1.0, 3.0 2.0 1.0, 5.0 4.0 1.0))", mls.toString());
    }

    @Test
    public void threeDimensionalWithoutZ() {
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read("MULTILINESTRING((0 0 0,1 1 0,1 2 1),(2 3 1,3 2 1,5 4 1))");

        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiLineString);
        MultiLineString mls = (MultiLineString) geometry;
        assertEquals(Dimension.Three, mls.getDimension());
        assertEquals(2, mls.getLineStrings().size());
        // 0
        LineString line = mls.getLineStrings().get(0);
        assertEquals(3, line.getCoordinates().size());
        assertEquals(Coordinate.create3D(0, 0, 0), line.getCoordinates().get(0));
        assertEquals(Coordinate.create3D(1, 1, 0), line.getCoordinates().get(1));
        assertEquals(Coordinate.create3D(1, 2, 1), line.getCoordinates().get(2));
        // 1
        line = mls.getLineStrings().get(1);
        assertEquals(3, line.getCoordinates().size());
        assertEquals(Coordinate.create3D(2, 3, 1), line.getCoordinates().get(0));
        assertEquals(Coordinate.create3D(3, 2, 1), line.getCoordinates().get(1));
        assertEquals(Coordinate.create3D(5, 4, 1), line.getCoordinates().get(2));
        // WKT
        assertEquals("MULTILINESTRING Z ((0.0 0.0 0.0, 1.0 1.0 0.0, 1.0 2.0 1.0), (2.0 3.0 1.0, 3.0 2.0 1.0, 5.0 4.0 1.0))", mls.toString());
    }

    @Test
    public void threeDimensionalMeasured() {
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read("MULTILINESTRING ZM ((0 0 0 7,1 1 0 8,1 2 1 9),(2 3 1 5,3 2 1 4,5 4 1 3))");

        assertNotNull(geometry);
        assertTrue(geometry instanceof MultiLineString);
        MultiLineString mls = (MultiLineString) geometry;
        assertEquals(Dimension.ThreeMeasured, mls.getDimension());
        assertEquals(2, mls.getLineStrings().size());
        // 0
        LineString line = mls.getLineStrings().get(0);
        assertEquals(3, line.getCoordinates().size());
        assertEquals(Coordinate.create3DM(0, 0, 0, 7), line.getCoordinates().get(0));
        assertEquals(Coordinate.create3DM(1, 1, 0, 8), line.getCoordinates().get(1));
        assertEquals(Coordinate.create3DM(1, 2, 1, 9), line.getCoordinates().get(2));
        // 1
        line = mls.getLineStrings().get(1);
        assertEquals(3, line.getCoordinates().size());
        assertEquals(Coordinate.create3DM(2, 3, 1, 5), line.getCoordinates().get(0));
        assertEquals(Coordinate.create3DM(3, 2, 1, 4), line.getCoordinates().get(1));
        assertEquals(Coordinate.create3DM(5, 4, 1, 3), line.getCoordinates().get(2));
        // WKT
        assertEquals("MULTILINESTRING ZM ((0.0 0.0 0.0 7.0, 1.0 1.0 0.0 8.0, 1.0 2.0 1.0 9.0), " +
                "(2.0 3.0 1.0 5.0, 3.0 2.0 1.0 4.0, 5.0 4.0 1.0 3.0))", mls.toString());
    }
}
