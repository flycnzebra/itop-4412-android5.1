
# Copyright 2007-2011 The Android Open Source Project

LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE_TAGS := optional
LOCAL_STATIC_JAVA_LIBRARIES :=  android-support-v4
LOCAL_STATIC_JAVA_LIBRARIES +=android-support-v7-appcompat
LOCAL_STATIC_JAVA_LIBRARIES +=gson-2.8.5

LOCAL_RESOURCE_DIR = \
        $(LOCAL_PATH)/res \
        frameworks/support/v7/appcompat/res

LOCAL_AAPT_FLAGS := \
        --auto-add-overlay \
        --extra-packages android.support.v7.appcompat \

LOCAL_SRC_FILES := $(call all-java-files-under, src) \
                   $(call all-Iaidl-files-under, src) \

#LOCAL_MANIFEST_FILE := AndroidManifest.xml

LOCAL_OVERRIDES_PACKAGES := mpApp
LOCAL_PACKAGE_NAME := NewMpApp
LOCAL_CERTIFICATE := platform
#LOCAL_PRIVILEGED_MODULE := false
#LOCAL_PROGUARD_FLAG_FILES := proguard.flags
LOCAL_PROGUARD_ENABLED := disabled
LOCAL_DEX_PREOPT := false
include $(BUILD_PACKAGE)

include $(CLEAR_VARS)
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES :=gson-2.8.5:libs/gson-2.8.5.jar \

include $(BUILD_MULTI_PREBUILT)
include $(call all-makefiles-under, $(LOCAL_PATH))
