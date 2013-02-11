package com.android.texample;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;
import com.android.texample_2.R;

public class D3Quad extends D3Shape {

	private float mRadius;
	
	protected static final int verticesNum = 4;
	private static final float[] quadVerticesDefault = {
		//X, Y, 0
		-0.5f, -0.5f, 0.0f,
		0.5f, -0.5f, 0.0f,
		-0.5f, 0.5f, 0.0f,
		0.5f, 0.5f, 0.0f,
	};
	
	protected static final float[] colorDefault = {0.0f, 1.0f, 0.0f, 1.0f};
	private static final String TAG = "D3Quad";
	
	private static FloatBuffer makeVerticesBuffer(float width, float height, float[] verticesDefault) {
		float[] quadVertices = new float[verticesNum * COORDS_PER_VERTEX];
		FloatBuffer buffer = ByteBuffer.allocateDirect(quadVertices.length * Utilities.BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		for (int i = 0; i < verticesNum; ++i) {
			quadVertices[i*3] = verticesDefault[i * 3] * width;
			quadVertices[i*3+1] = verticesDefault[i * 3 + 1] * height;
//			Log.v(TAG, "D3Quad vertex " + i + " " + quadVertices[i*3] + " " + quadVertices[i*3 + 1] + " " + quadVertices[i*3 + 2]);
		}
		buffer.put(quadVertices).position(0);
		return buffer;
	}
	
	public D3Quad(float width, float height, float[] verticesDefault, float[] drawColor, int drawType, Program program) {
		this(makeVerticesBuffer(width, height, verticesDefault), drawColor, drawType, program);
		mRadius = 0.5f*(width>height?width:height);
	}
	
	public D3Quad(float width, float height, Program program) {
		this(makeVerticesBuffer(width, height, quadVerticesDefault), colorDefault, GLES20.GL_TRIANGLE_STRIP, program);
		mRadius = 0.5f*(width>height?width:height);
	}
	
	public D3Quad(float width, float height, float[] drawColor, Program program) {
		this(makeVerticesBuffer(width, height, quadVerticesDefault), drawColor, GLES20.GL_TRIANGLE_STRIP, program);
		mRadius = 0.5f*(width>height?width:height);
	}
	
	public D3Quad(FloatBuffer verticesBuffer, float[] drawColor, int drawType, Program program) {	
		super(verticesBuffer, drawColor, drawType, program);
	}
	
	public D3Quad(float width, float height) {
		super();
		super.setVertexBuffer(makeVerticesBuffer(width, height, quadVerticesDefault));
		super.setColor(colorDefault);
		super.setDrawType(GLES20.GL_TRIANGLE_STRIP);
	}

	public float getRadius() {
		return mRadius;
	}
}
