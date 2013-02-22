package com.android.texample;



public class TextProgram extends Program {

	private static final AttribVariable[] programVariables = {
		AttribVariable.A_Position, AttribVariable.A_TexCoordinate
	};
	
	private static final String vertexShaderCode =
			"uniform mat4 u_MVPMatrix;      \n"     // A constant representing the combined model/view/projection matrix.
		  + "attribute vec4 a_Position;     \n"     // Per-vertex position information we will pass in.
		  + "attribute vec2 a_TexCoordinate;\n"     // Per-vertex texture coordinate information we will pass in
		  + "varying vec2 v_TexCoordinate;  \n"   // This will be passed into the fragment shader.
		  + "void main()                    \n"     // The entry point for our vertex shader.
		  + "{                              \n"
		  + "   v_TexCoordinate = a_TexCoordinate; \n"
		  + "   gl_Position = u_MVPMatrix   \n"     // gl_Position is a special variable used to store the final position.
		  + "               * a_Position;   \n"     // Multiply the vertex by the matrix to get the final point in
		  											// normalized screen coordinates.
		  + "}                              \n";    


	private static final String fragmentShaderCode =
			"uniform sampler2D u_Texture;       \n"    // The input texture.
			+	"precision mediump float;       \n"     // Set the default precision to medium. We don't need as high of a
	        // precision in the fragment shader.
			+ "uniform vec4 u_Color;          \n"
	        // triangle per fragment.
			
			+ "varying vec2 v_TexCoordinate;  \n" // Interpolated texture coordinate per fragment.
			
			+ "void main()                    \n"     // The entry point for our fragment shader.
			+ "{                              \n"
			+ "   gl_FragColor = u_Color * texture2D(u_Texture, v_TexCoordinate);\n"     // Pass the color directly through the pipeline.
			//+ "   gl_FragColor.a *= u_Color.a;\n"
			+ "}                             \n";
	
	public TextProgram() {
//		super(vertexShaderCode, fragmentShaderCode, programVariables);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		super.init(vertexShaderCode, fragmentShaderCode, programVariables);
	}
	
}
