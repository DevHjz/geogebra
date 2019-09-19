package org.geogebra.common.geogebra3D.euclidian3D.printer3D;

import org.geogebra.common.kernel.geos.GeoElement;

/**
 * OpenSCAD format
 */
public class FormatScad implements Format {

	@Override
	public void getExtension(StringBuilder sb) {
		sb.append(".scad");
	}

	@Override
	public void getScriptStart(StringBuilder sb) {
		sb.append("// Created with GeoGebra www.geogebra.org");
	}

	@Override
	public void getScriptEnd(StringBuilder sb) {
		sb.append("");
	}

	@Override
	public void getObjectStart(StringBuilder sb, String type, GeoElement geo, boolean transparency) {
		sb.append("\n///////////////////////\n// ");
		sb.append(type);
		sb.append(": ");
		sb.append(geo.getLabelSimple());
	}

	@Override
	public void getPolyhedronStart(StringBuilder sb) {
		sb.append("\npolyhedron(");
	}

	@Override
	public void getPolyhedronEnd(StringBuilder sb) {
		sb.append("\nconvexity = 10);\n");
	}

	@Override
	public void getVerticesStart(StringBuilder sb, int count) {
		sb.append("\n    points = [");
	}

	@Override
	public void getVertices(StringBuilder sb, double x, double y, double z) {
		sb.append("\n        [");
		sb.append(x);
		sb.append(",");
		sb.append(y);
		sb.append(",");
		sb.append(z);
		sb.append("]");
	}

	@Override
	public void getVerticesSeparator(StringBuilder sb) {
		sb.append(",");
	}

	@Override
	public void getVerticesEnd(StringBuilder sb) {
		sb.append("\n    ],");
	}

	@Override
	public void getFacesStart(StringBuilder sb, int count) {
		sb.append("\n    faces = [");
	}

	@Override
	public void getFaces(StringBuilder sb, int v1, int v2, int v3, int normal) {
		sb.append("\n        [");
		sb.append(v1);
		sb.append(",");
		sb.append(v3);
		sb.append(",");
		sb.append(v2);
		sb.append("]");
	}

	@Override
	public void getFacesSeparator(StringBuilder sb) {
		sb.append(",");
	}

	@Override
	public void getFacesEnd(StringBuilder sb) {
		sb.append("\n    ],");
	}

	@Override
	public void getListType(StringBuilder sb, int type) {
		// not used
	}

	@Override
	public void getNormalsStart(StringBuilder sb, int count) {
		// not used
	}

	@Override
	public void getNormal(StringBuilder sb, double x, double y, double z) {
		// not used
	}

	@Override
	public void getNormalsSeparator(StringBuilder sb) {
		// not used
	}

	@Override
	public void getNormalsEnd(StringBuilder sb) {
		// not used
	}

	@Override
	public boolean handlesSurfaces() {
		return false;
	}

	@Override
	public boolean needsClosedObjects() {
		return true;
	}

	@Override
	public boolean handlesNormals() {
		return false;
	}

}