package up.info.tp_shaders;

/**
 * The type Ball.
 */
public class Ball extends MyObject {

    /**
     * Instantiates a new Ball.
     *
     * @param sphereType    the sphere type
     * @param radius        the radius
     * @param posx          the posx
     * @param posz          the posz
     * @param color         the color
     * @param textureid     the textureid
     * @param specularcolor the specularcolor
     * @param shininess     the shininess
     */
    public Ball(SphereType sphereType, float radius, float posx, float posz, float[] color, int textureid, float[] specularcolor, float shininess) {
        super(posx, radius, posz, radius, color, textureid, specularcolor, shininess);

        Sphere sphere_ang = new Sphere(50, 50);
        Sphere sphere_sub = new Sphere(4);

        setMainvbo(sphereType == SphereType.ANGLES ? sphere_ang.getVbo() : sphere_sub.getVbo());
    }

    /**
     * Instantiates a new Ball.
     *
     * @param sphereType the sphere type
     * @param radius     the radius
     * @param posx       the posx
     * @param posz       the posz
     * @param color      the color
     */
    public Ball(SphereType sphereType, float radius, float posx, float posz, float[] color) {
        this(sphereType, radius, posx, posz, color, 0, null, 0);
    }

    /**
     * The enum Sphere type.
     */
    public enum SphereType {
        /**
         * Angles sphere type.
         */
        ANGLES,
        /**
         * Subdivsion sphere type.
         */
        SUBDIVSION
    }

}
