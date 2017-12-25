package org.cugos.wkg;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GeoJSONReaderTest {

    @Test
    public void readFeature() {
        GeoJSONReader reader = new GeoJSONReader();
        String json = "{\"type\": \"Feature\", \"properties\": {\"id\": 1, \"name\": \"Tacoma\"}, \"geometry\": {\"type\": \"Point\", \"coordinates\": [122.34, -43.56]}}";
        Geometry expectedGeometry = new Point(Coordinate.create2D(122.34, -43.56), Dimension.Two, "4326");
        Geometry actualGeometry = reader.read(json);
        assertEquals(expectedGeometry.toString(), actualGeometry.toString());
        Map data = (Map) actualGeometry.getData();
        assertNotNull(data);
        assertEquals(1, data.get("id"));
        assertEquals("Tacoma", data.get("name"));
    }

    @Test
    public void readFeatureCollection() {
        GeoJSONReader reader = new GeoJSONReader();
        String json = "{\"type\": \"FeatureCollection\", \"features\": [{\"type\": \"Feature\", \"properties\": {\"id\": 1, \"name\": \"Tacoma\"}, \"geometry\": {\"type\": \"Point\", \"coordinates\": [122.34, -43.56]}}]}";
        List<Geometry> geometries = Arrays.<Geometry>asList(
            new Point(Coordinate.create2D(122.34, -43.56), Dimension.Two, "4326")
        );
        Geometry expectedGeometry = new GeometryCollection(geometries, Dimension.Two, "4326");
        Geometry actualGeometry = reader.read(json);
        assertEquals(expectedGeometry.toString(), actualGeometry.toString());
        Map data = (Map) ((GeometryCollection) actualGeometry).getGeometries().get(0).getData();
        assertNotNull(data);
        assertEquals(1, data.get("id"));
        assertEquals("Tacoma", data.get("name"));
    }

    @Test
    public void readPoint() {
        GeoJSONReader reader = new GeoJSONReader();
        String json = "{\"type\": \"Point\", \"coordinates\": [122.34, -43.56]}";
        Geometry expectedGeometry = new Point(Coordinate.create2D(122.34, -43.56), Dimension.Two, "4326");
        Geometry actualGeometry = reader.read(json);
        assertEquals(expectedGeometry.toString(), actualGeometry.toString());
    }

    @Test
    public void readPoint3D() {
        GeoJSONReader reader = new GeoJSONReader();
        String json = "{\"type\": \"Point\", \"coordinates\": [122.34, -43.56, 12.42]}";
        Geometry expectedGeometry = new Point(Coordinate.create3D(122.34, -43.56, 12.42), Dimension.Three, "4326");
        Geometry actualGeometry = reader.read(json);
        assertEquals(expectedGeometry.toString(), actualGeometry.toString());
    }

    @Test
    public void readPointEmpty() {
        GeoJSONReader reader = new GeoJSONReader();
        String json = "{\"type\": \"Point\", \"coordinates\": []}";
        Geometry expectedGeometry = Point.createEmpty();
        expectedGeometry.setSrid("4326");
        Geometry actualGeometry = reader.read(json);
        assertEquals(expectedGeometry.toString(), actualGeometry.toString());
    }

    @Test
    public void readLineString() {
        GeoJSONReader reader = new GeoJSONReader();
        String json = "{\"type\": \"LineString\", \"coordinates\": [[0.0, 1.0], [10.0, 11.0]]}";
        Geometry expectedGeometry = new LineString(Arrays.asList(
            Coordinate.create2D(0, 1),
            Coordinate.create2D(10, 11)
        ), Dimension.Two, "4326");
        Geometry actualGeometry = reader.read(json);
        assertEquals(expectedGeometry.toString(), actualGeometry.toString());
    }

    @Test
    public void readLineStringEmpty() {
        GeoJSONReader reader = new GeoJSONReader();
        String json = "{\"type\": \"LineString\", \"coordinates\": []}";
        Geometry expectedGeometry = LineString.createEmpty();
        expectedGeometry.setSrid("4326");
        Geometry actualGeometry = reader.read(json);
        assertEquals(expectedGeometry.toString(), actualGeometry.toString());
    }

    @Test
    public void readPolygon() {
        GeoJSONReader reader = new GeoJSONReader();
        String json = "{ \"type\": \"Polygon\", \"coordinates\": [ [[30, 10], [40, 40], [20, 40], [10, 20], [30, 10]] ] }";
        Geometry expectedGeometry = new Polygon(
            new LinearRing(
                Arrays.asList(
                    Coordinate.create2D(30,10),
                    Coordinate.create2D(40,40),
                    Coordinate.create2D(20,40),
                    Coordinate.create2D(10,20),
                    Coordinate.create2D(30,10)
                ), Dimension.Two, "4326"
            ), 
            new ArrayList<LinearRing>(),
            Dimension.Two, "4326"
        );
        Geometry actualGeometry = reader.read(json);
        assertEquals(expectedGeometry.toString(), actualGeometry.toString());
    }

    @Test
    public void readPolygonWithHoles() {
        GeoJSONReader reader = new GeoJSONReader();
        String json = "{\"type\": \"Polygon\", \"coordinates\": [[[35.0, 10.0], [45.0, 45.0], [15.0, 40.0], [10.0, 20.0], [35.0, 10.0]], " +
            "[[20.0, 30.0], [35.0, 35.0], [30.0, 20.0], [20.0, 30.0]]]}";
        Geometry expectedGeometry = new Polygon(
            new LinearRing(
                Arrays.asList(
                    Coordinate.create2D(35,10),
                    Coordinate.create2D(45,45),
                    Coordinate.create2D(15,40),
                    Coordinate.create2D(10,20),
                    Coordinate.create2D(35,10)
                ), Dimension.Two, "4326"
            ),
            Arrays.asList(
                new LinearRing(
                    Arrays.asList(
                        Coordinate.create2D(20,30),
                        Coordinate.create2D(35,35),
                        Coordinate.create2D(30,20),
                        Coordinate.create2D(20,30)
                    ), Dimension.Two, "4326"
                )
            ),
            Dimension.Two, "4326"
        );
        Geometry actualGeometry = reader.read(json);
        assertEquals(expectedGeometry.toString(), actualGeometry.toString());
    }

    @Test
    public void readMultiPoint() {
        GeoJSONReader reader = new GeoJSONReader();
        String json = "{\"type\": \"MultiPoint\", \"coordinates\": [[10.0, 40.0], [40.0, 30.0], [20.0, 20.0], [30.0, 10.0]]}";
        MultiPoint expectedGeometry = new MultiPoint(
            Arrays.asList(
                new Point(Coordinate.create2D(10,40), Dimension.Two),
                new Point(Coordinate.create2D(40,30), Dimension.Two),
                new Point(Coordinate.create2D(20,20), Dimension.Two),
                new Point(Coordinate.create2D(30,10), Dimension.Two)
            ),
            Dimension.Two, "4326"
        );
        Geometry actualGeometry = reader.read(json);
        assertEquals(expectedGeometry.toString(), actualGeometry.toString());
    }

    @Test
    public void readMultiLineString() {
        GeoJSONReader reader = new GeoJSONReader();
        String json = "{\"type\": \"MultiLineString\", \"coordinates\": [[[10.0, 10.0], [20.0, 20.0], [10.0, 40.0]], [[40.0, 40.0], [30.0, 30.0], [40.0, 20.0], [30.0, 10.0]]]}";
        MultiLineString expectedGeometry = new MultiLineString(
            Arrays.asList(
                new LineString(
                    Arrays.asList(
                        Coordinate.create2D(10,10),
                        Coordinate.create2D(20,20),
                        Coordinate.create2D(10,40)
                    ),
                    Dimension.Two
                ),
                new LineString(
                    Arrays.asList(
                        Coordinate.create2D(40,40),
                        Coordinate.create2D(30,30),
                        Coordinate.create2D(40,20),
                        Coordinate.create2D(30,10)
                    ),
                    Dimension.Two
                )
            ),
            Dimension.Two, "4326"
        );
        Geometry actualGeometry = reader.read(json);
        assertEquals(expectedGeometry.toString(), actualGeometry.toString());
    }

    @Test
    public void readMultiPolygon() {
        GeoJSONReader reader = new GeoJSONReader();
        String json = "{\"type\": \"MultiPolygon\", \"coordinates\": [[[[30.0, 20.0], [45.0, 40.0], [10.0, 40.0], [30.0, 20.0]]], " +
            "[[[15.0, 5.0], [40.0, 10.0], [10.0, 20.0], [5.0, 10.0], [15.0, 5.0]]]]}";
        Geometry expectedGeometry = new MultiPolygon(
            Arrays.asList(
                new Polygon(
                    new LinearRing(
                        Arrays.asList(
                            Coordinate.create2D(30,20),
                            Coordinate.create2D(45,40),
                            Coordinate.create2D(10,40),
                            Coordinate.create2D(30,20)
                        ), Dimension.Two, "4326"
                    ),
                    new ArrayList<LinearRing>(),
                    Dimension.Two, "4326"
                ),
                new Polygon(
                    new LinearRing(
                        Arrays.asList(
                            Coordinate.create2D(15,5),
                            Coordinate.create2D(40,10),
                            Coordinate.create2D(10,20),
                            Coordinate.create2D(5,10),
                            Coordinate.create2D(15,5)
                        ), Dimension.Two, "4326"
                    ),
                    new ArrayList<LinearRing>(),
                    Dimension.Two, "4326"
                )
            ),
            Dimension.Two, "4326"
        );
        Geometry actualGeometry = reader.read(json);
        assertEquals(expectedGeometry.toString(), actualGeometry.toString());
    }

    @Test
    public void readMultiPolygonWithHoles() {
        GeoJSONReader reader = new GeoJSONReader();
        String json = "{\"type\": \"MultiPolygon\", \"coordinates\": [[[[40.0, 40.0], [20.0, 45.0], [45.0, 30.0], [40.0, 40.0]]], " +
            "[[[20.0, 35.0], [10.0, 30.0], [10.0, 10.0], [30.0, 5.0], [45.0, 20.0], [20.0, 35.0]], [[30.0, 20.0], [20.0, 15.0], [20.0, 25.0], [30.0, 20.0]]]]}";
        Geometry expectedGeometry = new MultiPolygon(
            Arrays.asList(
                new Polygon(
                    new LinearRing(
                        Arrays.asList(
                            Coordinate.create2D(40,40),
                            Coordinate.create2D(20,45),
                            Coordinate.create2D(45,30),
                            Coordinate.create2D(40,40)
                        ), Dimension.Two, "4326"
                    ),
                    new ArrayList<LinearRing>(),
                    Dimension.Two, "4326"
                ),
                new Polygon(
                    new LinearRing(
                        Arrays.asList(
                            Coordinate.create2D(20,35),
                            Coordinate.create2D(10,30),
                            Coordinate.create2D(10,10),
                            Coordinate.create2D(30,5),
                            Coordinate.create2D(45,20),
                            Coordinate.create2D(20,35)
                        ),
                        Dimension.Two, "4326"
                    ),
                    Arrays.asList(
                        new LinearRing(
                            Arrays.asList(
                                Coordinate.create2D(30,20),
                                Coordinate.create2D(20,15),
                                Coordinate.create2D(20,25),
                                Coordinate.create2D(30,20)
                            ), Dimension.Two, "4326"
                        )
                    ),
                    Dimension.Two, "4326"
                )
            ),
            Dimension.Two, "4326"
        );
        Geometry actualGeometry = reader.read(json);
        assertEquals(expectedGeometry.toString(), actualGeometry.toString());
    }

    @Test
    public void readGeometryCollection() {
        GeoJSONReader reader = new GeoJSONReader();
        String json = "{\"type\": \"GeometryCollection\", \"geometries\": [{\"type\": \"Point\", \"coordinates\": [10.0, 40.0]}, " +
            "{\"type\": \"LineString\", \"coordinates\": [[10.0, 10.0], [20.0, 20.0]]}]}";
        GeometryCollection expectedGeometry = new GeometryCollection(
            Arrays.asList(
                new Point(Coordinate.create2D(10,40), Dimension.Two),
                new LineString(Arrays.asList(
                    Coordinate.create2D(10,10),
                    Coordinate.create2D(20,20)
                ), Dimension.Two)
            ),
            Dimension.Two, "4326"
        );
        Geometry actualGeometry = reader.read(json);
        assertEquals(expectedGeometry.toString(), actualGeometry.toString());
    }

}
