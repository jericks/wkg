package org.cugos.wkg;

/**
 * Read a Geometry from an input type
 * @author Jared Erickson
 */
public interface Reader<T> {

    /**
     * Get the name of the Reader
     * @return The name
     */
    String getName();

    /**
     * Read a Geometry from the input
     * @param input The input
     * @return A Geometry
     */
    Geometry read(T input);

}
