package org.cugos.wkg;

import org.antlr.v4.runtime.*;
import org.cugos.wkg.internal.WKTBaseListener;
import org.cugos.wkg.internal.WKTLexer;
import org.cugos.wkg.internal.WKTParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Read a Geometry from a Well Known Text (WKT) String
 */
public class WKTReader implements Reader<String> {

    /**
     * Read the WKT and return a Geometry
     * @param wkt The WKT
     * @return A Geometry
     */
    @Override
    public Geometry read(String wkt) {
        WKTLexer lexer = new WKTLexer(new ANTLRInputStream(wkt));
        WKTParser parser = new WKTParser(new CommonTokenStream(lexer));
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                throw new IllegalStateException("failed to parse at line " + line + " due to " + msg, e);
            }
        });

        final Stack<Geometry> geometries = new Stack<Geometry>();
        final Var<Dimension> dimension = new Var<Dimension>();
        final Var<String> srid = new Var<String>();

        dimension.set(Dimension.Two);

        parser.addParseListener(new WKTBaseListener() {

            @Override
            public void exitDimension(WKTParser.DimensionContext ctx) {
                if (ctx.M() != null) {
                    dimension.set(Dimension.TwoMeasured);
                } else if (ctx.Z() != null) {
                    dimension.set(Dimension.Three);
                } else if (ctx.ZM() != null) {
                    dimension.set(Dimension.ThreeMeasured);
                }
            }

            @Override
            public void exitSrid(WKTParser.SridContext ctx) {
                srid.set(ctx.Number().getText());
            }

            @Override
            public void exitPoint(WKTParser.PointContext ctx) {
                Point point;
                if (ctx.coordinate() == null) {
                    point = Point.createEmpty();
                } else {
                    point = point(ctx, dimension, srid);
                }
                geometries.push(point);
            }

            @Override
            public void exitLineString(WKTParser.LineStringContext ctx) {
                LineString lineString;
                if (ctx.coordinates() == null) {
                    lineString = LineString.createEmpty();
                } else {
                    lineString = lineString(ctx, dimension, srid);
                }
                geometries.push(lineString);
            }

            @Override
            public void exitCircularString(WKTParser.CircularStringContext ctx) {
                CircularString lineString;
                if (ctx.coordinates() == null) {
                    lineString = CircularString.createEmpty();
                } else {
                    lineString = new CircularString(coordinates(ctx.coordinates(), dimension), dimension.get(), srid.get());
                }
                geometries.push(lineString);
            }

            @Override
            public void exitMultiPoint(WKTParser.MultiPointContext ctx) {
                MultiPoint lineString;
                if (ctx.coordinates() == null && ctx.coordinatesets() == null) {
                    lineString = MultiPoint.createEmpty();
                } else  {
                    lineString = multiPoint(ctx, dimension, srid);
                }
                geometries.push(lineString);
            }

            @Override
            public void exitPolygon(WKTParser.PolygonContext ctx) {
                Polygon polygon;
                if (ctx.coordinatesets() == null || ctx.coordinatesets().coordinates().size() == 0) {
                    polygon = Polygon.createEmpty();
                } else {
                    polygon = polygon(ctx.coordinatesets(), dimension, srid);
                }
                geometries.push(polygon);
            }

            @Override
            public void exitTriangle(WKTParser.TriangleContext ctx) {
                Triangle polygon;
                if (ctx.coordinatesets() == null || ctx.coordinatesets().coordinates().size() == 0) {
                    polygon = Triangle.createEmpty();
                } else {
                    polygon = triangle(ctx.coordinatesets().coordinates(), dimension, srid);
                }
                geometries.push(polygon);
            }

            @Override
            public void exitTin(WKTParser.TinContext ctx) {
                Tin tin;
                if (ctx.coordinatesetsset() == null || ctx.coordinatesetsset().coordinatesets().isEmpty()) {
                    tin = Tin.createEmpty();
                } else {
                    List<Triangle> triangles = new ArrayList<Triangle>();
                    for (WKTParser.CoordinatesetsContext cctx : ctx.coordinatesetsset().coordinatesets()) {
                        List<WKTParser.CoordinatesContext> coordinatesContexts = cctx.coordinates();
                        triangles.add(triangle(coordinatesContexts, dimension, srid));
                    }
                    tin = new Tin(triangles, dimension.get(), srid.get());
                }
                geometries.push(tin);
            }

            @Override
            public void exitMultiLineString(WKTParser.MultiLineStringContext ctx) {
                MultiLineString multiLineString;
                if (ctx.coordinatesets() == null || ctx.coordinatesets().coordinates().size() == 0) {
                    multiLineString = MultiLineString.createEmpty();
                } else {
                    List<LineString> linesStrings = new ArrayList<LineString>();
                    for(WKTParser.CoordinatesContext cctx : ctx.coordinatesets().coordinates()) {
                       linesStrings.add(new LineString(coordinates(cctx, dimension), dimension.get(), srid.get()));
                    }
                    multiLineString = new MultiLineString(linesStrings,dimension.get() , srid.get());
                }
                geometries.push(multiLineString);
            }

            @Override
            public void exitPolyHedralSurface(WKTParser.PolyHedralSurfaceContext ctx) {
                PolyHedralSurface polyHedralSurface;
                if (ctx.coordinatesetsset() == null || ctx.coordinatesetsset().coordinatesets().isEmpty()) {
                    polyHedralSurface = PolyHedralSurface.createEmpty();
                } else {
                    List<Polygon> polygons = new ArrayList<Polygon>();
                    for (WKTParser.CoordinatesetsContext cctx : ctx.coordinatesetsset().coordinatesets()) {
                        polygons.add(polygon(cctx, dimension, srid));
                    }
                    polyHedralSurface = new PolyHedralSurface(polygons, dimension.get(), srid.get());
                }
                geometries.push(polyHedralSurface);
            }

            @Override
            public void exitMultiPolygon(WKTParser.MultiPolygonContext ctx) {
                MultiPolygon multiPolygon;
                if (ctx.coordinatesetsset() == null || ctx.coordinatesetsset().coordinatesets().isEmpty()) {
                    multiPolygon = MultiPolygon.createEmpty();
                } else {
                    List<Polygon> polygons = new ArrayList<Polygon>();
                    for (WKTParser.CoordinatesetsContext cctx : ctx.coordinatesetsset().coordinatesets()) {
                        polygons.add(polygon(cctx, dimension, srid));
                    }
                    multiPolygon = new MultiPolygon(polygons, dimension.get(), srid.get());
                }
                geometries.push(multiPolygon);
            }

            @Override
            public void exitCurvePolygon(WKTParser.CurvePolygonContext ctx) {
                CurvePolygon curvePolygon;
                if (ctx.curvePolygonItems() == null || ctx.curvePolygonItems().curvePolygonElements() == null) {
                    curvePolygon = CurvePolygon.createEmpty();
                } else {
                    List<Curve> curves = new ArrayList<Curve>();
                    for(WKTParser.CurvePolygonElementsContext cpext : ctx.curvePolygonItems().curvePolygonElements()) {
                        if (cpext.circularString() != null) {
                            curves.add((Curve) geometries.pop());
                        } else if (cpext.compoundCurve() != null) {
                            curves.add((Curve) geometries.pop());
                        } else if (cpext.lineStringCoordinates() != null) {
                            curves.add(lineString(cpext.lineStringCoordinates().coordinates(), dimension, srid));
                        }
                    }
                    curvePolygon = new CurvePolygon(
                            curves.get(0),
                            curves.subList(1, curves.size()),
                            dimension.get(), srid.get());
                }
                geometries.push(curvePolygon);
            }

            @Override
            public void exitCompoundCurve(WKTParser.CompoundCurveContext ctx) {
                CompoundCurve compoundCurve;
                if (ctx.compoundCurveItems() == null || ctx.compoundCurveItems().compoundCurveElements() == null) {
                    compoundCurve = CompoundCurve.createEmpty();
                } else {
                    List<Curve> curves = new ArrayList<Curve>();
                    for(WKTParser.CompoundCurveElementsContext ccext : ctx.compoundCurveItems().compoundCurveElements()) {
                        if (ccext.circularString() != null) {
                            curves.add((Curve) geometries.pop());
                        } else if (ccext.lineStringCoordinates() != null) {
                            curves.add(lineString(ccext.lineStringCoordinates().coordinates(), dimension, srid));
                        }
                    }
                    compoundCurve = new CompoundCurve(
                            curves,
                            dimension.get(), srid.get());
                }
                geometries.push(compoundCurve);
            }

            @Override
            public void exitMultiCurve(WKTParser.MultiCurveContext ctx) {
                MultiCurve multiCurve;
                if (ctx.multiCurveItems() == null || ctx.multiCurveItems().multiCurveElements() == null) {
                    multiCurve = MultiCurve.createEmpty();
                } else {
                    List<Curve> curves = new ArrayList<Curve>();
                    for(WKTParser.MultiCurveElementsContext ccext : ctx.multiCurveItems().multiCurveElements()) {
                        if (ccext.circularString() != null) {
                            curves.add((CircularString) geometries.pop());
                        } else if (ccext.lineStringCoordinates() != null) {
                            curves.add(lineString(ccext.lineStringCoordinates().coordinates(), dimension, srid));
                        } else if (ccext.compoundCurve() != null) {
                            curves.add((CompoundCurve) geometries.pop());
                        }
                    }
                    multiCurve = new MultiCurve(
                            curves,
                            dimension.get(), srid.get());
                }
                geometries.push(multiCurve);
            }

            @Override
            public void exitMultiSurface(WKTParser.MultiSurfaceContext ctx) {
                MultiSurface multiSurface;
                if (ctx.multiSurfaceItems() == null || ctx.multiSurfaceItems().multiSurfaceElements() == null) {
                    multiSurface = MultiSurface.createEmpty();
                } else {
                    List<Surface> surfaces = new ArrayList<Surface>();
                    for(WKTParser.MultiSurfaceElementsContext ccext : ctx.multiSurfaceItems().multiSurfaceElements()) {
                        if (ccext.curvePolygon() != null) {
                            surfaces.add((CurvePolygon) geometries.pop());
                        } else if (ccext.polygonCoordinates() != null) {
                            surfaces.add(polygon(ccext.polygonCoordinates().coordinatesets(), dimension, srid));
                        }
                    }
                    multiSurface = new MultiSurface(
                            surfaces,
                            dimension.get(), srid.get());
                }
                geometries.push(multiSurface);
            }

            @Override
            public void exitGeometryCollection(WKTParser.GeometryCollectionContext ctx) {
                GeometryCollection geometryCollection;
                if (ctx.geometryCollectionItems() == null || ctx.geometryCollectionItems().geometryCollectionElements() == null) {
                    geometryCollection = GeometryCollection.createEmpty();
                } else {
                    List<Geometry> geometryList = new ArrayList<Geometry>();
                    for(WKTParser.GeometryCollectionElementsContext ccext : ctx.geometryCollectionItems().geometryCollectionElements()) {
                        geometryList.add(geometries.pop());
                    }
                    Collections.reverse(geometryList);
                    geometryCollection = new GeometryCollection(
                            geometryList,
                            dimension.get(), srid.get());
                }
                geometries.push(geometryCollection);
            }
        });
        parser.wkt();

        return geometries.isEmpty() ? null : geometries.pop();
    }

    private Coordinate coordinate(WKTParser.CoordinateContext ctx, Var<Dimension> dimension) {
        double x = Double.parseDouble(ctx.Number(0).getText());
        double y = Double.parseDouble(ctx.Number(1).getText());
        if (dimension.get() == Dimension.Two && ctx.Number().size() == 3) {
            dimension.set(Dimension.Three);
        } else if (dimension.get() == Dimension.Two && ctx.Number().size()== 4) {
            dimension.set(Dimension.ThreeMeasured);
        }
        if (dimension.get() == Dimension.TwoMeasured) {
            return Coordinate.create2DM(x,y, Double.parseDouble(ctx.Number(2).getText()));
        } if (dimension.get() == Dimension.Three) {
            return Coordinate.create3D(x,y, Double.parseDouble(ctx.Number(2).getText()));
        } if (dimension.get() == Dimension.ThreeMeasured) {
            return Coordinate.create3DM(x,y, Double.parseDouble(ctx.Number(2).getText()), Double.parseDouble(ctx.Number(3).getText()));
        } /*if (dimension == Dimension.Two)*/ {
            return Coordinate.create2D(x,y);
        }
    }

    private List<Coordinate> coordinates(WKTParser.CoordinatesContext ctx, Var<Dimension> dimension) {
        List<Coordinate> points = new ArrayList<Coordinate>();
        for(WKTParser.CoordinateContext cctx : ctx.coordinate()) {
            points.add(coordinate(cctx, dimension));
        }
        return points;
    }

    private List<Point> points(WKTParser.CoordinatesContext ctx, Var<Dimension> dimension, Var<String> srid) {
        List<Point> points = new ArrayList<Point>();
        for(Coordinate coordinate : coordinates(ctx, dimension)) {
            points.add(new Point(coordinate, dimension.get(), srid.get()));
        }
        return points;
    }

    private List<Point> points(WKTParser.CoordinatesetsContext ctx, Var<Dimension> dimension, Var<String> srid) {
        List<Point> points = new ArrayList<Point>();
        List<WKTParser.CoordinatesContext> coordinatesContexts = ctx.coordinates();
        for(WKTParser.CoordinatesContext cctx : coordinatesContexts) {
            points.addAll(points(cctx, dimension, srid));
        }
        return points;
    }

    private Point point(WKTParser.PointContext ctx, Var<Dimension> dimension, Var<String> srid) {
        return new Point(coordinate(ctx.coordinate(), dimension), dimension.get(), srid.get());
    }

    private LineString lineString(WKTParser.LineStringContext ctx, Var<Dimension> dimension, Var<String> srid) {
        return new LineString(coordinates(ctx.coordinates(), dimension), dimension.get(), srid.get());
    }

    private LineString lineString(WKTParser.CoordinatesContext ctx, Var<Dimension> dimension, Var<String> srid) {
        return new LineString(coordinates(ctx, dimension), dimension.get(), srid.get());
    }

    private Polygon polygon(WKTParser.CoordinatesetsContext cctx, Var<Dimension> dimension, Var<String> srid) {
        List<WKTParser.CoordinatesContext> coordinatesContexts = cctx.coordinates();
        LinearRing linearRing = new LinearRing(coordinates(coordinatesContexts.get(0), dimension), dimension.get(), srid.get());
        List<LinearRing> holes = new ArrayList<LinearRing>();
        for(int i = 1; i<coordinatesContexts.size(); i++) {
            LinearRing hole = new LinearRing(coordinates(coordinatesContexts.get(i), dimension), dimension.get(), srid.get());
            holes.add(hole);
        }
        return new Polygon(linearRing, holes, dimension.get(), srid.get());
    }

    private MultiPoint multiPoint(WKTParser.MultiPointContext ctx, Var<Dimension> dimension, Var<String> srid) {
        if (ctx.coordinates() != null) {
            return new MultiPoint(points(ctx.coordinates(), dimension, srid), dimension.get(), srid.get());
        } else {
            return new MultiPoint(points(ctx.coordinatesets(), dimension, srid), dimension.get(), srid.get());
        }
    }

    private Triangle triangle(List<WKTParser.CoordinatesContext> coordinatesContexts, Var<Dimension> dimension, Var<String> srid) {
        LinearRing linearRing = new LinearRing(coordinates(coordinatesContexts.get(0), dimension), dimension.get(), srid.get());
        List<LinearRing> holes = new ArrayList<LinearRing>();
        for(int i = 1; i<coordinatesContexts.size(); i++) {
            LinearRing hole = new LinearRing(coordinates(coordinatesContexts.get(i), dimension), dimension.get(), srid.get());
            holes.add(hole);
        }
        return new Triangle(linearRing, holes, dimension.get(), srid.get());
    }

    private static class Var<T> {
        private T variable = null;
        public T get() {
            return variable;
        }
        public void set(T var) {
            this.variable = var;
        }
    }

}