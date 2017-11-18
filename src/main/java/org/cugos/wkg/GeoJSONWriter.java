package org.cugos.wkg;

import java.util.List;

public class GeoJSONWriter implements Writer<String> {

  @Override
  public String write(Geometry geometry) {
    if (geometry instanceof Point) {
      return writePoint((Point) geometry);
    } else if (geometry instanceof LineString) {
      return writeLineString((LineString) geometry);
    } else if (geometry instanceof Polygon) {
      return writePolygon((Polygon) geometry);
    } else if (geometry instanceof MultiPoint) {
      return writeMultiPoint((MultiPoint) geometry);
    } else if (geometry instanceof MultiLineString) {
      return writeMultiLineString((MultiLineString) geometry);
    } else if (geometry instanceof MultiPolygon) {
      return writeMultiPolygon((MultiPolygon) geometry);
    } else if (geometry instanceof GeometryCollection) {
      return writeGeometryCollection((GeometryCollection) geometry);
    }
    return "";
  }

  @Override
  public String getName() {
    return "GeoJSON";
  }

  public String writeFeature(Geometry geometry) {
    StringBuilder str = new StringBuilder();
    str.append("{\"type\": \"Feature\", \"properties\": {}, \"geometry\": ");
    str.append(write(geometry));
    str.append("}");
    return str.toString();
  }

  public String writeFeatureCollection(Geometry geometry) {
    StringBuilder str = new StringBuilder();
    str.append("{\"type\": \"FeatureCollection\", \"features\": [");
    str.append(writeFeature(geometry));
    str.append("]}");
    return str.toString();
  }

  protected String writePoint(Point point) {
    StringBuilder str = new StringBuilder();
    str.append("{\"type\": \"Point\"").append(", ");
    str.append("\"coordinates\": ");
    writeCoordinate(str, point.getCoordinate());
    str.append("}");
    return str.toString();
  }

  protected String writeLineString(LineString lineString) {
    StringBuilder str = new StringBuilder();
    str.append("{\"type\": \"LineString\"").append(", ");
    str.append("\"coordinates\": [");
    writeCoordinates(str, lineString.getCoordinates());
    str.append("]");
    str.append("}");
    return str.toString();
  }

  protected String writePolygon(Polygon polygon) {
    StringBuilder str = new StringBuilder();
    str.append("{\"type\": \"Polygon\"").append(", ");
    str.append("\"coordinates\": [");
    str.append("[");
    writeCoordinates(str, polygon.getOuterLinearRing().getCoordinates());
    str.append("]");
    if (polygon.getInnerLinearRings().size() > 0) {
      str.append(", [");
      int i = 0;
      for (LinearRing interiorLinearRing : polygon.getInnerLinearRings()) {
        if (i > 0) {
          str.append(", ");
        }
        writeCoordinates(str, interiorLinearRing.getCoordinates());
        i++;
      }
      str.append("]");
    }
    str.append("]");
    str.append("}");
    return str.toString();
  }

  protected String writeMultiPoint(MultiPoint multiPoint) {
    StringBuilder str = new StringBuilder();
    str.append("{\"type\": \"MultiPoint\"").append(", ");
    str.append("\"coordinates\": [");
    writeCoordinates(str, multiPoint.getCoordinates());
    str.append("]");
    str.append("}");
    return str.toString();
  }

  protected String writeMultiLineString(MultiLineString multiLineString) {
    StringBuilder str = new StringBuilder();
    str.append("{\"type\": \"MultiLineString\"").append(", ");
    str.append("\"coordinates\": [");
    int i = 0;
    for (LineString lineString : multiLineString.getLineStrings()) {
      if (i > 0) {
        str.append(", ");
      }
      str.append("[");
      writeCoordinates(str, lineString.getCoordinates());
      str.append("]");
      i++;
    }
    str.append("]");
    str.append("}");
    return str.toString();
  }

  protected String writeMultiPolygon(MultiPolygon multiPolygon) {
    StringBuilder str = new StringBuilder();
    str.append("{\"type\": \"MultiPolygon\"").append(", ");
    str.append("\"coordinates\": [");
    int i = 0;
    for (Polygon polygon : multiPolygon.getPolygons()) {
      if (i > 0) {
        str.append(", ");
      }
      str.append("[");

      str.append("[");
      writeCoordinates(str, polygon.getOuterLinearRing().getCoordinates());
      str.append("]");
      if (polygon.getInnerLinearRings().size() > 0) {
        str.append(", [");
        int j = 0;
        for (LinearRing interiorLinearRing : polygon.getInnerLinearRings()) {
          if (j > 0) {
            str.append(", ");
          }
          writeCoordinates(str, interiorLinearRing.getCoordinates());
          j++;
        }
        str.append("]");
      }
      str.append("]");
      i++;
    }
    str.append("]");
    str.append("}");
    return str.toString();
  }

  protected String writeGeometryCollection(GeometryCollection geometryCollection) {
    StringBuilder str = new StringBuilder();
    str.append("{\"type\": \"GeometryCollection\"").append(", ");
    str.append("\"geometries\": [");
    int i = 0;
    for (Geometry geometry : geometryCollection.getGeometries()) {
      if (i > 0) {
        str.append(", ");
      }
      str.append(write(geometry));
      i++;
    }
    str.append("]");
    str.append("}");
    return str.toString();
  }

  protected void writeCoordinate(StringBuilder str, Coordinate coordinate) {
    str.append("[");
    if (!coordinate.isEmpty()) {
      str.append(coordinate.getX());
      str.append(", ");
      str.append(coordinate.getY());
      if (!Double.isNaN(coordinate.getZ())) {
        str.append(", ");
        str.append(coordinate.getZ());
      }
    }
    str.append("]");
  }

  protected void writeCoordinates(StringBuilder str, List<Coordinate> coordinates) {
    boolean first = true;
    for (Coordinate coordinate : coordinates) {
      if (!first) {
        str.append(", ");
      }
      writeCoordinate(str, coordinate);
      first = false;
    }
  }


}