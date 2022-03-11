#version 100
precision mediump float;

uniform mat4 uModelViewMatrix;
uniform mat4 uProjectionMatrix;
uniform mat3 uNormalMatrix;

uniform bool uNormalizing;
uniform vec4 uMaterialColor;

attribute vec3 aVertexPosition;
attribute vec3 aVertexNormal;

varying vec4 vColor;
varying vec3 vVertexNormal;
varying vec3 vVertexPosition;
varying mat3 vNormalMatrix;

void main(void) {
  vec4 pos = uModelViewMatrix * vec4(aVertexPosition, 1.0);
  vVertexPosition = aVertexPosition;
  vVertexNormal = aVertexNormal;
  vNormalMatrix = uNormalMatrix;
  vColor = uMaterialColor;
  gl_Position = uProjectionMatrix*pos;
}
