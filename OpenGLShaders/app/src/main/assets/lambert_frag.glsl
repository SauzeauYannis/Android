#version 100
precision mediump float;

uniform bool uLighting;
uniform vec4 uAmbiantLight;
uniform vec3 uLightPos;
uniform vec4 uLightColor;

uniform bool uNormalizing;

uniform mat4 uModelViewMatrix;

varying vec4 vColor;
varying vec3 vVertexNormal;
varying vec3 vVertexPosition;
varying mat3 vNormalMatrix;

void main(void) {
  if (uLighting) {
    vec4 pos=uModelViewMatrix*vec4(vVertexPosition, 1.0);
    vec3 normal = vNormalMatrix * vVertexNormal;
    if (uNormalizing) normal = normalize(normal);
    vec3 lightdir = normalize(uLightPos-pos.xyz);
    float weight = max(dot(normal, lightdir),0.0);
    gl_FragColor = vColor*(uAmbiantLight+weight*uLightColor);
  }
  else gl_FragColor = vColor;
}
