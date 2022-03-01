package up.info.tp_shaders;

import android.util.Pair;

import java.util.HashMap;

/**
 * The type Sphere.
 */
public class Sphere {

    private final float[] vertexpos;
    private final short[] triangles;

    private short nbvertex; // maybe useless
    private int nbtriangle;

    private final VBO vbo;

    /**
     * Instantiates a new Sphere.
     */
    public Sphere(int nbslice, int nbcut) {
        vertexpos = new float[3 * ((nbslice - 1) * nbcut + 2)];

        float thetastep = 180.0F / nbslice;
        float phistep = 360.0F / nbcut;

        for (int i = 1, n = -1; i < nbslice; i++) {
            double theta = Math.toRadians(-90.0F + thetastep * i);
            for (int j = 0; j < nbcut; j++) {
                double phi = Math.toRadians(phistep * j);
                vertexpos[++n] = (float) Math.cos(theta) * (float) Math.cos(phi);
                vertexpos[++n] = (float) Math.cos(theta) * (float) Math.sin(phi);
                vertexpos[++n] = (float) Math.sin(theta);
            }
        }

        vertexpos[vertexpos.length - 4] = -1;
        vertexpos[vertexpos.length - 1] = 1;

        triangles = new short[2 * 3 * nbcut * (nbslice - 1)];

        nbtriangle = -1;
        
        for (int i = 0; i < nbslice - 2; i++) {
            int h = nbcut * i;
            int h1 = h + nbcut;
            for (int j = 0; j < nbcut; j++) {
                int k = (j + 1) % nbcut;
                triangles[++nbtriangle] = (short) (h + k);
                triangles[++nbtriangle] = (short) (h1 + j);
                triangles[++nbtriangle] = (short) (h + j);
                triangles[++nbtriangle] = (short) (h1 + k);
                triangles[++nbtriangle] = (short) (h1 + j);
                triangles[++nbtriangle] = (short) (h + k);
            }
        }

        for (int i = 0; i < nbcut; i++) {
            triangles[++nbtriangle] = (short) (i);
            triangles[++nbtriangle] = (short) ((nbcut) * (nbslice - 1));
            triangles[++nbtriangle] = (short) ((i + 1) % nbcut);
        }

        for (int i = 0; i < nbcut; i++) {
            int h = (nbcut) * (nbslice - 2);
            int h1 = h + nbcut;
            triangles[++nbtriangle] = (short) (h1 + 1);
            triangles[++nbtriangle] = (short) (h + i);
            triangles[++nbtriangle] = (short) (h + (i + 1) % nbcut);
        }

        nbvertex = (short) vertexpos.length;

        float[] normals = new float[vertexpos.length];
        System.arraycopy(vertexpos, 0, normals, 0, vertexpos.length);

        int glposbuffer = VBO.floatArrayToGlBuffer(vertexpos);
        int glnmlbuffer = VBO.floatArrayToGlBuffer(normals);
        vbo = new VBO(glposbuffer, glnmlbuffer, triangles);
    }

    /**
     * Instantiates a new Sphere.
     *
     * @param nbsubdivision the nbsubdivision
     */
    public Sphere(int nbsubdivision) {
        nbvertex = 6;
        vertexpos = new float[6 + 3 * (int) Math.pow(4, nbsubdivision + 1)];

        for (int i = 0, n = -1; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                vertexpos[++n] = j == 0 ? (i == 0 ? 1 : -1) : 0;
                vertexpos[++n] = j == 1 ? (i == 0 ? 1 : -1) : 0;
                vertexpos[++n] = j == 2 ? (i == 0 ? 1 : -1) : 0;
            }
        }

        nbtriangle = -1;
        triangles = new short[3 * 8 * (int) Math.pow(4, nbsubdivision)];

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

        if (nbsubdivision > 0) {
            HashMap<Pair<Short, Short>, Short> middlemap = new HashMap<>();
            for (int i = 0; i < initTriangle.length; i++)
                createSphereRec(nbsubdivision, middlemap, initTriangle[i], initTriangle[++i], initTriangle[++i]);
        } else {
            System.arraycopy(initTriangle, 0, triangles, 0, initTriangle.length);
            nbtriangle = initTriangle.length;
        }

        float[] normals = new float[vertexpos.length];
        System.arraycopy(vertexpos, 0, normals, 0, vertexpos.length);

        int glposbuffer = VBO.floatArrayToGlBuffer(vertexpos);
        int glnmlbuffer = VBO.floatArrayToGlBuffer(normals);
        vbo = new VBO(glposbuffer, glnmlbuffer, triangles);
    }

    /**
     * Gets vbo.
     *
     * @return the vbo
     */
    public VBO getVbo() { return vbo; }

    private void createSphereRec(int nbsubdivision, HashMap<Pair<Short, Short>, Short> middlemap, short a, short b, short c) {
        if (nbsubdivision == 0) {
            triangles[++nbtriangle] = a;
            triangles[++nbtriangle] = b;
            triangles[++nbtriangle] = c;
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

        short vm = nbvertex++;

        for (int i = 0; i < 3; i++)
            vertexpos[3 * vm + i] = 0.5F * (vertexpos[3 * v1 + i] + vertexpos[3 * v2 + i]);

        float normd = (float) (1.0F / Math.sqrt(Math.pow(vertexpos[3 * vm], 2) + Math.pow(vertexpos[3 * vm + 1], 2) + Math.pow(vertexpos[3 * vm + 2], 2)));

        for (int i = 0; i < 3; i++)
            vertexpos[3 * vm + i] *= normd;

        middlemap.put(middlekey, vm);

        return vm;
    }

}
