#version 100
precision mediump float;

// Interpolated data
varying vec4 vColor;

void main(void) {
    gl_FragColor = vColor;
}

