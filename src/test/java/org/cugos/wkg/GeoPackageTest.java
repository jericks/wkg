package org.cugos.wkg;

import org.junit.Test;
import static org.junit.Assert.*;

public class GeoPackageTest {

    @Test
    public void flag() {
        GeoPackage.Flag flag = GeoPackage.Flag.BinaryType;
        assertEquals((byte) 0x20, flag.getValue());
    }

    @Test
    public void binaryType() {
        GeoPackage.BinaryType type = GeoPackage.BinaryType.Standard;
        assertEquals(0, type.getValue());
        assertNotNull(GeoPackage.BinaryType.get(0));
        assertNotNull(GeoPackage.BinaryType.get(1));
        assertNull(GeoPackage.BinaryType.get(2));
    }

    @Test
    public void geometryEmptyType() {
        GeoPackage.GeometryEmptyType type = GeoPackage.GeometryEmptyType.Empty;
        assertEquals(0, type.getValue());
        assertNotNull(GeoPackage.GeometryEmptyType.get(0));
        assertNotNull(GeoPackage.GeometryEmptyType.get(1));
        assertNull(GeoPackage.GeometryEmptyType.get(2));
    }

    @Test
    public void envelopeType() {
        GeoPackage.EnvelopeType type = GeoPackage.EnvelopeType.Envelope;
        assertEquals(1, type.getValue());
        assertNotNull(GeoPackage.EnvelopeType.get(0));
        assertNotNull(GeoPackage.EnvelopeType.get(1));
        assertNotNull(GeoPackage.EnvelopeType.get(2));
        assertNotNull(GeoPackage.EnvelopeType.get(3));
        assertNotNull(GeoPackage.EnvelopeType.get(4));
        assertNull(GeoPackage.EnvelopeType.get(5));
    }
}
