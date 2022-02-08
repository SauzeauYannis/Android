package up.info.tp1;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class VBO {

    private final int glposbuffer;
    private final int glnmlbuffer;
    private final int glelembuffer;
    private final int nbElem;

    public VBO(int glposbuffer, int glnmlbuffer, short[] elem) {
        this.glposbuffer = glposbuffer;
        this.glnmlbuffer = glnmlbuffer;
        this.glelembuffer = elemToGlBuffer(elem);
        nbElem = elem.length;
    }

    public VBO(int glposbuffer, int glnmlbuffer, int[] elem) {
        this.glposbuffer = glposbuffer;
        this.glnmlbuffer = glnmlbuffer;
        this.glelembuffer = elemToGlBuffer(elem);
        nbElem = elem.length;
    }

    public static int floatArrayToGlBuffer(float[] floatarray) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(floatarray.length * Float.BYTES);
        byteBuffer.order(ByteOrder.nativeOrder());

        FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
        floatBuffer.put(floatarray);
        floatBuffer.position(0);

        int[] buffers = new int[1];
        GLES20.glGenBuffers(1, buffers, 0);
        int glbuffer = buffers[0];

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, glbuffer);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, floatarray.length * Float.BYTES,
                floatBuffer, GLES20.GL_STATIC_DRAW);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

        return glbuffer;
    }

    private int elemToGlBuffer(short[] elem) {
        ByteBuffer elembytebuf = ByteBuffer.allocateDirect(elem.length * Short.BYTES);
        elembytebuf.order(ByteOrder.nativeOrder());

        ShortBuffer elembuffer = elembytebuf.asShortBuffer();
        elembuffer.put(elem);
        elembuffer.position(0);

        int[] buffers = new int[1];
        GLES20.glGenBuffers(1, buffers, 0);
        int glelembuffer = buffers[0];

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, glelembuffer);
        GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, elem.length * Short.BYTES,
                elembuffer, GLES20.GL_STATIC_DRAW);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);

        return glelembuffer;
    }

    private int elemToGlBuffer(int[] elem) {
        ByteBuffer elembytebuf = ByteBuffer.allocateDirect(elem.length * Integer.BYTES);
        elembytebuf.order(ByteOrder.nativeOrder());

        IntBuffer elembuffer = elembytebuf.asIntBuffer();
        elembuffer.put(elem);
        elembuffer.position(0);

        int[] buffers = new int[1];
        GLES20.glGenBuffers(1, buffers, 0);
        int glelembuffer = buffers[0];

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, glelembuffer);
        GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, elem.length * Integer.BYTES,
                elembuffer, GLES20.GL_STATIC_DRAW);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);

        return glelembuffer;
    }

    public void show(LightingShaders shaders, int elemtype, boolean useInt) {
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, glposbuffer);
        shaders.setPositionsPointer(3, GLES20.GL_FLOAT);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, glelembuffer);
        GLES20.glDrawElements(elemtype, nbElem, useInt ? GLES20.GL_UNSIGNED_INT :  GLES20.GL_UNSIGNED_SHORT, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, glnmlbuffer);
        shaders.setNormalsPointer(3, GLES20.GL_FLOAT);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void showOutline(LightingShaders shaders, int elemtype, boolean useInt) {
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, glposbuffer);
        shaders.setPositionsPointer(3, GLES20.GL_FLOAT);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, glelembuffer);
        GLES20.glDrawElements(elemtype, nbElem, useInt ? GLES20.GL_UNSIGNED_INT :  GLES20.GL_UNSIGNED_SHORT, 0);
        shaders.setMaterialColor(MyGLRenderer.black);
        GLES20.glLineWidth(4);
        for (int i = 0; i < nbElem; i += 3){
            GLES20.glDrawElements(GLES20.GL_LINE_STRIP, 3,
                    useInt ? GLES20.GL_UNSIGNED_INT :  GLES20.GL_UNSIGNED_SHORT, i * (useInt ? Integer.BYTES : Short.BYTES));
        }
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, glnmlbuffer);
        shaders.setNormalsPointer(3, GLES20.GL_FLOAT);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

}
