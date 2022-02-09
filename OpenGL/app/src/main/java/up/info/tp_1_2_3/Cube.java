package up.info.tp_1_2_3;

public class Cube extends MyObject {

    private static final float originsize = 1.0F;

    public Cube(float posx, float posz, float size, float[] color) {
        super(posx, size / 2.0F, posz, size, color, false);

        float d = originsize / 2.0F;

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

        short[] edges = new short[] {
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
        };

        int glposbuffer = VBO.vertexPosToGlBuffer(vertexpos);

        setMainvbo(new VBO(glposbuffer, triangles));
        setEdgevbo(new VBO(glposbuffer, edges));
    }

}
