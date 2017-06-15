package org.cugos.wkg;

public class Envelope {

    private Coordinate min;

    private Coordinate max;

    public Envelope(Coordinate min, Coordinate max) {
        this.min = min;
        this.max = max;
    }

    public static Envelope createEmpty() {
        return new Envelope(Coordinate.createEmpty(), Coordinate.createEmpty());
    }

    public static Envelope create2D(double minX, double minY, double maxX, double maxY) {
        return new Envelope(Coordinate.create2D(minX, minY), Coordinate.create2D(maxX, maxY));
    }

    public static Envelope create3D(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return new Envelope(Coordinate.create3D(minX, minY, minZ), Coordinate.create3D(maxX, maxY, maxZ));
    }

    public static Envelope create2DM(double minX, double minY, double minM, double maxX, double maxY, double maxM) {
        return new Envelope(Coordinate.create2DM(minX, minY, minM), Coordinate.create2DM(maxX, maxY, maxM));
    }

    public static Envelope create3DM(double minX, double minY, double minZ, double minM, double maxX, double maxY, double maxZ, double maxM) {
        return new Envelope(Coordinate.create3DM(minX, minY, minZ, minM), Coordinate.create3DM(maxX, maxY, maxZ, maxM));
    }

    public double getMinX() {
        return min.getX();
    }

    public double getMinY() {
        return min.getY();
    }

    public double getMaxX() {
        return max.getX();
    }

    public double getMaxY() {
        return max.getY();
    }

    public double getMinZ() {
        return min.getZ();
    }

    public double getMaxZ() {
        return max.getZ();
    }

    public double getMinM() {
        return min.getM();
    }

    public double getMaxM() {
        return max.getM();
    }

    /**
     * Get the Dimension
     * @return The Dimension
     */
    public Dimension getDimension() {
        if (!min.isEmpty()) {
            return min.getDimension();
        } else {
            return max.getDimension();
        }
    }

    /**
     * Determine if the Coordinate is empty (x and y values of NaN)
     * @return Whether this Coordinate is empty
     */
    public boolean isEmpty() {
        return min.isEmpty() && max.isEmpty();
    }

    @Override
    public String toString() {
        String str = "Envelope { ";
        str = str + "minX = " + min.getX() + ", ";
        str = str + "minY = " + min.getY() + ", ";
        if (!Double.isNaN(min.getZ())) {
            str = str + "minZ = " + min.getZ() + ", ";
        }
        if (!Double.isNaN(min.getM())) {
            str = str + "minM = " + min.getM() + ", ";
        }
        str = str + "maxX = " + max.getX() + ", ";
        str = str + "maxY = " + max.getY();
        if (!Double.isNaN(max.getZ())) {

            str = str + ", maxZ = " + max.getZ();
        }
        if (!Double.isNaN(max.getM())) {
            str = str + ", maxM = " + max.getM();
        }
        str = str + " }";
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Envelope envelope = (Envelope) o;

        if (min != null ? !min.equals(envelope.min) : envelope.min != null) return false;
        return max != null ? max.equals(envelope.max) : envelope.max == null;
    }

    @Override
    public int hashCode() {
        int result = min != null ? min.hashCode() : 0;
        result = 31 * result + (max != null ? max.hashCode() : 0);
        return result;
    }
}
