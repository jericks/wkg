package org.cugos.wkg;

import java.nio.ByteBuffer;

public class GeoPackageWriter {

    private int version = 0;

    private GeoPackage.BinaryType binaryType = GeoPackage.BinaryType.Standard;

    private GeoPackage.EnvelopeType envelopType = GeoPackage.EnvelopeType.NoEnvelope;

    private WKB.Endian endian = WKB.Endian.Big;

    private WKBWriter wkbWriter;

    public GeoPackageWriter() {
        this.wkbWriter = new WKBWriter(WKB.Type.WKB, endian);
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
    public byte[] write(Geometry g) {
        System.out.println(calculateNumberOfBytes(g));
        ByteBuffer buffer = ByteBuffer.allocate(calculateNumberOfBytes(g));
        // Magic (2)
        buffer.put((byte)'G');
        buffer.put((byte)'P');
        // Version (1)
        buffer.put((byte)version);
        // Flags (1)
        byte flag = 0;
        flag |= ((((byte) envelopType.getValue()) & GeoPackage.FLAG_ENVELOPE_INDICATOR) << 1);
        flag |= ((byte) binaryType.getValue()) & GeoPackage.FLAG_GEOPACKGE_BINARY_TYPE;
        flag |= ((byte) (g.isEmpty() ? GeoPackage.GeometryEmptyType.Empty.getValue() : GeoPackage.GeometryEmptyType.NotEmpty.getValue())) & GeoPackage.FLAG_GEOMETRY_EMPTY;
        flag |= ((byte) endian.getValue()) & GeoPackage.FLAG_ENDIANSESS;
        buffer.put(flag);
        // SRS ID (4)
        buffer.putInt(Integer.parseInt(g.getSrid()));
        // Envelope (8 * 4)
        // @TODO Needs Envelope support
        // WKBGeometry
        wkbWriter.putGeometry(buffer, g);
        return buffer.array();
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

    public static void main(String[] args) {

        Geometry[] geometries = {
            new Point(new Coordinate(-122, 47), Dimension.Two, "4326")
        };

        GeoPackageWriter writer = new GeoPackageWriter();
        GeoPackageReader reader = new GeoPackageReader();

        for(Geometry g : geometries) {
            System.out.println(g);
            String hex = writer.writeToHex(g);
            System.out.println(hex);
            Geometry g2 = reader.read(hex);
            System.out.println(g2);
            System.out.println("--------------------");
        }


    }
}
