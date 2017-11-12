package org.cugos.wkg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

public class Writers {

    public static List<Writer> list() {
        List<Writer> writers = new ArrayList<>();
        ServiceLoader<Writer> serviceLoader = ServiceLoader.load(Writer.class);
        Iterator<Writer> iterator = serviceLoader.iterator();
        while(iterator.hasNext()) {
            writers.add(iterator.next());
        }
        return writers;
    }

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