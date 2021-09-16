//
// Created by TuanLQ on 9/16/2021.
//

#include "game.h"
#include <android/log.h>
#include "glwrapper.h"
#include <jni.h>

#define  LOG_TAG    "testjni"
#define  ALOG(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)

void on_surface_created() {
    glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
}

void on_surface_changed(jint width, jint height) {
    glViewport(0, 0, width, height);
    glClearColor(0, 0, 0, 1);
}

void on_draw_frame() {
    ALOG("This message comes from C at line %d.", __LINE__);
    glClear(GL_COLOR_BUFFER_BIT);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bip_learnopengleswithcpp_GameLibJNIWrapper_on_1surface_1created(JNIEnv *env,
                                                                         jclass clazz) {
    on_surface_created();
}
extern "C"
JNIEXPORT void JNICALL
Java_com_bip_learnopengleswithcpp_GameLibJNIWrapper_on_1surface_1changed(JNIEnv *env, jclass clazz,
                                                                         jint width, jint height) {
    on_surface_changed(width, height);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_bip_learnopengleswithcpp_GameLibJNIWrapper_on_1draw_1frame(JNIEnv *env, jclass clazz) {
    on_draw_frame();
}