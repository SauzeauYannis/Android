package up.info.tp_1_2_3;

public class Tetrahedron extends MyObject {

    public Tetrahedron(float posx, float posz, float height, float[] color, boolean useInt) {
        super(posx, 1 / 3F, posz, height, color, useInt);

        float[] vertexpos = new float[] {
                (float) Math.sqrt(8 / 9F), - 1 / 3F, 0,
                (float) - Math.sqrt(2 / 9F), - 1 / 3F, (float) Math.sqrt(2 / 3F),
                (float) - Math.sqrt(2 / 9F), - 1 / 3F, (float) - Math.sqrt(2 / 3F),
                0, 1, 0,
        };

        short[] triangles = new short[] {
                0, 1, 2,
                0, 1, 3,
                1, 2, 3,
                2, 0, 3,
        };
        /*
        short[] edges = new short[] {
                0
        };
        */
        int glposbuffer = VBO.vertexPosToGlBuffer(vertexpos);

        setMainvbo(new VBO(glposbuffer, triangles));
        //setEdgevbo(new VBO(glposbuffer, edges));
    }

}
