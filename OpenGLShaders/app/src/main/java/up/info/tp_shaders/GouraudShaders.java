package up.info.tp_shaders;

import android.content.Context;

/**
 * Implementation class of shaders to compute diffuse lighting using Gouraud interpolation.
 * @author Philippe Meseure
 * @version 1.0
 */
public class GouraudShaders extends LightingShaders
{
    /**
     * Constructor : compile shaders and group them in a GLES program,
     * and set the various GLSL variables
     */
    public GouraudShaders(Context context)
    {
        super(context);
    }
    /**
     * Method to create shaders.
     * @return program id created after compiling and linking shader programs
     */
    @Override
    public int createProgram(Context context)
    {
        // First approach : get shader sources as String stored in the object
        //return initializeShaders(VERTSRC,FRAGSRC);

        // Second method : get shader codes from ressource files
        return initializeShadersFromResources(context,"gouraud_vert.glsl","gouraud_frag.glsl");
    }

}

