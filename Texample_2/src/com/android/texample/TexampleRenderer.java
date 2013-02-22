// This is based on the OpenGL ES 1.0 sample application from the Android Developer website:
// http://developer.android.com/resources/tutorials/opengl/opengl-es10.html

package com.android.texample;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;
import com.android.texample_2.R;

public class TexampleRenderer implements GLSurfaceView.Renderer  {

	private static final String TAG = "TexampleRenderer.java";
	private GLText glText;                             // A GLText Instance
	private Context context;                           // Context (from Activity)

	private int width = 100;                           // Updated to the Current Width + Height in onSurfaceChanged()
	private int height = 100;
	private float[] mProjMatrix = new float[16];
	private float[] mVMatrix = new float[16];
	private float[] mVPMatrix = new float[16];
	private ShaderProgramManager sm;

	public TexampleRenderer(Context context)  {
		super();
		this.context = context;                         // Save Specified Context
	}

	public void onSurfaceCreated(GL10 unused, EGLConfig config) {
		// Set the background frame color
		GLES20.glClearColor( 0.5f, 0.5f, 0.5f, 1.0f );

		sm = new ShaderProgramManager();
		// Create the GLText
//		glText = new GLText( sm.getTextProgram(), context.getAssets() );
		//glText = new GLText( sm.getTextProgram(), context.getAssets());

		glText = new GLText(sm.getBatchTextProgram(), context.getAssets());

		// Load the font from file (set size + padding), creates the texture
		// NOTE: after a successful call to this the font is ready for rendering!
		glText.load( "Roboto-Regular.ttf", 14, 2, 2 );  // Create Font (Height: 14 Pixels / X+Y Padding 2 Pixels)

		//      GLES20.glClearColor(bgColor[0], bgColor[1], bgColor[2], bgColor[3]);
				GLES20.glEnable(GLES20.GL_BLEND);
				GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		//		glText = new GLText(sm.getBatchTextProgram(), mContext.getAssets());
		//		mGLText.load( "Roboto-Regular.ttf", 14, 2, 2 );  // Create Font (Height: 14 Pixels / X+Y Padding 2 Pixels)
	}

	public void onDrawFrame(GL10 unused) {
		// Redraw background color
		//      gl.glClear( GL10.GL_COLOR_BUFFER_BIT );
		int clearMask = GLES20.GL_COLOR_BUFFER_BIT;

		//		if (MultisampleConfigChooser.usesCoverageAa()) {
		//			final int GL_COVERAGE_BUFFER_BIT_NV = 0x8000;
		//          clearMask |= GL_COVERAGE_BUFFER_BIT_NV;
		//		}

		GLES20.glClear(clearMask);
		
		// Set to ModelView mode
//		gl.glMatrixMode( GL10.GL_MODELVIEW );           // Activate Model View Matrix
//		gl.glLoadIdentity();                            // Load Identity Matrix

		// enable texture + alpha blending
		// NOTE: this is required for text rendering! we could incorporate it into
		// the GLText class, but then it would be called multiple times (which impacts performance).
//		gl.glEnable( GL10.GL_TEXTURE_2D );              // Enable Texture Mapping
//		gl.glEnable( GL10.GL_BLEND );                   // Enable Alpha Blend
//		gl.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA );  // Set Alpha Blend Function
		Matrix.multiplyMM(mVPMatrix, 0, mProjMatrix, 0, mVMatrix, 0);
		// TEST: render the entire font texture
//		gl.glColor4f( 1.0f, 1.0f, 1.0f, 1.0f );         // Set Color to Use
//		glText.drawTexture( 100, 100, mVPMatrix);            // Draw the Entire Texture
//
//		// TEST: render some strings with the font
		glText.begin( 1.0f, 1.0f, 1.0f, 1.0f, mVPMatrix );         // Begin Text Rendering (Set Color WHITE)
		glText.drawC( "Test String :)", 0, 0, 10 );          // Draw Test String
		glText.draw( "Line 1", 50, 50 );                // Draw Test String
		glText.draw( "Line 2", 100, 100 );              // Draw Test String
		glText.end();                                   // End Text Rendering
		
		glText.begin( 0.0f, 0.0f, 1.0f, 1.0f, mVPMatrix );         // Begin Text Rendering (Set Color BLUE)
		glText.draw( "More Lines...", 50, 150 );        // Draw Test String
		glText.draw( "The End.", 50, 150 + glText.getCharHeight() );  // Draw Test String
		glText.end();                                   // End Text Rendering

		float[] shape1color = {1.0f, 0.0f, 0.0f, 0.0f};
		D3Shape shape = new D3Quad(100f, 100f, sm.getDefaultProgram());
		shape.setPosition(640, 376);
		shape.setColor(shape1color);
		shape.draw(mVMatrix, mProjMatrix);
		
		float[] shape2color = {0.0f, 1.0f, 0.0f, 0.0f};
		D3Shape shape2 = new D3Circle(100, shape2color, 100);
		shape2.setProgram(sm.getDefaultProgram());
		shape2.draw(mVMatrix, mProjMatrix);
		
		float[] shape3color = {0.0f, 0.0f, 1.0f, 0.0f};
		D3Shape shape3 = new D3Circle(100, shape3color, 100);
		shape3.setPosition(-1100, -400);
		shape3.setProgram(sm.getDefaultProgram());
		
//		Matrix.multiplyMM(mVPMatrix, 0, mProjMatrix, 0, mVMatrix, 0);

		shape3.draw(mVMatrix, mProjMatrix);
		
		// disable texture + alpha
//		gl.glDisable( GL10.GL_BLEND );                  // Disable Alpha Blend
//		gl.glDisable( GL10.GL_TEXTURE_2D );             // Disable Texture Mapping
	}

	public void onSurfaceChanged(GL10 unused, int width, int height) { //		gl.glViewport( 0, 0, width, height ); 
		Log.v(TAG, "Width " + width + " height " + height);
//		// Setup orthographic projection
//		gl.glMatrixMode( GL10.GL_PROJECTION );          // Activate Projection Matrix
//		gl.glLoadIdentity();                            // Load Identity Matrix
//		gl.glOrthof(                                    // Set Ortho Projection (Left,Right,Bottom,Top,Front,Back)
//				0, width,
//				0, height,
//				1.0f, -1.0f
//				);

		GLES20.glViewport(0, 0, width, height);
		float ratio = (float) width / height;

		if (width > height) {
			Matrix.frustumM(mProjMatrix , 0, -ratio, ratio, -1, 1, 1, 10);
		}
		// Save width and height
		this.width = width;                             // Save Current Width
		this.height = height;                           // Save Current Height
		//TODO: whyyyyy!!!!!
		Matrix.orthoM(mVMatrix, 0,
				-height/2,
				height/2,
				-height/2,
				height/2, 0.1f, 100f);
//		Log.v(TAG, "Ortho matrix: " + -width/2 + " " + width/2 + " " + -height/2 + " " + height/2 + " ratio is " + ratio);
	}
}
