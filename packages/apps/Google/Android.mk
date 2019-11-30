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
##############Google Play Service###############
include $(CLEAR_VARS)

# Module name should match apk name to be installed
LOCAL_MODULE := com.google.android.gms
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := com.google.android.gms_19.8.26.apk
LOCAL_MODULE_CLASS := APPS
LOCAL_MODULE_SUFFIX := $(COMMON_ANDROID_PACKAGE_SUFFIX)
LOCAL_CERTIFICATE := PRESIGNED
LOCAL_PRIVILEGED_MODULE := true

include $(BUILD_PREBUILT)
##############Google Play Store###############
include $(CLEAR_VARS)

# Module name should match apk name to be installed
LOCAL_MODULE := com.android.vending
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := com.android.vending_17.6.19.apk
LOCAL_MODULE_CLASS := APPS
LOCAL_MODULE_SUFFIX := $(COMMON_ANDROID_PACKAGE_SUFFIX)
LOCAL_CERTIFICATE := PRESIGNED
LOCAL_PRIVILEGED_MODULE := true

include $(BUILD_PREBUILT)
##############Google Calendar##################
#include $(CLEAR_VARS)
#
## Module name should match apk name to be installed
#LOCAL_MODULE := com.google.android.calendar
#LOCAL_MODULE_TAGS := optional
#LOCAL_SRC_FILES := com.google.android.calendar_6.0.8.apk
#LOCAL_MODULE_CLASS := APPS
#LOCAL_MODULE_SUFFIX := $(COMMON_ANDROID_PACKAGE_SUFFIX)
#LOCAL_CERTIFICATE := PRESIGNED
#LOCAL_PRIVILEGED_MODULE := true
#
#include $(BUILD_PREBUILT)
##############Google Contacts##################
#include $(CLEAR_VARS)
#
## Module name should match apk name to be installed
#LOCAL_MODULE := com.google.android.contacts
#LOCAL_MODULE_TAGS := optional
#LOCAL_SRC_FILES := com.google.android.contacts_3.1.6.apk
#LOCAL_MODULE_CLASS := APPS
#LOCAL_MODULE_SUFFIX := $(COMMON_ANDROID_PACKAGE_SUFFIX)
#LOCAL_CERTIFICATE := PRESIGNED
#LOCAL_PRIVILEGED_MODULE := true
#
#include $(BUILD_PREBUILT)
################################################
##############Google Chrome##################
include $(CLEAR_VARS)

# Module name should match apk name to be installed
LOCAL_MODULE := com.android.chrome
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := com.android.chrome_78.0.3904.108.apk
LOCAL_MODULE_CLASS := APPS
LOCAL_MODULE_SUFFIX := $(COMMON_ANDROID_PACKAGE_SUFFIX)
LOCAL_CERTIFICATE := PRESIGNED
LOCAL_OVERRIDES_PACKAGES := Browser MtkBrowser
LOCAL_PRIVILEGED_MODULE := true

include $(BUILD_PREBUILT)
################################################
###############Google calendar##################
#include $(CLEAR_VARS)
#
## Module name should match apk name to be installed
#LOCAL_MODULE := calendar
#LOCAL_MODULE_TAGS := optional
#LOCAL_SRC_FILES := calendar.apk
#LOCAL_MODULE_CLASS := APPS
#LOCAL_MODULE_SUFFIX := $(COMMON_ANDROID_PACKAGE_SUFFIX)
#LOCAL_CERTIFICATE := PRESIGNED
#LOCAL_PRIVILEGED_MODULE := true
#
#include $(BUILD_PREBUILT)
################################################
##############Google calculator##################
#include $(CLEAR_VARS)
#
## Module name should match apk name to be installed
#LOCAL_MODULE := calculator
#LOCAL_MODULE_TAGS := optional
#LOCAL_SRC_FILES := calculator.apk
#LOCAL_MODULE_CLASS := APPS
#LOCAL_MODULE_SUFFIX := $(COMMON_ANDROID_PACKAGE_SUFFIX)
#LOCAL_CERTIFICATE := PRESIGNED
#LOCAL_PRIVILEGED_MODULE := true
#
#include $(BUILD_PREBUILT)
#################################################
###############Google clock##################
#include $(CLEAR_VARS)
#
## Module name should match apk name to be installed
#LOCAL_MODULE := clock
#LOCAL_MODULE_TAGS := optional
#LOCAL_SRC_FILES := clock.apk
#LOCAL_MODULE_CLASS := APPS
#LOCAL_MODULE_SUFFIX := $(COMMON_ANDROID_PACKAGE_SUFFIX)
#LOCAL_CERTIFICATE := PRESIGNED
#LOCAL_PRIVILEGED_MODULE := true
#
#include $(BUILD_PREBUILT)
#################################################
##############Google clock##################
include $(CLEAR_VARS)

# Module name should match apk name to be installed
LOCAL_MODULE := com.google.android.youtube
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := com.google.android.youtube_14.43.55.apk
LOCAL_MODULE_CLASS := APPS
LOCAL_MODULE_SUFFIX := $(COMMON_ANDROID_PACKAGE_SUFFIX)
LOCAL_CERTIFICATE := PRESIGNED
LOCAL_PRIVILEGED_MODULE := true

include $(BUILD_PREBUILT)
################################################