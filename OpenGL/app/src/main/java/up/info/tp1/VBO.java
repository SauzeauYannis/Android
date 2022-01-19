package up.info.tp1;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class VBO {

    public VBO(float[] vertexpos, char[] triangles) {
        ByteBuffer posbytebuf = ByteBuffer.allocateDirect(vertexpos.length * Float.BYTES);
        posbytebuf.order(ByteOrder.nativeOrder());

    }

}
