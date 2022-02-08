package up.info.tp_1_2_3;

import android.opengl.GLES20;
import android.opengl.Matrix;

public class Scene {

    /**
     * A constant for the size of the wall
     */
    static final float wallsize = 2.6F;

    /**
     * An angle used to animate the viewer
     */
    float anglex, angley;

    /**
     * A position used to move the viewer
     */
    float posx, posz;

    private Room room;
    private Ball ball0;
    private Ball ball1;
    private Ball ball2;
    private Ball ball3;
    private Ball ball4;
    private Cube cube;
    private ObjLoader armadilloObj;

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

        ball0 = new Ball(0.5F, 1.5F, 1.5F, MyGLRenderer.orange);
        ball1 = new Ball(wallsize / 3.0F, 0F, 5.0F, MyGLRenderer.magenta);
        ball2 = new Ball(4, 1F, -1.5F, -1.5F,  MyGLRenderer.gray);
        ball3 = new Ball(0.25F, 0F, 0F, MyGLRenderer.cyan);
        ball4 = new Ball(0.75F, 1F, -2.0F, MyGLRenderer.yellow);

        cube = new Cube(2.25F, 0.5F, 3.75F, 1F, new float[] {0.35F, 0.12F, 0.75F, 1F});

        armadilloObj = new ObjLoader("/assets/armadillo.obj", -1.5F, 1.0F, 1.5F, 0.80F, MyGLRenderer.darkgray, 3 * 15002, 3 * 30000);

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
        room.showSecondRoom(shaders, modelviewmatrix);

        ball0.show(shaders, modelviewmatrix, false);
        ball1.show(shaders, modelviewmatrix, true);
        ball2.show(shaders, modelviewmatrix, true);
        ball3.show(shaders, modelviewmatrix, false);
        ball4.show(shaders, modelviewmatrix, false);

        cube.show(shaders, modelviewmatrix, false);

        armadilloObj.show(shaders, modelviewmatrix, true);

        MainActivity.log("Rendering terminated.");
    }
}
