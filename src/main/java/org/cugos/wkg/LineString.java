package org.cugos.wkg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A LineString is a Curve with only straight lines
 * @author Jared Erickson
 */
public class LineString extends Curve {

    /**
     * The List of Coordinates
     */
    private final List<Coordinate> coordinates;

    /**
     * Create a new LineString
     * @param coordinates The List of Coordinates
     * @param dimension The Dimension
     */
    public LineString(List<Coordinate> coordinates, Dimension dimension) {
        this(coordinates, dimension, null);
    }

    /**
     * Create a new LineString
     * @param coordinates The List of Coordinates
     * @param dimension The Dimension
     * @param srid The SRID
     */
    public LineString(List<Coordinate> coordinates, Dimension dimension, String srid) {
        super(dimension, srid);
        this.coordinates = Collections.unmodifiableList(coordinates);
    }

    /**
     * Get the List of Coordinates
     * @return The List of Coordinates
     */
    @Override
    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    @Override
    public boolean isEmpty() {
        return coordinates.isEmpty();
    }

    @Override
    public int getNumberOfCoordinates() {
        return this.getCoordinates().size();
    }

    /**
     * Create an empty LineString
     * @return An empty LineString
     */
    public static LineString createEmpty() {
        return new LineString(new ArrayList<Coordinate>(), Dimension.Two, null);
    }
}
