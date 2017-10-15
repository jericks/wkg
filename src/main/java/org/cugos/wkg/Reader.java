package org.cugos.wkg;

public interface Reader<T> {

    Geometry read(T input);

}
