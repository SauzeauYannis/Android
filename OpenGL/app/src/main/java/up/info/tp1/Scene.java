package up.info.tp1;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

public class Scene {

    /**
     * A constant for the size of the wall
     */
    static final float wallsize = 2.5F;
    /**
     * 4 quads to represent the walls of the room
     */
    Quad wall1, wall2, wall3, wall4;
    /**
     * A quad to represent a floor
     */
    Quad floor;
    /**
     * A quad to represent the ceiling of the room
     */
    Quad ceiling;

    /**
     * An angle used to animate the viewer
     */
    float anglex, angley;

    /**
     * A position used to move the viewer
     */
    float posx, posz;


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

        // Create the front wall
        this.wall1= new Quad(
                new Vec3f(-3, 0, -3),
                new Vec3f(3, 0, -3),
                new Vec3f(3, wallsize, -3),
                new Vec3f(-3, wallsize, -3)
        );

        // Create the right wall
        this.wall2 = new Quad(
                new Vec3f(3, 0, -3),
                new Vec3f(3, 0, 3),
                new Vec3f(3, wallsize, 3),
                new Vec3f(3, wallsize, -3)
        );

        // Create the left wall
        this.wall3 = new Quad(
                new Vec3f(-3, 0, 3),
                new Vec3f(-3, 0, -3),
                new Vec3f(-3, wallsize, -3),
                new Vec3f(-3, wallsize, 3)
        );

        // create the back wall
        this.wall4 = new Quad(
                new Vec3f(3, 0, 3),
                new Vec3f(-3, 0, 3),
                new Vec3f(-3, wallsize, 3),
                new Vec3f(3, wallsize, 3)
        );

        // Create the floor of the room
        this.floor = new Quad(
                new Vec3f(-3, 0, 3),
                new Vec3f(3, 0, 3),
                new Vec3f(3, 0, -3),
                new Vec3f(-3, 0, -3)
        );

        // Create the ceiling of the room
        this.ceiling= new Quad(
                new Vec3f(3, wallsize, 3),
                new Vec3f(-3, wallsize, 3),
                new Vec3f(-3, wallsize, -3),
                new Vec3f(3, wallsize, -3)
        );
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

        // Draw walls, floor and ceil in selected colors
        shaders.setColor(MyGLRenderer.blue);
        this.wall1.draw(shaders);
        shaders.setColor(MyGLRenderer.magenta);
        this.wall2.draw(shaders);
        shaders.setColor(MyGLRenderer.green);
        this.wall3.draw(shaders);
        shaders.setColor(MyGLRenderer.gray);
        this.wall4.draw(shaders);
        shaders.setColor(MyGLRenderer.yellow);
        this.floor.draw(shaders);
        shaders.setColor(MyGLRenderer.orange);
        this.ceiling.draw(shaders);

        // shaders.setColor(MyGLRenderer.black);
        // this.wall1.drawWireframe(shaders);

        MainActivity.log("Rendering terminated.");
    }
}
