#version 100
precision mediump float;

// vertex attributes
attribute vec3 aVertexPosition;
attribute vec3 aVertexNormal;

// Interpolated data
varying vec4 vColor;

void main(void) {
    gl_FragColor = vColor;
}
