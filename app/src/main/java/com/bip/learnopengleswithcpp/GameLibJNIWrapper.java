package com.bip.learnopengleswithcpp;

public class GameLibJNIWrapper {
    static {
        System.loadLibrary("g3studioopengl");
    }
    public static native String stringFromJNI();

    public static native void on_surface_created();

    public static native void on_surface_changed(int width, int height);

    public static native void on_draw_frame();
}
