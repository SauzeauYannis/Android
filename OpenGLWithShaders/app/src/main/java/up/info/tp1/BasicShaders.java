package up.info.tp1;

import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
     * General method to get the content of a text file
     * @param file input file
     * @return string containing all the file content
     * @throws IOException
     */
    static String getTextContent(InputStream file) throws IOException
    {
        BufferedReader reader=new BufferedReader(new InputStreamReader(file));
        String content="";
        while(reader.ready())
        {
            String line=reader.readLine();
            content+=line+'\n';
        }
        return content;
    }

    /**
     * Method to create a program from source files for vertex and fragment shaders
     * @param context Context of the application, a way to get to assets
     * @param vertname name of the vertex program
     * @param fragname name of the fragment program
     * @return program linking the compiled vertex and fragment programs
     */
    static protected int initializeShadersFromResources(Context context,String vertname,String fragname)
    {
        String vertsrc,fragsrc;
        try {
            AssetManager assetmngr = context.getAssets();

            InputStream vertinput = assetmngr.open(vertname);
            InputStream fraginput = assetmngr.open(fragname);
            vertsrc = getTextContent(vertinput);
            fragsrc = getTextContent(fraginput);
        }
        catch(IOException e)
        {
            MainActivity.log("Error loading shaders : " +e);
            throw new RuntimeException("Error loading shaders");
        }

        int shaderprogram=initializeShaders(vertsrc,fragsrc);
        return shaderprogram;
    }

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
    public BasicShaders(Context context)
    {
        this.shaderprogram=createProgram(context);
        this.findVariables();
    }

    /**
     * Method to create shaders. Made abstract to make sure that it is
     * created by downclasses
     * @return program id created after compiling and linking shader programs
     */
    public abstract int createProgram(Context context);


    /**
     * Get Shaders variables (uniform, attributes, etc.)
     */
    public void findVariables()
    {
        // Variables for matrices
        this.uProjectionMatrix = GLES20.glGetUniformLocation(this.shaderprogram, "uProjectionMatrix");
        if (this.uProjectionMatrix==-1) throw new RuntimeException("uPojectionMatrix not found in shaders");
        this.uModelViewMatrix = GLES20.glGetUniformLocation(this.shaderprogram, "uModelViewMatrix");
        if (this.uProjectionMatrix==-1) throw new RuntimeException("uModelViewMatrix not found in shaders");

        // vertex attributes
        this.aVertexPosition = GLES20.glGetAttribLocation(this.shaderprogram, "aVertexPosition");
        if (this.aVertexPosition==-1) throw new RuntimeException("aVertexPosition not found in shaders");
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
