#version 100
precision mediump float;

// Transformation Matrices
uniform mat3 uNormalMatrix;

// Light source definition
uniform bool uLighting;
uniform vec3 uLightPos;
uniform vec4 uAmbiantLight;
uniform vec4 uLightColor;
uniform vec4 uLightSpecular;
uniform float uConstantAttenuation;
uniform float uLinearAttenuation;
uniform float uQuadraticAttenuation;

// Material definition
uniform bool uNormalizing;
uniform vec4 uMaterialColor;
uniform vec4 uMaterialSpecular;
uniform float uMaterialShininess;

// Interpolated data
varying vec3 vVertexNormal;
varying vec4 pos;

void main(void) {
  if (uLighting) {
    vec3 normal = uNormalMatrix * vVertexNormal;
    if (uNormalizing)
      normal = normalize(normal);

    vec3 lightdir = normalize(uLightPos - pos.xyz);
    float weight = max(dot(normal, lightdir), 0.0);
    float shininess = max(dot(normal, vec3(normalize(-pos))), 0.0);

    float d = distance(uLightPos, pos.xyz);
    float attenuation = 1.0 / (uConstantAttenuation + uLinearAttenuation * d + uQuadraticAttenuation * d * d);

    gl_FragColor = attenuation * (uMaterialColor * (uAmbiantLight + weight * uLightColor) + uMaterialSpecular * (pow(shininess, uMaterialShininess) * uLightSpecular));
  } else {
    gl_FragColor = uMaterialColor;
  }
}
