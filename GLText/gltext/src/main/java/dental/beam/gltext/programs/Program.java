package dental.beam.gltext.programs;

import android.opengl.GLES20;

import dental.beam.gltext.AttribVariable;
import dental.beam.gltext.Utilities;

public abstract class Program
{

	private int programHandle;
	private int vertexShaderHandle;
	private int fragmentShaderHandle;
	private boolean mInitialized;

	public Program ()
	{
		mInitialized = false;
	}

	public void init ()
	{
		init(null, null, null);
	}

	public void init (String vertexShaderCode, String fragmentShaderCode, AttribVariable[] programVariables)
	{
		vertexShaderHandle = Utilities.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
		fragmentShaderHandle = Utilities.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

		programHandle = Utilities.createProgram(
			vertexShaderHandle, fragmentShaderHandle, programVariables);

		mInitialized = true;
	}

	public int getHandle ()
	{
		return programHandle;
	}

	public void delete ()
	{
		GLES20.glDeleteShader(vertexShaderHandle);
		GLES20.glDeleteShader(fragmentShaderHandle);
		GLES20.glDeleteProgram(programHandle);
		mInitialized = false;
	}

	public boolean initialized ()
	{
		return mInitialized;
	}
}
