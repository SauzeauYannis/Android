package up.info.tp_1_2_3;

import android.util.Log;

public class Cone extends MyObject {

    private static final int nbdiv = 25;

    public Cone(float posx, float posz, float height, float[] color) {
        super(posx, 0F, posz, height, color, false);

        float[] vertexpos = new float[3 * (nbdiv + 1)];

        float phistep = 360F / nbdiv;

        for (int i = 0, n = -1; i < nbdiv; i++) {
            float phi = (float) Math.toRadians(phistep * i);
            vertexpos[++n] = (float) Math.cos(phi);
            vertexpos[++n] = 0F;
            vertexpos[++n] = (float) Math.sin(phi);
        }

        vertexpos[vertexpos.length - 2] = 1F;

        short[] triangles = new short[3 * nbdiv];

        for (short i = 0, n = -1; i < nbdiv; i++) {
            triangles[++n] = i;
            triangles[++n] = (short) ((i + 1) % nbdiv);
            triangles[++n] = nbdiv;
        }

        setMainvbo(new VBO(VBO.vertexPosToGlBuffer(vertexpos), triangles));
    }

}
