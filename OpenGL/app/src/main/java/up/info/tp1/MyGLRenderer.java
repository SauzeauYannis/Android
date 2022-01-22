package up.info.tp1;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;

public class MyGLRenderer implements GLSurfaceView.Renderer
{
    /**
     * Some useful colors...
     */
    static public final float[] black={0.0F,0.0F,0.0F,1.F};
    static public final float[] white={1.0F,1.0F,1.0F,1.F};
    static public final float[] gray={0.5F,0.5F,0.5F,1.F};
    static public final float[] lightgray={0.8F,0.8F,0.8F,1.F};
    static public final float[] darkgray={0.2F,0.2F,0.2F,1.F};
    static public final float[] red={1.F,0.F,0.F,1.F};
    static public final float[] green={0.F,1.F,0.F,1.F};
    static public final float[] blue={0.F,0.F,1.F,1.F};
    static public final float[] yellow={1.F,1.F,0.F,1.F};
    static public final float[] magenta={1.F,0.F,1.F,1.F};
    static public final float[] cyan={0.F,1.0F,1.F,1.F};
    static public final float[] orange={1.F,0.5F,0.F,1.F};
    /**
     * Reference to the scene environment
     */
    private Scene scene;
    /**
     * Reference to the OpenGL surface view
     */
    private GLSurfaceView view;
    /**
     * Shaders
     */
    private NoLightShaders shaders;
    /**
     * Projection matrix to provide to the shader
     */
    private final float[] projectionmatrix = new float[16];

    /**
     * @return the current Shader
     */
    public NoLightShaders getShaders()
    {
        return this.shaders;
    }

    /**
     * @return the scene environment
     */
    public Scene getScene() { return this.scene; }

    /**
     * @return the surface view
     */
    public GLSurfaceView getView()
    {
        return this.view;
    }


    /**
     * Constructor
     * @param view OpenGL surface view
     * @param scene the scene environment
     */
    public MyGLRenderer(final GLSurfaceView view, final Scene scene)
    {
        this.view=view;
        this.scene =scene;
        this.shaders=null;
    }

    /**
     * general routine called when the support drawing function is created
     * Aims at initializing all graphics data
     * @param unused
     * @param config
     */
    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config)
    {
        // Create shader
        this.shaders=new NoLightShaders(this); // or other shaders
        checkGlError("Shader Creation");

        scene.initGraphics(this);
    }


    /**
     * Called on newdisplay events
     * @param unused
     */
    @Override
    public void onDrawFrame(GL10 unused)
    {
        // Display the scene:
        // Drawing the scene is mandatory, since display buffers are swapped in any case.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        this.scene.draw(this);
        // Dirty mode, so post a new display request to loop
        this.view.requestRender();
    }


    /**
     * Called when the surface has changed (screen rotation, for instance)
     * always called at the beginning, before first display.
     * @param unused
     * @param width
     * @param height
     */
    @Override
    public void onSurfaceChanged(GL10 unused,final int width,final int height) {
        // Adjust the viewport based on geometry changes,
        GLES20.glViewport(0, 0, width, height);

        // Compute projection matrix
        float ratio = (float) width / height;
        if (width > height) // Landscape mode
        {
            Matrix.perspectiveM(this.projectionmatrix, 0, 60.F, ratio, 0.1F, 100.F);
        } else // Portrait mode
        {
            Matrix.perspectiveM(this.projectionmatrix, 0, 45.F, ratio, 0.1F, 100.F);
        }
        shaders.setProjectionMatrix(this.projectionmatrix);
    }

    /**
     * Utility method for debugging OpenGL calls.
     * If the operation is not successful, the check throws an error.
     *
     * @param gloperation - Name of the OpenGL call that was called
     */
    public static void checkGlError(String gloperation)
    {
        int firsterror,error;

        // Check if there is an error
        error = GLES20.glGetError();
        if (error==GLES20.GL_NO_ERROR) return;

        // In case of error, display the error list and throw an exception...
        firsterror=error;
        do
        {
            MainActivity.log("Gl Error "+error+" after "+gloperation);
            error = GLES20.glGetError();
        } while (error!=GLES20.GL_NO_ERROR);
        throw new RuntimeException("GL Error "+firsterror+" after "+gloperation);

    }

    /**
     * Utility method to load a texture defined as a resource
     * This method is freely inspired from www.learnopengles.com
     * @param context Context of application
     * @param resourceId Id of the application resource to load
     *                   Typically, this resource is something like R.drawable.name_of_the_file
     * @return Texture handle
     */
    public static int loadTexture(final Context context,final int resourceId)
    {
        // Create a new texture handle to store the loaded texture
        final int[] textureHandle = new int[1];
        GLES20.glGenTextures(1, textureHandle, 0);

        if (textureHandle[0] != 0)
        {
            // Load the given ressource as a bitmap
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;   // No pre-scaling

            // Read the resource
            final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);

            // Bind to the allocated texture handle so that the following instructions are done
            // within this texture handle
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

            // Set filtering parameters (can be changed to allow a better visualization)
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

            // Load the bitmap into the bound texture
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

            // Unbind texture
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,0);

            // Recycle the bitmap, it has been loaded into the graphics card memory and is no longer
            // used in the main memory
            bitmap.recycle();
        }
        return textureHandle[0];
    }
}

