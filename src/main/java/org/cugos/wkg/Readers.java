package org.cugos.wkg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

public class Readers {
  
    public static List<Reader> list() {
        List<Reader> readers = new ArrayList<>();
        ServiceLoader<Reader> serviceLoader = ServiceLoader.load(Reader.class);
        Iterator<Reader> iterator = serviceLoader.iterator();
        while(iterator.hasNext()) {
            readers.add(iterator.next());
        }
        return readers;
    }

    public static Reader find(String name) {
        List<Reader> readers = list();
        for(Reader reader : readers) {
            if (reader.getName().equalsIgnoreCase(name)) {
                return reader;
            }
        }
        return null;
    }

}