package up.info.tp1;

import android.opengl.GLES20;
import android.opengl.Matrix;

public class Room {

    private final VBO wall;
    private final VBO floor;
    private final VBO ceiling;

    private final VBO edge;

    private final float[] matrix;

    public Room() {

        float[] vertexPos = new float[] {
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

        int glposbuffer = VBO.vertexPosToGlBuffer(vertexPos);

        wall = new VBO(glposbuffer,
                new short[] {
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
                        22, 23, 21}
        );

        floor = new VBO(glposbuffer,
                new short[] {
                        24, 25, 27,
                        26, 27, 25}
        );

        ceiling = new VBO(glposbuffer,
                new short[] {
                        28, 29, 31,
                        30, 31, 29}
        );

        edge = new VBO(glposbuffer,
                new short[] {0, 3, 1, 2, 5, 6, 9, 10});

        matrix = new float[16];

        Matrix.setIdentityM(matrix, 0);
    }

    public float[] getMatrix() { return matrix; }

    public void show(NoLightShaders shaders) {
        shaders.setColor(MyGLRenderer.blue);
        this.wall.show(shaders, GLES20.GL_TRIANGLES);
        shaders.setColor(MyGLRenderer.red);
        this.floor.show(shaders, GLES20.GL_TRIANGLES);
        shaders.setColor(MyGLRenderer.green);
        this.ceiling.show(shaders, GLES20.GL_TRIANGLES);
        shaders.setColor(MyGLRenderer.black);
        this.edge.show(shaders, GLES20.GL_LINES);
    }

}
