package up.info.tp1;

public class Sphere {

    private final float[] vertexpos;
    private final short[] triangles;
    private final VBO vbo;

    public Sphere() {
        int nbslice = 25;
        int nbcut = 25;

        vertexpos = new float[3 * ((nbslice - 1) * nbcut + 2)];

        float theta = 180.0F / nbslice;
        float phi = 360.0F / nbcut;

        int n = 0;

        for (int i = 1; i < nbslice; i++) {
            double t = Math.toRadians(-90.0F + theta * i);
            for (int j = 0; j < nbcut; j++) {
                double p = Math.toRadians(phi * j);
                vertexpos[n++] = (float) Math.cos(t) * (float) Math.cos(p);
                vertexpos[n++] = (float) Math.cos(t) * (float) Math.sin(p);
                vertexpos[n++] = (float) Math.sin(t);
            }
        }

        vertexpos[vertexpos.length - 4] = -1;
        vertexpos[vertexpos.length - 1] = 1;

        n = 0;

        triangles = new short[6 * nbcut* (nbslice - 1)];

        for (int i = 0; i < nbslice - 2; i++) {
            int h = nbcut * i;
            int h1 = h + nbcut;
            for (int j = 0; j < nbcut; j++) {
                int k = (j + 1) % nbcut;
                triangles[n++] = (short) (h + k);
                triangles[n++] = (short) (h + j);
                triangles[n++] = (short) (h1 + j);
                triangles[n++] = (short) (h1 + k);
                triangles[n++] = (short) (h + k);
                triangles[n++] = (short) (h1 + j);
            }
        }

        for (int i = 0; i < nbcut; i++) {
            triangles[n++] = (short) (i);
            triangles[n++] = (short) ((i + 1) % nbcut);
            triangles[n++] = (short) ((nbcut) * (nbslice - 1));
        }

        for (int i = 0; i < nbcut; i++) {
            int h = (nbcut) * (nbslice - 2);
            int h1 = h + nbcut;
            triangles[n++] = (short) (h1 + 1);
            triangles[n++] = (short) (h + (i + 1) % nbcut);
            triangles[n++] = (short) (h + i);
        }

        vbo = new VBO(VBO.vertexPosToGlBuffer(vertexpos), triangles);
    }

    public Sphere(int nbsubdivision) {

        vertexpos = new float[3 * 6 * (int) Math.pow(2, nbsubdivision - 1)];

        for (int i = 0, n = -1; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                vertexpos[++n] = j == 0 ? (i == 0 ? 1 : -1) : 0;
                vertexpos[++n] = j == 1 ? (i == 0 ? 1 : -1) : 0;
                vertexpos[++n] = j == 2 ? (i == 0 ? 1 : -1) : 0;
            }
        }

        triangles = new short[3 * 8 * (int) Math.pow(4, nbsubdivision - 1)];

        short[] initTriangle = new short[] {
                0, 1, 2,
                2, 4, 0,
                1, 0, 5,
                0, 4, 5,
                2, 3, 1,
                2, 4, 3,
                1, 3, 5,
                3, 4, 5
        };

        for (int i = 0; i < initTriangle.length; i++)
            createSphere(nbsubdivision, i, initTriangle[i], initTriangle[++i], initTriangle[++i]);

        vbo = new VBO(VBO.vertexPosToGlBuffer(vertexpos), triangles);
    }

    private void createSphere(int nbsubdivision, int pos, short a, short b, short c) {
        if (nbsubdivision == 1) {
            triangles[pos]   = a;
            triangles[++pos] = b;
            triangles[++pos] = c;
        } else {

        }
    }

    public VBO getVbo() { return vbo; }
}
