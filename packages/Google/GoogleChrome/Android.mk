LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := com.android.chrome
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := com.android.chrome_78.0.3904.108.apk
LOCAL_MODULE_CLASS := APPS
LOCAL_MODULE_SUFFIX := $(COMMON_ANDROID_PACKAGE_SUFFIX)
LOCAL_CERTIFICATE := PRESIGNED
LOCAL_OVERRIDES_PACKAGES := Browser MtkBrowser
#LOCAL_PRIVILEGED_MODULE := false
LOCAL_PREBUILT_JNI_LIBS := \
lib/armeabi-v7a/libchrome.1847.114.so \
lib/armeabi-v7a/libchrome.1916.122.so \
lib/armeabi-v7a/libchrome.1916.138.so \
lib/armeabi-v7a/libchrome.1916.141.so \
lib/armeabi-v7a/libchrome.1985.122.so \
lib/armeabi-v7a/libchrome.1985.128.so \
lib/armeabi-v7a/libchrome.1985.131.so \
lib/armeabi-v7a/libchrome.1985.135.so \
lib/armeabi-v7a/libchrome.2062.117.so \
lib/armeabi-v7a/libchrome.2125.102.so \
lib/armeabi-v7a/libchrome.2125.114.so \
lib/armeabi-v7a/libchrome.2171.37.so \
lib/armeabi-v7a/libchrome.2171.59.so \
lib/armeabi-v7a/libchrome_crashpad_handler.so \
lib/armeabi-v7a/libchrome.so \
lib/armeabi-v7a/libchromeview.so \
lib/armeabi-v7a/libchromium_android_linker.so \

include $(BUILD_PREBUILT)