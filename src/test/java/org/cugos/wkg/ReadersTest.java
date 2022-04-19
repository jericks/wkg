package org.cugos.wkg;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ReadersTest {

    @Test
    public void list() {
        List<Reader> readers = Readers.list();
        assertFalse(readers.isEmpty());
    }

    @Test
    public void find() {
        assertNotNull(Readers.find("GeoJSON"));
        assertNotNull(Readers.find("wkt"));
        assertNotNull(Readers.find("WKB"));
        assertNotNull(Readers.find("Geopackage"));
        assertNull(Readers.find("csv"));
    }

    @Test
    public void findAndUse() {
        Point point = new Point(Coordinate.create2D(-122.45, 42.62), Dimension.Two, "4326");
        assertEquals(point.toString(), Readers.find("WKT").read("SRID=4326;POINT (-122.45 42.62)").toString());
        assertEquals(point.toString(), Readers.find("GeoJSON").read("{\"type\": \"Point\", \"coordinates\": [-122.45, 42.62]}").toString());
    }
}
