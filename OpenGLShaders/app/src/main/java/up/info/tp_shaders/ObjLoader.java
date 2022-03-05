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
        List<Integer> normposlist = new ArrayList<>();

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
                    normposlist.add(Integer.parseInt(splitted[1].split("/")[2]) - 1);
                    normposlist.add(Integer.parseInt(splitted[2].split("/")[2]) - 1);
                    normposlist.add(Integer.parseInt(splitted[3].split("/")[2]) - 1);
                }
            }

            reader.close();
        } catch (IOException e) {
            Log.e("LOG", "IO error");
        }

        float[] vertexPos = new float[vertexlist.size()];
        //float[] normals = new float[vertexlist.size()];
        float[] normals = new float[normallist.size()];
        int[] triangles = new int[triangleslist.size()];

        for (int i = 0; i < vertexlist.size(); i++)
            vertexPos[i] = vertexlist.get(i);

        for (int i = 0; i < normposlist.size(); i++) {
            int n = 3 * normposlist.get(i);
            normals[n] = normallist.get(n);
            normals[n + 1] = normallist.get(n + 1);
            normals[n + 2] = normallist.get(n + 2);
        }

        for (int i = 0; i < triangleslist.size(); i++)
            triangles[i] = triangleslist.get(i);

        Log.d("NML", String.valueOf(vertexlist.size()));
        Log.d("NML", String.valueOf(normallist.size()));
        Log.d("NML", String.valueOf(normals[0]));
        Log.d("NML", String.valueOf(normals[1]));
        Log.d("NML", String.valueOf(normals[2]));
        Log.d("NML", String.valueOf(normals[3]));
        Log.d("NML", String.valueOf(normals[4]));
        Log.d("NML", String.valueOf(normals[5]));

        setMainvbo(new VBO(VBO.floatArrayToGlBuffer(vertexPos),
                VBO.floatArrayToGlBuffer(normals), triangles));
    }

}
