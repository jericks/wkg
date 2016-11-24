package org.cugos.wkg;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class WKBReaderTest {

    private final WKTReader wktReader = new WKTReader();

    private final WKBReader wkbReader = new WKBReader();

    private final WKBWriter wkbWriter = new WKBWriter();

    protected void assertGeometryEquals(String wkt, String wkb) {
        Geometry geometry1 = wktReader.read(wkt);
        if (wkb.length() == 0) {
            System.out.println(wkbWriter.writeToHex(geometry1));
        }
        Geometry geometry2 = wkbReader.read(wkb);
        boolean equals = geometry1.toString().equals(geometry2.toString());
        if (!equals) {
            System.out.println("WKT: " + geometry1);
            System.out.println("WKB: " + geometry2);
        }
        assertTrue(equals);
    }

    @Test
    public void readPoint2D() {
        String wkt = "POINT (2.0 4)";
        String wkb = "000000000140000000000000004010000000000000";
        assertGeometryEquals(wkt, wkb);
    }

    @Test
    public void readLineString2D() {
        String wkt = "LINESTRING (101 234,345 567)";
        String wkb = "0000000002000000024059400000000000406D40000000000040759000000000004081B80000000000";
        assertGeometryEquals(wkt, wkb);
    }

    @Test
    public void readPolygonWithHole2D() {
        String wkt = "POLYGON ((35 10, 45 45, 15 40, 10 20, 35 10),(20 30, 35 35, 30 20, 20 30))";
        String wkb = "000000000300000002000000054041800000000000402400000000000040468000000000004046800000000000402E00000000000040440000000000004024000000000000403400000000000040418000000000004024000000000000000000044034000000000000403E00000000000040418000000000004041800000000000403E00000000000040340000000000004034000000000000403E000000000000";
        assertGeometryEquals(wkt, wkb);
    }

    @Test
    public void readPolygon2D() {
        String wkt = "POLYGON ((35.0 10.0, 45.0 45.0, 15.0 40.0, 10.0 20.0, 35.0 10.0))";
        String wkb = "000000000300000001000000054041800000000000402400000000000040468000000000004046800000000000402E00000000000040440000000000004024000000000000403400000000000040418000000000004024000000000000";
        assertGeometryEquals(wkt, wkb);
    }

    @Test
    public void readMultiPoint2D() {
        String wkt = "MULTIPOINT (10 40, 40 30, 20 20, 30 10)";
        String wkb = "00000000040000000400000000014024000000000000404400000000000000000000014044000000000000403E0000000000000000000001403400000000000040340000000000000000000001403E0000000000004024000000000000";
        assertGeometryEquals(wkt, wkb);
    }

    @Test
    public void readMultiLineString2D() {
        String wkt = "MULTILINESTRING ((0.0 0.0, 1.0 1.0, 1.0 2.0), (2.0 3.0, 3.0 2.0, 5.0 4.0))";
        String wkb = "000000000500000002000000000200000003000000000000000000000000000000003FF00000000000003FF00000000000003FF00000000000004000000000000000000000000200000003400000000000000040080000000000004008000000000000400000000000000040140000000000004010000000000000";
        assertGeometryEquals(wkt, wkb);
    }

    @Test
    public void readMultiPolygon2D() {
        String wkt = "MULTIPOLYGON (((40 40, 20 45, 45 30, 40 40)),((20 35, 10 30, 10 10, 30 5, 45 20, 20 35),(30 20, 20 15, 20 25, 30 20)))";
        String wkb = "0000000006000000020000000003000000010000000440440000000000004044000000000000403400000000000040468000000000004046800000000000403E0000000000004044000000000000404400000000000000000000030000000200000006403400000000000040418000000000004024000000000000403E00000000000040240000000000004024000000000000403E0000000000004014000000000000404680000000000040340000000000004034000000000000404180000000000000000004403E00000000000040340000000000004034000000000000402E00000000000040340000000000004039000000000000403E0000000000004034000000000000";
        assertGeometryEquals(wkt, wkb);
    }

    @Test
    public void readGeometryCollection2D() {
        String wkt = "GEOMETRYCOLLECTION (POINT (4.0 6.0), LINESTRING (4.0 6.0, 7.0 10.0))";
        String wkb = "00000000070000000200000000014010000000000000401800000000000000000000020000000240100000000000004018000000000000401C0000000000004024000000000000";
        assertGeometryEquals(wkt, wkb);
    }

    @Test
    public void readCircularString2D() {
        String wkt = "CIRCULARSTRING(0 0, 1 1, 1 0)";
        String wkb = "000000000800000003000000000000000000000000000000003FF00000000000003FF00000000000003FF00000000000000000000000000000";
        assertGeometryEquals(wkt, wkb);
    }

    @Test
    public void readTriangle2D() {
        String wkt = "TRIANGLE ((0.0 0.0, 0.0 1.0, 1.0 1.0, 0.0 0.0))";
        String wkb = "000000001100000001000000040000000000000000000000000000000000000000000000003FF00000000000003FF00000000000003FF000000000000000000000000000000000000000000000";
        assertGeometryEquals(wkt, wkb);
    }

    @Test
    public void readTin2D() {
        String wkt = "TIN (((0 0, 1 0, 0 1, 0 0)), ((0 0, 0 1, 1 1, 0 0)))";
        String wkb = "00000000100000000200000000110000000100000004000000000000000000000000000000003FF0000000000000000000000000000000000000000000003FF000000000000000000000000000000000000000000000000000001100000001000000040000000000000000000000000000000000000000000000003FF00000000000003FF00000000000003FF000000000000000000000000000000000000000000000";
        assertGeometryEquals(wkt, wkb);
    }

    @Test
    public void readCompoundCurve2D() {
        String wkt = "COMPOUNDCURVE(CIRCULARSTRING(1 0, 0 1, -1 0), (-1 0, 2 0))";
        String wkb = "0000000009000000020000000008000000033FF0000000000000000000000000000000000000000000003FF0000000000000BFF00000000000000000000000000000000000000200000002BFF0000000000000000000000000000040000000000000000000000000000000";
        assertGeometryEquals(wkt, wkb);
    }

    @Test
    public void readCurvePolygon2D() {
        String wkt = "CURVEPOLYGON(CIRCULARSTRING(0 0, 4 0, 4 4, 0 4, 0 0),(1 1, 3 3, 3 1, 1 1))";
        String wkb = "000000000A0000000200000000080000000500000000000000000000000000000000401000000000000000000000000000004010000000000000401000000000000000000000000000004010000000000000000000000000000000000000000000000000000002000000043FF00000000000003FF00000000000004008000000000000400800000000000040080000000000003FF00000000000003FF00000000000003FF0000000000000";
        assertGeometryEquals(wkt, wkb);
    }

    @Test
    public void readMultiCurve2D() {
        String wkt = "MULTICURVE((5 5,3 5,3 3,0 3),CIRCULARSTRING(0 0,2 1,2 2))";
        String wkb = "000000000B00000002000000000200000004401400000000000040140000000000004008000000000000401400000000000040080000000000004008000000000000000000000000000040080000000000000000000008000000030000000000000000000000000000000040000000000000003FF000000000000040000000000000004000000000000000";
        assertGeometryEquals(wkt, wkb);
    }

    @Test
    public void readMultiSurface2D() {
        String wkt = "MULTISURFACE(CURVEPOLYGON(CIRCULARSTRING(0 0, 4 0, 4 4, 0 4, 0 0),(1 1, 3 3, 3 1, 1 1)),((10 10, 14 12, 11 10, 10 10),(11 11, 11.5 11, 11 11.5, 11 11)))";
        String wkb = "000000000C00000002000000000A0000000200000000080000000500000000000000000000000000000000401000000000000000000000000000004010000000000000401000000000000000000000000000004010000000000000000000000000000000000000000000000000000002000000043FF00000000000003FF00000000000004008000000000000400800000000000040080000000000003FF00000000000003FF00000000000003FF00000000000000000000003000000020000000440240000000000004024000000000000402C000000000000402800000000000040260000000000004024000000000000402400000000000040240000000000000000000440260000000000004026000000000000402700000000000040260000000000004026000000000000402700000000000040260000000000004026000000000000";
        assertGeometryEquals(wkt, wkb);
    }

    @Test
    public void readPolyHedralSurface2D() {
        String wkt = "POLYHEDRALSURFACE (((40 40, 20 45, 45 30, 40 40)),((20 35, 10 30, 10 10, 30 5, 45 20, 20 35),(30 20, 20 15, 20 25, 30 20)))";
        String wkb = "000000000F000000020000000003000000010000000440440000000000004044000000000000403400000000000040468000000000004046800000000000403E0000000000004044000000000000404400000000000000000000030000000200000006403400000000000040418000000000004024000000000000403E00000000000040240000000000004024000000000000403E0000000000004014000000000000404680000000000040340000000000004034000000000000404180000000000000000004403E00000000000040340000000000004034000000000000402E00000000000040340000000000004039000000000000403E0000000000004034000000000000";
        assertGeometryEquals(wkt, wkb);
    }

}
