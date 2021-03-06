package up.info.tp_shaders;

/**
 * The type Cone.
 */
public class Cone extends MyObject {

    /**
     * Instantiates a new Cone.
     *
     * @param nbdiv         the nbdiv
     * @param posx          the posx
     * @param posz          the posz
     * @param scale         the scale
     * @param color         the color
     * @param textureid     the textureid
     * @param specularcolor the specularcolor
     * @param shininess     the shininess
     * @param gltexbuffer   the gltexbuffer
     */
    public Cone(int nbdiv, float posx, float posz, float scale, float[] color, int textureid, float[] specularcolor, float shininess, int gltexbuffer) {
        super(posx, 0F, posz, scale, color, textureid, specularcolor, shininess);

        float[] vertexpos = new float[3 * (nbdiv + 1)];

        float phistep = 360F / nbdiv;

        for (int i = 0, n = -1; i < nbdiv; i++) {
            float phi = (float) Math.toRadians(phistep * i);
            vertexpos[++n] = (float) Math.cos(phi);
            vertexpos[++n] = 0F;
            vertexpos[++n] = (float) Math.sin(phi);
        }

        vertexpos[vertexpos.length - 2] = 1F;

        short[] triangles = new short[3 * nbdiv];

        for (short i = 0, n = -1; i < nbdiv; i++) {
            triangles[++n] = i;
            triangles[++n] = (short) nbdiv;
            triangles[++n] = (short) ((i + 1) % nbdiv);
        }

        int glposbuffer = VBO.floatArrayToGlBuffer(vertexpos);
        int glmlbuffer = VBO.floatArrayToGlBuffer(
                VBO.computeNormals(vertexpos, triangles)
        );

        setMainvbo(new VBO(glposbuffer, glmlbuffer, gltexbuffer, triangles));
    }

    /**
     * Instantiates a new Cone.
     *
     * @param nbdiv         the nbdiv
     * @param posx          the posx
     * @param posz          the posz
     * @param scale         the scale
     * @param color         the color
     * @param textureid     the textureid
     * @param specularcolor the specularcolor
     * @param shininess     the shininess
     */
    public Cone(int nbdiv, float posx, float posz, float scale, float[] color, int textureid, float[] specularcolor, float shininess) {
        this(nbdiv, posx, posz, scale, color, textureid, specularcolor, shininess, 0);
    }

    /**
     * Instantiates a new Cone.
     *
     * @param nbdiv the nbdiv
     * @param posx  the posx
     * @param posz  the posz
     * @param scale the scale
     * @param color the color
     */
    public Cone(int nbdiv, float posx, float posz, float scale, float[] color) {
        this(nbdiv, posx, posz, scale, color, 0, null, 0);
    }

}
