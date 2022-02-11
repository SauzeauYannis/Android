package up.info.tp_1_2_3;

public class Ball extends MyObject {

    public enum SphereType { ANGLES, SUBDIVSION };

    private static final Sphere sphere_ang = new Sphere();
    private static final Sphere sphere_sub = new Sphere(4);

    public Ball(SphereType sphereType, float radius, float posx, float posz, float[] color) {
        super(posx, radius, posz, radius, color, false);

        setMainvbo(sphereType == SphereType.ANGLES ? sphere_ang.getVbo() : sphere_sub.getVbo());
    }

}
