package up.info.tp1;

import android.opengl.GLES20;
import android.opengl.Matrix;

public class Scene {

    /**
     * A constant for the size of the wall
     */
    static final float wallsize = 2.5F;

    /**
     * An angle used to animate the viewer
     */
    float anglex, angley;

    /**
     * A position used to move the viewer
     */
    float posx, posz;

    private Room room;
    private Sphere sphere;

    /**
     * Constructor : build each wall, the floor and the ceiling as quads
     */
    public Scene()
    {
        // Init observer's view angles and positions
        anglex = 0.F;
        angley = 0.F;
        posx = 0.F;
        posz = 0.F;
    }


    /**
     * Init some OpenGL and shaders uniform data to render the simulation scene
     * @param renderer Renderer
     */
    public void initGraphics(MyGLRenderer renderer)
    {
        MainActivity.log("Initializing graphics");
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        // Allow back face culling !!
        GLES20.glDisable(GLES20.GL_CULL_FACE);

        GLES20.glDepthFunc(GLES20.GL_LESS);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        GLES20.glPolygonOffset(2.F, 4.F);
        GLES20.glEnable(GLES20.GL_POLYGON_OFFSET_FILL);

        room = new Room();

        sphere = new Sphere(1, 100, 100);

        MainActivity.log("Graphics initialized");
    }


    /**
     * Draw the current simulation state
     * @param renderer Renderer
     */
    public void draw(MyGLRenderer renderer)
    {
        float[] modelviewmatrix = new float[16];

        MainActivity.log("Starting rendering");

        // Get shader to send uniform data
        NoLightShaders shaders = renderer.getShaders();

        // Place viewer in the right position and orientation
        Matrix.setIdentityM(modelviewmatrix,0);
        // setRotateM instead of rotateM in the next instruction would avoid this initialization...
        Matrix.rotateM(modelviewmatrix, 0, anglex, 1.0F, 0.0F, 0.0F);
        Matrix.rotateM(modelviewmatrix, 0, angley, 0.0F, 1.0F, 0.0F);
        Matrix.translateM(modelviewmatrix, 0, posx, -1.6F, posz);

        shaders.setModelViewMatrix(modelviewmatrix);

        room.show(shaders);

        float[] modelviewmatrixsphere = new float[16];

        Matrix.setIdentityM(modelviewmatrixsphere, 0);
        Matrix.translateM(modelviewmatrixsphere, 0, 0.0F, 1.0F, 0.0F);

        Matrix.multiplyMM(modelviewmatrixsphere, 0, modelviewmatrix, 0, modelviewmatrixsphere, 0);

        shaders.setColor(MyGLRenderer.magenta);
        shaders.setModelViewMatrix(modelviewmatrixsphere);

        sphere.show(shaders, GLES20.GL_TRIANGLES);

        float[] modelviewmatrixroom = new float[16];

        System.arraycopy(room.getMatrix(), 0, modelviewmatrixroom, 0, room.getMatrix().length);
        Matrix.rotateM(modelviewmatrixroom, 0, 180, 0.0F, 1.0F, 0.0F);
        Matrix.translateM(modelviewmatrixroom, 0, 0.0F, 0.0F, -6.0F);

        Matrix.multiplyMM(modelviewmatrixroom, 0, modelviewmatrix, 0, modelviewmatrixroom, 0);

        shaders.setModelViewMatrix(modelviewmatrixroom);

        room.show(shaders);

        MainActivity.log("Rendering terminated.");
    }
}
