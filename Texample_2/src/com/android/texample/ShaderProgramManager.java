package com.android.texample;



public class ShaderProgramManager {
	
	private DefaultProgram defaultProgram;
	private TextProgram textProgram;
	private BatchTextProgram batchTextProgram;
	
	public ShaderProgramManager() {
		defaultProgram = new DefaultProgram();
		textProgram = new TextProgram();
		batchTextProgram = new BatchTextProgram();
	}

	public Program getDefaultProgram() {
		if (!defaultProgram.initialized()) {
			defaultProgram.init();
		}
		return defaultProgram;
	}

	public void clear() {
		defaultProgram.delete(); textProgram.delete(); batchTextProgram.delete();
//		textProgram.delete(); batchTextProgram.delete();
	}

	public Program getTextProgram() {
		if (!textProgram.initialized()) {
			textProgram.init();
		}
		return textProgram;
	}

	public Program getBatchTextProgram() {
		if (!batchTextProgram.initialized()) {
			batchTextProgram.init();
		}
		return batchTextProgram;
	}
}
