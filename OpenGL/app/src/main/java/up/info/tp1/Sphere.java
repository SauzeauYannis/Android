package up.info.tp1;

public class Sphere extends VBO {

    public static int initglposbuffer(int radius, int nbLat, int nbLong) {
        float[] vertexpos = new float[3 * (nbLong * nbLat + 2)];

        float phi = 360.0F / (nbLat - 1);
        float theta = 180.0F / nbLong;

        for (int i = 0; i < nbLong; i++) {
            for (int j = 0; j < nbLat; j++) {
                int offset =  3 * j + 3 * i * nbLong;
                double p = Math.toRadians(phi * j);
                double t = Math.toRadians(-90 + theta * (i + 1));
                vertexpos[offset]     = radius * (float) Math.cos(t) * (float) Math.cos(p);
                vertexpos[offset + 1] = radius * (float) Math.sin(t);
                vertexpos[offset + 2] = radius * (float) Math.cos(t) * (float) Math.sin(p);
            }
        }

        vertexpos[vertexpos.length - 5] = -radius;
        vertexpos[vertexpos.length - 2] = radius;

        return VBO.vertexPosToGlBuffer(vertexpos);
    }

    public static short[] inittrianglepos(int nbLat, int nbLong) {
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

        return triangles;
    }

    public Sphere(int radius, int nbLat, int nbLong) {
        super(initglposbuffer(radius, nbLat, nbLong),
                inittrianglepos(nbLat, nbLong));
    }

}
