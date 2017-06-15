package org.cugos.wkg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A GeometryCollection is made up of other Geometries
 * @author Jared Erickson
 */
public class GeometryCollection extends AbstractGeometryCollection<Geometry> {

    /**
     * Create a new GeometryCollection
     * @param geometries The List of Geometries
     * @param dimension The Dimension
     */
    public GeometryCollection(List<Geometry> geometries, Dimension dimension) {
        this(geometries, dimension, null);
    }

    /**
     * Create a new GeometryCollection
     * @param geometries The List of Geometries
     * @param dimension The Dimension
     * @param srid The SRID
     */
    public GeometryCollection(List<Geometry> geometries, Dimension dimension, String srid) {
        super(geometries, dimension, srid);
    }

    /**
     * Get the List of Geometries
     * @return The List of Geometries
     */
    public List<Geometry> getGeometries() {
        return geometries;
    }

    /**
     * Create an empty GeometryCollection
     * @return An empty GeometryCollection
     */
    public static GeometryCollection createEmpty() {
        return new GeometryCollection(new ArrayList<Geometry>(), Dimension.Two, null);
    }
}
