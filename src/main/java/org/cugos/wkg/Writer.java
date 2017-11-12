package org.cugos.wkg;

public interface Writer<T> {

    String getName();

    T write(Geometry g);

}
