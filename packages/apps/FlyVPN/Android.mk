LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

# Module name should match apk name to be installed
LOCAL_MODULE := NewMpApp
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := xinwei.com.mpapp_release_v1.0.d77bc7b.201912291122_120.apk
LOCAL_MODULE_CLASS := APPS
LOCAL_MODULE_SUFFIX := $(COMMON_ANDROID_PACKAGE_SUFFIX)
LOCAL_CERTIFICATE := platform
LOCAL_PRIVILEGED_MODULE := false

include $(BUILD_PREBUILT)