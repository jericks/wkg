package org.cugos.wkg;

import java.util.Arrays;
import java.util.List;

/**
 * A Point is made up of a single Coordinate
 * @author Jared Erickson
 */
public class Point extends Geometry {

    /**
     * The Coordinate
     */
    private final Coordinate coordinate;

    /**
     * Create a new Point
     * @param coordinate The Coordinate
     * @param dimension The Dimension
     */
    public Point(Coordinate coordinate, Dimension dimension) {
        this(coordinate, dimension, null);
    }

    /**
     * Create a new Point
     * @param coordinate The Coordinate
     * @param dimension The Dimension
     * @param srid The SRID
     */
    public Point(Coordinate coordinate, Dimension dimension, String srid) {
        super(dimension, srid);
        this.coordinate = coordinate;
    }

    /**
     * Get the Coordinate
     * @return The Coordinate
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public boolean isEmpty() {
        return this.coordinate == null || this.coordinate.isEmpty();
    }

    @Override
    public int getNumberOfCoordinates() {
        return isEmpty() ? 0 : 1;
    }

    @Override
    public List<Coordinate> getCoordinates() {
        return Arrays.asList(coordinate);
    }

    /**
     * Create an empty Point.
     * @return An empty Point
     */
    public static Point createEmpty() {
        return new Point(Coordinate.createEmpty(), Dimension.Two, null);
    }
}
