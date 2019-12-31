LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

# Module name should match apk name to be installed
LOCAL_MODULE := com.android.vending
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := com.android.vending_17.6.19.apk
LOCAL_MODULE_CLASS := APPS
LOCAL_MODULE_SUFFIX := $(COMMON_ANDROID_PACKAGE_SUFFIX)
LOCAL_CERTIFICATE := PRESIGNED
#LOCAL_PRIVILEGED_MODULE := true
LOCAL_PREBUILT_JNI_LIBS := \
lib/armeabi-v7a/libbrotli.so \
lib/armeabi-v7a/libconscrypt_jni.so \
lib/armeabi-v7a/libcronet.80.0.3955.6.so \
lib/armeabi-v7a/libgame_sdk_device_info_jni.so \
lib/armeabi-v7a/libtensorflowlite_jni.so \

include $(BUILD_PREBUILT)