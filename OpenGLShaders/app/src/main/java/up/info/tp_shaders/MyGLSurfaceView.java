package up.info.tp_shaders;

import android.content.Context;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;

/**
 * Class to described the surface view. Mainly based on well-known code.
 */
public class MyGLSurfaceView extends GLSurfaceView
{
    private final MyGLRenderer renderer;
    private final Scene scene;

    private final int centerWidth;

    private boolean isMoving;

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

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        centerWidth = size.x / 2;
    }

    private final float SCALE_FACTOR = -0.008F;
    private float previousx = 0;
    private float previousy = 0;

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
        
        MainActivity.log(String.valueOf(e.getAction()));

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isMoving = x < centerWidth;
                break;
            case MotionEvent.ACTION_MOVE:
                if (isMoving) {
                    double angleyrad = Math.toRadians(this.scene.angley);
                    this.scene.posx += SCALE_FACTOR * (deltax * Math.cos(angleyrad) - deltay * Math.sin(angleyrad));
                    this.scene.posz += SCALE_FACTOR * (deltax * Math.sin(angleyrad) + deltay * Math.cos(angleyrad));
                    if (this.scene.posx >= 3) this.scene.posx -= 1.0F;
                    if (this.scene.posx <= -3) this.scene.posx += 1.0F;
                    if (this.scene.posz >= 3) this.scene.posz -= 1.0F;
                    if (this.scene.posz <= -9) this.scene.posz += 1.0F;
                } else {
                    this.scene.anglex += deltay * SCALE_FACTOR * 10;
                    this.scene.angley += deltax * SCALE_FACTOR * 10;
                }
                break;        
}

        previousx = x;
        previousy = y;
        this.requestRender();
        return true;
    }

}
