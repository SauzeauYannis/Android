#version 100
precision mediump float;

// Transformation Matrices
uniform mat4 uModelViewMatrix;
uniform mat4 uProjectionMatrix;
uniform mat3 uNormalMatrix;

// Light source definition
uniform vec4 uAmbiantLight;
uniform bool uLighting;
uniform vec3 uLightPos;
uniform vec4 uLightColor;

// Material definition
uniform bool uNormalizing;
uniform bool uTexturing;
uniform vec4 uMaterialColor;
uniform sampler2D uTextureUnit;

// Vertex attributes
attribute vec3 aVertexPosition;
attribute vec3 aVertexNormal;
attribute vec2 aTexCoord;

// Interpolated data
varying vec4 vColor;

void main(void) {
    vec4 texelColor = uMaterialColor;
    if (uTexturing)
        texelColor *= texture2D(uTextureUnit, aTexCoord);

    vec4 pos = uModelViewMatrix * vec4(aVertexPosition, 1.0);
    if (uLighting) {
        vec3 normal = uNormalMatrix * aVertexNormal;
        if (uNormalizing)
            normal = normalize(normal);

        vec3 lightdir = normalize(uLightPos - pos.xyz);
        float weight = max(dot(normal, lightdir), 0.0);

        vColor = texelColor * (uAmbiantLight + weight * uLightColor);
    } else {
        vColor = texelColor;
    }
    gl_Position = uProjectionMatrix * pos;
}
