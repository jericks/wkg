package org.cugos.wkg;

import java.util.ArrayList;
import java.util.List;

/**
 * A MultiSurface is a GeometryCollection made up of only Surfaces
 * @author Jared Erickson
 */
public class MultiSurface extends AbstractGeometryCollection<Surface> {

    /**
     * Create a new MultiSurface
     * @param surfaces A List of Surfaces
     * @param dimension The Dimension
     */
    public MultiSurface(List<Surface> surfaces, Dimension dimension) {
        this(surfaces, dimension, null);
    }

    /**
     * Create a new MultiSurface
     * @param surfaces A List of Surfaces
     * @param dimension The Dimension
     * @param srid The SRID
     */
    public MultiSurface(List<Surface> surfaces, Dimension dimension, String srid) {
        super(surfaces, dimension, srid);
    }

    /**
     * Get the List of Surfaces
     * @return The List of Surfaces
     */
    public List<Surface> getSurfaces() {
        return this.geometries;
    }

    /**
     * Create an empty MultiSurface
     * @return An empty MultiSurface
     */
    public static MultiSurface createEmpty() {
        return new MultiSurface(new ArrayList<Surface>(), Dimension.Two, null);
    }

}
