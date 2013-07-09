// This is based on the OpenGL ES 1.0 sample application from the Android Developer website:
// http://developer.android.com/resources/tutorials/opengl/opengl-es10.html

package com.android.texample2;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Texample2 extends Activity
{
   private GLSurfaceView glView;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate( savedInstanceState );
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
      // Create a GLSurfaceView instance and set it
      // as the ContentView for this Activity.
      glView = new TexampleSurfaceView( this );
      setContentView( glView );
   }

   @Override
   protected void onPause() {
      super.onPause();
      // The following call pauses the rendering thread.
      // If your OpenGL application is memory intensive,
      // you should consider de-allocating objects that
      // consume significant memory here.
      glView.onPause();
   }

   @Override
   protected void onResume() {
      super.onResume();
      // The following call resumes a paused rendering thread.
      // If you de-allocated graphic objects for onPause()
      // this is a good place to re-allocate them.
      glView.onResume();
   }
}

class TexampleSurfaceView extends GLSurfaceView {

   public TexampleSurfaceView(Context context){
      super( context );
      
      // Set to use OpenGL ES 2.0
      setEGLContextClientVersion(2); 

      // Set the Renderer for drawing on the GLSurfaceView
      setRenderer( new Texample2Renderer( context ) );
   }
}
