LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := GaodemapAuto
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := guanwangV4.1.0.20047_VV701IIQWV.apk
LOCAL_MODULE_CLASS := APPS
LOCAL_MODULE_SUFFIX := $(COMMON_ANDROID_PACKAGE_SUFFIX)
LOCAL_CERTIFICATE := PRESIGNED
LOCAL_PREBUILT_JNI_LIBS := \
lib/armeabi-v7a/libaime_auth_gd.so \
lib/armeabi-v7a/libaime_hcr_gd.so \
lib/armeabi-v7a/libaime_key_gd.so \
lib/armeabi-v7a/libalclog.so \
lib/armeabi-v7a/libapssdk.so \
lib/armeabi-v7a/libAssembly.so \
lib/armeabi-v7a/libAutoCrypto.so \
lib/armeabi-v7a/libautonavi.so \
lib/armeabi-v7a/libAutoSSL.so \
lib/armeabi-v7a/libcmb_AGroupPackage.so \
lib/armeabi-v7a/libcmb_Common.so \
lib/armeabi-v7a/libcmb_DriveReportPackage.so \
lib/armeabi-v7a/libcmb_Dupinfo.so \
lib/armeabi-v7a/libcmb_FileMerge.so \
lib/armeabi-v7a/libcmb_font_cn.so \
lib/armeabi-v7a/libcmb_GVersion.so \
lib/armeabi-v7a/libcmb_MainPackage.so \
lib/armeabi-v7a/libcmb_MyTrackPackage.so \
lib/armeabi-v7a/libcmb_PhoneConnPackage.so \
lib/armeabi-v7a/libcmb_SwitchCardPackage.so \
lib/armeabi-v7a/libcmb_WechatBindPackage.so \
lib/armeabi-v7a/libCommon.so \
lib/armeabi-v7a/libContentProvider.so \
lib/armeabi-v7a/libdumpcrash.so \
lib/armeabi-v7a/libEagletEx.so \
lib/armeabi-v7a/libEaglet.so \
lib/armeabi-v7a/libGAdaptorInterface.so \
lib/armeabi-v7a/libGAdaptor.so \
lib/armeabi-v7a/libGbl.so \
lib/armeabi-v7a/libGComm3rd.so \
lib/armeabi-v7a/libGFrame.so \
lib/armeabi-v7a/libGLogSpy.so \
lib/armeabi-v7a/libGNaviDice.so \
lib/armeabi-v7a/libGNet.so \
lib/armeabi-v7a/libGPlatformInterface.so \
lib/armeabi-v7a/libhsl.so \
lib/armeabi-v7a/libifly_aime_gd.so \
lib/armeabi-v7a/libisstts.so \
lib/armeabi-v7a/liblinkProxy-1.0.0.so \
lib/armeabi-v7a/libmqtt_jni.so \
lib/armeabi-v7a/libplug_ins_project.so \
lib/armeabi-v7a/libsync_cxx.so \
lib/armeabi-v7a/libUpdateMng.so \

include $(BUILD_PREBUILT)