package org.cugos.wkg;

import java.util.ArrayList;
import java.util.List;

/**
 * A MultiCurve is a GeometryCollection made up of only Curves
 */
public class MultiCurve extends AbstractGeometryCollection<Curve> {

    /**
     * Create a new MultiCurve
     * @param curves The List of Curves
     * @param dimension The Dimension
     */
    public MultiCurve(List<Curve> curves, Dimension dimension) {
        this(curves, dimension, null);
    }

    /**
     * Create a new MultiCurve
     * @param curves The List of Curves
     * @param dimension The Dimension
     * @param srid The SRID
     */
    public MultiCurve(List<Curve> curves, Dimension dimension, String srid) {
        super(curves, dimension, srid);
    }

    /**
     * Get the List of Curves
     * @return The List of Curves
     */
    public List<Curve> getCurves() {
        return this.geometries;
    }

    /**
     * Create an empty MultiCurve
     * @return An empty MultiCurve
     */
    public static MultiCurve createEmpty() {
        return new MultiCurve(new ArrayList<Curve>(), Dimension.Two, null);
    }
}
