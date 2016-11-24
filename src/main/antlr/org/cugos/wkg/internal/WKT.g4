grammar WKT;

wkt: point | lineString | polygon | multiPoint | multiLineString | circularString | triangle | tin | polyHedralSurface | multiPolygon | compoundCurve | curvePolygon | multiCurve | multiSurface | geometryCollection;

point: srid? 'POINT' dimension? WhiteSpace* ( ( '(' coordinate     ')' ) | 'EMPTY' );

lineString: srid? 'LINESTRING' dimension? WhiteSpace* ( ( '(' coordinates    ')' ) | 'EMPTY' );

polygon: srid? 'POLYGON' dimension? WhiteSpace* ( ( '(' coordinatesets ')' ) | 'EMPTY' );

triangle: srid? 'TRIANGLE' dimension? WhiteSpace* ( ( '(' coordinatesets ')' ) | 'EMPTY' );

multiPoint: srid? 'MULTIPOINT' dimension? WhiteSpace* ( ( '(' coordinates ')' ) | ( '(' coordinatesets ')' ) | 'EMPTY' );

multiLineString: srid? 'MULTILINESTRING' dimension? WhiteSpace* ( ( '(' coordinatesets ')' ) | 'EMPTY' );

circularString: srid? 'CIRCULARSTRING' dimension? WhiteSpace* ( ( '(' coordinates    ')' ) | 'EMPTY' );

tin: srid? 'TIN' dimension? WhiteSpace* ( ( '(' coordinatesetsset ')' ) | 'EMPTY' );

polyHedralSurface: srid? 'POLYHEDRALSURFACE' dimension? WhiteSpace* ( ( '(' coordinatesetsset ')' ) | 'EMPTY' );

multiPolygon: srid? 'MULTIPOLYGON' dimension? WhiteSpace* ( ( '(' coordinatesetsset ')' ) | 'EMPTY' );

curvePolygon: srid? 'CURVEPOLYGON' dimension? WhiteSpace* ( ( '(' curvePolygonItems ')' ) | 'EMPTY' );

curvePolygonItems: (curvePolygonElements) (WhiteSpace* ',' WhiteSpace* (curvePolygonElements))* ;

curvePolygonElements: compoundCurve | circularString | lineStringCoordinates;

compoundCurve: srid? 'COMPOUNDCURVE' dimension? WhiteSpace* ( ( '(' compoundCurveItems ')' ) | 'EMPTY' );

compoundCurveItems: compoundCurveElements (WhiteSpace* ',' WhiteSpace* compoundCurveElements WhiteSpace*)* ;

compoundCurveElements: circularString | lineStringCoordinates;

multiCurve: srid? 'MULTICURVE' dimension? WhiteSpace* ( ( '(' multiCurveItems ')' ) | 'EMPTY' );

multiCurveItems: multiCurveElements (WhiteSpace* ',' WhiteSpace* multiCurveElements WhiteSpace*)* ;

multiCurveElements: compoundCurve | circularString | lineStringCoordinates;

multiSurface: srid? 'MULTISURFACE' dimension? WhiteSpace* ( ( '(' multiSurfaceItems ')' ) | 'EMPTY' );

multiSurfaceItems: multiSurfaceElements (WhiteSpace* ',' WhiteSpace* multiSurfaceElements WhiteSpace*)* ;

multiSurfaceElements: curvePolygon | polygonCoordinates;

geometryCollection: srid? 'GEOMETRYCOLLECTION' dimension? WhiteSpace* ( ( '(' geometryCollectionItems ')' ) | 'EMPTY' );

geometryCollectionItems: geometryCollectionElements (WhiteSpace* ',' WhiteSpace* geometryCollectionElements WhiteSpace*)* ;

geometryCollectionElements: wkt;

lineStringCoordinates: '(' WhiteSpace* coordinates WhiteSpace* ')' ;

polygonCoordinates: '(' WhiteSpace* coordinatesets WhiteSpace* ')' ;

coordinate: WhiteSpace* Number WhiteSpace* Number WhiteSpace* (Number WhiteSpace*)? (Number WhiteSpace*)?;

coordinates: coordinate (WhiteSpace* ',' WhiteSpace* coordinate)*;

coordinatesets: '(' WhiteSpace* coordinates WhiteSpace* ')' (WhiteSpace* ',' WhiteSpace* '(' WhiteSpace* coordinates WhiteSpace* ')')*;

coordinatesetsset: '(' WhiteSpace* coordinatesets WhiteSpace* ')' (WhiteSpace* ',' WhiteSpace* '(' WhiteSpace* coordinatesets WhiteSpace* ')')*;

empty: 'EMPTY';

srid: 'SRID=' Number ';';

dimension: WhiteSpace* (M | Z | ZM) WhiteSpace*;

M: 'M';

Z: 'Z';

ZM: 'ZM';

Number: ('+' | '-')? ('0'..'9')+ ('.' ('0'..'9')+)?;

WhiteSpace: (' ' | '\t')+;

NewLine:  '\r'? '\n';
