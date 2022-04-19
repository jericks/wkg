package org.cugos.wkg;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class CompoundCurveTest {

    @Test
    public void create() {
        CompoundCurve cc = new CompoundCurve(Arrays.asList(
                new CircularString(Arrays.asList(new Coordinate(1,0), new Coordinate(0,1), new Coordinate(-1,0)), Dimension.Two),
                new LineString(Arrays.asList(new Coordinate(-1,0), new Coordinate(2,0)), Dimension.Two)
        ), Dimension.Two);
        assertFalse(cc.isEmpty());
        assertEquals(2, cc.getCurves().size());
        assertEquals(5, cc.getNumberOfCoordinates());
        assertEquals(5, cc.getCoordinates().size());
        assertNull(cc.getSrid());
        assertEquals(Dimension.Two, cc.getDimension());
        // 0
        assertTrue(cc.getCurves().get(0) instanceof CircularString);
        CircularString cs = (CircularString) cc.getCurves().get(0);
        assertNull(cs.getSrid());
        assertEquals(Dimension.Two, cs.getDimension());
        assertEquals(3, cs.getCoordinates().size());
        assertEquals(Coordinate.create2D(1, 0), cs.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(0, 1), cs.getCoordinates().get(1));
        assertEquals(Coordinate.create2D(-1, 0), cs.getCoordinates().get(2));
        // 1
        assertTrue(cc.getCurves().get(1) instanceof LineString);
        LineString ls = (LineString) cc.getCurves().get(1);
        assertNull(ls.getSrid());
        assertEquals(Dimension.Two, ls.getDimension());
        assertEquals(2, ls.getCoordinates().size());
        assertEquals(Coordinate.create2D(-1, 0), ls.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(2, 0), ls.getCoordinates().get(1));
        // WKT
        assertEquals("COMPOUNDCURVE (CIRCULARSTRING (1.0 0.0, 0.0 1.0, -1.0 0.0), (-1.0 0.0, 2.0 0.0))", cc.toString());
    }

    @Test
    public void createEmpty() {
        CompoundCurve cc = CompoundCurve.createEmpty();
        assertTrue(cc.isEmpty());
        assertTrue(cc.getCurves().isEmpty());
        assertNull(cc.getSrid());
        assertEquals(Dimension.Two, cc.getDimension());
        assertEquals("COMPOUNDCURVE EMPTY", cc.toString());
    }

    @Test
    public void empty() {
        String wkt = "COMPOUNDCURVE EMPTY";
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read(wkt);

        assertNotNull(geometry);
        assertTrue(geometry instanceof CompoundCurve);
        CompoundCurve cc = (CompoundCurve) geometry;
        assertTrue(cc.isEmpty());
        assertTrue(cc.getCurves().isEmpty());
        assertNull(cc.getSrid());
        assertEquals(Dimension.Two, cc.getDimension());
        // WKT
        assertEquals("COMPOUNDCURVE EMPTY", cc.toString());
    }

    @Test
    public void twoDimensional() {
        String wkt = "COMPOUNDCURVE(CIRCULARSTRING(1 0, 0 1, -1 0), (-1 0, 2 0))";
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read(wkt);

        assertNotNull(geometry);
        assertTrue(geometry instanceof CompoundCurve);
        CompoundCurve cc = (CompoundCurve) geometry;
        assertEquals(2, cc.getCurves().size());
        assertEquals(5, cc.getNumberOfCoordinates());
        assertNull(cc.getSrid());
        assertEquals(Dimension.Two, cc.getDimension());
        // 0
        assertTrue(cc.getCurves().get(0) instanceof CircularString);
        CircularString cs = (CircularString) cc.getCurves().get(0);
        assertNull(cs.getSrid());
        assertEquals(Dimension.Two, cs.getDimension());
        assertEquals(3, cs.getCoordinates().size());
        assertEquals(Coordinate.create2D(1, 0), cs.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(0, 1), cs.getCoordinates().get(1));
        assertEquals(Coordinate.create2D(-1, 0), cs.getCoordinates().get(2));
        // 1
        assertTrue(cc.getCurves().get(1) instanceof LineString);
        LineString ls = (LineString) cc.getCurves().get(1);
        assertNull(ls.getSrid());
        assertEquals(Dimension.Two, ls.getDimension());
        assertEquals(2, ls.getCoordinates().size());
        assertEquals(Coordinate.create2D(-1, 0), ls.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(2, 0), ls.getCoordinates().get(1));
        // WKT
        assertEquals("COMPOUNDCURVE (CIRCULARSTRING (1.0 0.0, 0.0 1.0, -1.0 0.0), (-1.0 0.0, 2.0 0.0))", cc.toString());
    }

    @Test
    public void twoDimensionalWithSrid() {
        String wkt = "SRID=4326;COMPOUNDCURVE(CIRCULARSTRING(1 0, 0 1, -1 0), (-1 0, 2 0))";
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read(wkt);

        assertNotNull(geometry);
        assertTrue(geometry instanceof CompoundCurve);
        CompoundCurve cc = (CompoundCurve) geometry;
        assertEquals(2, cc.getCurves().size());
        assertEquals("4326", cc.getSrid());
        assertEquals(Dimension.Two, cc.getDimension());
        // 0
        assertTrue(cc.getCurves().get(0) instanceof CircularString);
        CircularString cs = (CircularString) cc.getCurves().get(0);
        assertEquals("4326", cs.getSrid());
        assertEquals(Dimension.Two, cs.getDimension());
        assertEquals(3, cs.getCoordinates().size());
        assertEquals(Coordinate.create2D(1, 0), cs.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(0, 1), cs.getCoordinates().get(1));
        assertEquals(Coordinate.create2D(-1, 0), cs.getCoordinates().get(2));
        // 1
        assertTrue(cc.getCurves().get(1) instanceof LineString);
        LineString ls = (LineString) cc.getCurves().get(1);
        assertEquals("4326", ls.getSrid());
        assertEquals(Dimension.Two, ls.getDimension());
        assertEquals(2, ls.getCoordinates().size());
        assertEquals(Coordinate.create2D(-1, 0), ls.getCoordinates().get(0));
        assertEquals(Coordinate.create2D(2, 0), ls.getCoordinates().get(1));
        // WKT
        assertEquals("SRID=4326;COMPOUNDCURVE (CIRCULARSTRING (1.0 0.0, 0.0 1.0, -1.0 0.0), (-1.0 0.0, 2.0 0.0))", cc.toString());
    }

    @Test
    public void twoDimensionalMeasured() {
        String wkt = "COMPOUNDCURVE M (CIRCULARSTRING(1 0 1, 0 1 2, -1 0 3), (-1 0 3, 2 0 4))";
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read(wkt);

        assertNotNull(geometry);
        assertTrue(geometry instanceof CompoundCurve);
        CompoundCurve cc = (CompoundCurve) geometry;
        assertEquals(2, cc.getCurves().size());
        assertNull(cc.getSrid());
        assertEquals(Dimension.TwoMeasured, cc.getDimension());
        // 0
        assertTrue(cc.getCurves().get(0) instanceof CircularString);
        CircularString cs = (CircularString) cc.getCurves().get(0);
        assertNull(cs.getSrid());
        assertEquals(Dimension.TwoMeasured, cs.getDimension());
        assertEquals(3, cs.getCoordinates().size());
        assertEquals(Coordinate.create2DM(1, 0, 1), cs.getCoordinates().get(0));
        assertEquals(Coordinate.create2DM(0, 1, 2), cs.getCoordinates().get(1));
        assertEquals(Coordinate.create2DM(-1, 0, 3), cs.getCoordinates().get(2));
        // 1
        assertTrue(cc.getCurves().get(1) instanceof LineString);
        LineString ls = (LineString) cc.getCurves().get(1);
        assertNull(ls.getSrid());
        assertEquals(Dimension.TwoMeasured, ls.getDimension());
        assertEquals(2, ls.getCoordinates().size());
        assertEquals(Coordinate.create2DM(-1, 0, 3), ls.getCoordinates().get(0));
        assertEquals(Coordinate.create2DM(2, 0, 4), ls.getCoordinates().get(1));
        // WKT
        assertEquals("COMPOUNDCURVE M (CIRCULARSTRING (1.0 0.0 1.0, 0.0 1.0 2.0, -1.0 0.0 3.0), (-1.0 0.0 3.0, 2.0 0.0 4.0))", cc.toString());
    }

    @Test
    public void threeDimensional() {
        String wkt = "COMPOUNDCURVE Z (CIRCULARSTRING(1 0 1, 0 1 2, -1 0 3), (-1 0 3, 2 0 4))";
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read(wkt);

        assertNotNull(geometry);
        assertTrue(geometry instanceof CompoundCurve);
        CompoundCurve cc = (CompoundCurve) geometry;
        assertEquals(2, cc.getCurves().size());
        assertNull(cc.getSrid());
        assertEquals(Dimension.Three, cc.getDimension());
        // 0
        assertTrue(cc.getCurves().get(0) instanceof CircularString);
        CircularString cs = (CircularString) cc.getCurves().get(0);
        assertNull(cs.getSrid());
        assertEquals(Dimension.Three, cs.getDimension());
        assertEquals(3, cs.getCoordinates().size());
        assertEquals(Coordinate.create3D(1, 0, 1), cs.getCoordinates().get(0));
        assertEquals(Coordinate.create3D(0, 1, 2), cs.getCoordinates().get(1));
        assertEquals(Coordinate.create3D(-1, 0, 3), cs.getCoordinates().get(2));
        // 1
        assertTrue(cc.getCurves().get(1) instanceof LineString);
        LineString ls = (LineString) cc.getCurves().get(1);
        assertNull(ls.getSrid());
        assertEquals(Dimension.Three, ls.getDimension());
        assertEquals(2, ls.getCoordinates().size());
        assertEquals(Coordinate.create3D(-1, 0, 3), ls.getCoordinates().get(0));
        assertEquals(Coordinate.create3D(2, 0, 4), ls.getCoordinates().get(1));
        // WKT
        assertEquals("COMPOUNDCURVE Z (CIRCULARSTRING (1.0 0.0 1.0, 0.0 1.0 2.0, -1.0 0.0 3.0), (-1.0 0.0 3.0, 2.0 0.0 4.0))", cc.toString());
    }

    @Test
    public void threeDimensionalMeasured() {
        String wkt = "COMPOUNDCURVE ZM (CIRCULARSTRING(1 0 1 5, 0 1 2 4, -1 0 3 2), (-1 0 3 2, 2 0 4 1))";
        WKTReader reader = new WKTReader();
        Geometry geometry = reader.read(wkt);

        assertNotNull(geometry);
        assertTrue(geometry instanceof CompoundCurve);
        CompoundCurve cc = (CompoundCurve) geometry;
        assertEquals(2, cc.getCurves().size());
        assertNull(cc.getSrid());
        assertEquals(Dimension.ThreeMeasured, cc.getDimension());
        // 0
        assertTrue(cc.getCurves().get(0) instanceof CircularString);
        CircularString cs = (CircularString) cc.getCurves().get(0);
        assertNull(cs.getSrid());
        assertEquals(Dimension.ThreeMeasured, cs.getDimension());
        assertEquals(3, cs.getCoordinates().size());
        assertEquals(Coordinate.create3DM(1, 0, 1, 5), cs.getCoordinates().get(0));
        assertEquals(Coordinate.create3DM(0, 1, 2, 4), cs.getCoordinates().get(1));
        assertEquals(Coordinate.create3DM(-1, 0, 3, 2), cs.getCoordinates().get(2));
        // 1
        assertTrue(cc.getCurves().get(1) instanceof LineString);
        LineString ls = (LineString) cc.getCurves().get(1);
        assertNull(ls.getSrid());
        assertEquals(Dimension.ThreeMeasured, ls.getDimension());
        assertEquals(2, ls.getCoordinates().size());
        assertEquals(Coordinate.create3DM(-1, 0, 3, 2), ls.getCoordinates().get(0));
        assertEquals(Coordinate.create3DM(2, 0, 4, 1), ls.getCoordinates().get(1));
        // WKT
        assertEquals("COMPOUNDCURVE ZM (CIRCULARSTRING (1.0 0.0 1.0 5.0, 0.0 1.0 2.0 4.0, -1.0 0.0 3.0 2.0), (-1.0 0.0 3.0 2.0, 2.0 0.0 4.0 1.0))", cc.toString());
    }
}
