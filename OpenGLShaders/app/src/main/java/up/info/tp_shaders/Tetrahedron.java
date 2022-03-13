package up.info.tp_shaders;

/**
 * The type Tetrahedron.
 */
public class Tetrahedron extends Cone {

    /**
     * Instantiates a new Tetrahedron.
     *
     * @param posx   the posx
     * @param posz   the posz
     * @param height the height
     * @param color  the color
     */
    public Tetrahedron(float posx, float posz, float height, float[] color, int textureid) {
        super(3, posx, posz, height, color, textureid);
    }

}
