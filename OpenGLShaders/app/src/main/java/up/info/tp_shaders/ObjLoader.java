package up.info.tp_shaders;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Obj loader.
 */
public class ObjLoader extends MyObject {

    /**
     * Instantiates a new Obj loader.
     *
     * @param filepath      the filepath
     * @param posx          the posx
     * @param posy          the posy
     * @param posz          the posz
     * @param scale         the scale
     * @param color         the color
     * @param textureid     the textureid
     * @param specularcolor the specularcolor
     * @param shininess     the shininess
     */
    public ObjLoader(String filepath, float posx, float posy, float posz, float scale, float[] color, int textureid, float[] specularcolor, float shininess) {
        super(posx, posy, posz, scale, color, textureid, specularcolor, shininess);

        List<Float> vertexlist = new ArrayList<>();
        List<Float> normallist = new ArrayList<>();
        List<Float> textcoordlist = new ArrayList<>();
        List<Integer> triangleslist = new ArrayList<>();
        HashMap<Integer, Integer> normpos = new HashMap<>();
        HashMap<Integer, Integer> textpos = new HashMap<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(ObjLoader.class.getResourceAsStream(filepath)));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("v ")) {
                    String[] splitted = line.split(" ");
                    vertexlist.add(Float.parseFloat(splitted[1]));
                    vertexlist.add(Float.parseFloat(splitted[2]));
                    vertexlist.add(Float.parseFloat(splitted[3]));
                } else if (line.startsWith("vt")) {
                    String[] splitted = line.split(" ");
                    textcoordlist.add(Float.parseFloat(splitted[1]));
                    textcoordlist.add(Float.parseFloat(splitted[2]));
                } else if (line.startsWith("vn")) {
                    String[] splitted = line.split(" ");
                    normallist.add(Float.parseFloat(splitted[1]));
                    normallist.add(Float.parseFloat(splitted[2]));
                    normallist.add(Float.parseFloat(splitted[3]));
                } else if (line.startsWith("f")) {
                    String[] splitted = line.split(" ");
                    // ABC triangle
                    int A = Integer.parseInt(splitted[1].split("/")[0]) - 1;
                    int B = Integer.parseInt(splitted[2].split("/")[0]) - 1;
                    int C = Integer.parseInt(splitted[3].split("/")[0]) - 1;

                    triangleslist.add(A);
                    triangleslist.add(B);
                    triangleslist.add(C);

                    if (splitted[1].split("/")[1].length() > 0) {
                        textpos.put(A, Integer.parseInt(splitted[1].split("/")[1]) - 1);
                        textpos.put(A, Integer.parseInt(splitted[2].split("/")[1]) - 1);
                    }

                    normpos.put(A, Integer.parseInt(splitted[1].split("/")[2]) - 1);
                    normpos.put(A, Integer.parseInt(splitted[2].split("/")[2]) - 1);
                    normpos.put(A, Integer.parseInt(splitted[3].split("/")[2]) - 1);
                }
            }

            reader.close();
        } catch (IOException e) {
            Log.e("LOG", "IO error");
        }

        float[] vertexPos = new float[vertexlist.size()];
        float[] normals = new float[vertexlist.size()];
        float[] textcoords = new float[vertexlist.size()];
        int[] triangles = new int[triangleslist.size()];

        for (int i = 0; i < vertexlist.size(); i++)
            vertexPos[i] = vertexlist.get(i);

        for (Map.Entry<Integer, Integer> entry : normpos.entrySet()) {
            normals[3 * entry.getKey()] = normallist.get(3 * entry.getValue());
            normals[3 * entry.getKey() + 1] = normallist.get(3 * entry.getValue() + 1);
            normals[3 * entry.getKey() + 2] = normallist.get(3 * entry.getValue() + 2);
        }

        for (Map.Entry<Integer, Integer> entry : textpos.entrySet()) {
            textcoords[2 * entry.getKey()] = textcoordlist.get(2 * entry.getValue());
            textcoords[2 * entry.getKey() + 1] = textcoordlist.get(2 * entry.getValue() + 1);
        }

        for (int i = 0; i < triangleslist.size(); i++)
            triangles[i] = triangleslist.get(i);

        setMainvbo(new VBO(VBO.floatArrayToGlBuffer(vertexPos),
                VBO.floatArrayToGlBuffer(normals), VBO.floatArrayToGlBuffer(textcoords), triangles));
    }

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
        this(filepath, posx, posy, posz, scale, color, 0, null, 0);
    }

}
