package up.info.tp1;

import android.opengl.GLES20;
import android.opengl.Matrix;

public abstract class MyObject {

    private final float[] modelviewmatrixobj;

    private final float posx;
    private final float posy;
    private final float posz;
    private final float scale;
    private final float[] color;

    private VBO vbo;

    public MyObject(float posx, float posy, float posz, float scale, float[] color) {
        this.posx = posx;
        this.posy = posy;
        this.posz = posz;
        this.scale = scale;
        this.color = color;
        this.modelviewmatrixobj = new float[16];
    }

    public void setVbo(VBO vbo) { this.vbo = vbo; }

    public void show(NoLightShaders shaders, float[] modelviewmatrix, boolean withOutline, boolean useInt) {
        Matrix.setIdentityM(this.modelviewmatrixobj, 0);

        Matrix.translateM(this.modelviewmatrixobj, 0, this.posx, this.posy, this.posz);

        Matrix.scaleM(this.modelviewmatrixobj, 0, this.scale, this.scale, this.scale);

        Matrix.multiplyMM(this.modelviewmatrixobj, 0, modelviewmatrix, 0, this.modelviewmatrixobj, 0);

        shaders.setColor(this.color);
        shaders.setModelViewMatrix(this.modelviewmatrixobj);

        if (withOutline)
            vbo.showOutline(shaders, GLES20.GL_TRIANGLES, useInt);
        else
            vbo.show(shaders, GLES20.GL_TRIANGLES, useInt);
    }

}
