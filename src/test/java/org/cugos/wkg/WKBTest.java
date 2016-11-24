package org.cugos.wkg;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class WKBTest {

    @Test
    public void getGeometryType() {
        assertEquals(WKB.GeometryType.Point, WKB.GeometryType.get(WKB.GeometryType.Point.getValue()));
        assertEquals(WKB.GeometryType.LineString, WKB.GeometryType.get(WKB.GeometryType.LineString.getValue()));
        assertEquals(WKB.GeometryType.Polygon, WKB.GeometryType.get(WKB.GeometryType.Polygon.getValue()));
        assertNull(WKB.GeometryType.get(999));
    }

    @Test
    public void getEndian() {
        assertEquals(WKB.Endian.Big, WKB.Endian.get(WKB.Endian.Big.getValue()));
        assertEquals(WKB.Endian.Little, WKB.Endian.get(WKB.Endian.Little.getValue()));
        assertNull(WKB.Endian.get(999));
    }
}
