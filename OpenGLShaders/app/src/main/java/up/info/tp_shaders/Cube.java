package up.info.tp_shaders;

/**
 * The type Cube.
 */
public class Cube extends MyObject {

    private static final float originsize = 1.0F;
    private static final float l = originsize / 2.0F;

    private static final float[] vertexPos = new float[]{
            -l, -l, -l,
            l, -l, -l,
            l, l, -l,
            -l, l, -l,
            -l, l, -l,
            -l, l, l,
            -l, -l, l,
            -l, -l, -l,
            l, -l, -l,
            l, -l, l,
            l, l, l,
            l, l, -l,
            l, -l, l,
            -l, -l, l,
            -l, l, l,
            l, l, l,
            -l, -l, l,
            l, -l, l,
            l, -l, -l,
            -l, -l, -l,
            l, l, l,
            -l, l, l,
            -l, l, -l,
            l, l, -l
    };

    private final static float[] textures = new float[]{
            0, 0,
            1, 0,
            1, 1,
            0, 1,
            0, 0,
            1, 0,
            1, 1,
            0, 1,
            0, 0,
            1, 0,
            1, 1,
            0, 1,
            0, 0,
            1, 0,
            1, 1,
            0, 1,
            0, 0,
            1, 0,
            1, 1,
            0, 1,
            0, 0,
            1, 0,
            1, 1,
            0, 1
    };

    private static final short[] triangles = new short[]{
            1, 0, 3,
            3, 2, 1,
            5, 4, 7,
            7, 6, 5,
            9, 8, 11,
            11, 10, 9,
            13, 12, 15,
            15, 14, 13,
            17, 16, 19,
            19, 18, 17,
            21, 20, 23,
            23, 22, 21
    };

    /**
     * Instantiates a new Cube.
     *
     * @param posx      the posx
     * @param posz      the posz
     * @param size      the size
     * @param color     the color
     * @param textureid the textureid
     */
    public Cube(float posx, float posz, float size, float[] color, int textureid) {
        super(posx, originsize * size / 2.0F, posz, size, color, textureid);

        int glposbuffer = VBO.floatArrayToGlBuffer(vertexPos);
        int glnmlbuffer = VBO.floatArrayToGlBuffer(
                VBO.computeNormals(vertexPos, triangles)
        );
        int gltexbuffer = VBO.floatArrayToGlBuffer(textures);

        setMainvbo(new VBO(glposbuffer, glnmlbuffer, gltexbuffer, triangles));
    }

}
