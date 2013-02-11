package com.android.texample;

import java.nio.FloatBuffer;

import android.opengl.GLES20;
import com.android.texample_2.R;

public class D3Circle extends D3Shape {

	private float mRadius;

	public D3Circle(float r, float[] color, int vertices) {
		super();
		super.setVertexBuffer(makeCircleVerticesBuffer(r, vertices));
		super.setColor(color);
		super.setDrawType(GLES20.GL_LINE_LOOP);
		mRadius = r;
	}

	private static FloatBuffer makeCircleVerticesBuffer(float r, int vertices) {
		FloatBuffer buffer = Utilities.newFloatBuffer(D3Maths.circleVerticesData(r, vertices));
		return buffer;
	}

	@Override
	public float getRadius() {
		return mRadius;
	}

}
