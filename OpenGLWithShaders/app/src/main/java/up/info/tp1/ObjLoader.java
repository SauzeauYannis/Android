package up.info.tp1;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ObjLoader extends MyObject {

    private final float[] vertexPos;
    private final int[] triangles;

    public ObjLoader(String filepath, float posx, float posy, float posz, float scale, float[] color, int vertexnum, int trianglesnum) {
        super(posx, posy, posz, scale, color);

        this.vertexPos = new float[vertexnum];
        this.triangles = new int[trianglesnum];

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(ObjLoader.class.getResourceAsStream(filepath)));
            String line;
            int vn = -1;
            int tn = -1;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("v ")) {
                    String[] splitted = line.split(" ");
                    this.vertexPos[++vn] = Float.parseFloat(splitted[1]);
                    this.vertexPos[++vn] = Float.parseFloat(splitted[2]);
                    this.vertexPos[++vn] = Float.parseFloat(splitted[3]);
                } else if (line.startsWith("f")) {
                    String[] splitted = line.split(" ");
                    this.triangles[++tn] = Integer.parseInt(splitted[1].split("/")[0]) - 1;
                    this.triangles[++tn] = Integer.parseInt(splitted[2].split("/")[0]) - 1;
                    this.triangles[++tn] = Integer.parseInt(splitted[3].split("/")[0]) - 1;
                }
            }

            reader.close();
        } catch (IOException e) {
            Log.e("LOG", "IO error");
        }

        setVbo(new VBO(VBO.floatArrayToGlBuffer(this.vertexPos), 0, this.triangles));
    }

    public void show(LightingShaders shaders, float[] modelviewmatrix, boolean withOutline) {
        show(shaders, modelviewmatrix, withOutline, true);
    }
}
