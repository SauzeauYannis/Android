package up.info.tp1;

import android.opengl.GLES20;
import android.opengl.Matrix;

public class Ball {

    private final Sphere sphere;

    private final float[] modelviewmatrixsphere;

    private final float radius;
    private final float posx;
    private final float posz;
    private final float[] color;

    private Ball(Sphere sphere, float radius, float posx, float posz, float[] color) {
        this.sphere = sphere;

        this.modelviewmatrixsphere = new float[16];

        this.radius = radius;
        this.posx = posx;
        this.posz = posz;
        this.color = color;
    }

    public Ball(float radius, float posx, float posz, float[] color) {
        this(new Sphere(), radius, posx, posz, color);
    }

    public Ball(int nbsubdivision, float radius, float posx, float posz, float[] color) {
        this(new Sphere(nbsubdivision), radius, posx, posz, color);
    }

    public void show(NoLightShaders shaders, float[] modelviewmatrix, boolean withOutline) {
        Matrix.setIdentityM(this.modelviewmatrixsphere, 0);

        Matrix.translateM(this.modelviewmatrixsphere, 0, this.posx, radius, this.posz);

        Matrix.scaleM(this.modelviewmatrixsphere, 0, this.radius, this.radius, this.radius);

        Matrix.multiplyMM(this.modelviewmatrixsphere, 0, modelviewmatrix, 0, this.modelviewmatrixsphere, 0);

        shaders.setColor(this.color);
        shaders.setModelViewMatrix(this.modelviewmatrixsphere);

        if (withOutline)
            sphere.getVbo().showOutline(shaders);
        else
            sphere.getVbo().show(shaders, GLES20.GL_TRIANGLES);
    }

}
