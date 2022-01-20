package up.info.tp1;

public class Shpere extends VBO {

    private static float[] initvertexpos(int radius, int nbQuarter, int nbSlice) {
        float[] vertexpos = new float[3 * nbQuarter * nbSlice];

        int theta = 360 / nbQuarter;
        int phi = 180 / nbSlice;

        for (int i = 0; i < nbQuarter; i++) {
            for (int j = 0; j < nbSlice; j++) {
                vertexpos[i + j]     = (float) (radius * Math.cos(theta * i) * Math.cos(-90 + phi * i));
                vertexpos[i + j + 1] = 0;
                vertexpos[i + j + 2] = 0;
            }
        }

        return vertexpos;
    }

    private static short[] inittrianglepos(int radius, int nbQuarter, int nbSlice) {
        short[] triangles = new short[3 * (2 * nbSlice * (nbQuarter - 1))];

        return triangles;
    }

    private Shpere(int radius, int nbQuarter, int nbSlice) {
        super(0,//initvertexpos(radius, nbQuarter, nbSlice),
                inittrianglepos(radius, nbQuarter, nbSlice));
    }

}
