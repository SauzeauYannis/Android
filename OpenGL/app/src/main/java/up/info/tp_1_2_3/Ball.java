package up.info.tp_1_2_3;

public class Ball extends MyObject {

    public Ball(float radius, float posx, float posz, float[] color) {
        super(posx, radius, posz, radius, color, false);

        Sphere sphere = new Sphere();
        setMainvbo(sphere.getVbo());
    }

    public Ball(int nbsubdivision, float radius, float posx, float posz, float[] color) {
        super(posx, radius, posz, radius, color, false);

        Sphere sphere = new Sphere(nbsubdivision);
        setMainvbo(sphere.getVbo());
    }

}
