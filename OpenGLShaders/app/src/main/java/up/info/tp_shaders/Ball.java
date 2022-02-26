package up.info.tp_shaders;

/**
 * The type Ball.
 */
public class Ball extends MyObject {

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
        SUBDIVSION }

    private static final Sphere sphere_ang = new Sphere(50, 50);
    private static final Sphere sphere_sub = new Sphere(4);

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
        super(posx, radius, posz, radius, color);

        setMainvbo(sphereType == SphereType.ANGLES ? sphere_ang.getVbo() : sphere_sub.getVbo());
    }

}
