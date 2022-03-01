package up.info.tp_shaders;

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

//    private Ball angballoutline;
//    private Ball subballoutline;
//    private Ball angball;
//    private Ball subball;

//    private Cube cube;
//
//    private Tetrahedron tetrahedron;
//
//    private Cone cone;
//
//    private Cylinder cylinder;
//
//    private ObjLoader armadilloObj;

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

        LightingShaders shaders = renderer.getShaders();

        shaders.setNormalizing(true);
        shaders.setLighting(true);

        shaders.setAmbiantLight(new float[]{0.2F, 0.2F, 0.2F, 1F});
        shaders.setLightSpecular(MyGLRenderer.white);
        shaders.setLightColor(new float[]{0.8F, 0.8F, 0.8F, 1F});

        shaders.setLightPosition(new float[]{0F, 0F, 0F});

        room = new Room(MyGLRenderer.blue, MyGLRenderer.red, MyGLRenderer.green);

//        angballoutline = new Ball(Ball.SphereType.ANGLES, 1.0F, 1.5F, -1.5F, MyGLRenderer.magenta);
//        subballoutline = new Ball(Ball.SphereType.SUBDIVSION,  1.0F, -1.5F, -1.5F,  MyGLRenderer.lightgray);
//        angball = new Ball(Ball.SphereType.ANGLES, 0.5F, 2.0F, 7.0F, MyGLRenderer.orange);
//        subball = new Ball(Ball.SphereType.SUBDIVSION,  0.5F, -2.0F, 7.0F, MyGLRenderer.cyan);

//        cube = new Cube(2.25F,  1.75F, 1.25F, new float[] {0.35F, 0.12F, 0.75F, 1F});
//
//        tetrahedron = new Tetrahedron(-2.0F, 5.0F, 1F, MyGLRenderer.yellow);
//
//        cone = new Cone(50, 2.0F, 5.0F, 1.0F, new float[] {0.62F, 0.81F, 0,21F, 1F});
//
//        cylinder = new Cylinder(50, 0F, 7.0F, 1.25F, new float[] {0.2F, 0.5F, 0.8F, 1.0F});
//
//        armadilloObj = new ObjLoader("/assets/armadillo.obj", -1.5F, 0.90F, 1.5F, 0.80F, new float[] {0.33F, 0.05F, 0.05F, 1.0F});

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

        shaders.setModelViewMatrix(modelviewmatrix);

        room.show(shaders);
        room.showSecondRoom(shaders, modelviewmatrix);

//        angballoutline.show(shaders, modelviewmatrix, true);
//        subballoutline.show(shaders, modelviewmatrix, true);
//        angball.show(shaders, modelviewmatrix, false);
//        subball.show(shaders, modelviewmatrix, false);

        //cube.show(shaders, modelviewmatrix, false);

        //tetrahedron.show(shaders, modelviewmatrix, true);

        //cone.show(shaders, modelviewmatrix, false);

        //cylinder.show(shaders, modelviewmatrix, false);

        //armadilloObj.show(shaders, modelviewmatrix, true);

        MainActivity.log("Rendering terminated.");
    }
}
