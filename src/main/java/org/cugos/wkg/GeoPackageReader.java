package org.cugos.wkg;

import java.nio.ByteBuffer;

/**
 * Read GeoPackage encoded Geometry
 */
public class GeoPackageReader implements Reader<byte[]> {

    /**
     * The WKBReader
     */
    private final WKBReader wkbReader = new WKBReader();

    /**
     * Read a Geometry from an array of bytes.
     * @param bytes The array of bytes
     * @return A Geometry or null
     */
    @Override
    public Geometry read(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        return read(buffer, true).getGeometry();
    }

    @Override
    public String getName() {
        return "GeoPackage";
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
    private GeoPackageGeometry read(ByteBuffer buffer, boolean shouldReadGeometry) {

        GeoPackageGeometry geoPackageGeometry = new GeoPackageGeometry();

        // Magic
        byte[] magic = new byte[2];
        buffer.get(magic);

        // Version
        int version = buffer.get();
        geoPackageGeometry.setVersion(version);

        // Flags
        byte flags = buffer.get();

        int geoPackageBinaryType = flags & GeoPackage.Flag.BinaryType.getValue();
        GeoPackage.BinaryType binaryType = GeoPackage.BinaryType.get(geoPackageBinaryType);
        geoPackageGeometry.setBinaryType(binaryType);

        int emptyGeometryFlag = flags & GeoPackage.Flag.GeometryEmpty.getValue();
        GeoPackage.GeometryEmptyType geometryEmptyType = GeoPackage.GeometryEmptyType.get(emptyGeometryFlag);
        geoPackageGeometry.setGeometryEmptyType(geometryEmptyType);

        int envelopeTypeFlag = (flags & GeoPackage.Flag.EnvelopeIndicator.getValue()) >> 1;
        GeoPackage.EnvelopeType envelopeType = GeoPackage.EnvelopeType.get(envelopeTypeFlag);
        geoPackageGeometry.setEnvelopeType(envelopeType);

        int byteOrder = flags & GeoPackage.Flag.Endianess.getValue();
        WKB.Endian endian = WKB.Endian.get(byteOrder);
        geoPackageGeometry.setEndian(endian);

        // SRS ID
        int srsId = buffer.getInt();
        geoPackageGeometry.setSrsId(srsId);

        //Envelope
        Envelope envelope = null;
        if (envelopeType != GeoPackage.EnvelopeType.NoEnvelope) {
            double minX = buffer.getDouble();
            double maxX = buffer.getDouble();
            double minY = buffer.getDouble();
            double maxY = buffer.getDouble();
            double minZ = Double.NaN;
            double maxZ = Double.NaN;
            double minM = Double.NaN;
            double maxM = Double.NaN;
            if (envelopeType == GeoPackage.EnvelopeType.EnvelopeZ || envelopeType == GeoPackage.EnvelopeType.EnvelopeZM) {
                minZ = buffer.getDouble();
                maxZ = buffer.getDouble();
            }
            if (envelopeType == GeoPackage.EnvelopeType.EnvelopeM || envelopeType == GeoPackage.EnvelopeType.EnvelopeZM) {
                minM = buffer.getDouble();
                maxM = buffer.getDouble();
            }
            envelope = Envelope.create3DM(minX, minY, minZ, minM, maxX, maxY, maxZ, maxM);
        }
        geoPackageGeometry.setEnvelope(envelope);

        // WKBGeometry
        if (shouldReadGeometry) {
            Geometry geometry = wkbReader.read(buffer);
            geometry.setSrid(String.valueOf(srsId));
            geoPackageGeometry.setGeometry(geometry);
        }

        return geoPackageGeometry;
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

    /**
     * A GeoPackageGeometry holds the GeoPackage header information plus a Geometry
     */
    private static class GeoPackageGeometry {

        private int version;

        private GeoPackage.BinaryType binaryType;

        private GeoPackage.GeometryEmptyType geometryEmptyType;

        private GeoPackage.EnvelopeType envelopeType;

        private WKB.Endian endian;

        private int srsId;

        private Envelope envelope;

        private Geometry geometry;

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public GeoPackage.BinaryType getBinaryType() {
            return binaryType;
        }

        public void setBinaryType(GeoPackage.BinaryType binaryType) {
            this.binaryType = binaryType;
        }

        public GeoPackage.GeometryEmptyType getGeometryEmptyType() {
            return geometryEmptyType;
        }

        public void setGeometryEmptyType(GeoPackage.GeometryEmptyType geometryEmptyType) {
            this.geometryEmptyType = geometryEmptyType;
        }

        public GeoPackage.EnvelopeType getEnvelopeType() {
            return envelopeType;
        }

        public void setEnvelopeType(GeoPackage.EnvelopeType envelopeType) {
            this.envelopeType = envelopeType;
        }

        public WKB.Endian getEndian() {
            return endian;
        }

        public void setEndian(WKB.Endian endian) {
            this.endian = endian;
        }

        public int getSrsId() {
            return srsId;
        }

        public void setSrsId(int srsId) {
            this.srsId = srsId;
        }

        public Envelope getEnvelope() {
            return envelope;
        }

        public void setEnvelope(Envelope envelope) {
            this.envelope = envelope;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }
    }

}
