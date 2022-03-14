package up.info.tp_shaders;

import android.content.Context;
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

    private Ball angballoutline;
    private Ball subballoutline;
    private Ball angball;
    private Ball subball;

    private Cube cube;

    private Tetrahedron tetrahedron;

    private Cone cone;

    private Cylinder cylinder;

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
        GLES20.glEnable(GLES20.GL_CULL_FACE);

        GLES20.glLineWidth(8);
        GLES20.glDepthFunc(GLES20.GL_LESS);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        GLES20.glPolygonOffset(2.F, 4.F);
        GLES20.glEnable(GLES20.GL_POLYGON_OFFSET_FILL);

        Context context = renderer.getView().getContext();

        LightingShaders shaders = renderer.getShaders();

        shaders.setNormalizing(true);
        shaders.setLighting(true);

        shaders.setAmbiantLight(new float[]{0.2F, 0.2F, 0.2F, 1F});
        shaders.setLightSpecular(MyGLRenderer.white);
        shaders.setLightColor(new float[]{0.8F, 0.8F, 0.8F, 1F});
        shaders.setLightAttenuation(1.0f, 0.1f, 0.01f);

        shaders.setLightPosition(new float[]{0F, 0F, 0F});

        shaders.setMaterialShininess(25);
        shaders.setMaterialSpecular(MyGLRenderer.white);

        room = new Room(MyGLRenderer.white, MyGLRenderer.loadTexture(context, R.drawable.wall),
                new float[]{1F, 1F, 1F, 0.5F}, MyGLRenderer.loadTexture(context, R.drawable.marble2),
                new float[]{0.1F, 0.1F, 0.65F, 1F}, MyGLRenderer.loadTexture(context, R.drawable.ceiling));

        angballoutline = new Ball(Ball.SphereType.ANGLES, 1.0F, 1.5F, -1.5F, MyGLRenderer.magenta, 0);
        subballoutline = new Ball(Ball.SphereType.SUBDIVSION,  1.0F, -1.5F, -1.5F,  MyGLRenderer.lightgray, 0);
        angball = new Ball(Ball.SphereType.ANGLES, 0.5F, 2.0F, 7.0F, MyGLRenderer.white, MyGLRenderer.loadTexture(context, R.drawable.basketball));
        //angball = new Ball(Ball.SphereType.ANGLES, 1.0F, 0, 0, MyGLRenderer.white, MyGLRenderer.loadTexture(context, R.drawable.beachball));
        subball = new Ball(Ball.SphereType.SUBDIVSION,  0.5F, -2.0F, 7.0F, MyGLRenderer.cyan, 0);

        cube = new Cube(2.25F,  1.75F, 1.25F, MyGLRenderer.white, MyGLRenderer.loadTexture(context, R.drawable.rubiks));

        tetrahedron = new Tetrahedron(-2.0F, 5.0F, 1F, MyGLRenderer.yellow, 0);

        cone = new Cone(50, 2.0F, 5.0F, 1.0F, new float[] {0.62F, 0.81F, 0,21F, 1F}, 0);

        cylinder = new Cylinder(50, 0F, 7.0F, 1.25F, new float[] {0.2F, 0.5F, 0.8F, 1F}, 0);

        armadilloObj = new ObjLoader("/assets/armadillo.obj", -1.5F, 0.90F, 1.5F, 0.015F, MyGLRenderer.red);

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
        LightingShaders shaders = renderer.getShaders();

        // Place viewer in the right position and orientation
        Matrix.setIdentityM(modelviewmatrix,0);
        // setRotateM instead of rotateM in the next instruction would avoid this initialization...
        Matrix.rotateM(modelviewmatrix, 0, anglex, 1.0F, 0.0F, 0.0F);
        Matrix.rotateM(modelviewmatrix, 0, angley, 0.0F, 1.0F, 0.0F);
        Matrix.translateM(modelviewmatrix, 0, posx, -1.6F, posz);

        Matrix.scaleM(modelviewmatrix, 0, 1.0F, -1.0F, 1.0F);

        GLES20.glFrontFace(GLES20.GL_CW);

        showScene(modelviewmatrix, shaders);

        Matrix.scaleM(modelviewmatrix, 0, 1.0F, -1.0F, 1.0F);

        GLES20.glFrontFace(GLES20.GL_CCW);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        showScene(modelviewmatrix, shaders);

        GLES20.glDisable(GLES20.GL_BLEND);

        MainActivity.log("Rendering terminated.");
    }

    private void showScene(float[] modelviewmatrix, LightingShaders shaders) {
        shaders.setModelViewMatrix(modelviewmatrix);

        room.show(shaders);
        room.showSecondRoom(shaders, modelviewmatrix);

        angballoutline.show(shaders, modelviewmatrix, true);
        subballoutline.show(shaders, modelviewmatrix, true);
        angball.show(shaders, modelviewmatrix, false);
        subball.show(shaders, modelviewmatrix, false);

        cube.show(shaders, modelviewmatrix, false);
        tetrahedron.show(shaders, modelviewmatrix, true);
        cone.show(shaders, modelviewmatrix, false);
        cylinder.show(shaders, modelviewmatrix, false);
        armadilloObj.show(shaders, modelviewmatrix, false);
    }
}
