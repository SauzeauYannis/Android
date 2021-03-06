package up.info.tp_shaders;

import android.content.Context;
import android.opengl.GLES20;

/**
 * Implementation class to manipulate shaders for non-lighted objects (uniform color).
 *
 * @author Philippe Meseure
 * @version 1.0
 */
public class NoLightShaders extends BasicShaders {
    /**
     * Color of the object (GLSL uniform variable)
     */
    private int uColor;

    /**
     * Constructor of the complete rendering Shader programs
     *
     * @param context the context
     */
    public NoLightShaders(Context context) {
        super(context);
    }

    @Override
    public int createProgram(Context context) {
        // Second method : get shader codes from ressource files
        return initializeShadersFromResources(context, "nolight_vert.glsl", "nolight_frag.glsl");
    }

    /**
     * Get Shaders variables (uniform, attributes, etc.)
     */
    @Override
    public void findVariables() {
        super.findVariables();

        // Variables for material
        this.uColor = GLES20.glGetUniformLocation(this.shaderprogram, "uColor");
        if (this.uColor == -1) throw new RuntimeException("uColor not found in shaders");
    }


    /* ======================
       = Material functions =
       ====================== */

    /**
     * Set the color of the object (uniform variable)
     *
     * @param color Color of the object
     */
    public void setColor(final float[] color) {
        GLES20.glUniform4fv(this.uColor, 1, color, 0);
    }
}

