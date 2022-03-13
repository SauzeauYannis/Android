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
uniform vec4 uMaterialColor;

// Interpolated data
varying vec3 vVertexNormal;
varying vec4 vPos;

void main(void) {
  if (uLighting) {
    vec3 normal = uNormalMatrix * vVertexNormal;
    if (uNormalizing)
      normal = normalize(normal);

    vec3 lightdir = normalize(uLightPos - vPos.xyz);
    float weight = max(dot(normal, lightdir), 0.0);

    gl_FragColor = uMaterialColor * (uAmbiantLight + weight * uLightColor);
  } else {
    gl_FragColor = uMaterialColor;
  }
}
