package up.info.tp1;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.microedition.khronos.opengles.GL;

public class ObjLoader {

    private final float[] vertexPos;
    private final int[] triangles;
    private VBO vbo;

    private final float[] modelviewmatrixobj;

    private final float posx;
    private final float posy;
    private final float posz;
    private final float[] color;

    public ObjLoader(String filepath, float posx, float posy, float posz, float[] color, int vertexnum, int trianglesnum) {
        this.vertexPos = new float[vertexnum];
        this.triangles = new int[trianglesnum];

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(ObjLoader.class.getResourceAsStream(filepath)));
            String line;
            int vn = -1;
            int tn = -1;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("v ") || line.startsWith("f")) {
                    String[] splitted = line.split(" ");
                    if (splitted[0].equals("v")) {
                        this.vertexPos[++vn] = Float.parseFloat(splitted[1]);
                        this.vertexPos[++vn] = Float.parseFloat(splitted[2]);
                        this.vertexPos[++vn] = Float.parseFloat(splitted[3]);
                    } else if (splitted[0].equals("f")) {
                        this.triangles[++tn] = Integer.parseInt(splitted[1].split("/")[0]) - 1;
                        this.triangles[++tn] = Integer.parseInt(splitted[2].split("/")[0]) - 1;
                        this.triangles[++tn] = Integer.parseInt(splitted[3].split("/")[0]) - 1;
                    }
                }
            }

            reader.close();
        } catch (IOException e) {
            Log.e("LOG", "IO error");
        }

        this.vbo = new VBO(VBO.vertexPosToGlBuffer(this.vertexPos), this.triangles);

        this.modelviewmatrixobj = new float[16];

        this.posx = posx;
        this.posy = posy;
        this.posz = posz;
        this.color = color;
    }

    public void show(NoLightShaders shaders, float[] modelviewmatrix, boolean withOutline) {
        Matrix.setIdentityM(this.modelviewmatrixobj, 0);

        Matrix.translateM(this.modelviewmatrixobj, 0, this.posx, this.posy, this.posz);

        Matrix.multiplyMM(this.modelviewmatrixobj, 0, modelviewmatrix, 0, this.modelviewmatrixobj, 0);

        shaders.setColor(color);
        shaders.setModelViewMatrix(this.modelviewmatrixobj);

        if (withOutline)
            this.vbo.showBigOutline(shaders);
        else
            this.vbo.showBig(shaders, GLES20.GL_TRIANGLES);
    }
}
