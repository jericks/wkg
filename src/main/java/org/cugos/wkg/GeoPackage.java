package org.cugos.wkg;

public class GeoPackage {

    public static final byte FLAG_GEOPACKGE_BINARY_TYPE = (byte) 0x20;
    public static final byte FLAG_GEOMETRY_EMPTY = (byte) 0x10;
    public static final byte FLAG_ENVELOPE_INDICATOR = (byte) 0x0e;
    public static final byte FLAG_ENDIANSESS = (byte) 0x01;


    public static enum BinaryType {
        Standard(0),
        Extended (1);

        private final int value;

        BinaryType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public static BinaryType get(int value) {
            for(BinaryType binaryType : values()) {
                if (binaryType.getValue() == value) {
                    return binaryType;
                }
            }
            return null;
        }
    }

    public static enum GeometryEmptyType {
        Empty(0),
        NotEmpty (1);

        private final int value;

        GeometryEmptyType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public static GeometryEmptyType get(int value) {
            for(GeometryEmptyType geometryEmptyType : values()) {
                if (geometryEmptyType.getValue() == value) {
                    return geometryEmptyType;
                }
            }
            return null;
        }
    }

    public static enum EnvelopeType {
        NoEnvelope(0),
        Envelope(1),
        EnvelopeZ(2),
        EnvelopeM(3),
        EnvelopeZM(4);

        private final int value;

        EnvelopeType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public static EnvelopeType get(int value) {
            for(EnvelopeType envelopeType : values()) {
                if (envelopeType.getValue() == value) {
                    return envelopeType;
                }
            }
            return null;
        }
    }

}
