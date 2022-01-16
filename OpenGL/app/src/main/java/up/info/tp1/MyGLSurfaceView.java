package up.info.tp1;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import up.info.tp1.MyGLRenderer;
import up.info.tp1.Scene;

/**
 * Class to described the surface view. Mainly based on well-known code.
 */
public class MyGLSurfaceView extends GLSurfaceView
{
    private final MyGLRenderer renderer;
    private final Scene scene;

    public MyGLSurfaceView(Context context, Scene scene)
    {
        super(context);
        this.scene=scene;

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
        this.renderer = new MyGLRenderer(this,scene);
        setRenderer(this.renderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        //setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    private final float SCALE_FACTOR = 0.005F;
    private float previousx;
    private float previousy;

    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        float deltax = x - previousx; // motion along x axis
        float deltay = y - previousy; // motion along y axis

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (deltay > 1) {
                    this.scene.anglex += 0.5F;
                    this.scene.posz += 0.1F;
                } else if (deltay < -1) {
                    this.scene.anglex -= 0.5F;
                    this.scene.posz -= 0.1F;
                }
                if (deltax > 1) {
                    this.scene.angley += 0.5F;
                    this.scene.posx += 0.1F;
                } else if (deltax < -1) {
                    this.scene.angley -= 0.5F;
                    this.scene.posx -= 0.1F;
                }
                break;
        }

        previousx = x;
        previousy = y;
        this.requestRender();
        return true;
    }

}
