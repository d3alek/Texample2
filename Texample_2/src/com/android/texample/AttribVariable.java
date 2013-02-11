package com.android.texample;

import com.android.texample_2.R;

public enum AttribVariable {
//	U_MVPMatrix(1, "u_MVPMatrix", VarType.UNIFORM),
	A_Position(1, "a_Position"), 
//	U_Color(3, "u_Color", VarType.UNIFORM), 
	A_TexCoordinate(2, "a_TexCoordinate"), 
	A_MVPMatrixIndex(3, "a_MVPMatrixIndex");
//	U_Texture(5, "u_Texture", VarType.UNIFORM);
	
	private int mHandle;
	private String mName;

	private AttribVariable(int handle, String name) {
		mHandle = handle;
		mName = name;
	}

	public int getHandle() {
		return mHandle;
	}
	
	public String getName() {
		return mName;
	}
}
