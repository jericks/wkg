package org.cugos.wkg;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class GeometryCollectionTest {

    @Test
    public void create() {
        GeometryCollection gc = new GeometryCollection(
                Arrays.<Geometry>asList(
                        new Point(Coordinate.create2D(1,1), Dimension.Two),
                        new Point(Coordinate.create2D(2,3), Dimension.Two)
                ),
                Dimension.Two
        );
        assertFalse(gc.isEmpty());
        assertEquals(gc.getDimension(), Dimension.Two);
        assertEquals(2, gc.getGeometries().size());
    }

    @Test
    public void createEmpty() {
        GeometryCollection gc = GeometryCollection.createEmpty();
        assertTrue(gc.isEmpty());
        assertNull(gc.getSrid());
        assertTrue(gc.getGeometries().isEmpty());
        assertEquals("GEOMETRYCOLLECTION EMPTY", gc.toString());
    }

    @Test
    public void empty() {
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read("GEOMETRYCOLLECTION EMPTY");
        assertNotNull(geometry);
        assertTrue(geometry instanceof GeometryCollection);
        GeometryCollection gc = (GeometryCollection) geometry;
        assertTrue(gc.isEmpty());
        assertNull(gc.getSrid());
        assertTrue(gc.getGeometries().isEmpty());
        // WKT
        assertEquals("GEOMETRYCOLLECTION EMPTY", gc.toString());
    }

    @Test
    public void twoDimensional() {
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read("GEOMETRYCOLLECTION(POINT(4 6),LINESTRING(4 6,7 10))");

        assertNotNull(geometry);
        assertTrue(geometry instanceof GeometryCollection);
        GeometryCollection gc = (GeometryCollection) geometry;
        assertNull(gc.getSrid());
        assertEquals(3, gc.getNumberOfCoordinates());
        assertEquals(Dimension.Two, gc.getDimension());
        assertEquals(2, gc.getGeometries().size());
        // 0
        Point point = (Point) gc.getGeometries().get(0);
        assertEquals(Dimension.Two, point.getDimension());
        assertEquals(Coordinate.create2D(4, 6), point.getCoordinate());
        // 1
        LineString line = (LineString) gc.getGeometries().get(1);
        assertEquals(Dimension.Two, line.getDimension());
        assertEquals(2, line.getCoordinates().size());
        assertEquals(Coordinate.create2D(4, 6), line.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(7, 10), line.getCoordinates().get(1));
        // WKT
        assertEquals("GEOMETRYCOLLECTION (POINT (4.0 6.0), LINESTRING (4.0 6.0, 7.0 10.0))", gc.toString());
    }

    @Test
    public void twoDimensionalWithSrid() {
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read("SRID=4326;GEOMETRYCOLLECTION(POINT(4 6),LINESTRING(4 6,7 10))");

        assertNotNull(geometry);
        assertTrue(geometry instanceof GeometryCollection);
        GeometryCollection gc = (GeometryCollection) geometry;
        assertEquals("4326", gc.getSrid());
        assertEquals(Dimension.Two, gc.getDimension());
        assertEquals(2, gc.getGeometries().size());
        // 0
        Point point = (Point) gc.getGeometries().get(0);
        assertEquals("4326", point.getSrid());
        assertEquals(Dimension.Two, point.getDimension());
        assertEquals(Coordinate.create2D(4, 6), point.getCoordinate());
        // 1
        LineString line = (LineString) gc.getGeometries().get(1);
        assertEquals("4326", line.getSrid());
        assertEquals(Dimension.Two, line.getDimension());
        assertEquals(2, line.getCoordinates().size());
        assertEquals(Coordinate.create2D(4, 6), line.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(7, 10), line.getCoordinates().get(1));
        // WKT
        assertEquals("SRID=4326;GEOMETRYCOLLECTION (POINT (4.0 6.0), LINESTRING (4.0 6.0, 7.0 10.0))", gc.toString());
    }

    @Test
    public void twoDimensionalMeasured() {
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read("GEOMETRYCOLLECTION M (POINT(4 6 3),LINESTRING(4 6 2,7 10 4))");

        assertNotNull(geometry);
        assertTrue(geometry instanceof GeometryCollection);
        GeometryCollection gc = (GeometryCollection) geometry;
        assertEquals(Dimension.TwoMeasured, gc.getDimension());
        assertNull(gc.getSrid());
        assertEquals(2, gc.getGeometries().size());
        // 0
        Point point = (Point) gc.getGeometries().get(0);
        assertEquals(Dimension.TwoMeasured, point.getDimension());
        assertEquals(Coordinate.create2DM(4, 6, 3), point.getCoordinate());
        // 1
        LineString line = (LineString) gc.getGeometries().get(1);
        assertEquals(Dimension.TwoMeasured, line.getDimension());
        assertEquals(2, line.getCoordinates().size());
        assertEquals(Coordinate.create2DM(4, 6, 2), line.getCoordinates().get(0));
        assertEquals(Coordinate.create2DM(7, 10, 4), line.getCoordinates().get(1));
        // WKT
        assertEquals("GEOMETRYCOLLECTION M (POINT (4.0 6.0 3.0), LINESTRING (4.0 6.0 2.0, 7.0 10.0 4.0))", gc.toString());
    }

    @Test
    public void threeDimensional() {
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read("GEOMETRYCOLLECTION Z (POINT(4 6 3),LINESTRING(4 6 2,7 10 4))");

        assertNotNull(geometry);
        assertTrue(geometry instanceof GeometryCollection);
        GeometryCollection gc = (GeometryCollection) geometry;
        assertEquals(Dimension.Three, gc.getDimension());
        assertNull(gc.getSrid());
        assertEquals(2, gc.getGeometries().size());
        // 0
        Point point = (Point) gc.getGeometries().get(0);
        assertEquals(Dimension.Three, point.getDimension());
        assertEquals(Coordinate.create3D(4, 6, 3), point.getCoordinate());
        // 1
        LineString line = (LineString) gc.getGeometries().get(1);
        assertEquals(Dimension.Three, line.getDimension());
        assertEquals(2, line.getCoordinates().size());
        assertEquals(Coordinate.create3D(4, 6, 2), line.getCoordinates().get(0));
        assertEquals(Coordinate.create3D(7, 10, 4), line.getCoordinates().get(1));
        // WKT
        assertEquals("GEOMETRYCOLLECTION Z (POINT (4.0 6.0 3.0), LINESTRING (4.0 6.0 2.0, 7.0 10.0 4.0))", gc.toString());
    }

    @Test
    public void threeDimensionalMeasured() {
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read("GEOMETRYCOLLECTION ZM (POINT(4 6 3 1.2),LINESTRING(4 6 2 3.4,7 10 4 5.6))");

        assertNotNull(geometry);
        assertTrue(geometry instanceof GeometryCollection);
        GeometryCollection gc = (GeometryCollection) geometry;
        assertEquals(Dimension.ThreeMeasured, gc.getDimension());
        assertNull(gc.getSrid());
        assertEquals(2, gc.getGeometries().size());
        // 0
        Point point = (Point) gc.getGeometries().get(0);
        assertEquals(Dimension.ThreeMeasured, point.getDimension());
        assertEquals(Coordinate.create3DM(4, 6, 3, 1.2), point.getCoordinate());
        // 1
        LineString line = (LineString) gc.getGeometries().get(1);
        assertEquals(Dimension.ThreeMeasured, line.getDimension());
        assertEquals(2, line.getCoordinates().size());
        assertEquals(Coordinate.create3DM(4, 6, 2, 3.4), line.getCoordinates().get(0));
        assertEquals(Coordinate.create3DM(7, 10, 4, 5.6), line.getCoordinates().get(1));
        // WKT
        assertEquals("GEOMETRYCOLLECTION ZM (POINT (4.0 6.0 3.0 1.2), LINESTRING (4.0 6.0 2.0 3.4, 7.0 10.0 4.0 5.6))", gc.toString());
    }
}

