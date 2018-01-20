package org.cugos.wkg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Get a list of Writers or find a Writer by name.
 * Writers uses the Service Provider Interface (SPI) to look up registered Writers
 * @author Jared Erickson
 */
public class Writers {

    /**
     * Get a list of all registered Writers
     * @return A List of Writers
     */
    public static List<Writer> list() {
        List<Writer> writers = new ArrayList<>();
        ServiceLoader<Writer> serviceLoader = ServiceLoader.load(Writer.class);
        Iterator<Writer> iterator = serviceLoader.iterator();
        while(iterator.hasNext()) {
            writers.add(iterator.next());
        }
        return writers;
    }

    /**
     * Find a Writer by name or return null
     * @param name The name
     * @return A Writer or null
     */
    public static Writer find(String name) {
        List<Writer> writers = list();
        for(Writer writer : writers) {
            if (writer.getName().equalsIgnoreCase(name)) {
                return writer;
            }
        }
        return null;
    }

}