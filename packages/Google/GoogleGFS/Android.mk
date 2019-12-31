LOCAL_PATH := $(call my-dir)

#############Google Service Fragment################
include $(CLEAR_VARS)

# Module name should match apk name to be installed
LOCAL_MODULE := com.google.android.gsf
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := com.google.android.gsf_5.1.apk
LOCAL_MODULE_CLASS := APPS
LOCAL_MODULE_SUFFIX := $(COMMON_ANDROID_PACKAGE_SUFFIX)
LOCAL_CERTIFICATE := PRESIGNED
LOCAL_PRIVILEGED_MODULE := true

include $(BUILD_PREBUILT)
