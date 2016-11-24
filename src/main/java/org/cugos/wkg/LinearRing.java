package org.cugos.wkg;

import java.util.ArrayList;
import java.util.List;

/**
 * A LinearRing is a LineString that is closed
 * @author Jared Erickson
 */
public class LinearRing extends LineString {

    /**
     * Create a new LinearRing
     * @param coordinates The List of Coordinates.
     * @param dimension The Dimension
     */
    public LinearRing(List<Coordinate> coordinates, Dimension dimension) {
        this(coordinates, dimension, null);
    }

    /**
     * Create a new LinearRing
     * @param coordinates The List of Coordinates.
     * @param dimension The Dimension
     * @param srid The SRID
     */
    public LinearRing(List<Coordinate> coordinates, Dimension dimension, String srid) {
        super(coordinates, dimension, srid);
    }

    @Override
    public int getNumberOfCoordinates() {
        return this.getCoordinates().size();
    }

    /**
     * Create an empty LinearRing
     * @return An empty LinearRing
     */
    public static LinearRing createEmpty() {
        return new LinearRing(new ArrayList<Coordinate>(), Dimension.Two, null);
    }

}
