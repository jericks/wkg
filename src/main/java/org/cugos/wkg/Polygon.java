package org.cugos.wkg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A Polygon is a Surface made up of an outer LinearRing and zero or more inner LinearRings.
 * @author Jared Erickson
 */
public class Polygon extends Surface {

    /**
     * The outer LinearRing
     */
    private final LinearRing outerLinearRing;

    /**
     * The List of inner LinearRings
     */
    private final List<LinearRing> innerLinearRings;

    /**
     * Create a new Polygon
     * @param outerLinearRing The outer LinearRing
     * @param innerLinearRings The List of inner LinearRings
     * @param dimension The Dimension
     */
    public Polygon(LinearRing outerLinearRing, List<LinearRing> innerLinearRings, Dimension dimension) {
        this(outerLinearRing, innerLinearRings, dimension, null);
    }

    /**
     * Create a new Polygon
     * @param outerLinearRing The outer LinearRing
     * @param innerLinearRings The List of inner LinearRings
     * @param dimension The Dimension
     * @param srid The SRID
     */
    public Polygon(LinearRing outerLinearRing, List<LinearRing> innerLinearRings, Dimension dimension, String srid) {
        super(dimension, srid);
        this.outerLinearRing = outerLinearRing;
        this.innerLinearRings = Collections.unmodifiableList(innerLinearRings);
    }

    /**
     * Get the outer LinearRing
     * @return The outer LinearRing
     */
    public LinearRing getOuterLinearRing() {
        return outerLinearRing;
    }

    /**
     * Get the List of inner LinearRings
     * @return The List of inner LinearRings
     */
    public List<LinearRing> getInnerLinearRings() {
        return innerLinearRings;
    }

    @Override
    public boolean isEmpty() {
        return outerLinearRing == null | outerLinearRing.isEmpty();
    }

    @Override
    public int getNumberOfCoordinates() {
        int numberOfCoordinates = outerLinearRing.getNumberOfCoordinates();
        for(LinearRing linearRing : innerLinearRings) {
            numberOfCoordinates += linearRing.getNumberOfCoordinates();
        }
        return numberOfCoordinates;
    }

    @Override
    public List<Coordinate> getCoordinates() {
        List<Coordinate> coordinates = new ArrayList<Coordinate>();
        coordinates.addAll(outerLinearRing.getCoordinates());
        for(LinearRing linearRing : innerLinearRings) {
            coordinates.addAll(linearRing.getCoordinates());
        }
        return Collections.unmodifiableList(coordinates);
    }

    /**
     * Create an empty Polygon
     * @return An empty Polygon
     */
    public static Polygon createEmpty() {
        return new Polygon(LinearRing.createEmpty(), new ArrayList<LinearRing>(), Dimension.Two, null);
    }
}
