package up.info.tp_1_2_3;

import android.util.Log;

public class Cylinder extends MyObject {

    private static final int nbdiv = 25;

    public Cylinder(float posx, float posz, float height, float[] color) {
        super(posx, 0F, posz, height, color, false);

        float[] vertexpos = new float[2 * 3 * (nbdiv + 1)];

        float phistep = 360F / nbdiv;

        for (int i = 0, n = -1; i < 2; i++) {
            for (int j = 0; j < nbdiv; j++) {
                float phi = (float) Math.toRadians(phistep * j);
                vertexpos[++n] = (float) Math.cos(phi);
                vertexpos[++n] = i % 2 == 0 ? 0F : 1F;
                vertexpos[++n] = (float) Math.sin(phi);
            }
        }

        vertexpos[vertexpos.length - 2] = 1.0F;

        short[] triangles = new short[2 * 2 * 3 * nbdiv];

        for (short i = 0, n = -1; i < nbdiv; i++) {
            triangles[++n] = i;
            triangles[++n] = (short) ((i + 1) % nbdiv);
            triangles[++n] = (short) (nbdiv + i);
            triangles[++n] = (short) (nbdiv + i);
            triangles[++n] = (short) ((i + 1) % nbdiv);
            triangles[++n] = (short) (nbdiv + (i + 1) % nbdiv);
        }

        for (short i = 0, n = 2 * 3 * nbdiv - 1; i < nbdiv; i++) {
            triangles[++n] = i;
            triangles[++n] = (short) ((i + 1) % nbdiv);
            triangles[++n] = (short) 2 * nbdiv - 1;
            triangles[++n] = (short) (nbdiv + i);
            triangles[++n] = (short) (nbdiv + (i + 1) % nbdiv);
            triangles[++n] = (short) 2 * nbdiv;
        }

        setMainvbo(new VBO(VBO.vertexPosToGlBuffer(vertexpos), triangles));
    }

}
