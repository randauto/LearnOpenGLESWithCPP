precision highp float;
uniform sampler2D Texture;
varying vec2 TextureCoordsVarying;

//RGB的变换因子，即权重值
const highp vec3 W = vec3(0.2125, 0.7154, 0.0721);

void main() {
    //获取对应纹理坐标系下颜色值
    vec4 mask = texture2D(Texture, TextureCoordsVarying);

    //将颜色mask与变换因子相乘得到灰度值
    float luminance = dot(mask.rgb, W);

    //将灰度值转换为(luminance,luminance,luminance,mask.a)并填充到像素中
    gl_FragColor = vec4(vec3(luminance), 1.0);
}