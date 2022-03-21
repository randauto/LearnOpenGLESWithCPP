precision mediump float;
varying vec4 v_Color; // varying nghĩa là điểm sẽ có màu khác nhau, tức là màu của 1 điểm sẽ đổi.
//uniform vec4 u_Color; // uniform: nghĩa là các điểm sẽ đồng nhất màu với nhau.

void main() {
    gl_FragColor = v_Color;
}