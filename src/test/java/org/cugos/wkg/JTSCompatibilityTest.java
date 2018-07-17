package org.cugos.wkg;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class JTSCompatibilityTest {

    @Test
    public void jtsCompatibility() throws Exception {
        org.locationtech.jts.geom.GeometryFactory jtsGeometryFactory = new org.locationtech.jts.geom.GeometryFactory();
        org.locationtech.jts.geom.Geometry[] jtsGeometries = {
            // POINT
            jtsGeometryFactory.createPoint(new org.locationtech.jts.geom.Coordinate(-122.21, 47.57)),
            // LINESTRING
            jtsGeometryFactory.createLineString(new org.locationtech.jts.geom.Coordinate[]{
                new org.locationtech.jts.geom.Coordinate(-121.640625, 47.27922900257082),
                new org.locationtech.jts.geom.Coordinate(-111.796875, 42.032974332441405),
                new org.locationtech.jts.geom.Coordinate(-103.71093749999999, 46.800059446787316)
            }),
            // POLYGON
            jtsGeometryFactory.createPolygon(new org.locationtech.jts.geom.Coordinate[]{
                new org.locationtech.jts.geom.Coordinate(-117.42187500000001, 64.77412531292873),
                new org.locationtech.jts.geom.Coordinate(-120.9375, 61.270232790000634),
                new org.locationtech.jts.geom.Coordinate(-110.74218749999999, 58.07787626787517),
                new org.locationtech.jts.geom.Coordinate(-106.171875, 62.59334083012024),
                new org.locationtech.jts.geom.Coordinate(-108.6328125, 64.62387720204688),
                new org.locationtech.jts.geom.Coordinate(-117.42187500000001, 64.77412531292873)
            }),
            // MULTIPOINT
            jtsGeometryFactory.createMultiPoint(new org.locationtech.jts.geom.Point[] {
                jtsGeometryFactory.createPoint(new org.locationtech.jts.geom.Coordinate(48.1640625, 48.69096039092549)),
                jtsGeometryFactory.createPoint(new org.locationtech.jts.geom.Coordinate(45.703125, 60.58696734225869)),
                jtsGeometryFactory.createPoint(new org.locationtech.jts.geom.Coordinate(18.28125, 64.92354174306496))
            }),
            // MULTILINESTRING
            jtsGeometryFactory.createMultiLineString(new org.locationtech.jts.geom.LineString[] {
                jtsGeometryFactory.createLineString(new org.locationtech.jts.geom.Coordinate[] {
                    new org.locationtech.jts.geom.Coordinate(64.6875, 54.97761367069625),
                    new org.locationtech.jts.geom.Coordinate(84.72656249999999, 61.77312286453146)
                }),
                jtsGeometryFactory.createLineString(new org.locationtech.jts.geom.Coordinate[] {
                    new org.locationtech.jts.geom.Coordinate(69.2578125, 46.31658418182218),
                    new org.locationtech.jts.geom.Coordinate(96.328125, 45.336701909968106)
                }),
                jtsGeometryFactory.createLineString(new org.locationtech.jts.geom.Coordinate[] {
                    new org.locationtech.jts.geom.Coordinate(99.140625, 59.712097173322924),
                    new org.locationtech.jts.geom.Coordinate(102.65625, 46.31658418182218)
                })
            }),
            // MULTIPOLYGON
            jtsGeometryFactory.createMultiPolygon(new org.locationtech.jts.geom.Polygon[]{
                jtsGeometryFactory.createPolygon(new org.locationtech.jts.geom.Coordinate[] {
                    new org.locationtech.jts.geom.Coordinate(3.8671874999999996, 35.17380831799959),
                    new org.locationtech.jts.geom.Coordinate(3.8671874999999996, 45.82879925192134),
                    new org.locationtech.jts.geom.Coordinate(16.171875, 45.82879925192134),
                    new org.locationtech.jts.geom.Coordinate(16.171875, 35.17380831799959),
                    new org.locationtech.jts.geom.Coordinate(3.8671874999999996, 35.17380831799959)
                }),
                jtsGeometryFactory.createPolygon(new org.locationtech.jts.geom.Coordinate[] {
                    new org.locationtech.jts.geom.Coordinate(-16.171875, 16.63619187839765),
                    new org.locationtech.jts.geom.Coordinate(-16.171875, 23.563987128451217),
                    new org.locationtech.jts.geom.Coordinate(-6.328125 ,23.563987128451217),
                    new org.locationtech.jts.geom.Coordinate(-6.328125 ,16.63619187839765),
                    new org.locationtech.jts.geom.Coordinate(-16.171875, 16.63619187839765)
                }),
                jtsGeometryFactory.createPolygon(new org.locationtech.jts.geom.Coordinate[] {
                    new org.locationtech.jts.geom.Coordinate(10.546875, 14.944784875088372),
                    new org.locationtech.jts.geom.Coordinate(10.546875, 23.563987128451217),
                    new org.locationtech.jts.geom.Coordinate(17.9296875, 23.563987128451217),
                    new org.locationtech.jts.geom.Coordinate(17.9296875, 14.944784875088372),
                    new org.locationtech.jts.geom.Coordinate(10.546875, 14.944784875088372)
                }),
            }),
            // GEOMETRYCOLLECTION
            jtsGeometryFactory.createGeometryCollection(new org.locationtech.jts.geom.Geometry[]{
                jtsGeometryFactory.createPoint(new org.locationtech.jts.geom.Coordinate(-122.21, 47.57)),
                jtsGeometryFactory.createLineString(new org.locationtech.jts.geom.Coordinate[]{
                    new org.locationtech.jts.geom.Coordinate(-121.640625, 47.27922900257082),
                    new org.locationtech.jts.geom.Coordinate(-111.796875, 42.032974332441405),
                    new org.locationtech.jts.geom.Coordinate(-103.71093749999999, 46.800059446787316)
                }),
                jtsGeometryFactory.createPolygon(new org.locationtech.jts.geom.Coordinate[]{
                    new org.locationtech.jts.geom.Coordinate(-117.42187500000001, 64.77412531292873),
                    new org.locationtech.jts.geom.Coordinate(-120.9375, 61.270232790000634),
                    new org.locationtech.jts.geom.Coordinate(-110.74218749999999, 58.07787626787517),
                    new org.locationtech.jts.geom.Coordinate(-106.171875, 62.59334083012024),
                    new org.locationtech.jts.geom.Coordinate(-108.6328125, 64.62387720204688),
                    new org.locationtech.jts.geom.Coordinate(-117.42187500000001, 64.77412531292873)
                })
            })
        };

        org.locationtech.jts.io.WKTWriter jtsWKTWriter = new org.locationtech.jts.io.WKTWriter();
        org.locationtech.jts.io.WKBWriter jtsWKBWriter = new org.locationtech.jts.io.WKBWriter();
        org.locationtech.jts.io.WKTReader jtsWKTReader = new org.locationtech.jts.io.WKTReader();
        org.locationtech.jts.io.WKBReader jtsWKBReader = new org.locationtech.jts.io.WKBReader();

        WKTReader wktReader = new WKTReader();
        WKBReader wkbReader = new WKBReader();
        WKTWriter wktWriter = new WKTWriter();
        WKBWriter wkbWriter = new WKBWriter();

        for(org.locationtech.jts.geom.Geometry jtsGeometry : jtsGeometries) {
            // Convert the JTS Geometry to a WKG Geometry
            Geometry geometry = fromJTS(jtsGeometry);
            // Compare Envelopes
            Envelope wkgEnvelope = geometry.getEnvelope();
            org.locationtech.jts.geom.Envelope jtsEnvelope = jtsGeometry.getEnvelopeInternal();
            assertEquals(jtsEnvelope.getMinX(), wkgEnvelope.getMinX(), 0.001);
            assertEquals(jtsEnvelope.getMinY(), wkgEnvelope.getMinY(), 0.001);
            assertEquals(jtsEnvelope.getMaxX(), wkgEnvelope.getMaxX(), 0.001);
            assertEquals(jtsEnvelope.getMaxY(), wkgEnvelope.getMaxY(), 0.001);
            // Write the JTS Geometry to WKT and WKB
            String jtsWkt = jtsWKTWriter.write(jtsGeometry);
            byte[] jtsWkb = jtsWKBWriter.write(jtsGeometry);
            // Read the WKT and WKB using WKG Readers
            Geometry geometryFromWKT = wktReader.read(jtsWkt);
            Geometry geometryFromWKB = wkbReader.read(jtsWkb);
            // Make sure the Geometries are equal
            assertEquals(geometry.toString(), geometryFromWKT.toString());
            assertEquals(geometry.toString(), geometryFromWKB.toString());
            //  Write the WKG Geometry to WKT and WKB
            String wkgWkt = wktWriter.write(geometry);
            byte[] wkgWkb = wkbWriter.write(geometry);
            // Read the WKT and WKB using JTS Readers
            org.locationtech.jts.geom.Geometry jtsGeometryFromWkt = jtsWKTReader.read(wkgWkt);
            org.locationtech.jts.geom.Geometry jtsGeometryFromWkb = jtsWKBReader.read(wkgWkb);
            // Make sure the JTS Geometries are equal
            assertEquals(jtsGeometry, jtsGeometryFromWkt);
            assertEquals(jtsGeometry, jtsGeometryFromWkb);
            // Convert WKG Geometry to JTS Geometry
            org.locationtech.jts.geom.Geometry jtsFromWkgGeometry = toJTS(geometry);
            assertEquals(jtsGeometry, jtsFromWkgGeometry);
        }
    }

    /**
     * Convert a JTS Geometry to a WKG Geometry
     * @param geometry The JTS Geometry
     * @return A WKG Geometry
     */
    private Geometry fromJTS(org.locationtech.jts.geom.Geometry geometry) {
        if (geometry instanceof org.locationtech.jts.geom.Point) {
            org.locationtech.jts.geom.Point point = (org.locationtech.jts.geom.Point) geometry;
            return new Point(fromJTS(point.getCoordinate()), Dimension.Two);
        } else if (geometry instanceof org.locationtech.jts.geom.LineString) {
            org.locationtech.jts.geom.LineString lineString = (org.locationtech.jts.geom.LineString) geometry;
            return new LineString(fromJTS(lineString.getCoordinates()), Dimension.Two);
        } else if (geometry instanceof org.locationtech.jts.geom.Polygon) {
            org.locationtech.jts.geom.Polygon polygon = (org.locationtech.jts.geom.Polygon) geometry;
            LinearRing exteriorRing = new LinearRing(fromJTS(polygon.getExteriorRing().getCoordinates()), Dimension.Two);
            List<LinearRing> interiorRings = new ArrayList<>();
            for(int i = 0; i < polygon.getNumInteriorRing(); i++) {
                interiorRings.add(new LinearRing(fromJTS(polygon.getInteriorRingN(i).getCoordinates()), Dimension.Two));
            }
            return new Polygon(exteriorRing, interiorRings, Dimension.Two);
        } else if (geometry instanceof org.locationtech.jts.geom.MultiPoint) {
            org.locationtech.jts.geom.MultiPoint multiPoint = (org.locationtech.jts.geom.MultiPoint) geometry;
            List<Point> points = new ArrayList<>();
            for(int i=0; i<multiPoint.getNumGeometries(); i++) {
                org.locationtech.jts.geom.Point point = (org.locationtech.jts.geom.Point) multiPoint.getGeometryN(i);
                points.add((Point) fromJTS(point));
            }
            return new MultiPoint(points, Dimension.Two);
        } else if (geometry instanceof org.locationtech.jts.geom.MultiLineString) {
            org.locationtech.jts.geom.MultiLineString multiLineString = (org.locationtech.jts.geom.MultiLineString) geometry;
            List<LineString> lineStrings = new ArrayList<>();
            for(int i=0; i<multiLineString.getNumGeometries(); i++) {
                org.locationtech.jts.geom.LineString lineString = (org.locationtech.jts.geom.LineString) multiLineString.getGeometryN(i);
                lineStrings.add((LineString) fromJTS(lineString));
            }
            return new MultiLineString(lineStrings, Dimension.Two);
        } else if (geometry instanceof org.locationtech.jts.geom.MultiPolygon) {
            org.locationtech.jts.geom.MultiPolygon multiPolygon = (org.locationtech.jts.geom.MultiPolygon) geometry;
            List<Polygon> polygons = new ArrayList<>();
            for(int i=0; i<multiPolygon.getNumGeometries(); i++) {
                org.locationtech.jts.geom.Polygon polygon = (org.locationtech.jts.geom.Polygon) multiPolygon.getGeometryN(i);
                polygons.add((Polygon) fromJTS(polygon));
            }
            return new MultiPolygon(polygons, Dimension.Two);
        } else if (geometry instanceof org.locationtech.jts.geom.GeometryCollection) {
            org.locationtech.jts.geom.GeometryCollection geometryCollection = (org.locationtech.jts.geom.GeometryCollection) geometry;
            List<Geometry> geometries = new ArrayList<>();
            for(int i=0; i<geometryCollection.getNumGeometries(); i++) {
                geometries.add(fromJTS(geometryCollection.getGeometryN(i)));
            }
            return new GeometryCollection(geometries, Dimension.Two);
        }
        return null;
    }

    /**
     * Convert a JTS Coordinate to a WKG Coordinate
     * @param coordinate A JTS Coordinate
     * @return A WKG Coordinate
     */
    private Coordinate fromJTS(org.locationtech.jts.geom.Coordinate coordinate) {
        return Coordinate.create2D(coordinate.x, coordinate.y);
    }

    /**
     * Convert a JTS Coordinate array to a List of WKG Coordinates
     * @param jtsCoordinates The JTS Coordinate Array
     * @return A List of WKG Coordinates
     */
    private List<Coordinate> fromJTS(org.locationtech.jts.geom.Coordinate[] jtsCoordinates) {
        List<Coordinate> coordinates = new ArrayList<>();
        for(int i = 0; i<jtsCoordinates.length; i++) {
            coordinates.add(fromJTS(jtsCoordinates[i]));
        }
        return coordinates;
    }

    /**
     * Convert a WKG Geometry to a JTS Geometry or null if the Geometry is not supported
     * @param geometry A WKG Geometry
     * @return A JTS Geometry or null
     */
    private org.locationtech.jts.geom.Geometry toJTS(Geometry geometry) {
        org.locationtech.jts.geom.GeometryFactory jtsGeometryFactory = new org.locationtech.jts.geom.GeometryFactory();
        // POINT
        if (geometry instanceof Point) {
            Point point = (Point) geometry;
            return jtsGeometryFactory.createPoint(toJTS(point.getCoordinate()));
        } 
        // LINESTRING
        else if (geometry instanceof LineString) {
            LineString lineString = (LineString) geometry;
            return jtsGeometryFactory.createLineString(toJTS(lineString.getCoordinates()));
        } 
        // POLYGON
        else if (geometry instanceof Polygon) {
            Polygon polygon = (Polygon) geometry;
            org.locationtech.jts.geom.LinearRing exteriorLinearRing = jtsGeometryFactory.createLinearRing(toJTS(polygon.getOuterLinearRing().getCoordinates()));
            org.locationtech.jts.geom.LinearRing[] interiorLinearRings = new org.locationtech.jts.geom.LinearRing[polygon.getInnerLinearRings().size()];
            for(int i = 0; i < polygon.getInnerLinearRings().size(); i++) {
                interiorLinearRings[i] = jtsGeometryFactory.createLinearRing(toJTS(polygon.getInnerLinearRings().get(i).getCoordinates()));
            }
            return jtsGeometryFactory.createPolygon(exteriorLinearRing, interiorLinearRings);
        } 
        // MULTIPOINT
        else if (geometry instanceof MultiPoint) {
            MultiPoint multiPoint = (MultiPoint) geometry;
            org.locationtech.jts.geom.Point[] points = new org.locationtech.jts.geom.Point[multiPoint.getPoints().size()];
            for(int i = 0; i<points.length; i++) {
                points[i] = (org.locationtech.jts.geom.Point) toJTS(multiPoint.getPoints().get(i));
            }
            return jtsGeometryFactory.createMultiPoint(points);
        }
        // MULTILINESTRING
        else if (geometry instanceof MultiLineString) {
            MultiLineString multiLineString = (MultiLineString) geometry;
            org.locationtech.jts.geom.LineString[] lineStrings = new org.locationtech.jts.geom.LineString[multiLineString.getLineStrings().size()];
            for(int i = 0; i<lineStrings.length; i++) {
                lineStrings[i] = (org.locationtech.jts.geom.LineString) toJTS(multiLineString.getLineStrings().get(i));
            }
            return jtsGeometryFactory.createMultiLineString(lineStrings);
        }
        // MULTIPOLYGON
        else if (geometry instanceof MultiPolygon) {
            MultiPolygon multiPolygon = (MultiPolygon) geometry;
            org.locationtech.jts.geom.Polygon[] polygons = new org.locationtech.jts.geom.Polygon[multiPolygon.getPolygons().size()];
            for(int i = 0; i<polygons.length; i++) {
                polygons[i] = (org.locationtech.jts.geom.Polygon) toJTS(multiPolygon.getPolygons().get(i));
            }
            return jtsGeometryFactory.createMultiPolygon(polygons);
        }
        // GEOMETRYCOLLECTION
        else if (geometry instanceof GeometryCollection) {
            GeometryCollection geometryCollection = (GeometryCollection) geometry;
            org.locationtech.jts.geom.Geometry[] geometries = new org.locationtech.jts.geom.Geometry[geometryCollection.getGeometries().size()];
            for(int i = 0; i<geometries.length; i++) {
                geometries[i] = toJTS(geometryCollection.getGeometries().get(i));
            }
            return jtsGeometryFactory.createGeometryCollection(geometries);
        }
        // UNSUPPORTED
        else {
            return null;
        }
    }

    /**
     * Convert a WKG Coordinate to a JTS Coordinate
     * @param coordinate A WKG Coordinate
     * @return A JTS Coordinate
     */
    private org.locationtech.jts.geom.Coordinate toJTS(Coordinate coordinate) {
        return new org.locationtech.jts.geom.Coordinate(coordinate.getX(), coordinate.getY());
    }

    /**
     * Convert a List of WKG Coordinates to a JTS Coordinate Array
     * @param coordinates A List of WKG Coordinates
     * @return A JTS Coordinate Array
     */
    private org.locationtech.jts.geom.Coordinate[] toJTS(List<Coordinate> coordinates) {
        org.locationtech.jts.geom.Coordinate[] jtsCoordinates = new org.locationtech.jts.geom.Coordinate[coordinates.size()];
        int i = 0;
        for(Coordinate coordinate : coordinates) {
            jtsCoordinates[i] = toJTS(coordinate);
            i++;
        }
        return jtsCoordinates;
    }

}
