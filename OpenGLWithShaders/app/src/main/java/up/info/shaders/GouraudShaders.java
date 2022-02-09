package up.info.shaders;

import android.content.Context;

/**
 * Implementation class of shaders to compute diffuse lighting using Gouraud interpolation.
 * @author Philippe Meseure
 * @version 1.0
 */
public class GouraudShaders extends LightingShaders
{

    private int uMaterialColor;

    /**
     * Fragment shader
     */
    private static final String FRAGSRC=
      "precision mediump float;\n"
        +"varying vec4 vColor;\n"
        +"void main(void) {\n"
        +"  gl_FragColor = vColor;\n"
        +"}\n";

    /**
     * Vertex shader
     */
    private static final String VERTSRC=
      // Matrices
      "uniform mat4 uModelViewMatrix;\n"
        +"uniform mat4 uProjectionMatrix;\n"
        +"uniform mat3 uNormalMatrix;\n"
        // Light source definition
        +"uniform vec4 uAmbiantLight;\n"
        +"uniform bool uLighting;\n"
        +"uniform vec3 uLightPos;\n"
        +"uniform vec4 uLightColor;\n"
        // Material definition
        +"uniform bool uNormalizing;\n"
        +"uniform vec4 uMaterialColor;\n"
        // vertex attributes
        +"attribute vec3 aVertexPosition;\n"
        +"attribute vec3 aVertexNormal;\n"
        // Interpolated data
        +"varying vec4 vColor;\n"

        +"void main(void) {\n"
        +"  vec4 pos=uModelViewMatrix*vec4(aVertexPosition, 1.0);\n"
        +"  if (uLighting)\n"
        +"  {\n"
        +"    vec3 normal = uNormalMatrix * aVertexNormal;\n"
        +"    if (uNormalizing) normal=normalize(normal);\n"
        +"    vec3 lightdir=normalize(uLightPos-pos.xyz);\n"
        +"    float weight = max(dot(normal, lightdir),0.0);\n"
        +"    vColor = uMaterialColor*(uAmbiantLight+weight*uLightColor);\n"
        +"  }\n"
        +"  else vColor = uMaterialColor;\n"
        +"  gl_Position= uProjectionMatrix*pos;\n"
        +"}\n";

    /**
     * Constructor : compile shaders and group them in a GLES program,
     * and set the various GLSL variables
     */
    public GouraudShaders(Context context)
    {
        super(context);
    }

    @Override
    public int createProgram(Context context) {
        // First approach : get shader sources as String stored in the object
        //return initializeShaders(VERTSRC, FRAGSRC);

        // Second method : get shader codes from ressource files
        return initializeShadersFromResources(context,"gouraud_vert.glsl","gouraud_frag.glsl");
    }

}
