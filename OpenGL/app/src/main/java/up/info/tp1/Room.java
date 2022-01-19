package up.info.tp1;

import android.opengl.GLES20;

public class Room {

    private final VBO wall;
    private final VBO floor;
    private final VBO ceiling;

    public Room() {
        wall = new VBO(
                new float[] {
                        // Front wall
                        -3, 0, -3,
                        3, 0, -3,
                        3, Scene.wallsize, -3,
                        -3, Scene.wallsize, -3,
                        // Right wall
                        3, 0, -3,
                        3, 0, 3,
                        3, Scene.wallsize, 3,
                        3, Scene.wallsize, -3,
                        // Left wall
                        -3, 0, 3,
                        -3, 0, -3,
                        -3, Scene.wallsize, -3,
                        -3, Scene.wallsize, 3,
                        // Back wall
                        3, 0, 3,
                        -3, 0, 3,
                        -3, Scene.wallsize, 3,
                        3, Scene.wallsize, 3},
                new short[] {
                        // Front wall
                        0, 1, 3,
                        2, 3, 1,
                        // Right wall
                        0, 1, 3,
                        2, 3, 1,
                        // Left wall
                        0, 1, 3,
                        2, 3, 1,
                        // Back wall
                        0, 1, 3,
                        2, 3, 1}
        );

        floor = new VBO(
                new float[] {
                        -3, 0, 3,
                        3, 0, 3,
                        3, 0, -3,
                        -3, 0, -3,
                },
                new  short[] {
                        0, 1, 3,
                        2, 3, 1}
        );

        ceiling = new VBO(
                new float[] {
                        3, Scene.wallsize, 3,
                        -3, Scene.wallsize, 3,
                        -3, Scene.wallsize, -3,
                        3, Scene.wallsize, -3,
                },
                new  short[] {
                        0, 1, 3,
                        2, 3, 1}
        );
    }

    public void showWall(NoLightShaders shaders) {
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, wall.getGlposbuffer());
        shaders.setPositionsPointer(3, GLES20.GL_FLOAT);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, wall.getGlelembuffer());
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, wall.getNbElem(), GLES20.GL_UNSIGNED_SHORT, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void showFloor(NoLightShaders shaders) {
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, floor.getGlposbuffer());
        shaders.setPositionsPointer(3, GLES20.GL_FLOAT);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, floor.getGlelembuffer());
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, floor.getNbElem(), GLES20.GL_UNSIGNED_SHORT, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void showCeiling(NoLightShaders shaders) {
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, ceiling.getGlposbuffer());
        shaders.setPositionsPointer(3, GLES20.GL_FLOAT);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, ceiling.getGlelembuffer());
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, ceiling.getNbElem(), GLES20.GL_UNSIGNED_SHORT, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

}
