package up.info.tp_shaders;

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
    private final int glnmlbuffer;
    private final int gltexbuffer;
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
    public VBO(int glposbuffer, int glnmlbuffer, int gltexbuffer, short[] elem) {
        this.glposbuffer = glposbuffer;
        this.glnmlbuffer = glnmlbuffer;
        this.gltexbuffer = gltexbuffer;
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
    public VBO(int glposbuffer, int glnmlbuffer, int gltexbuffer, int[] elem) {
        this.glposbuffer = glposbuffer;
        this.glnmlbuffer = glnmlbuffer;
        this.gltexbuffer = gltexbuffer;
        this.glelembuffer = elemToGlBuffer(elem);
        this.nbelem = elem.length;
        this.typeelem = GLES20.GL_UNSIGNED_INT;
        this.sizeelem = Integer.BYTES;
    }

    /**
     * Vertex pos to gl buffer int.
     *
     * @param floatarray the vertexpos
     * @return the int
     */
    public static int floatArrayToGlBuffer(float[] floatarray) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(floatarray.length * Float.BYTES);
        byteBuffer.order(ByteOrder.nativeOrder());

        FloatBuffer posbuffer = byteBuffer.asFloatBuffer();
        posbuffer.put(floatarray);
        posbuffer.position(0);

        int[] buffers = new int[1];
        GLES20.glGenBuffers(1, buffers, 0);
        int glbuffer = buffers[0];

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, glbuffer);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, floatarray.length * Float.BYTES,
                posbuffer, GLES20.GL_STATIC_DRAW);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

        return glbuffer;
    }

    public static float[] computeNormals(float[] pos, short[] elem) {
        float[] nml = new float[pos.length];

        for (int i = 0; i < elem.length; i += 3) {
            Vec3f A = new Vec3f(pos[3 * elem[i]], pos[3 * elem[i] + 1], pos[3 * elem[i] + 2]);
            Vec3f B = new Vec3f(pos[3 * elem[i + 1]], pos[3 * elem[i + 1] + 1], pos[3 * elem[i + 1] + 2]);
            Vec3f C = new Vec3f(pos[3 * elem[i + 2]], pos[3 * elem[i + 2] + 1], pos[3 * elem[i + 2] + 2]);

            Vec3f X = new Vec3f();
            Vec3f Y = new Vec3f();

            X.setSub(B, A);
            Y.setSub(C, A);

            Vec3f vec3f = new Vec3f();
            vec3f.setCrossProduct(X, Y);
            vec3f.normalize();

            for (int j = 0; j < 3; j++) {
                nml[3 * elem[i + j]] = vec3f.x;
                nml[3 * elem[i + j] + 1] = vec3f.y;
                nml[3 * elem[i + j] + 2] = vec3f.z;
            }
        }

        return nml;
    }

    /**
     * Show.
     *
     * @param shaders  the shaders
     * @param elemtype the elemtype
     */
    public void show(LightingShaders shaders, int elemtype, int textureid, boolean withoutline) {
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, glposbuffer);
        shaders.setPositionsPointer(3, GLES20.GL_FLOAT);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, glnmlbuffer);
        shaders.setNormalsPointer(3, GLES20.GL_FLOAT);

        if (this.gltexbuffer != 0) {
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureid);
            shaders.setTextureUnit(0);

            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, gltexbuffer);
            shaders.setTextureCoordsPointer(2, GLES20.GL_FLOAT);
        }

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, glelembuffer);
        GLES20.glDrawElements(elemtype, this.nbelem, this.typeelem, 0);

        if (withoutline) {
            shaders.setMaterialColor(MyGLRenderer.black);
            for (int i = 0; i < this.nbelem; i += 3)
                GLES20.glDrawElements(GLES20.GL_LINE_LOOP, 3, this.typeelem, i * this.sizeelem);
        }

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
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

}
