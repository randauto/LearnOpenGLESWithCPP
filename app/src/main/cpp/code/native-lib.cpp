#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_bip_learnopengleswithcpp_GameLibJNIWrapper_stringFromJNI(JNIEnv *env, jclass thiz) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}