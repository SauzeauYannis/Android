package up.info.tp_1_2_3;

public class Cube extends MyObject {

    private final float size = 1.0F;

    public Cube(float posx, float posy, float posz, float scale, float[] color) {
        super(posx, posy, posz, scale, color, false);

        float d = size / 2.0F;

        float[] vertexpos = new float[] {
                -d, -d, d,
                d, -d, d,
                -d, -d, -d,
                d, -d, -d,
                -d, d, d,
                d, d, d,
                -d, d, -d,
                d, d, -d
        };

        short[] triangles = new short[] {
                0, 1, 4,
                1, 5, 4,
                1, 3, 5,
                3, 7, 5,
                2, 3, 6,
                3, 7, 6,
                0, 2, 4,
                4, 2, 6,
                0, 1, 2,
                1, 3, 2,
                6, 4, 5,
                6, 5, 7
        };

        setVbo(new VBO(VBO.vertexPosToGlBuffer(vertexpos), triangles));
    }

}
