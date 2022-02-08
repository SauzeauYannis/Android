package up.info.tp_1_2_3;

import android.util.Pair;

import java.util.HashMap;

public class Sphere {

    private final float[] vertexpos;
    private final short[] triangles;

    private short vertexnum;
    private int trianglesnum;

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

        vertexnum = (short) vertexpos.length;
        trianglesnum = triangles.length;
        vbo = new VBO(VBO.vertexPosToGlBuffer(vertexpos), triangles);
    }

    public Sphere(int nbsubdivision) {
        vertexnum = 6;
        vertexpos = new float[6 + 3 * (int) Math.pow(4, nbsubdivision)];

        for (int i = 0, n = -1; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                vertexpos[++n] = j == 0 ? (i == 0 ? 1 : -1) : 0;
                vertexpos[++n] = j == 1 ? (i == 0 ? 1 : -1) : 0;
                vertexpos[++n] = j == 2 ? (i == 0 ? 1 : -1) : 0;
            }
        }

        trianglesnum = 0;
        triangles = new short[3 * 8 * (int) Math.pow(4, nbsubdivision - 1)];

        short[] initTriangle = new short[] {
                0, 1, 2,
                1, 3, 2,
                3, 4, 2,
                4, 0, 2,
                5, 1, 0,
                5, 3, 1,
                5, 4, 3,
                5, 0, 4
        };

        if (nbsubdivision > 1) {
            HashMap<Pair<Short, Short>, Short> middlemap = new HashMap<>();
            for (int i = 0; i < initTriangle.length; i++)
                createSphereRec(nbsubdivision, middlemap, initTriangle[i], initTriangle[++i], initTriangle[++i]);
        } else {
            System.arraycopy(initTriangle, 0, triangles, 0, initTriangle.length);
            trianglesnum = initTriangle.length;
        }

        vbo = new VBO(VBO.vertexPosToGlBuffer(vertexpos), triangles);
    }

    public VBO getVbo() { return vbo; }

    private void createSphereRec(int nbsubdivision, HashMap<Pair<Short, Short>, Short> middlemap, short a, short b, short c) {
        if (nbsubdivision == 1) {
            triangles[trianglesnum++] = a;
            triangles[trianglesnum++] = b;
            triangles[trianglesnum++] = c;
        } else {
            short d = computeMiddle(middlemap, a, b);
            short e = computeMiddle(middlemap, b, c);
            short f = computeMiddle(middlemap, c, a);

            createSphereRec(nbsubdivision - 1, middlemap, a, d, f);
            createSphereRec(nbsubdivision - 1, middlemap, d, b, e);
            createSphereRec(nbsubdivision - 1, middlemap, f, e, c);
            createSphereRec(nbsubdivision - 1, middlemap, d, e, f);
        }
    }

    private short computeMiddle(HashMap<Pair<Short, Short>, Short> middlemap, short v1, short v2) {
        Pair<Short, Short> middlekey = v1 < v2 ? new Pair<>(v1, v2) : new Pair<>(v2, v1);

        if (middlemap.containsKey(middlekey))
            return middlemap.get(middlekey);

        short vm = vertexnum++;

        for (int i = 0; i < 3; i++)
            vertexpos[3 * vm + i] = 0.5F * (vertexpos[3 * v1 + i] + vertexpos[3 * v2 + i]);

        float normd = (float) (1.0F / Math.sqrt(Math.pow(vertexpos[3 * vm], 2) + Math.pow(vertexpos[3 * vm + 1], 2) + Math.pow(vertexpos[3 * vm + 2], 2)));

        for (int i = 0; i < 3; i++)
            vertexpos[3 * vm + i] *= normd;

        middlemap.put(middlekey, vm);

        return vm;
    }

}
