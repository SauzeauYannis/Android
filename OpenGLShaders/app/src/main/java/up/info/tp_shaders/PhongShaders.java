package up.info.tp_shaders;

import android.content.Context;

public class PhongShaders extends LightingShaders {

    /**
     * Constructor. nothing to do, everything is done in the super class...
     *
     * @param context
     */
    public PhongShaders(Context context) {
        super(context);
    }

    @Override
    public int createProgram(Context context) {
        return initializeShadersFromResources(context,"phong_vert.glsl","phong_frag.glsl");
    }
}
