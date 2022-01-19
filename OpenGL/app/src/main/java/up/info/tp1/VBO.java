package up.info.tp1;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class VBO {

    private final int glposbuffer;
    private final int glelembuffer;

    private final int nbElem;

    public VBO(float[] vertexpos, short[] triangles) {
        nbElem = triangles.length;

        ByteBuffer posbytebuf = ByteBuffer.allocateDirect(vertexpos.length * Float.BYTES);
        posbytebuf.order(ByteOrder.nativeOrder());

        FloatBuffer posbuffer = posbytebuf.asFloatBuffer();
        posbuffer.put(vertexpos);
        posbuffer.position(0);

        ByteBuffer tribytebuf = ByteBuffer.allocateDirect(triangles.length * Short.BYTES);
        tribytebuf.order(ByteOrder.nativeOrder());

        ShortBuffer tribuffer = tribytebuf.asShortBuffer();
        tribuffer.put(triangles);
        tribuffer.position(0);

        int[] buffers = new int[1];
        GLES20.glGenBuffers(1, buffers, 0);
        glposbuffer = buffers[0];

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, glposbuffer);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexpos.length * Float.BYTES,
                posbuffer, GLES20.GL_STATIC_DRAW);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

        buffers = new int[1];
        GLES20.glGenBuffers(1, buffers, 0);
        glelembuffer = buffers[0];

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, glelembuffer);
        GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, triangles.length * Short.BYTES,
                tribuffer, GLES20.GL_STATIC_DRAW);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public int getGlposbuffer() { return glposbuffer; }
    public int getGlelembuffer() { return glelembuffer; }

    public int getNbElem() { return nbElem; }
}
