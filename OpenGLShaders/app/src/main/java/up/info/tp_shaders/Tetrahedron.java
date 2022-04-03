package up.info.tp_shaders;

/**
 * The type Tetrahedron.
 */
public class Tetrahedron extends Cone {

    /**
     * Instantiates a new Tetrahedron.
     *
     * @param posx          the posx
     * @param posz          the posz
     * @param height        the height
     * @param color         the color
     * @param textureid     the textureid
     * @param specularcolor the specularcolor
     * @param shininess     the shininess
     */
    public Tetrahedron(float posx, float posz, float height, float[] color, int textureid, float[] specularcolor, float shininess) {
        super(3, posx, posz, height, color, textureid, specularcolor, shininess, VBO.floatArrayToGlBuffer(new float[]{
                1.0f, 0.0f,
                0.0f, 0.0f,
                0.5f, 1.0f,
                0.5f, 0.5f})
        );
    }

    /**
     * Instantiates a new Tetrahedron.
     *
     * @param posx   the posx
     * @param posz   the posz
     * @param height the height
     * @param color  the color
     */
    public Tetrahedron(float posx, float posz, float height, float[] color) {
        this(posx, posz, height, color, 0, null, 0.0f);
    }

}
