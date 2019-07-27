#define LOG_TAG "ZebraServiceJNI"

#include "jni.h"
#include "JNIHelp.h"
#include "android_runtime/AndroidRuntime.h"

#include <utils/misc.h>
#include <utils/Log.h>
#include <hardware/hardware.h>
#include <hardware/zebrahw.h>

#include <stdio.h>

namespace android
{

zebrahw_device_t* zebrahw_dev;

static jint init_native(JNIEnv *env, jobject /* clazz */)
{
    int err;
    hw_module_t* module;
    zebrahw_device_t* dev = NULL;

    //ALOGI("init_native()"); 

    err = hw_get_module(ZEBRAHW_HARDWARE_MODULE_ID, (hw_module_t const**)&module);
    if (err == 0) {
        if (module->methods->open(module, "", ((hw_device_t**) &dev)) != 0) {
            ALOGE("Can't open zebra module!!!");
            return 0;
        }
    } else {
        ALOGE("Can't get zebra module!!!");
        return 0;
    }

    return (jint)dev;
}

static void finalize_native(JNIEnv *env, jobject /* clazz */, int ptr)
{
    zebrahw_device_t* dev = (zebrahw_device_t*)ptr;

    //ALOGI("finalize_native()");

    if (dev == NULL) {
        return;
    }

    dev->close();

    free(dev);
}

static int read_native(JNIEnv *env, jobject /* clazz */, int ptr, jbyteArray buffer)
{
    zebrahw_device_t* dev = (zebrahw_device_t*)ptr;
    jbyte* real_byte_array;
    int length;

    //ALOGI("read_native()");

    real_byte_array = env->GetByteArrayElements(buffer, NULL);

    if (dev == NULL) {
        return 0;
    }

    length = dev->read((char*) real_byte_array, env->GetArrayLength(buffer));

    ALOGI("read data from hal: %s", (char *)real_byte_array);

    env->ReleaseByteArrayElements(buffer, real_byte_array, 0);

    return length;
}

static int write_native(JNIEnv *env, jobject /* clazz */, int ptr, jbyteArray buffer)
{
    zebrahw_device_t* dev = (zebrahw_device_t*)ptr;
    jbyte* real_byte_array;
    int length;

    //ALOGI("write_native()");

    real_byte_array = env->GetByteArrayElements(buffer, NULL);

    if (dev == NULL) {
        return 0;
    }

    length = dev->write((char*) real_byte_array, env->GetArrayLength(buffer));

    ALOGI("write data to hal: %s", (char *)real_byte_array);

    env->ReleaseByteArrayElements(buffer, real_byte_array, 0);

    return length;
}


static int test_native(JNIEnv *env, jobject /* clazz */, int ptr, int value)
{
    zebrahw_device_t* dev = (zebrahw_device_t*)ptr;

    if (dev == NULL) {
        return 0;
    }

    ALOGI("test_native()");

    return dev->test(value);
}

static JNINativeMethod method_table[] = {
    { "init_native", "()I", (void*)init_native },
    { "finalize_native", "(I)V", (void*)finalize_native },
    { "read_native", "(I[B)I", (void*)read_native },
    { "write_native", "(I[B)I", (void*)write_native },
    { "test_native", "(II)I", (void*)test_native}
};

int register_android_server_zebra_ZebraService(JNIEnv *env)
{
    return jniRegisterNativeMethods(env, "com/android/server/zebra/ZebraService",
            method_table, NELEM(method_table));

}
}