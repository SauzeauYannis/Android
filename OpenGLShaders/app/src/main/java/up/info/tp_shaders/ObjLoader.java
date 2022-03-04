package up.info.tp_shaders;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Obj loader.
 */
public class ObjLoader extends MyObject {

    /**
     * Instantiates a new Obj loader.
     *
     * @param filepath the filepath
     * @param posx     the posx
     * @param posy     the posy
     * @param posz     the posz
     * @param scale    the scale
     * @param color    the color
     */
    public ObjLoader(String filepath, float posx, float posy, float posz, float scale, float[] color) {
        super(posx, posy, posz, scale, color);

        List<Float> vertexlist = new ArrayList<>();
        List<Float> normallist = new ArrayList<>();
        List<Integer> triangleslist = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(ObjLoader.class.getResourceAsStream(filepath)));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("v ")) {
                    String[] splitted = line.split(" ");
                    vertexlist.add(Float.parseFloat(splitted[1]));
                    vertexlist.add(Float.parseFloat(splitted[2]));
                    vertexlist.add(Float.parseFloat(splitted[3]));
                } else if (line.startsWith("vn")) {
                    String[] splitted = line.split(" ");
                    normallist.add(Float.parseFloat(splitted[1]));
                    normallist.add(Float.parseFloat(splitted[2]));
                    normallist.add(Float.parseFloat(splitted[3]));
                } else if (line.startsWith("f")) {
                    String[] splitted = line.split(" ");
                    triangleslist.add(Integer.parseInt(splitted[1].split("/")[0]) - 1);
                    triangleslist.add(Integer.parseInt(splitted[2].split("/")[0]) - 1);
                    triangleslist.add(Integer.parseInt(splitted[3].split("/")[0]) - 1);
                }
            }

            reader.close();
        } catch (IOException e) {
            Log.e("LOG", "IO error");
        }

        float[] vertexPos = new float[vertexlist.size()];
        float[] normals = new float[normallist.size()];
        int[] triangles = new int[triangleslist.size()];

        for (int i = 0; i < vertexlist.size(); i++)
            vertexPos[i] = vertexlist.get(i);

        for (int i = 0; i < normallist.size(); i++)
            normals[i] = normallist.get(i);

        for (int i = 0; i < triangleslist.size(); i++)
            triangles[i] = triangleslist.get(i);

        setMainvbo(new VBO(VBO.floatArrayToGlBuffer(vertexPos),
                VBO.floatArrayToGlBuffer(normals), triangles));
    }

}
