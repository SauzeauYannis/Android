#version 100
precision mediump float;

// Transformation Matrices
uniform mat4 uModelViewMatrix;
uniform mat4 uProjectionMatrix;

// Vertex attributes
attribute vec3 aVertexPosition;
attribute vec3 aVertexNormal;

// Interpolated data
varying vec3 vVertexNormal;
varying vec4 pos;

void main(void) {
  vVertexNormal = aVertexNormal;

  pos = uModelViewMatrix * vec4(aVertexPosition, 1.0);

  gl_Position = uProjectionMatrix * pos;
}
