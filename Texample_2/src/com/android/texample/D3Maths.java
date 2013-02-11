package com.android.texample;

import java.util.Random;

import android.util.FloatMath;
import com.android.texample_2.R;

public class D3Maths {
	
	public static final String TAG = "D3Maths";
	public static final float EPSILON = 0.00001f;
	public static final float PI = 3.14f;
	private static Random random = new Random();
	
	public static float angleBetweenVectors(float x1, float y1, float x2, float y2, float len1, float len2) {
		return (float) Math.toDegrees(Math.acos((x1*x2 + y1*y2)/(len1*len2)));
	}

	public static int compareFloats(float f, float g) {
		if (f < g + EPSILON && f > g - EPSILON) return 0;
		if (f < g - EPSILON) return -1;
		else return 1;
	}

	public static int compareFloatsTolerance(float f, float g, float tolerance) {
		if (f < g + tolerance && f > g - tolerance) return 0;
		if (f < g - tolerance) return -1;
		else return 1;
	}
	
	public static boolean circleContains(float centerX, float centerY,
			float radius, float x, float y) {
		return D3Maths.distance(centerX, centerY, x, y) <= radius;
	}

	public static boolean circlesIntersect(float c1X, float c1Y, 
			float rad1, float c2X, float c2Y, float rad2) {
		return D3Maths.distance(c1X, c1Y, c2X, c2Y) <= rad1 + rad2;
	}
	
	public static float distance(float mX, float mY, float fX, float fY) {
		return FloatMath.sqrt((mX-fX)*(mX-fX)+(mY-fY)*(mY-fY));
	}
	
	public static float distanceToCircle(float x, float y, float circleX,
			float circleY, float circleRadius) {
		return distance(x, y, circleX, circleY) - circleRadius; 
	}
	public static float det(float x1, float y1, float x2, float y2, float x3, float y3) {
		return x1 * y2 + y1 * x3 + x2 * y3 - x3 * y2 - y3 * x1 - x2 * y1;
	}

	public static boolean rectContains(float rX, float rY,
			float rWidth, float rHeight, float x, float y) {
		if (x > rX - rWidth/2 && x < rX + rWidth/2 
				&& y > rY - rHeight/2 && y < rY + rHeight/2) {
			return true;
		}
		
		return false;
	}

	public static float[] quadBezierCurveVertices(float[] a,
			float[] b, float[] c, float[] d, float dt, float scale) {
		int verticesNum = (int)(1/dt);
		float[] vertices = new float[D3Shape.COORDS_PER_VERTEX * (verticesNum + 1)];
		float t = 0;
		for (int i = 0; i < verticesNum; i++, t += dt) {
			vertices[i * D3Shape.COORDS_PER_VERTEX] = scale * (
					(1 - t) * (1 - t) * (1 - t) * a[0] 
					+ 3 * (1 - t) * (1 - t) * t * b[0]
					+ 3 * (1 - t) * t * t * c[0]
					+ t * t * t * d[0]);
			
			vertices[i * D3Shape.COORDS_PER_VERTEX + 1] = scale * (
					(1 - t) * (1 - t) * (1 - t) * a[1] 
					+ 3 * (1 - t) * (1 - t) * t * b[1]
					+ 3 * (1 - t) * t * t * c[1]
					+ t * t * t * d[1]);
	
			vertices[i * D3Shape.COORDS_PER_VERTEX + 2] = 0;
		}
		vertices[verticesNum * D3Shape.COORDS_PER_VERTEX] = d[0] * scale;
		vertices[verticesNum * D3Shape.COORDS_PER_VERTEX + 1] = d[1] * scale;
		vertices[verticesNum * D3Shape.COORDS_PER_VERTEX + 2] = d[2] * scale;
		return vertices;
	}

	public static float[] circleVerticesData(float r, int verticesNum) {
		float[] vertices = new float[verticesNum*D3Shape.COORDS_PER_VERTEX];
		float step = 2*3.14f/verticesNum;
		float angleRad = 0;
		for (int i = 0; i < verticesNum; ++i) {
			vertices[i*D3Shape.COORDS_PER_VERTEX] = r * FloatMath.sin(angleRad);
			vertices[i*D3Shape.COORDS_PER_VERTEX + 1] = r * FloatMath.cos(angleRad);
			vertices[i*D3Shape.COORDS_PER_VERTEX + 2] = 0;
			angleRad += step;
		}
		return vertices;
	}

	public static float getRandAngle() {
		return random.nextFloat()*PI*2;
	}

	
}
