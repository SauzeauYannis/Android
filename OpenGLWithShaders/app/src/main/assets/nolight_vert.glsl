#version 100
precision mediump float;

// Uniform data (matrices)
uniform mat4 uModelViewMatrix;
uniform mat4 uProjectionMatrix;

// Vertex attributes
attribute vec3 aVertexPosition;

void main()
{
  gl_Position= uProjectionMatrix*uModelViewMatrix*vec4(aVertexPosition, 1.0);
}
