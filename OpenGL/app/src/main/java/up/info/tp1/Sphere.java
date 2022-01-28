package up.info.tp1;

public class Sphere {

    private final int nbLat = 25;
    private final int nbLong = 25;

    private final VBO vbo;

    public Sphere() {
        float[] vertexpos = new float[3 * (nbLong * nbLat + 2)];

        int phi = 360 / (nbLat - 1);
        int theta = 180 / nbLong;

        for (int i = 0; i < nbLong; i++) {
            for (int j = 0; j < nbLat; j++) {
                int offset =  3 * j + 3 * i * nbLong;
                double p = Math.toRadians(phi * j);
                double t = Math.toRadians(-90 + theta * (i + 1));
                vertexpos[offset]     = (float) Math.cos(t) * (float) Math.cos(p);
                vertexpos[offset + 1] = (float) Math.sin(t);
                vertexpos[offset + 2] = (float) Math.cos(t) * (float) Math.sin(p);
            }
        }

        vertexpos[vertexpos.length - 5] = 1;
        vertexpos[vertexpos.length - 2] = -1;

        int glposbuffer = VBO.vertexPosToGlBuffer(vertexpos);

        short[] triangles = new short[(6 * (nbLat - 1) * (nbLong - 1)) + 6 * (nbLat - 1)];

        for (int i = 0; i < nbLat - 1; i++) {
            for (int j = 0; j < nbLong - 1; j++) {
                int offset =  3 * j + 3 * i * (nbLong - 1);
                triangles[offset]     = (short) ((i * nbLat) + j + 1);
                triangles[offset + 1] = (short) ((i * nbLat) + j);
                triangles[offset + 2] = (short) ((i * nbLat) + j + nbLat);
            }
        }

        for (int i = 0; i < nbLat - 1; i++) {
            for (int j = 0; j < nbLong - 1; j++) {
                int offset =  3 * (nbLat - 1) * (nbLong - 1) + 3 * j + 3 * i * (nbLong - 1);
                triangles[offset]     = (short) ((i * nbLat) + j + nbLat + 1);
                triangles[offset + 1] = (short) ((i * nbLat) + j + 1);
                triangles[offset + 2] = (short) ((i * nbLat) + j + nbLat);
            }
        }

        for (int i = 0; i < nbLat - 1; i++) {
            int offset =  6 * (nbLat - 1) * (nbLong - 1) + 3 * i;
            triangles[offset]     = (short) (i);
            triangles[offset + 1] = (short) (i + 1);
            triangles[offset + 2] = (short) (nbLat * nbLong + 1);
        }

        for (int i = 0; i < nbLat - 1; i++) {
            int offset =  3 * (nbLat - 1) + 6 * (nbLat - 1) * (nbLong - 1) + 3 * i;
            triangles[offset]     = (short) (nbLat * (nbLong - 1) + i + 1);
            triangles[offset + 1] = (short) (nbLat * (nbLong - 1) + i);
            triangles[offset + 2] = (short) (nbLat * nbLong + 2);
        }

        vbo = new VBO(glposbuffer, triangles);
    }

    public VBO getVbo() { return vbo; }
}
