package org.cugos.wkg;

/**
 * Write a Geometry to an output type
 * @author Jared Erickson
 */
public interface Writer<T> {

    /**
     * Get the name of the Writer
     * @return The name
     */
    String getName();

    /**
     * Write a Geometry
     * @param g The Geometry
     * @return T The output type
     */
    T write(Geometry g);

}
