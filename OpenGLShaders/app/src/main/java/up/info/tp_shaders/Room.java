package up.info.tp_shaders;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.util.Arrays;

/**
 * The type Room.
 */
public class Room {

    private final static float[] vertexPos = new float[]{
            // Front wall
            -3, 0, -3,
            3, 0, -3,
            3, Scene.wallsize, -3,
            -3, Scene.wallsize, -3,
            // Left wall
            -3, Scene.wallsize, -3,
            -3, Scene.wallsize, 3,
            -3, 0, 3,
            -3, 0, -3,
            // Right wall
            3, 0, -3,
            3, 0, 3,
            3, Scene.wallsize, 3,
            3, Scene.wallsize, -3,
            // Back wall
            // right quad
            3, 0, 3,
            1, 0, 3,
            1, Scene.wallsize - 0.5F, 3,
            3, Scene.wallsize - 0.5F, 3,
            // left quad
            -1, 0, 3,
            -3, 0, 3,
            -3, Scene.wallsize - 0.5F, 3,
            -1, Scene.wallsize - 0.5F, 3,
            // top quad
            3, Scene.wallsize - 0.5F, 3,
            -3, Scene.wallsize - 0.5F, 3,
            -3, Scene.wallsize, 3,
            3, Scene.wallsize, 3,
            // floor
            -3, 0, 3,
            3, 0, 3,
            3, 0, -3,
            -3, 0, -3,
            // ceiling
            3, Scene.wallsize, 3,
            -3, Scene.wallsize, 3,
            -3, Scene.wallsize, -3,
            3, Scene.wallsize, -3
    };

    private final static float[] textures = new float[]{
            // Front wall
            0, 0,
            1, 0,
            1, 1,
            0, 1,
            // Left wall
            1, 1,
            0, 1,
            0, 0,
            1, 0,
            // Right wall
            0, 0,
            1, 0,
            1, 1,
            0, 1,
            // Back wall
            // right quad
            1f/3, 0,
            0, 0,
            0, 0.8f,
            1f/3, 0.8f,
            // left quad
            1f/3, 0,
            0, 0,
            0, 0.8f,
            1f/3, 0.8f,
            // top quad
            1, 0,
            0, 0,
            0, 0.2f,
            1, 0.2f,
            // floor
            0, 0,
            1, 0,
            1, 1,
            0, 1,
            // ceiling
            1, 0,
            0, 0,
            0, 1,
            1, 1
    };

    private final static short[] triangles = new short[]{
            // Front wall
            0, 1, 3,
            2, 3, 1,
            // Left wall
            4, 5, 7,
            6, 7, 5,
            // Right wall
            8, 9, 11,
            10, 11, 9,
            // Back wall
            // right quad
            12, 13, 15,
            14, 15, 13,
            // left quad
            16, 17, 19,
            18, 19, 17,
            // top quad
            20, 21, 23,
            22, 23, 21,
            // floor
            24, 25, 27,
            26, 27, 25,
            // ceiling
            28, 29, 31,
            30, 31, 29};

    private final int glposbuffer = VBO.floatArrayToGlBuffer(vertexPos);

    private final int glnmlbuffer = VBO.floatArrayToGlBuffer(
            VBO.computeNormals(vertexPos, triangles)
    );

    private final int gltexbuffer = VBO.floatArrayToGlBuffer(textures);

    private final VBO wall = new VBO(glposbuffer, glnmlbuffer, gltexbuffer,
            Arrays.copyOfRange(triangles, 0, 36)
    );

    private final VBO floor = new VBO(glposbuffer, glnmlbuffer, gltexbuffer,
            Arrays.copyOfRange(triangles, 36, 42)
    );

    private final VBO ceiling = new VBO(glposbuffer, glnmlbuffer, gltexbuffer,
            Arrays.copyOfRange(triangles, 42, 48)
    );

    private final VBO edge = new VBO(glposbuffer, glnmlbuffer, 0,
            new short[]{
                    0, 1,
                    1, 2,
                    2, 3,
                    3, 0,
                    4, 5,
                    5, 6,
                    6, 7,
                    8, 9,
                    9, 10,
                    10, 11,
                    13, 14,
                    16, 19,
                    24, 25,
                    28, 29}
    );

    private final float[] wallcolor;
    private final float[] floorcolor;
    private final float[] ceilingcolor;

    private final int walltexid;
    private final int floortexid;
    private final int ceilingtexid;

    private final float[] matrix;

    /**
     * Instantiates a new Room.
     *
     * @param wallcolor    the color of wall
     * @param floorcolor   the color of floor
     * @param ceilingcolor the color of ceiling
     */
    public Room(float[] wallcolor, int walltexid,
                float[] floorcolor, int floortexid,
                float[] ceilingcolor, int ceilingtexid) {
        this.wallcolor = wallcolor;
        this.floorcolor = floorcolor;
        this.ceilingcolor = ceilingcolor;
        this.walltexid = walltexid;
        this.floortexid = floortexid;
        this.ceilingtexid = ceilingtexid;
        this.matrix = new float[16];
    }

    /**
     * Show.
     *
     * @param shaders the shaders
     */
    public void show(LightingShaders shaders) {
        shaders.setTexturing(true);

        shaders.setMaterialColor(this.wallcolor);
        wall.show(shaders, GLES20.GL_TRIANGLES, this.walltexid, false);

        shaders.setMaterialColor(this.floorcolor);
        floor.show(shaders, GLES20.GL_TRIANGLES, this.floortexid, false);

        shaders.setMaterialColor(this.ceilingcolor);
        ceiling.show(shaders, GLES20.GL_TRIANGLES, this.ceilingtexid, false);

        shaders.setTexturing(false);

        shaders.setMaterialColor(MyGLRenderer.black);
        edge.show(shaders, GLES20.GL_LINES, 0, false);
    }

    /**
     * Show second room.
     *
     * @param shaders         the shaders
     * @param modelviewmatrix the modelviewmatrix
     */
    public void showSecondRoom(LightingShaders shaders, float[] modelviewmatrix) {
        Matrix.setIdentityM(this.matrix, 0);

        Matrix.rotateM(this.matrix, 0, 180, 0.0F, 1.0F, 0.0F);

        Matrix.translateM(this.matrix, 0, 0.0F, 0.0F, -6.0F);

        Matrix.multiplyMM(this.matrix, 0, modelviewmatrix, 0, this.matrix, 0);

        shaders.setModelViewMatrix(this.matrix);

        this.show(shaders);
    }

}
