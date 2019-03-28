package org.cugos.wkg;

import java.util.ArrayList;
import java.util.List;

/**
 * An Envelope is a rectangular bounding box
 * @author Jared Erickson
 */
public class Envelope {

    /**
     * The min Coordinate
     */
    private Coordinate min;

    /**
     * The max Coordinate
     */
    private Coordinate max;

    /**
     * Create a new Envelope with a min and max Coordinate
     * @param min The min Coordinate
     * @param max The max Coordinate
     */
    public Envelope(Coordinate min, Coordinate max) {
        this.min = min;
        this.max = max;
    }

    /**
     * Create an empty Envelope
     * @return An empty Envelope
     */
    public static Envelope createEmpty() {
        return new Envelope(Coordinate.createEmpty(), Coordinate.createEmpty());
    }

    /**
     * Create a 2D Envelope
     * @param minX The min X
     * @param minY The min Y
     * @param maxX The max X
     * @param maxY The max Y
     * @return A 2D Envelope
     */
    public static Envelope create2D(double minX, double minY, double maxX, double maxY) {
        return new Envelope(Coordinate.create2D(minX, minY), Coordinate.create2D(maxX, maxY));
    }

    /**
     * Create a 3D Envelope
     * @param minX The min X
     * @param minY The min Y
     * @param minZ The min Z
     * @param maxX The max X
     * @param maxY The max Y
     * @param maxZ The max Z
     * @return A 3D Envelope
     */
    public static Envelope create3D(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return new Envelope(Coordinate.create3D(minX, minY, minZ), Coordinate.create3D(maxX, maxY, maxZ));
    }

    /**
     * Create a 2DM Envelope
     * @param minX The min X
     * @param minY The min Y
     * @param minM The min M
     * @param maxX The max X
     * @param maxY The max Y
     * @param maxM The max M
     * @return A 2DM Envelope
     */
    public static Envelope create2DM(double minX, double minY, double minM, double maxX, double maxY, double maxM) {
        return new Envelope(Coordinate.create2DM(minX, minY, minM), Coordinate.create2DM(maxX, maxY, maxM));
    }

    /**
     * Create a 3DM Envelope
     * @param minX The min X
     * @param minY The min Y
     * @param minZ The min Z
     * @param minM The min M
     * @param maxX The max X
     * @param maxY The max Y
     * @param maxZ The max Z
     * @param maxM The max M
     * @return A 3DM Envelope
     */
    public static Envelope create3DM(double minX, double minY, double minZ, double minM, double maxX, double maxY, double maxZ, double maxM) {
        return new Envelope(Coordinate.create3DM(minX, minY, minZ, minM), Coordinate.create3DM(maxX, maxY, maxZ, maxM));
    }

    /**
     * Get the min X
     * @return The min X
     */
    public double getMinX() {
        return min.getX();
    }

    /**
     * Get the min Y
     * @return The min Y
     */
    public double getMinY() {
        return min.getY();
    }

    /**
     * Get the max X
     * @return The max X
     */
    public double getMaxX() {
        return max.getX();
    }

    /**
     * Get the max Y
     * @return The max Y
     */
    public double getMaxY() {
        return max.getY();
    }

    /**
     * Get the min Z
     * @return The min Z
     */
    public double getMinZ() {
        return min.getZ();
    }

    /**
     * Get the max Z
     * @return The max Z
     */
    public double getMaxZ() {
        return max.getZ();
    }

    /**
     * Get the min M
     * @return The min M
     */
    public double getMinM() {
        return min.getM();
    }

    /**
     * Get the max M
     * @return The max M
     */
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

    /**
     * Convert the Envelope into a Geometry
     * @return A Geometry
     */
    public Geometry toGeometry() {
        if (isEmpty()) {
            return Polygon.createEmpty();
        } else {
            Dimension dimension = Dimension.Two;
            List<Coordinate> coordinates = new ArrayList<>();
            coordinates.add(Coordinate.create2D(min.getX(), min.getY()));
            coordinates.add(Coordinate.create2D(max.getX(), min.getY()));
            coordinates.add(Coordinate.create2D(max.getX(), max.getY()));
            coordinates.add(Coordinate.create2D(min.getX(), max.getY()));
            return new Polygon(new LinearRing(coordinates, dimension), new ArrayList<LinearRing>(), dimension);
        }
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
