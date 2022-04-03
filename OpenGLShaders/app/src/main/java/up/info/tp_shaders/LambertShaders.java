package up.info.tp_shaders;

import android.content.Context;

/**
 * The type Lambert shaders.
 */
public class LambertShaders extends LightingShaders {

    /**
     * Constructor. nothing to do, everything is done in the super class...
     *
     * @param context the context
     */
    public LambertShaders(Context context) {
        super(context);
    }

    @Override
    public int createProgram(Context context) {
        return initializeShadersFromResources(context, "lambert_vert.glsl", "lambert_frag.glsl");
    }
}
