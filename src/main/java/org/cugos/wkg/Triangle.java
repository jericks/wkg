package org.cugos.wkg;

import java.util.ArrayList;
import java.util.List;

/**
 * A Triangle is a Polygon where the outer LinearRing is made up of 4 Coordinates
 * @author Jared Erickson
 */
public class Triangle extends Polygon {

    /**
     * Create a new Triangle
     * @param outerLinearRing The outer LinearRing
     * @param innerLinearRings The List of inner LinearRings
     * @param dimension The Dimension
     */
    public Triangle(LinearRing outerLinearRing, List<LinearRing> innerLinearRings, Dimension dimension) {
        this(outerLinearRing, innerLinearRings, dimension, null);
    }

    /**
     * Create a new Triangle
     * @param outerLinearRing The outer LinearRing
     * @param innerLinearRings The List of inner LinearRings
     * @param dimension The Dimension
     * @param srid The SRID
     */
    public Triangle(LinearRing outerLinearRing, List<LinearRing> innerLinearRings, Dimension dimension, String srid) {
        super(outerLinearRing, innerLinearRings, dimension, srid);
    }

    /**
     * Create an empty Triangle
     * @return An empty Triangle
     */
    public static Triangle createEmpty() {
        return new Triangle(LinearRing.createEmpty(), new ArrayList<LinearRing>(), Dimension.Two, null);
    }
}
