LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

# Module name should match apk name to be installed
LOCAL_MODULE := com.google.android.gms
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := com.google.android.gms_19.6.29_020300-278422107-19629006_minAPI21_armeabi-v7a_nodpi.apk
LOCAL_MODULE_CLASS := APPS
LOCAL_MODULE_SUFFIX := $(COMMON_ANDROID_PACKAGE_SUFFIX)
LOCAL_CERTIFICATE := PRESIGNED
LOCAL_PRIVILEGED_MODULE := true
LOCAL_PREBUILT_JNI_LIBS := \
lib/armeabi-v7a/libAppDataSearch.so \
lib/armeabi-v7a/libconscrypt_gmscore_jni.so \
lib/armeabi-v7a/libgcastv2_base.so \
lib/armeabi-v7a/libgcastv2_support.so \
lib/armeabi-v7a/libgmscore.so \
lib/armeabi-v7a/libgoogle-ocrclient-v3.so \
lib/armeabi-v7a/libinertial-anchor-jni.so \
lib/armeabi-v7a/libjgcastservice.so \
lib/armeabi-v7a/libjingle_peerconnection_so.so \
lib/armeabi-v7a/libleveldbjni.so \
lib/armeabi-v7a/libsslwrapper_jni.so \
lib/armeabi-v7a/libvcdiffjni.so \
lib/armeabi-v7a/libwearable-selector.so \
lib/armeabi-v7a/libWhisper.so \
	
include $(BUILD_PREBUILT)