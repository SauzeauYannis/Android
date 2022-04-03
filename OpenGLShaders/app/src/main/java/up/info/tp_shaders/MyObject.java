package up.info.tp_shaders;

import android.opengl.GLES20;
import android.opengl.Matrix;

/**
 * The type My object.
 */
public abstract class MyObject {

    private final float[] modelviewmatrixobj;

    private final float posx;
    private final float posy;
    private final float posz;

    private final float scale;

    private final float[] color;
    private final int textureid;

    private VBO mainvbo;
    private VBO edgevbo;

    /**
     * Instantiates a new My object.
     *
     * @param posx  the posx
     * @param posy  the posy
     * @param posz  the posz
     * @param scale the scale
     * @param color the color
     */
    public MyObject(float posx, float posy, float posz, float scale, float[] color, int textureid) {
        this.posx = posx;
        this.posy = posy;
        this.posz = posz;
        this.scale = scale;
        this.color = color;
        this.textureid = textureid;
        this.modelviewmatrixobj = new float[16];
    }

    /**
     * Sets mainvbo.
     *
     * @param mainvbo the mainvbo
     */
    public void setMainvbo(VBO mainvbo) { this.mainvbo = mainvbo; }

    /**
     * Show.
     *
     * @param shaders                the shaders
     * @param modelviewmatrix        the modelviewmatrix
     * @param showtrianglesoutline   the showtriangles
     */
    public void show(LightingShaders shaders, float[] modelviewmatrix, boolean showtrianglesoutline) {
        Matrix.setIdentityM(this.modelviewmatrixobj, 0);

        Matrix.translateM(this.modelviewmatrixobj, 0, this.posx, this.posy, this.posz);

        Matrix.scaleM(this.modelviewmatrixobj, 0, this.scale, this.scale, this.scale);

        Matrix.multiplyMM(this.modelviewmatrixobj, 0, modelviewmatrix, 0, this.modelviewmatrixobj, 0);

        shaders.setMaterialColor(this.color);
        shaders.setModelViewMatrix(this.modelviewmatrixobj);

        shaders.setTexturing(this.textureid != 0);
        mainvbo.show(shaders, GLES20.GL_TRIANGLES, this.textureid, showtrianglesoutline);
        shaders.setTexturing(this.textureid == 0);

        if (edgevbo != null) {
            shaders.setMaterialColor(MyGLRenderer.black);
            edgevbo.show(shaders, GLES20.GL_LINES, 0, false);
        }
    }

}
