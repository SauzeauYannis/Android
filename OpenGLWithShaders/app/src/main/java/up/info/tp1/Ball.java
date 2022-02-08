package up.info.tp1;

public class Ball extends MyObject {

    public Ball(float radius, float posx, float posz, float[] color) {
        super(posx, radius, posz, radius, color);

        Sphere sphere = new Sphere();
        setVbo(sphere.getVbo());
    }

    public Ball(int nbsubdivision, float radius, float posx, float posz, float[] color) {
        super(posx, radius, posz, radius, color);

        Sphere sphere = new Sphere(nbsubdivision);
        setVbo(sphere.getVbo());
    }

    public void show(LightingShaders shaders, float[] modelviewmatrix, boolean withOutline) {
        show(shaders, modelviewmatrix, withOutline, false);
    }

}
