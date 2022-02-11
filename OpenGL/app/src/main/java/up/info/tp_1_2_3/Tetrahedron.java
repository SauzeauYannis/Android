package up.info.tp_1_2_3;

/**
 * The type Tetrahedron.
 */
public class Tetrahedron extends MyObject {

    /**
     * Instantiates a new Tetrahedron.
     *
     * @param posx   the posx
     * @param posz   the posz
     * @param height the height
     * @param color  the color
     */
    public Tetrahedron(float posx, float posz, float height, float[] color) {
        super(posx, 0F, posz, height, color);

        float[] vertexpos = new float[] {
                (float) Math.cos(Math.toRadians(0)), 0F, (float) Math.sin(Math.toRadians(0)),
                (float) Math.cos(Math.toRadians(120)), 0F, (float) Math.sin(Math.toRadians(120)),
                (float) Math.cos(Math.toRadians(240)), 0F, (float) Math.sin(Math.toRadians(240)),
                0F, 1F, 0F,
        };

        short[] triangles = new short[] {
                0, 1, 2,
                0, 1, 3,
                1, 2, 3,
                2, 0, 3,
        };

        short[] edges = new short[] {
                0, 3,
                1, 3,
                2, 3,
        };

        int glposbuffer = VBO.vertexPosToGlBuffer(vertexpos);

        setMainvbo(new VBO(glposbuffer, triangles));
        setEdgevbo(new VBO(glposbuffer, edges));
    }

}
