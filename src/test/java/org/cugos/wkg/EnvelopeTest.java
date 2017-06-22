package org.cugos.wkg;

import org.junit.Test;
import static org.junit.Assert.*;

public class EnvelopeTest {

    @Test
    public void empty() {
        Envelope envelope = Envelope.createEmpty();
        assertTrue(Double.isNaN(envelope.getMinX()));
        assertTrue(Double.isNaN(envelope.getMaxX()));
        assertTrue(Double.isNaN(envelope.getMinY()));
        assertTrue(Double.isNaN(envelope.getMaxY()));
        assertTrue(Double.isNaN(envelope.getMinZ()));
        assertTrue(Double.isNaN(envelope.getMaxZ()));
        assertTrue(Double.isNaN(envelope.getMinM()));
        assertTrue(Double.isNaN(envelope.getMaxM()));
        assertTrue(envelope.isEmpty());
        assertEquals(Dimension.Two, envelope.getDimension());
        assertEquals("Envelope { minX = NaN, minY = NaN, maxX = NaN, maxY = NaN }", envelope.toString());
        assertEquals(Polygon.createEmpty().toString(), envelope.toGeometry().toString());
    }

    @Test
    public void create2D() {
        Envelope envelope = Envelope.create2D(-122.56, 47.25, -122.41, 47.31);
        assertEquals(-122.56, envelope.getMinX(), 0.01);
        assertEquals(47.25, envelope.getMinY(), 0.01);
        assertEquals(-122.41, envelope.getMaxX(), 0.01);
        assertEquals(47.31, envelope.getMaxY(), 0.01);
        assertTrue(Double.isNaN(envelope.getMinZ()));
        assertTrue(Double.isNaN(envelope.getMaxZ()));
        assertTrue(Double.isNaN(envelope.getMinM()));
        assertTrue(Double.isNaN(envelope.getMaxM()));
        assertFalse(envelope.isEmpty());
        assertEquals(Dimension.Two, envelope.getDimension());
        assertEquals("Envelope { minX = -122.56, minY = 47.25, maxX = -122.41, maxY = 47.31 }", envelope.toString());
        assertEquals("POLYGON ((-122.56 47.25, -122.41 47.25, -122.41 47.31, -122.56 47.31))", envelope.toGeometry().toString());
    }

    @Test
    public void create3D() {
        Envelope envelope = Envelope.create3D(-122.56, 47.25, 54.15, -122.41, 47.31, 100.32);
        assertEquals(-122.56, envelope.getMinX(), 0.01);
        assertEquals(47.25, envelope.getMinY(), 0.01);
        assertEquals(54.15, envelope.getMinZ(), 0.01);
        assertEquals(-122.41, envelope.getMaxX(), 0.01);
        assertEquals(47.31, envelope.getMaxY(), 0.01);
        assertEquals(100.32, envelope.getMaxZ(), 0.01);
        assertTrue(Double.isNaN(envelope.getMinM()));
        assertTrue(Double.isNaN(envelope.getMaxM()));
        assertFalse(envelope.isEmpty());
        assertEquals(Dimension.Three, envelope.getDimension());
        assertEquals("Envelope { minX = -122.56, minY = 47.25, minZ = 54.15, maxX = -122.41, maxY = 47.31, maxZ = 100.32 }", envelope.toString());
    }

    @Test
    public void create2DM() {
        Envelope envelope = Envelope.create2DM(-122.56, 47.25, 54.15, -122.41, 47.31, 100.32);
        assertEquals(-122.56, envelope.getMinX(), 0.01);
        assertEquals(47.25, envelope.getMinY(), 0.01);
        assertEquals(54.15, envelope.getMinM(), 0.01);
        assertEquals(-122.41, envelope.getMaxX(), 0.01);
        assertEquals(47.31, envelope.getMaxY(), 0.01);
        assertEquals(100.32, envelope.getMaxM(), 0.01);
        assertTrue(Double.isNaN(envelope.getMinZ()));
        assertTrue(Double.isNaN(envelope.getMaxZ()));
        assertFalse(envelope.isEmpty());
        assertEquals(Dimension.TwoMeasured, envelope.getDimension());
        assertEquals("Envelope { minX = -122.56, minY = 47.25, minM = 54.15, maxX = -122.41, maxY = 47.31, maxM = 100.32 }", envelope.toString());
    }

    @Test
    public void create3DM() {
        Envelope envelope = Envelope.create3DM(-122.56, 47.25, 12.45, 54.15, -122.41, 47.31, 45.67, 100.32);
        assertEquals(-122.56, envelope.getMinX(), 0.01);
        assertEquals(47.25, envelope.getMinY(), 0.01);
        assertEquals(12.45, envelope.getMinZ(), 0.01);
        assertEquals(54.15, envelope.getMinM(), 0.01);
        assertEquals(-122.41, envelope.getMaxX(), 0.01);
        assertEquals(47.31, envelope.getMaxY(), 0.01);
        assertEquals(45.67, envelope.getMaxZ(), 0.01);
        assertEquals(100.32, envelope.getMaxM(), 0.01);
        assertFalse(envelope.isEmpty());
        assertEquals(Dimension.ThreeMeasured, envelope.getDimension());
        assertEquals("Envelope { minX = -122.56, minY = 47.25, minZ = 12.45, minM = 54.15, maxX = -122.41, maxY = 47.31, maxZ = 45.67, maxM = 100.32 }", envelope.toString());
    }

    @Test
    public void equalsAndHashCode() {
        Envelope envelope1 = Envelope.create2D(-122.56, 47.25, -122.41, 47.31);
        Envelope envelope2 = Envelope.create2D(-122.56, 47.25, -122.41, 47.31);
        Envelope envelope3 = Envelope.create2D(-123.45, 49.87, -124.12, 51.18);
        assertEquals(envelope1, envelope2);
        assertNotEquals(envelope1, envelope3);
        assertNotEquals(envelope2, envelope3);
        assertEquals(envelope1.hashCode(), envelope2.hashCode());
        assertNotEquals(envelope1.hashCode(), envelope3.hashCode());
        assertNotEquals(envelope2.hashCode(), envelope3.hashCode());
    }

}
