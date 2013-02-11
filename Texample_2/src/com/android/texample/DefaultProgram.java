package com.android.texample;

import android.opengl.GLES20;
import com.android.texample_2.R;

public class DefaultProgram extends Program {
	
	private static final AttribVariable[] programVariables = {
		AttribVariable.A_Position
	};
	
	private static final String vertexShaderCode =
			"uniform mat4 u_MVPMatrix;      \n"     // A constant representing the combined model/view/projection matrix.
		 
		  + "attribute vec4 a_Position;     \n"     // Per-vertex position information we will pass in.
		 
		  + "void main()                    \n"     // The entry point for our vertex shader.
		  + "{                              \n"
		  + "   gl_Position = u_MVPMatrix   \n"     // gl_Position is a special variable used to store the final position.
		  + "               * a_Position;   \n"     // Multiply the vertex by the matrix to get the final point in
		  + "}                              \n";    // normalized screen coordinates.


	private static final String fragmentShaderCode =
				"precision mediump float;       \n"     // Set the default precision to medium. We don't need as high of a
	        // precision in the fragment shader.
			+ "uniform vec4 u_Color;          \n"
	        // triangle per fragment.
			+ "void main()                    \n"     // The entry point for our fragment shader.
			+ "{                              \n"
			+ "   gl_FragColor = u_Color;     \n"     // Pass the color directly through the pipeline.
			+ "}                              \n";
	
	
	public DefaultProgram() {
//		super(vertexShaderCode, fragmentShaderCode, programVariables);
	}
	
	@Override
	public void init() {
		super.init(vertexShaderCode, fragmentShaderCode, programVariables);
	}

}
