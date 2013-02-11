package com.android.texample;

import java.nio.FloatBuffer;

import android.graphics.PointF;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;
import com.android.texample_2.R;

abstract public class D3Shape {
	public static final int COORDS_PER_VERTEX = 3;
	private int VERTICES_NUM;
	protected static final int STRIDE_BYTES = COORDS_PER_VERTEX * Utilities.BYTES_PER_FLOAT;
	private static final String TAG = "D3Shape";
	private float[] mColor = new float[4];
	private Program mProgram;
	private int mMVPMatrixHandle;
	private int mPositionHandle;
	private int mColorHandle;
	private float[] mMVPMatrix = new float[16];
	private float[] mProjMatrix = new float[16];
	private float[] mVMatrix = new float[16];
	
	private FloatBuffer vertexBuffer;
	private int drawType;
	private float[] mMMatrix;
	private float[] mCenter;
	private float[] mCenterDefault;
	private float mVelocityY;
	private float mVelocityX;
	private float mScale;
	
	private static final float MIN_ALPHA = 0.1f;
	
	protected D3Shape(FloatBuffer vertBuffer, float[] colorData, int drType, Program program) {
		this();
		setColor(colorData);
		setDrawType(drType);
		setVertexBuffer(vertBuffer); //TODO: make this the default constructor without the vertBuffer argument and ssetVertexBuffer abstract
		setProgram(program);
	}
	
	protected D3Shape() {
		mMMatrix = new float[16];
		mCenterDefault = new float[] {0.0f, 0.0f, 0.0f, 1.0f};
		mCenter = new float[4];
		mVelocityX = mVelocityY = 0;
		Matrix.setIdentityM(getMMatrix(), 0);
		mScale = 1;
	    mPositionHandle = AttribVariable.A_Position.getHandle();
	}

	public void setVertexBuffer(FloatBuffer vertBuffer) {
		vertexBuffer = vertBuffer;
		if (vertBuffer != null) VERTICES_NUM = vertBuffer.capacity()/COORDS_PER_VERTEX;
		else {
			VERTICES_NUM = 0;
			Log.v(TAG, "Vertex buffer is null!");
		}
	}
	
	public void setModelMatrix(float[] modelMatrix) {
		setMMatrix(modelMatrix);
		Matrix.multiplyMV(mCenter, 0, getMMatrix(), 0, mCenterDefault, 0);
	}
	
	public void setColor(float[] color) {
		//mColor = Arrays.copyOf(color, color.length);
		for (int i = 0; i < color.length; ++i) {
			mColor[i] = color[i];
		}
	}
	
	public void setProgram(Program program) {
		mProgram = program;
		mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram.getHandle(), "u_MVPMatrix");
        mColorHandle = GLES20.glGetUniformLocation(mProgram.getHandle(), "u_Color");
	}
	
	public void setDrawType(int drawType) {
		this.drawType = drawType;
	}
	
	public void draw(float[] mVMatrix, float[] mProjMatrix, float interpolation) {
		if (mVelocityX*mVelocityY != 0) {
			setPosition(getCenterX() + interpolation * mVelocityX, 
				getCenterY() + interpolation * mVelocityY);
		}
		draw(mVMatrix, mProjMatrix);
	}
	
	public void draw(float[] mVMatrix, float[] mProjMatrix) {
		setDrawVMatrix(mVMatrix);
		setDrawProjMatrix(mProjMatrix);
				
		useProgram();
        useColor();
        drawBuffer(vertexBuffer, mMMatrix);
	}
	public void drawBuffer(FloatBuffer vertexBuffer, float[] modelMatrix) {
        Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, modelMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
		
		GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, 
        		GLES20.GL_FLOAT, false, STRIDE_BYTES, vertexBuffer);
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);
        //Matrix.m
        GLES20.glDrawArrays(drawType, 0, vertexBuffer.capacity()/COORDS_PER_VERTEX);
	}
	
	public float getCenterX() {
		return mCenter[0];
	}

	public float getCenterY() {
		return mCenter[1];
	}

	public PointF getCenter() {
		return new PointF(mCenter[0], mCenter[1]);
	}
	
	abstract public float getRadius();
	
	public int getVerticesNum() {
		return VERTICES_NUM;
	}
	
	public boolean fadeDone() {
		return fadeDone(MIN_ALPHA);
	}
	
	public boolean fadeDone(float minAlpha) {
		return D3Maths.compareFloats(mColor[3], minAlpha) <= 0;
	}

	public void fade(float fadeSpeed) {
		if (mColor[3] > 0) {
			mColor[3] -= fadeSpeed;
		}
	}

	public void setPosition(float x, float y) {
		Matrix.setIdentityM(mMMatrix, 0);
		Matrix.translateM(mMMatrix, 0, x, y, 0);
		Matrix.scaleM(mMMatrix, 0, mScale, mScale, 0);
		Matrix.multiplyMV(mCenter, 0, mMMatrix, 0, mCenterDefault, 0);
	}
	public void setPosition(float x, float y, float angleDeg) {
		setPosition(x, y);
		Matrix.rotateM(mMMatrix, 0, angleDeg, 0, 0, 1);
	}
	
	public float[] getMMatrix() {
		return mMMatrix;
	}

	public void setMMatrix(float[] mMMatrix) {
		this.mMMatrix = mMMatrix;
	}

	public FloatBuffer getVertexBuffer() {
		return vertexBuffer;
	}
	
	public float[] getColor() {
		return mColor;
	}

	public void setDrawProjMatrix(float[] projMatrix) {
		mProjMatrix = projMatrix;
	}

	public void setDrawVMatrix(float[] vMatrix) {
		mVMatrix = vMatrix;
	}
	
	public void useProgram() {
		GLES20.glUseProgram(mProgram.getHandle());
	}

	public void useColor() {
      GLES20.glUniform4fv(mColorHandle, 1, mColor , 0);
      GLES20.glEnableVertexAttribArray(mColorHandle);
	}
	
	public Program getProgram() {
		return mProgram;
	}
	
	public void noFade() {
		mColor[3] = 1f;
	}

	public void setVelocity(float vx, float vy) {
		mVelocityX = vx; mVelocityY = vy;
	}
	
	public void setScale(float scale) {
		mScale = scale;
//		Log.v(TAG, "Scale is " + scale);
		Matrix.scaleM(mMMatrix, 0, mScale, mScale, 0);
	}
	
	public float getScale() {
		return mScale;
	}
	
	public void grow(float growSpeed) {
		mScale = mScale*(1+growSpeed);
		Matrix.scaleM(mMMatrix, 0, (1+growSpeed), (1+growSpeed), 0);
	}
	
	public void shrink(float shrinkSpeed) {
		mScale = mScale*(1-shrinkSpeed);
		Matrix.scaleM(mMMatrix, 0, (1-shrinkSpeed), (1-shrinkSpeed), 0);
	}
}
