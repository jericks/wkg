[![Build Status](https://travis-ci.org/jericks/wkg.svg?branch=master)](https://travis-ci.org/jericks/wkg)

Well Known Geometry
===================
Well Known Geometry (WKG) is a Java Library that reads and writes Geometries to and from well known text (WKT) and well known binary (WKB).

It does not use the Java Topology Suite (JTS) because it supports Geometry types that JTS does not yet support.  

It can parse EWKT or WKT with a spatial reference system identifier. 

It can parse Geometry with XY, XYZ, XYM, or XYZM coordinates.

It can read and write WKB and [EWKB](http://lists.osgeo.org/pipermail/postgis-devel/2004-December/000710.html).

Example Geometries in WKT:

    POINT (1 1)

    POINT Z (1 2 3)

    POINT M (1 2 3)

    POINT ZM (1 2 3 4)

    SRID=4326;POINT (1 1)

    LINESTRING (101 234,345 567)

    POLYGON ((35 10, 45 45, 15 40, 10 20, 35 10),(20 30, 35 35, 30 20, 20 30))

    MULTIPOINT (10 40, 40 30, 20 20, 30 10)

    MULTIPOINT ((10 40), (40 30), (20 20), (30 10))

    MULTILINESTRING ((0.0 0.0, 1.0 1.0, 1.0 2.0), (2.0 3.0, 3.0 2.0, 5.0 4.0))

    MULTIPOLYGON (((40 40, 20 45, 45 30, 40 40)), ((20 35, 10 30, 10 10, 30 5, 45 20, 20 35),(30 20, 20 15, 20 25, 30 20)))

    GEOMETRYCOLLECTION(POINT(4 6),LINESTRING(4 6,7 10))

    CIRCULARSTRING (1.0 1.0, 5.0 5.0, 2.0 2.0)

    COMPOUNDCURVE(CIRCULARSTRING(1 0, 0 1, -1 0), (-1 0, 2 0))

    CURVEPOLYGON(CIRCULARSTRING(0 0, 4 0, 4 4, 0 4, 0 0),(1 1, 3 3, 3 1, 1 1))

    MULTICURVE ((5.0 5.0, 3.0 5.0, 3.0 3.0, 0.0 3.0), CIRCULARSTRING (0.0 0.0, 2.0 1.0, 2.0 2.0))

    MULTISURFACE(CURVEPOLYGON(CIRCULARSTRING(0 0, 4 0, 4 4, 0 4, 0 0),(1 1, 3 3, 3 1, 1 1)),((10 10, 14 12, 11 10, 10 10),(11 11, 11.5 11, 11 11.5, 11 11)))

    POLYHEDRALSURFACE (((40 40, 20 45, 45 30, 40 40)),((20 35, 10 30, 10 10, 30 5, 45 20, 20 35),(30 20, 20 15, 20 25, 30 20)))

    TIN (((0 0, 1 0, 0 1, 0 0)), ((0 0, 0 1, 1 1, 0 0)))

    TRIANGLE ((0.0 0.0, 0.0 1.0, 1.0 1.0, 0.0 0.0))

Use
---
You can use the **org.cugos.wkg.WTKReader** to read Geometry from a WKT string.

```groovy
import org.cugos.wkg.WKTReader;
import org.cugos.wkg.Point;

String wkt = "POINT (1.0 2.0)";
WKTReader reader = new WKTReader();
Point point = (Point) reader.read(wkt);
```

Once you have parse a WKT String into a **org.cugos.wkg.Geometry** you can write it back to WKT using the **org.cugos.wkg.WKTWriter**.

```groovy
import org.cugos.wkg.WKTWriter;
import org.cugos.wkg.Coordinate;
import org.cugos.wkg.Dimension;
import org.cugos.wkg.CircularString;

WKTWriter writer = new WKTWriter();
CircularString cs = new CircularString(Arrays.asList(
    Coordinate.create2D(1, 1),
    Coordinate.create2D(5, 5),
    Coordinate.create2D(2, 2)),
    Dimension.Two)
)
String wkt = writer.write(cs);
```
You can also read and write WKB and EWKB.

```groovy
import org.cugos.wkg.WKTReader;
import org.cugos.wkg.Geometry;

import org.cugos.parboiledwkb.WKBWriter;
import org.cugos.parboiledwkb.WKBReader;
import org.cugos.wkg.WKB.Endian;
import org.cugos.wkg.WKB.Type;

WKTReader reader = new WKTReader();
Geometry geometry = reader.read("POINT (2 4)");

WKBWriter writer = new WKBWriter(Type.WKB, Endian.Big);
byte[] wkb = wkb.write(geometry);

WKBReader wkbReader = new WKBReader();
Geometry point = wkb.read(wkb);
```

Licene
------
Well Known Geometry is open source and licensed under the MIT License.