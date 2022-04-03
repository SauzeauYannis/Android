#version 100
precision mediump float;

// Transformation Matrices
uniform mat3 uNormalMatrix;

// Light source definition
uniform bool uLighting;
uniform vec3 uLightPos;
uniform vec4 uAmbiantLight;
uniform vec4 uLightColor;

// Material definition
uniform bool uNormalizing;
uniform bool uTexturing;
uniform vec4 uMaterialColor;
uniform sampler2D uTextureUnit;

// Interpolated data
varying vec3 vVertexNormal;
varying vec4 vPos;
varying vec2 vTexCoord;

void main(void) {
  vec4 texelColor = uMaterialColor;
  if (uTexturing)
    texelColor *= texture2D(uTextureUnit, vTexCoord);

  if (uLighting) {
    vec3 normal = uNormalMatrix * vVertexNormal;
    if (uNormalizing)
      normal = normalize(normal);

    vec3 lightdir = normalize(uLightPos - vPos.xyz);
    float weight = max(dot(normal, lightdir), 0.0);

    gl_FragColor = texelColor * (uAmbiantLight + weight * uLightColor);
  } else {
    gl_FragColor = texelColor;
  }
}
