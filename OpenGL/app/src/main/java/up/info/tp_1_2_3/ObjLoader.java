package up.info.tp_1_2_3;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ObjLoader extends MyObject {

    private final float[] vertexPos;
    private final int[] triangles;

    public ObjLoader(String filepath, float posx, float posy, float posz, float scale, float[] color) {
        super(posx, posy, posz, scale, color, true);

        List<Float> vertexlist = new ArrayList<>();
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

        this.vertexPos = new float[vertexlist.size()];
        this.triangles = new int[triangleslist.size()];

        for (int i = 0; i < vertexlist.size(); i++)
            this.vertexPos[i] = vertexlist.get(i);

        for (int i = 0; i < triangleslist.size(); i++)
            this.triangles[i] = triangleslist.get(i);

        setMainvbo(new VBO(VBO.vertexPosToGlBuffer(this.vertexPos), this.triangles));
    }

}
