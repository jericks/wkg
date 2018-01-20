package org.cugos.wkg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Get a list of Readers or find a Reader by name.
 * Readers uses the Service Provider Interface (SPI) to look up registered Readers
 * @author Jared Erickson
 */

public class Readers {
  
    /**
     * Get a list of all registered Readers
     * @return A List of Readers
     */
    public static List<Reader> list() {
        List<Reader> readers = new ArrayList<>();
        ServiceLoader<Reader> serviceLoader = ServiceLoader.load(Reader.class);
        Iterator<Reader> iterator = serviceLoader.iterator();
        while(iterator.hasNext()) {
            readers.add(iterator.next());
        }
        return readers;
    }

    /**
     * Find a Reader by name or return null
     * @param name The name
     * @return A Reader or null
     */
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