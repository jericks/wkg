package org.cugos.wkg;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class GeoJSONWriterTest {

    @Test
    public void writeFeatureWithProperties() {
        GeoJSONWriter writer = new GeoJSONWriter();
        Point point = new Point(Coordinate.create2D(122.34, -43.56), Dimension.Two);
        Map<String, Object> properties = new TreeMap<>();
        properties.put("id", 1);
        properties.put("name", "Seattle");
        point.setData(properties);
        String json = writer.writeFeature(point);
        assertEquals("{\"type\": \"Feature\", \"properties\": {\"id\": \"1\", \"name\": \"Seattle\"}, \"geometry\": {\"type\": \"Point\", \"coordinates\": [122.34, -43.56]}}", json);
    }

    @Test
    public void writeFeature() {
        GeoJSONWriter writer = new GeoJSONWriter();
        Point point = new Point(Coordinate.create2D(122.34, -43.56), Dimension.Two);
        String json = writer.writeFeature(point);
        assertEquals("{\"type\": \"Feature\", \"properties\": {}, \"geometry\": {\"type\": \"Point\", \"coordinates\": [122.34, -43.56]}}", json);
    }

    @Test
    public void writeFeatureCollection() {
        GeoJSONWriter writer = new GeoJSONWriter();
        Point point = new Point(Coordinate.create2D(122.34, -43.56), Dimension.Two);
        String json = writer.writeFeatureCollection(point);
        assertEquals("{\"type\": \"FeatureCollection\", \"features\": [{\"type\": \"Feature\", \"properties\": {}, \"geometry\": {\"type\": \"Point\", \"coordinates\": [122.34, -43.56]}}]}", json);
    }

    @Test
    public void writeFeatureCollectionWithProperties() {
        GeoJSONWriter writer = new GeoJSONWriter();
        Point point = new Point(Coordinate.create2D(122.34, -43.56), Dimension.Two);
        Map<String, Object> properties = new TreeMap<>();
        properties.put("id", 1);
        properties.put("name", "Seattle");
        point.setData(properties);
        String json = writer.writeFeatureCollection(point);
        assertEquals("{\"type\": \"FeatureCollection\", \"features\": [{\"type\": \"Feature\", \"properties\": {\"id\": \"1\", \"name\": \"Seattle\"}, \"geometry\": {\"type\": \"Point\", \"coordinates\": [122.34, -43.56]}}]}", json);
    }

    @Test
    public void writePoint() {
        GeoJSONWriter writer = new GeoJSONWriter();
        Point point = new Point(Coordinate.create2D(122.34, -43.56), Dimension.Two);
        String json = writer.write(point);
        assertEquals("{\"type\": \"Point\", \"coordinates\": [122.34, -43.56]}", json);
    }

    @Test
    public void writePointZ() {
        GeoJSONWriter writer = new GeoJSONWriter();
        Point point = new Point(Coordinate.create3D(122.34, -43.56, 56.7), Dimension.Three);
        String json = writer.write(point);
        assertEquals("{\"type\": \"Point\", \"coordinates\": [122.34, -43.56, 56.7]}", json);
    }

    @Test
    public void writePointZM() {
        GeoJSONWriter writer = new GeoJSONWriter();
        Point point = new Point(Coordinate.create3DM(122.34, -43.56, 56.7, 12.2), Dimension.ThreeMeasured);
        String json = writer.write(point);
        assertEquals("{\"type\": \"Point\", \"coordinates\": [122.34, -43.56, 56.7]}", json);
    }

    @Test
    public void writeEmptyPoint() {
        GeoJSONWriter writer = new GeoJSONWriter();
        Point point = Point.createEmpty();
        String json = writer.write(point);
        assertEquals("{\"type\": \"Point\", \"coordinates\": []}", json);
    }

    @Test
    public void writeLineString() {
        GeoJSONWriter writer = new GeoJSONWriter();
        LineString lineString = new LineString(Arrays.asList(
            Coordinate.create2D(0, 1),
            Coordinate.create2D(10, 11)
        ), Dimension.Two);
        String json = writer.write(lineString);
        assertEquals("{\"type\": \"LineString\", \"coordinates\": [[0.0, 1.0], [10.0, 11.0]]}", json);
    }

    @Test
    public void writePolygon() {
        GeoJSONWriter writer = new GeoJSONWriter();
        Geometry polygon = new Polygon(
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
        String json = writer.write(polygon);
        assertEquals("{\"type\": \"Polygon\", \"coordinates\": [[[30.0, 10.0], [40.0, 40.0], [20.0, 40.0], [10.0, 20.0], [30.0, 10.0]]]}", json);
    }

    @Test
    public void writePolygonWithHoles() {
        GeoJSONWriter writer = new GeoJSONWriter();
        Geometry polygon = new Polygon(
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
        String json = writer.write(polygon);
        assertEquals("{\"type\": \"Polygon\", \"coordinates\": [[[35.0, 10.0], [45.0, 45.0], [15.0, 40.0], [10.0, 20.0], [35.0, 10.0]], " +
            "[[20.0, 30.0], [35.0, 35.0], [30.0, 20.0], [20.0, 30.0]]]}", json);
    }

    @Test
    public void writeMultiPoint() {
        GeoJSONWriter writer = new GeoJSONWriter();
        MultiPoint multiPoint = new MultiPoint(
            Arrays.asList(
                new Point(Coordinate.create2D(10,40), Dimension.Two),
                new Point(Coordinate.create2D(40,30), Dimension.Two),
                new Point(Coordinate.create2D(20,20), Dimension.Two),
                new Point(Coordinate.create2D(30,10), Dimension.Two)
            ),
            Dimension.Two
        );
        String json = writer.write(multiPoint);
        assertEquals("{\"type\": \"MultiPoint\", \"coordinates\": [[10.0, 40.0], [40.0, 30.0], [20.0, 20.0], [30.0, 10.0]]}", json);
    }

    @Test
    public void writeMultiLineString() {
        GeoJSONWriter writer = new GeoJSONWriter();
        MultiLineString multiLineString = new MultiLineString(
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
            Dimension.Two
        );
        String json = writer.write(multiLineString);
        assertEquals("{\"type\": \"MultiLineString\", \"coordinates\": [[[10.0, 10.0], [20.0, 20.0], [10.0, 40.0]], [[40.0, 40.0], [30.0, 30.0], [40.0, 20.0], [30.0, 10.0]]]}", json);
    }

    @Test
    public void writeMultiPolygon() {
        GeoJSONWriter writer = new GeoJSONWriter();
        Geometry polygon = new MultiPolygon(
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
            Dimension.Two

        );
        String json = writer.write(polygon);
        assertEquals("{\"type\": \"MultiPolygon\", \"coordinates\": [[[[30.0, 20.0], [45.0, 40.0], [10.0, 40.0], [30.0, 20.0]]], " +
            "[[[15.0, 5.0], [40.0, 10.0], [10.0, 20.0], [5.0, 10.0], [15.0, 5.0]]]]}", json);
    }

    @Test
    public void writeMultiPolygonWithHoles() {
        GeoJSONWriter writer = new GeoJSONWriter();
        Geometry polygon = new MultiPolygon(
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
            Dimension.Two

        );
        String json = writer.write(polygon);
        assertEquals("{\"type\": \"MultiPolygon\", \"coordinates\": [[[[40.0, 40.0], [20.0, 45.0], [45.0, 30.0], [40.0, 40.0]]], " +
            "[[[20.0, 35.0], [10.0, 30.0], [10.0, 10.0], [30.0, 5.0], [45.0, 20.0], [20.0, 35.0]], [[30.0, 20.0], [20.0, 15.0], [20.0, 25.0], [30.0, 20.0]]]]}", json);
    }

    @Test
    public void writeGeometryCollection() {
        GeoJSONWriter writer = new GeoJSONWriter();
        GeometryCollection geometryCollection = new GeometryCollection(
            Arrays.asList(
                new Point(Coordinate.create2D(10,40), Dimension.Two),
                new LineString(Arrays.asList(
                    Coordinate.create2D(10,10),
                    Coordinate.create2D(20,20)
                ), Dimension.Two)
            ),
            Dimension.Two
        );
        String json = writer.write(geometryCollection);
        assertEquals("{\"type\": \"GeometryCollection\", \"geometries\": [{\"type\": \"Point\", \"coordinates\": [10.0, 40.0]}, " +
            "{\"type\": \"LineString\", \"coordinates\": [[10.0, 10.0], [20.0, 20.0]]}]}", json);
    }



}
