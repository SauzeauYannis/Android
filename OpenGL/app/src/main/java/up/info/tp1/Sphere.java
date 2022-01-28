package up.info.tp1;

public class Sphere {

    private final VBO vbo;

    public Sphere() {
        int nbslice = 25;
        int nbcut = 25;

        float[] vertexpos = new float[3 * ((nbslice - 1) * nbcut + 2)];

        float theta = 180.0F / nbslice;
        float phi = 360.0F / nbcut;

        int n = 0;

        for (int i = 1; i < nbslice; i++) {
            for (int j = 0; j < nbcut; j++) {
                double t = Math.toRadians(-90.0F + theta * i);
                double p = Math.toRadians(phi * j);
                vertexpos[n++] = (float) Math.cos(t) * (float) Math.cos(p);
                vertexpos[n++] = (float) Math.cos(t) * (float) Math.sin(p);
                vertexpos[n++] = (float) Math.sin(t);
            }
        }

        vertexpos[vertexpos.length - 4] = -1;
        vertexpos[vertexpos.length - 1] = 1;

        int glposbuffer = VBO.vertexPosToGlBuffer(vertexpos);

        n = 0;

        short[] triangles = new short[6 * nbcut* (nbslice - 1)];

        for (int i = 0; i < nbslice - 2; i++) {
            for (int j = 0; j < nbcut; j++) {
                triangles[n++] = (short) (nbcut * i + (j + 1) % nbcut);
                triangles[n++] = (short) (nbcut * i + j);
                triangles[n++] = (short) (nbcut * (i + 1) + j);

                triangles[n++] = (short) (nbcut * (i + 1) + (j + 1) % nbcut);
                triangles[n++] = (short) (nbcut * i + (j + 1) % nbcut);
                triangles[n++] = (short) (nbcut * (i + 1) + j);
            }
        }

        for (int i = 0; i < nbcut; i++) {
            triangles[n++] = (short) (i);
            triangles[n++] = (short) ((i + 1) % nbcut);
            triangles[n++] = (short) ((nbcut) * (nbslice - 1));
        }

        for (int i = 0; i < nbcut; i++) {
            triangles[n++] = (short) ((nbcut) * (nbslice - 1) + 1);
            triangles[n++] = (short) ((nbcut) * (nbslice - 2) + (i + 1) % nbcut);
            triangles[n++] = (short) ((nbcut) * (nbslice - 2) + i);
        }

        vbo = new VBO(glposbuffer, triangles);
    }

    public VBO getVbo() { return vbo; }
}
