package up.info.tp_1_2_3;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/**
 * The type Vbo.
 */
public class VBO {

    private final int glposbuffer;
    private final int glelembuffer;
    private final int nbelem;
    private final int typeelem;
    private final int sizeelem;

    /**
     * Instantiates a new Vbo.
     *
     * @param glposbuffer the glposbuffer
     * @param elem        the elem
     */
    public VBO(int glposbuffer, short[] elem) {
        this.glposbuffer = glposbuffer;
        this.glelembuffer = elemToGlBuffer(elem);
        this.nbelem = elem.length;
        this.typeelem = GLES20.GL_UNSIGNED_SHORT;
        this.sizeelem = Short.BYTES;
    }

    /**
     * Instantiates a new Vbo.
     *
     * @param glposbuffer the glposbuffer
     * @param elem        the elem
     */
    public VBO(int glposbuffer, int[] elem) {
        this.glposbuffer = glposbuffer;
        this.glelembuffer = elemToGlBuffer(elem);
        this.nbelem = elem.length;
        this.typeelem = GLES20.GL_UNSIGNED_INT;
        this.sizeelem = Integer.BYTES;
    }

    /**
     * Vertex pos to gl buffer int.
     *
     * @param vertexpos the vertexpos
     * @return the int
     */
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

    /**
     * Show.
     *
     * @param shaders  the shaders
     * @param elemtype the elemtype
     */
    public void show(NoLightShaders shaders, int elemtype, boolean withoutline) {
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, glposbuffer);
        shaders.setPositionsPointer(3, GLES20.GL_FLOAT);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, glelembuffer);
        GLES20.glDrawElements(elemtype, nbelem, this.typeelem, 0);
        if (withoutline) {
            shaders.setColor(MyGLRenderer.black);
            for (int i = 0; i < nbelem; i += 3)
                GLES20.glDrawElements(GLES20.GL_LINE_LOOP, 3, this.typeelem, i * this.sizeelem);
        }
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

}
