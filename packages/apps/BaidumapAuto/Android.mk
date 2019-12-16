LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := BaidumapAuto
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := BaidumapAuto_3.1.0_guanwang.apk
LOCAL_MODULE_CLASS := APPS
LOCAL_MODULE_SUFFIX := $(COMMON_ANDROID_PACKAGE_SUFFIX)
LOCAL_CERTIFICATE := PRESIGNED
LOCAL_PREBUILT_JNI_LIBS := \
lib/armeabi/libaime.so \
lib/armeabi/libapp_BaiduMapApplib.so \
lib/armeabi/libapp_BaiduMapBaselib.so \
lib/armeabi/libapp_BaiduNaviApplib.so \
lib/armeabi/libapp_BaiduVIlib.so \
lib/armeabi/libaudiomessage-jni.so \
lib/armeabi/libbaidumap_breakpad.so \
lib/armeabi/libBaiduSpeechSDK.so \
lib/armeabi/libbase64encoder_v1_6.so \
lib/armeabi/libbd_etts.so \
lib/armeabi/libbdpush_V2_9.so \
lib/armeabi/libBDSpeechDecoder_V1.so \
lib/armeabi/libbdspilaec.so \
lib/armeabi/libbdSpilWakeup.so \
lib/armeabi/libbdtts.so \
lib/armeabi/libbspatch.so \
lib/armeabi/libcrypto.so \
lib/armeabi/libetts_domain_data_builder.so \
lib/armeabi/libfire.so \
lib/armeabi/libgif.so \
lib/armeabi/libgnustl_shared.so \
lib/armeabi/libindoor.so \
lib/armeabi/liblocSDK7b.so \
lib/armeabi/libmap_0815-20180815145021-rOhQlG.pkg.so \
lib/armeabi/libmpcr.so \
lib/armeabi/libssl.so \
lib/armeabi/libturbonet.so \
lib/armeabi/libvad.asr.so \
lib/armeabi/libvad.wp.so \

include $(BUILD_PREBUILT)