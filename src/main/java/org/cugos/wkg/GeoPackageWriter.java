package org.cugos.wkg;

import java.nio.ByteBuffer;

/**
 * Write GeoPackage encoded Geometry
 * @author Jared Erickson
 */
public class GeoPackageWriter implements Writer<byte[]> {

    /**
     * The GeoPackage version
     */
    private int version = 0;

    /**
     * The GeoPackage binary type
     */
    private GeoPackage.BinaryType binaryType = GeoPackage.BinaryType.Standard;

    /**
     * The GeoPackage Envelope Type
     */
    private GeoPackage.EnvelopeType envelopType = GeoPackage.EnvelopeType.NoEnvelope;

    /**
     * The Endian flag
     */
    private WKB.Endian endian = WKB.Endian.Big;

    /**
     * The WKBWriter
     */
    private WKBWriter wkbWriter;

    /**
     * Create a new GeoPackageWriter that uses Big Endian and encodes the Envelope without MZ values
     */
    public GeoPackageWriter() {
        this(WKB.Endian.Big, GeoPackage.EnvelopeType.Envelope);
    }

    /**
     * Create a new GeopackageWriter
     * @param endian The Endian flag
     * @param envelopeType The EnvelopeType
     */
    public GeoPackageWriter(WKB.Endian endian, GeoPackage.EnvelopeType envelopeType) {
        this.endian = endian;
        this.envelopType = envelopeType;
        this.wkbWriter = new WKBWriter(WKB.Type.EWKB, endian);
    }

    /**
     * Write the Geometry to a hex String
     * @param geometry The Geometry
     * @return A WKB hex String
     */
    public String writeToHex(Geometry geometry) {
        return toHex(write(geometry));
    }

    /**
     * Write a Geometry to an array of bytes
     * @param g The Geometry
     * @return An array of bytes
     */
    @Override
    public byte[] write(Geometry g) {
        //System.out.println(calculateNumberOfBytes(g));
        ByteBuffer buffer = ByteBuffer.allocate(calculateNumberOfBytes(g));
        // Magic (2)
        buffer.put((byte)'G');
        buffer.put((byte)'P');
        // Version (1)
        buffer.put((byte)version);
        // Flags (1)
        byte flag = 0;
        flag |= ((((byte) envelopType.getValue() << 1) & GeoPackage.Flag.EnvelopeIndicator.getValue()));
        flag |= ((byte) binaryType.getValue()) & GeoPackage.Flag.BinaryType.getValue();
        flag |= ((byte) (g.isEmpty() ? GeoPackage.GeometryEmptyType.Empty.getValue() : GeoPackage.GeometryEmptyType.NotEmpty.getValue())) & GeoPackage.Flag.GeometryEmpty.getValue();
        flag |= ((byte) endian.getValue()) & GeoPackage.Flag.Endianess.getValue();
        buffer.put(flag);
        // SRS ID (4)
        buffer.putInt(g.getSrid() != null ? Integer.parseInt(g.getSrid()) : -1);
        // Envelope (8 * 4)
        if (envelopType != GeoPackage.EnvelopeType.NoEnvelope) {
            Envelope envelope = g.getEnvelope();
            buffer.putDouble(envelope.getMinX());
            buffer.putDouble(envelope.getMaxX());
            buffer.putDouble(envelope.getMinY());
            buffer.putDouble(envelope.getMaxY());
            if (envelopType == GeoPackage.EnvelopeType.EnvelopeZ || envelopType == GeoPackage.EnvelopeType.EnvelopeZM) {
                buffer.putDouble(envelope.getMinZ());
                buffer.putDouble(envelope.getMaxZ());
            }
            if (envelopType == GeoPackage.EnvelopeType.EnvelopeM || envelopType == GeoPackage.EnvelopeType.EnvelopeZM) {
                buffer.putDouble(envelope.getMinM());
                buffer.putDouble(envelope.getMaxM());
            }
        }
        // WKBGeometry
        wkbWriter.putGeometry(buffer, g);
        return buffer.array();
    }

    @Override
    public String getName() {
        return "GeoPackage";
    }

    protected int calculateNumberOfBytes(Geometry g) {
        return wkbWriter.calculateNumberOfBytes(g)
            + 2 // magic
            + 1 // version
            + 1 // flags
            + 4 // srid
            + calculateNumberOfBytes(envelopType);
    }

    private int calculateNumberOfBytes(GeoPackage.EnvelopeType envelopeType) {
        if (envelopeType == GeoPackage.EnvelopeType.NoEnvelope) {
            return 0;
        } else if (envelopeType == GeoPackage.EnvelopeType.Envelope) {
            return 8 * 4;
        } else if (envelopeType == GeoPackage.EnvelopeType.EnvelopeZ) {
            return 8 * 6;
        } else if (envelopeType == GeoPackage.EnvelopeType.EnvelopeM) {
            return 8 * 6;
        } else if (envelopeType == GeoPackage.EnvelopeType.EnvelopeZM) {
            return 8 * 8;
        } else {
            return 0;
        }
    }

    /**
     * The hex array
     */
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    /**
     * Convert an array of bytes to a hex String.
     * @param bytes The array of bytes
     * @return The hex String
     */
    private static String toHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

}
