package org.cugos.wkg;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class WritersTest {

    @Test
    public void list() {
        List<Writer> writers = Writers.list();
        assertFalse(writers.isEmpty());
    }

    @Test
    public void find() {
        assertNotNull(Writers.find("GeoJSON"));
        assertNotNull(Writers.find("wkt"));
        assertNotNull(Writers.find("WKB"));
        assertNotNull(Writers.find("Geopackage"));
        assertNull(Writers.find("csv"));
    }

    @Test
    public void findAndUse() {
        Point point = new Point(Coordinate.create2D(-122.45, 42.62), Dimension.Two, "4326");
        assertEquals("SRID=4326;POINT (-122.45 42.62)", Writers.find("WKT").write(point));
        assertEquals("{\"type\": \"Point\", \"coordinates\": [-122.45, 42.62]}", Writers.find("GeoJSON").write(point));
    }

}
