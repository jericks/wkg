package org.cugos.wkg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A CompoundCurve contains one or more connected Curves
 * @author Jared Erickson
 */
public class CompoundCurve extends Curve {

    /**
     * The list of Curves
     */
    private final List<Curve> curves;

    /**
     * Create a new CompoundCurve
     * @param curves The list of Curves
     * @param dimension The Dimension
     */
    public CompoundCurve(List<Curve> curves, Dimension dimension) {
        this(curves, dimension, null);
    }

    /**
     * Create a new CompoundCurve
     * @param curves The list of Curves
     * @param dimension The Dimension
     * @param srid The SRID
     */
    public CompoundCurve(List<Curve> curves, Dimension dimension, String srid) {
        super(dimension, srid);
        this.curves = Collections.unmodifiableList(curves);
    }

    /**
     * Get a List of Curves
     * @return The List of Curves
     */
    public List<Curve> getCurves() {
        return curves;
    }

    @Override
    public boolean isEmpty() {
        return curves.isEmpty();
    }

    @Override
    public int getNumberOfCoordinates() {
        int numberOfCoordinates = 0;
        for(Curve curve : curves) {
            numberOfCoordinates += curve.getNumberOfCoordinates();
        }
        return numberOfCoordinates;
    }

    @Override
    public List<Coordinate> getCoordinates() {
        List<Coordinate> coordinates = new ArrayList<Coordinate>();
        for(Curve curve : curves) {
            coordinates.addAll(curve.getCoordinates());
        }
        return Collections.unmodifiableList(coordinates);
    }

    /**
     * Create an empty CompoundCurve
     * @return An empty CompoundCurve
     */
    public static CompoundCurve createEmpty() {
        return new CompoundCurve(new ArrayList<Curve>(), Dimension.Two, null);
    }
}
