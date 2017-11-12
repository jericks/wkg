package org.cugos.wkg;

public interface Reader<T> {

    String getName();

    Geometry read(T input);

}
