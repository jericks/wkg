package org.cugos.wkg;

/**
 * A holder to GeoPackage enums used in the GeoPackageReader and Writer
 */
public class GeoPackage {
    
    public static enum Flag {
        BinaryType((byte) 0x20),
        GeometryEmpty((byte) 0x10),
        EnvelopeIndicator((byte) 0x0e),
        Endianess((byte) 0x01);

        private final byte value;

        Flag(byte value) {
            this.value = value;
        }

        public byte getValue() {
            return this.value;
        }
    }

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
