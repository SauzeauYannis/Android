package up.info.tp_shaders;

/**
 * The type Cube.
 */
public class Cube extends MyObject {

    private static final float originsize = 1.0F;

    private static final int glposbuffer = VBO.floatArrayToGlBuffer(new float[] {
            -originsize / 2, -originsize / 2, originsize / 2,
            originsize / 2, -originsize / 2, originsize / 2,
            -originsize / 2, -originsize / 2, -originsize / 2,
            originsize / 2, -originsize / 2, -originsize / 2,
            -originsize / 2, originsize / 2, originsize / 2,
            originsize / 2, originsize / 2, originsize / 2,
            -originsize / 2, originsize / 2, -originsize / 2,
            originsize / 2, originsize / 2, -originsize / 2
    });

    private static final VBO mainvbo = new VBO(glposbuffer, 0, new short[] {
            0, 1, 4,
            1, 5, 4,
            1, 3, 5,
            3, 7, 5,
            2, 6, 3,
            3, 6, 7,
            0, 4, 2,
            4, 6, 2,
            0, 1, 2,
            1, 3, 2,
            6, 4, 5,
            6, 5, 7
    }); // TODO: 26-Feb-22

    private static final VBO edgevbo = new VBO(glposbuffer, 0, new short[] {
            1, 0,
            0, 2,
            2, 3,
            3, 1,
            1, 5,
            0, 4,
            2, 6,
            3, 7,
            5, 4,
            4, 6,
            6, 7,
            7, 5
    }); // TODO: 26-Feb-22

    /**
     * Instantiates a new Cube.
     *
     * @param posx  the posx
     * @param posz  the posz
     * @param size  the size
     * @param color the color
     */
    public Cube(float posx, float posz, float size, float[] color) {
        super(posx, originsize * size / 2.0F, posz, size, color);

        setMainvbo(mainvbo);
        setEdgevbo(edgevbo);
    }

}
