LOCAL_PATH := $(call my-dir)

##############Google youtube##################
include $(CLEAR_VARS)

# Module name should match apk name to be installed
LOCAL_MODULE := com.google.android.youtube
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := com.google.android.youtube_14.43.55.apk
LOCAL_MODULE_CLASS := APPS
LOCAL_MODULE_SUFFIX := $(COMMON_ANDROID_PACKAGE_SUFFIX)
LOCAL_CERTIFICATE := PRESIGNED
#LOCAL_PRIVILEGED_MODULE := true
LOCAL_PREBUILT_JNI_LIBS := \
lib/armeabi-v7a/libcronet.79.0.3940.0.so \
lib/armeabi-v7a/libc++_shared.so \
lib/armeabi-v7a/libdrishti_jni_native.so \
lib/armeabi-v7a/libfilterframework_jni.so \
lib/armeabi-v7a/libframesequence.so \
lib/armeabi-v7a/libgvr_audio.so \
lib/armeabi-v7a/libgvr.so \
lib/armeabi-v7a/libjsapi.so \
lib/armeabi-v7a/libjscontroller.so \
lib/armeabi-v7a/libjsc.so \
lib/armeabi-v7a/libopusJNI.so \
lib/armeabi-v7a/libopusV2JNI.so \
lib/armeabi-v7a/libunified_template_resolver.so \
lib/armeabi-v7a/libvpx.so \
lib/armeabi-v7a/libvpxV2JNI.so \
lib/armeabi-v7a/libvpxYTJNI.so \
lib/armeabi-v7a/libwebp_android.so \
lib/armeabi-v7a/libyoga.so \

include $(BUILD_PREBUILT)