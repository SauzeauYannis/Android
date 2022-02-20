package up.info.tp_1_2_3;

import android.opengl.GLES20;
import java.nio.FloatBuffer;

/**
 * Abstract class to manipulate any shaders. Only position of vertices and their tranformation
 * are handled
 * @author Philippe Meseure
 * @version 1.0
 */
public abstract class BasicShaders
{

    /**
     * General method to compile and link vertex and fragment shaders in a shader program
     * @param vertsrc vertex shader
     * @param fragsrc fragment shader
     * @return program shader
     */
    static public int initializeShaders(String vertsrc,String fragsrc)
    {
        MainActivity.log("Loading shaders");

        // First compile vertex and fragment shaders
        int vertexshader=loadShader(GLES20.GL_VERTEX_SHADER,vertsrc);
        int fragmentshader=loadShader(GLES20.GL_FRAGMENT_SHADER,fragsrc);

        // Create a GL program
        int shaderprogram = GLES20.glCreateProgram();
        MyGLRenderer.checkGlError("glCreateProgram");
        if (shaderprogram==0) return shaderprogram; // error ???

        // Attach vertex shader to program
        GLES20.glAttachShader(shaderprogram, vertexshader);
        MyGLRenderer.checkGlError("glAttachShader");
        // Attach fragment shader to program
        GLES20.glAttachShader(shaderprogram, fragmentshader);
        MyGLRenderer.checkGlError("glAttachShader");

        // Link both shaders into a program
        GLES20.glLinkProgram(shaderprogram);
        // Check if a link error appeared
        int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(shaderprogram, GLES20.GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] != GLES20.GL_TRUE)
        {
            throw new RuntimeException("Could not link program: "
                    +GLES20.glGetProgramInfoLog(shaderprogram));
        }

        // Now activate program
        GLES20.glUseProgram(shaderprogram);
        MyGLRenderer.checkGlError("glUseProgram");

        MainActivity.log("Shaders initialized");
        return shaderprogram;
    }

    /**
     * Utility method for compiling a OpenGL shader.
     *
     * @param type       - Vertex or fragment shader type.
     * @param shaderCode - String containing the shader code.
     * @return - Returns an id for the shader.
     */
    private static int loadShader(int type, String shaderCode)
    {
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);
        MyGLRenderer.checkGlError("glCreateShader");
        if (shader==0) return shader; // Could not create ??

        // Add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        MyGLRenderer.checkGlError("glShaderSource");

        // Compile shader and check compile errors
        GLES20.glCompileShader(shader);
        int[] compiled = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
        if (compiled[0] == 0) {
            throw new RuntimeException("Could not compile shader: "
                    + GLES20.glGetShaderInfoLog(shader));
        }

        return shader;
    }


    /**
     * Shader program id (GLSL uniform variable)
     */
    protected int shaderprogram;
    /**
     * Matrix to represent to projection onto the screen (GLSL uniform variable)
     */
    private int uProjectionMatrix;
    /**
     * Matrix to represent a transformation from an object space into viewer's space
     * (GLSL uniform variable)
     */
    private int uModelViewMatrix;
    /**
     * Index to give the array containing vertex position
     */
    private int aVertexPosition;


    /**
     * Constructor of the complete rendering Shader programs
     */
    public BasicShaders(final MyGLRenderer renderer)
    {
        this.shaderprogram=createProgram();
        this.findVariables();
    }

    /**
     * Method to create shaders. Made abstract to make sure that it is
     * created by downclasses
     * @return program id created after compiling and linking shader programs
     */
    public abstract int createProgram();


    /**
     * Get Shaders variables (uniform, attributes, etc.)
     */
    public void findVariables()
    {
        // Variables for matrices
        this.uProjectionMatrix = GLES20.glGetUniformLocation(this.shaderprogram, "uProjectionMatrix");
        this.uModelViewMatrix = GLES20.glGetUniformLocation(this.shaderprogram, "uModelViewMatrix");

        // vertex attributes
        this.aVertexPosition = GLES20.glGetAttribLocation(this.shaderprogram, "aVertexPosition");
        GLES20.glEnableVertexAttribArray(this.aVertexPosition);
    }

    /*====================
      = Matrix functions =
      ====================*/
    /**
     * Set the uniform variable representing the Modelview Matrix
     * Modelview = transformation from object's space to viewer's space
     * @param matrix Matrix used to set the modelview matrix
     */
    public void setModelViewMatrix(final float[] matrix)
    {
        GLES20.glUniformMatrix4fv(this.uModelViewMatrix, 1, false, matrix,0);
    }

    /**
     * Set the uniform variable representing the projection Matrix
     * @param matrix Matrix used to set the projection matrix
     */
    public void setProjectionMatrix(final float[] matrix)
    {
        GLES20.glUniformMatrix4fv(this.uProjectionMatrix, 1, false, matrix, 0);
    }



    /* =======================
       = Attributes handling =
       ======================= */
    /**
     * Provide the shaders with the list of vertex positions
     * @param size Number of coordinates for each vertex
     * @param dtype Type of coordinates
     * @param stride Offset between two vertex coordinates
     * @param buffer Buffer containing the vertex positions
     */
    public void setPositionsPointer(final int size, final int dtype, final int stride,
                                    final FloatBuffer buffer)
    {
        GLES20.glVertexAttribPointer(this.aVertexPosition,size,dtype,false,stride,buffer);
    }

    /**
     * Provide the shaders with the list of vertex of the desired VBO
     * @param size Number of coordinates for each vertex
     * @param dtype Type of coordinates
     */
    public void setPositionsPointer(final int size, final int dtype)
    {
        GLES20.glVertexAttribPointer(this.aVertexPosition,size,dtype,false,0,0);
    }

}
