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

    public VBO(int glposbuffer, short[] elem) {
        this.glposbuffer = glposbuffer;

        nbElem = elem.length;

        ByteBuffer elembytebuf = ByteBuffer.allocateDirect(elem.length * Short.BYTES);
        elembytebuf.order(ByteOrder.nativeOrder());

        ShortBuffer elembuffer = elembytebuf.asShortBuffer();
        elembuffer.put(elem);
        elembuffer.position(0);

        int[] buffers = new int[1];
        GLES20.glGenBuffers(1, buffers, 0);
        glelembuffer = buffers[0];

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, glelembuffer);
        GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, elem.length * Short.BYTES,
                elembuffer, GLES20.GL_STATIC_DRAW);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void show(NoLightShaders shaders, int elemtype) {
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, glposbuffer);
        shaders.setPositionsPointer(3, GLES20.GL_FLOAT);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, glelembuffer);
        GLES20.glDrawElements(elemtype, nbElem, GLES20.GL_UNSIGNED_SHORT, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void showOutline(NoLightShaders shaders) {
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, glposbuffer);
        shaders.setPositionsPointer(3, GLES20.GL_FLOAT);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, glelembuffer);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, nbElem, GLES20.GL_UNSIGNED_SHORT, 0);
        shaders.setColor(MyGLRenderer.black);
        GLES20.glLineWidth(4);
        GLES20.glDrawElements(GLES20.GL_LINE_STRIP, nbElem, GLES20.GL_UNSIGNED_SHORT, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public static int vertexPosToGlBuffer(float[] vertexpos) {
        ByteBuffer posbytebuf = ByteBuffer.allocateDirect(vertexpos.length * Float.BYTES);
        posbytebuf.order(ByteOrder.nativeOrder());

        FloatBuffer posbuffer = posbytebuf.asFloatBuffer();
        posbuffer.put(vertexpos);
        posbuffer.position(0);

        int[] buffers = new int[1];
        GLES20.glGenBuffers(1, buffers, 0);
        int glposbuffer = buffers[0];

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, glposbuffer);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexpos.length * Float.BYTES,
                posbuffer, GLES20.GL_STATIC_DRAW);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

        return glposbuffer;
    }

}
