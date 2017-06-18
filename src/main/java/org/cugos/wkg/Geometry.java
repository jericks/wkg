package org.cugos.wkg;

import java.util.List;

/**
 * The Abstract base class for all Geometries
 * @author Jared Erickson
 */
public abstract class Geometry {

    /**
     * The SRID
     */
    protected String srid;

    /**
     * The Dimension
     */
    protected final Dimension dimension;

    /**
     * Create a new Geometry with Dimension and SRID
     * @param dimension The Dimension
     * @param srid The SRID
     */
    protected Geometry(Dimension dimension, String srid) {
        this.dimension = dimension;
        this.srid = srid;
    }

    /**
     * Is this Geometry empty?
     * @return Whether the Geometry is empty
     */
    public abstract boolean isEmpty();

    /**
     * Get the number of coordinates
     * @return The number of coordinates
     */
    public abstract int getNumberOfCoordinates();

    /**
     * Get a List of all coordinates
     * @return A List of all coordinates
     */ 
    public abstract List<Coordinate> getCoordinates();

    /**
     * Get the bounding Envelope
     * @return The bounding Envelope
     */
    public Envelope getEnvelope() {
        double minX = Double.NaN;
        double minY = Double.NaN;
        double maxX = Double.NaN;
        double maxY = Double.NaN;
        double minZ = Double.NaN;
        double maxZ = Double.NaN;
        double minM = Double.NaN;
        double maxM = Double.NaN;
        for(Coordinate coordinate : getCoordinates()) {
            if (Double.isNaN(minX)) {
                minX = coordinate.getX();
            } else {
                minX = Math.min(coordinate.getX(), minX);
            }
            if (Double.isNaN(minY)) {
                minY = coordinate.getY();
            } else {
                minY = Math.min(coordinate.getY(), minY);
            }
            if (Double.isNaN(maxX)) {
                maxX = coordinate.getX();
            } else {
                maxX = Math.max(coordinate.getX(), maxX);
            }
            if (Double.isNaN(maxY)) {
                maxY = coordinate.getY();
            } else {
                maxY = Math.max(coordinate.getY(), maxY);
            }
            // Z
            if (Double.isNaN(minZ)) {
                minZ = coordinate.getZ();
            } else {
                minZ = Math.min(coordinate.getZ(), minZ);
            }
            if (Double.isNaN(maxZ)) {
                maxZ = coordinate.getZ();
            } else {
                maxZ = Math.max(coordinate.getZ(), maxZ);
            }
            // M
            if (Double.isNaN(minM)) {
                minM = coordinate.getM();
            } else {
                minM = Math.min(coordinate.getM(), minM);
            }
            if (Double.isNaN(maxM)) {
                maxM = coordinate.getM();
            } else {
                maxM = Math.max(coordinate.getM(), maxM);
            }

        }
        return Envelope.create3DM(minX, minY, minZ, minM, maxX, maxY, maxZ, maxM);
    }

    /**
     * Get the SRID which often is null
     * @return The SRID
     */
    public String getSrid() {
        return srid;
    }
    
    /**
     * Set the SRID for this GeometryType
     * @param srid The SRID
     */ 
    public void setSrid(String srid) {
        this.srid = srid;
    }

    /**
     * Get the Dimension
     * @return The Dimension
     */
    public Dimension getDimension() {
        return dimension;
    }

    /**
     * Write the Geometry to WKT
     * @return The WKT of the Geometry
     */
    @Override
    public String toString() {
        return new WKTWriter().write(this);
    }


}
