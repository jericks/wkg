package org.cugos.wkg;

import java.nio.ByteBuffer;

public class GeoPackageReader {


    private final WKBReader wkbReader = new WKBReader();

    /**
     * Read a Geometry from an array of bytes.
     * @param bytes The array of bytes
     * @return A Geometry or null
     */
    public Geometry read(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        return read(buffer);
    }

    /**
     * Read a Geometry from a hex String
     * @param hex The hex String
     * @return A Geometry or null
     */
    public Geometry read(String hex) {
        return read(toBytes(hex));
    }

    /**
     * Read a Geometry from a ByteBuffer
     * @param buffer The ByteBuffer
     * @return A Geometry or null
     */
    private Geometry read(ByteBuffer buffer) {

        // Magic
        byte[] magic = new byte[2];
        buffer.get(magic);
        System.out.println("Magic = " + new String(magic));

        // Version
        int version = buffer.get();
        System.out.println("Version = " + version);

        // Flags
        byte flags = buffer.get();

        int geoPackageBinaryType = flags & GeoPackage.FLAG_GEOPACKGE_BINARY_TYPE;
        GeoPackage.BinaryType binaryType = GeoPackage.BinaryType.get(geoPackageBinaryType);
        System.out.println("GeoPackage Binary Type = " + geoPackageBinaryType);

        int emptyGeometryFlag = flags & GeoPackage.FLAG_GEOMETRY_EMPTY;
        System.out.println("Geometry Flag = " + emptyGeometryFlag);
        GeoPackage.GeometryEmptyType geometryEmptyType = GeoPackage.GeometryEmptyType.get(emptyGeometryFlag);

        int envelopeTypeFlag = (flags & GeoPackage.FLAG_ENVELOPE_INDICATOR) >> 1;
        System.out.println("Envelope Flag = " + envelopeTypeFlag);
        GeoPackage.EnvelopeType envelopeType = GeoPackage.EnvelopeType.get(envelopeTypeFlag);

        int byteOrder = flags & GeoPackage.FLAG_ENDIANSESS;
        WKB.Endian endian = WKB.Endian.get(byteOrder);
        System.out.println("Byte Order = " + byteOrder);

        // SRS ID
        int srsId = buffer.getInt();
        System.out.println("SRS ID = " + srsId);

        //Envelope
        if (envelopeType != GeoPackage.EnvelopeType.NoEnvelope) {
            double minX = buffer.getDouble();
            double minY = buffer.getDouble();
            double maxX = buffer.getDouble();
            double maxY = buffer.getDouble();
            System.out.println("Envelope = " + minX + ", " + minY + ", " + maxX + ", " + maxY);
            if (envelopeType == GeoPackage.EnvelopeType.EnvelopeZ) {
                double minZ = buffer.getDouble();
                double maxZ = buffer.getDouble();
                System.out.println("   Z = " + minX + ", " + maxZ);
            } else if (envelopeType == GeoPackage.EnvelopeType.EnvelopeM) {
                double minM = buffer.getDouble();
                double maxM = buffer.getDouble();
                System.out.println("   M = " + minM + ", " + maxM);
            } else if (envelopeType == GeoPackage.EnvelopeType.EnvelopeZM) {
                double minZ = buffer.getDouble();
                double maxZ = buffer.getDouble();
                double minM = buffer.getDouble();
                double maxM = buffer.getDouble();
                System.out.println("   Z = " + minX + ", " + maxZ);
                System.out.println("   M = " + minM + ", " + maxM);
            }
        }

        // WKBGeometry
        Geometry geometry = wkbReader.read(buffer);
        System.out.println("Geometry = " + geometry);

        return geometry;
    }

    /**
     * Convert a hex String into a byte Array
     * @param hexString The hex String
     * @return An array of bytes
     */
    private static byte[] toBytes(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                + Character.digit(hexString.charAt(i+1), 16));
        }
        return data;
    }

    public static void main(String[] args) {
        GeoPackageReader reader = new GeoPackageReader();
        reader.read("47500002000010e6403f333302b582c0403f333302b582c0c03a777784cc9dc0c03a777784cc9dc00000000001403f333302b582c0c03a777784cc9dc0");
    }

}
