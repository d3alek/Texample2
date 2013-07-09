package com.android.texample2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;
import android.util.Log;

public class Utilities {

	public static final int BYTES_PER_FLOAT = 4;
	public static final int BYTES_PER_SHORT = 2;
	private static final String TAG = "Utilities";
	
	public static int createProgram(int vertexShaderHandle, int fragmentShaderHandle, AttribVariable[] variables) {
		int  mProgram = GLES20.glCreateProgram();
		
		if (mProgram != 0) {
	        GLES20.glAttachShader(mProgram, vertexShaderHandle);
	        GLES20.glAttachShader(mProgram, fragmentShaderHandle);
	
	        for (AttribVariable var: variables) {
	        	GLES20.glBindAttribLocation(mProgram, var.getHandle(), var.getName());
	        }   
	        
	        GLES20.glLinkProgram(mProgram);
	     
	        final int[] linkStatus = new int[1];
	        GLES20.glGetProgramiv(mProgram, GLES20.GL_LINK_STATUS, linkStatus, 0);
	
	        if (linkStatus[0] == 0)
	        {
	        	Log.v(TAG, GLES20.glGetProgramInfoLog(mProgram));
	            GLES20.glDeleteProgram(mProgram);
	            mProgram = 0;
	        }
	    }
	     
	    if (mProgram == 0)
	    {
	        throw new RuntimeException("Error creating program.");
	    }
		return mProgram;
	}

	public static int loadShader(int type, String shaderCode){
	    int shaderHandle = GLES20.glCreateShader(type);
	     
	    if (shaderHandle != 0)
	    {
	        GLES20.glShaderSource(shaderHandle, shaderCode);
	        GLES20.glCompileShader(shaderHandle);
	    
	        // Get the compilation status.
	        final int[] compileStatus = new int[1];
	        GLES20.glGetShaderiv(shaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
	     
	        // If the compilation failed, delete the shader.
	        if (compileStatus[0] == 0)
	        {
	        	Log.v(TAG, "Shader fail info: " + GLES20.glGetShaderInfoLog(shaderHandle));
	            GLES20.glDeleteShader(shaderHandle);
	            shaderHandle = 0;
	        }
	    }
	    
	     
	    if (shaderHandle == 0)
	    {
	        throw new RuntimeException("Error creating shader " + type);
	    }
	    return shaderHandle;
	}

	public static FloatBuffer newFloatBuffer(float[] verticesData) {
		FloatBuffer floatBuffer;
		floatBuffer = ByteBuffer.allocateDirect(verticesData.length * BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		floatBuffer.put(verticesData).position(0);
		return floatBuffer;
	}
}
