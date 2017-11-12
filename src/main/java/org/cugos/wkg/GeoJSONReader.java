package org.cugos.wkg;

import org.antlr.v4.runtime.*;
import org.cugos.wkg.internal.JSONBaseListener;
import org.cugos.wkg.internal.JSONLexer;
import org.cugos.wkg.internal.JSONParser;

import java.util.*;

/**
 * Read a Geometry from a GeoJSON String
 */
public class GeoJSONReader implements Reader<String> {

    /**
     * The default SRID for GeoJSON is EPSG:4326
     */
    private final String srid = "4326";

    /**
     * Read a Geometry from a GeoJSON String
     * @param jsonStr The GeoJSON String
     * @return The Geometry or null
     */
    @Override
    public Geometry read(String jsonStr) {
        JSON json = parse(jsonStr);
        Geometry geometry = null;
        if (json instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) json;
            geometry = readGeometry(jsonObject);
        }
        return geometry;
    }

    @Override
    public String getName() {
        return "GeoJSON";
    }

    private Point readPoint(JSONObject jsonObject) {
        Coordinate coordinate = getCoordinate((JSONArray) jsonObject.get("coordinates"));
        return new Point(coordinate, coordinate.getDimension(), srid);
    }

    private LineString readLineString(JSONObject jsonObject) {
        List<Coordinate>coordinates = getCoordinates((JSONArray) jsonObject.get("coordinates"));
        return new LineString(coordinates, coordinates.size() > 0 ? coordinates.get(0).getDimension() : Dimension.Two, srid);
    }

    private Polygon readPolygon(JSONObject jsonObject) {
        List<List<Coordinate>> coordinateSets = getCoordinateSets((JSONArray) jsonObject.get("coordinates"));
        if (coordinateSets.size() > 0) {
            LinearRing exteriorRing = getLinearRing(coordinateSets.get(0));
            List<LinearRing> interiorRings = new ArrayList<>();
            for (List<Coordinate> coordinates : coordinateSets.subList(1, coordinateSets.size())) {
                interiorRings.add(getLinearRing(coordinates));
            }
            return new Polygon(exteriorRing, interiorRings, exteriorRing.getDimension(), srid);
        } else {
            return Polygon.createEmpty();
        }
    }

    private MultiPoint readMultiPoint(JSONObject jsonObject) {
        List<Point> points = getPoints((JSONArray) jsonObject.get("coordinates"));
        return new MultiPoint(points, points.size() > 0 ? points.get(0).getDimension() : Dimension.Two, srid);
    }

    private MultiLineString readMultiLineString(JSONObject jsonObject) {
        JSONArray coordinates = (JSONArray) jsonObject.get("coordinates");
        List<LineString> lineStrings = new ArrayList<>();
        for(Object coords : coordinates.values()) {
            List<Coordinate> c = getCoordinates((JSONArray) coords);
            lineStrings.add(new LineString(c, c.size() > 0 ? c.get(0).getDimension() : Dimension.Two, srid));
        }
        return new MultiLineString(lineStrings, lineStrings.size() > 0 ? lineStrings.get(0).getDimension() : Dimension.Two, srid);
    }

    private MultiPolygon readMultiPolygon(JSONObject jsonObject) {
        JSONArray coordinates = (JSONArray) jsonObject.get("coordinates");
        List<Polygon> polygons = new ArrayList<>();
        for(Object polygonsCoords : coordinates.values()) {
            List<List<Coordinate>> coordinateSets = getCoordinateSets((JSONArray) polygonsCoords);
            if (coordinateSets.size() > 0) {
                LinearRing exteriorRing = getLinearRing(coordinateSets.get(0));
                List<LinearRing> interiorRings = new ArrayList<>();
                for (List<Coordinate> coords : coordinateSets.subList(1, coordinateSets.size())) {
                    interiorRings.add(getLinearRing(coords));
                }
                polygons.add(new Polygon(exteriorRing, interiorRings, exteriorRing.getDimension(), srid));
            } else {
                polygons.add(Polygon.createEmpty());
            }
        }
        return new MultiPolygon(polygons, polygons.size() > 0 ? polygons.get(0).getDimension() : Dimension.Two, srid);
    }

    private GeometryCollection readGeometryCollection(JSONObject jsonObject) {
        JSONArray geometriesJsonArray = (JSONArray) jsonObject.get("geometries");
        List<Geometry> geometries = new ArrayList<>();
        for(Object geometryJsonObject : geometriesJsonArray.values()) {
            geometries.add(readGeometry((JSONObject) geometryJsonObject));
        }
        return new GeometryCollection(geometries, geometries.size() > 0 ? geometries.get(0).getDimension() : Dimension.Two, srid);
    }

    private Geometry readGeometry(JSONObject jsonObject) {
        String type = (String) jsonObject.get("type");
        Geometry geometry = null;
        if (type != null) {
            if (type.equalsIgnoreCase("point")) {
                geometry = readPoint(jsonObject);
            } else if (type.equalsIgnoreCase("LineString")) {
                geometry = readLineString(jsonObject);
            } else if (type.equalsIgnoreCase("Polygon")) {
                geometry = readPolygon(jsonObject);
            } else if (type.equalsIgnoreCase("MultiPoint")) {
                geometry = readMultiPoint(jsonObject);
            } else if (type.equalsIgnoreCase("MultiLineString")) {
                geometry = readMultiLineString(jsonObject);
            } else if (type.equalsIgnoreCase("MultiPolygon")) {
                geometry = readMultiPolygon(jsonObject);
            } else if (type.equalsIgnoreCase("GeometryCollection")) {
                geometry = readGeometryCollection(jsonObject);
            }
        }
        return geometry;
    }

    private LinearRing getLinearRing(List<Coordinate> coordinates) {
       return new LinearRing(coordinates, coordinates.size() > 0 ? coordinates.get(0).getDimension() : Dimension.Two, srid);
    }

    private Coordinate getCoordinate(JSONArray jsonArray) {
        if (jsonArray.values().size() > 2) {
            return Coordinate.create3D(getDouble(jsonArray.get(0)), getDouble(jsonArray.get(1)), getDouble(jsonArray.get(2)));
        } else if (jsonArray.values().size() == 2) {
            return Coordinate.create2D(getDouble(jsonArray.get(0)), getDouble(jsonArray.get(1)));
        } else {
            return Coordinate.createEmpty();
        }
    }

    private Double getDouble(Object value) {
        if (value instanceof Double) {
            return (Double) value;
        } else if (value instanceof Integer) {
            return ((Integer) value).doubleValue();
        } else if (value != null) {
            return Double.parseDouble(value.toString());
        } else {
            return Double.NaN;
        }
    }

    private List<Coordinate> getCoordinates(JSONArray jsonArray) {
        List<Coordinate> coordinates = new ArrayList<>();
        for(Object value : jsonArray.values()) {
            if (value instanceof JSONArray) {
                coordinates.add(getCoordinate((JSONArray)value));
            }
        }
        return coordinates;
    }

    private List<Point> getPoints(JSONArray jsonArray) {
        List<Point> points = new ArrayList<>();
        for(Object value : jsonArray.values()) {
            if (value instanceof JSONArray) {
                Coordinate coordinate = getCoordinate((JSONArray)value);
                points.add(new Point(coordinate, coordinate.getDimension()));
            }
        }
        return points;
    }

    private List<List<Coordinate>> getCoordinateSets(JSONArray jsonArray) {
        List<List<Coordinate>> coordinateSets = new ArrayList<>();
        for(Object value : jsonArray.values()) {
            if (value instanceof JSONArray) {
                coordinateSets.add(getCoordinates((JSONArray)value));
            }
        }
        return coordinateSets;
    }

    private JSON parse(String jsonStr) {

        JSONLexer lexer = new JSONLexer(new ANTLRInputStream(jsonStr));
        JSONParser parser = new JSONParser(new CommonTokenStream(lexer));
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                throw new IllegalStateException("failed to parse at line " + line + " due to " + msg, e);
            }
        });

        final Stack<JSON> json = new Stack<>();

        parser.addParseListener(new JSONBaseListener() {

            @Override
            public void enterObj(JSONParser.ObjContext ctx) {
                json.push(new JSONObject());
            }

            @Override
            public void exitPair(JSONParser.PairContext ctx) {
                Object value = getValueFromValueContent(ctx.value());
                JSONObject jsonObject = (JSONObject) json.peek();
                jsonObject.values().put(removeDoubleQuotes(ctx.STRING().getText()), value);
            }

            @Override
            public void enterArray(JSONParser.ArrayContext ctx) {
               json.push(new JSONArray());
            }

            @Override
            public void exitArray(JSONParser.ArrayContext ctx) {
                List<Object> values = new ArrayList<>();
                for(JSONParser.ValueContext valueContext : ctx.value()) {
                    Object value = getValueFromValueContent(valueContext);
                    if (value instanceof JSON) {
                        values.add(0, value);
                    } else {
                        values.add(value);
                    }
                }
                JSONArray jsonArray = (JSONArray) json.peek();
                jsonArray.values().addAll(values);
            }

            private String removeDoubleQuotes(String str) {
                if (str != null) {
                    return str.replaceAll("\"", "");
                } else {
                    return null;
                }
            }

            private Number parseNumber(String value) {
                if (value.contains(".")) {
                    return Double.parseDouble(value);
                } else {
                    return Integer.parseInt(value);
                }
            }

            private Object getValueFromValueContent(JSONParser.ValueContext ctx) {
                if (ctx.obj() != null) {
                    return json.pop();
                } else if (ctx.array() != null) {
                    return json.pop();
                } else if (ctx.STRING() != null) {
                    return removeDoubleQuotes(ctx.STRING().getText());
                } else if (ctx.NUMBER() != null) {
                    return parseNumber(removeDoubleQuotes(ctx.NUMBER().getText()));
                } else {
                    return null;
                }
            }


        });
        parser.json();

        return json.pop();
    }

    private static interface JSON {
    }

    private static class JSONObject implements JSON {
        private Map<String,Object> values = new LinkedHashMap<>();
        public Map<String,Object> values() {
            return values;
        }
        public Object get(String key) {
            return values.get(key);
        }
        @Override
        public String toString() {
            return values.toString();
        }
    }

    private static class JSONArray implements JSON {
        private List<Object> values = new ArrayList<>();
        public List<Object> values() {
            return values;
        }
        public Object get(int index) {
            return values.get(index);
        }
        @Override
        public String toString() {
            return values.toString();
        }
    }
    
}
