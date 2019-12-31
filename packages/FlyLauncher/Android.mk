LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

# Module name should match apk name to be installed
LOCAL_MODULE := FlyLauncher
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := Launcher3-release.apk
LOCAL_MODULE_CLASS := APPS
LOCAL_MODULE_SUFFIX := $(COMMON_ANDROID_PACKAGE_SUFFIX)
LOCAL_CERTIFICATE := platform
LOCAL_OVERRIDES_PACKAGES := Home Launcher Launcher2
LOCAL_PRIVILEGED_MODULE := true

include $(BUILD_PREBUILT)