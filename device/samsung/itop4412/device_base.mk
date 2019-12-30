# Copyright (C) 2010 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.


# This file is the device-specific product definition file for
# itop4412. It lists all the overlays, files, modules and properties
# that are specific to this hardware: i.e. those are device-specific
# drivers, configuration files, settings, etc...

LOCAL_PATH := device/samsung/itop4412

include $(LOCAL_PATH)/BoardConfig.mk

# These is the hardware-specific overlay, which points to the location
# of hardware-specific resource overrides, typically the frameworks and
# application settings that are stored in resourced.
DEVICE_PACKAGE_OVERLAYS := \
	$(LOCAL_PATH)/overlay

# Init files
PRODUCT_COPY_FILES += \
	$(LOCAL_PATH)/conf/init.rc:root/init.rc \
	$(LOCAL_PATH)/conf/init.environ.rc:root/init.environ.rc \
	$(LOCAL_PATH)/conf/init.trace.rc:root/init.trace.rc \
	$(LOCAL_PATH)/conf/init.zygote32.rc:root/init.zygote32.rc \
	$(LOCAL_PATH)/conf/init.itop4412.rc:root/init.itop4412.rc \
	$(LOCAL_PATH)/conf/init.itop4412.usb.rc:root/init.itop4412.usb.rc \
	$(LOCAL_PATH)/conf/ueventd.itop4412.rc:root/ueventd.itop4412.rc \
	$(LOCAL_PATH)/conf/fstab.itop4412:root/fstab.itop4412

# Prebuilt kl and kcm keymaps
PRODUCT_COPY_FILES += \
	$(LOCAL_PATH)/samsung-keypad.kl:system/usr/keylayout/samsung-keypad.kl \
	$(LOCAL_PATH)/itop4412-key.kl:system/usr/keylayout/itop4412-key.kl

# Input device calibration files
PRODUCT_COPY_FILES += \
	$(LOCAL_PATH)/ft5x0x_ts.idc:system/usr/idc/ft5x0x_ts.idc \
	$(LOCAL_PATH)/it7260_ts.idc:system/usr/idc/it7260_ts.idc \
	$(LOCAL_PATH)/fa_ts_input.idc:system/usr/idc/fa_ts_input.idc

# Remote control
PRODUCT_COPY_FILES += \
	$(LOCAL_PATH)/ir-keytable:system/bin/ir-keytable \
	$(LOCAL_PATH)/nanopc_rc01.km:system/etc/nanopc_rc01.km

# OpenMAX IL configuration files
PRODUCT_COPY_FILES += \
	$(LOCAL_PATH)/media_codecs.xml:system/etc/media_codecs.xml \
	$(LOCAL_PATH)/media_profiles.xml:system/etc/media_profiles.xml

# Generated kcm keymaps
PRODUCT_PACKAGES += \
	samsung-keypad.kcm

# Filesystem management tools
PRODUCT_PACKAGES += \
	make_ext4fs \
	setup_fs

# Connectivity
PRODUCT_PACKAGES := \
	libwpa_client \
	hostapd \
	dhcpcd.conf \
	wpa_supplicant \
	wpa_supplicant.conf

# Audio
PRODUCT_PACKAGES += \
	audio_policy.itop4412 \
	audio.primary.itop4412 \
	audio.a2dp.default \
	audio.usb.default \
    audio.r_submix.default \
	libaudioutils \
	audio.usb.default \
    audio.r_submix.default \

# AudioPolicyManager will read this file.
PRODUCT_COPY_FILES += \
	$(LOCAL_PATH)/audio_policy.conf:/system/etc/audio_policy.conf

# ULP Audio
ifeq ($(BOARD_USE_ULP_AUDIO),true)
PRODUCT_PACKAGES += \
	libaudiohw \
	MusicULP \
	libsa_jni
endif

# ALP Audio
ifeq ($(BOARD_USE_ALP_AUDIO),true)
#PRODUCT_PACKAGES += \
	libOMX.SEC.MP3.Decoder
endif

# Camera
PRODUCT_PACKAGES += \
	camera.itop4412

# SEC_Camera
ifeq ($(USE_SEC_CAMERA),true)
PRODUCT_PACKAGES += \
	SEC_Camera
endif

# Libs
PRODUCT_PACKAGES += \
	libstagefrighthw \
	com.android.future.usb.accessory
#	libcamera

ifeq ($(BOARD_USES_PWMLIGHTS),true)
PRODUCT_PACKAGES += \
	lights.itop4412
endif

# Video Editor
PRODUCT_PACKAGES += \
	VideoEditorGoogle

# Widevine DRM
PRODUCT_PACKAGES += com.google.widevine.software.drm.xml \
	com.google.widevine.software.drm \
	WidevineSamplePlayer \
	test-libwvm \
	test-wvdrmplugin \
	test-wvplayer_L1 \
	libdrmwvmplugin \
	libwvm \
	libWVStreamControlAPI_L1 \
	libwvdrm_L1

# OpenMAX IL modules
PRODUCT_PACKAGES += \
	libSEC_OMX_Core \
	libSEC_OMX_Resourcemanager \
	libOMX.SEC.AVC.Decoder \
	libOMX.SEC.M4V.Decoder \
	libOMX.SEC.M4V.Encoder \
	libOMX.SEC.AVC.Encoder

# hwconvertor modules
PRODUCT_PACKAGES += \
	libhwconverter

# MFC firmware
PRODUCT_COPY_FILES += \
	device/samsung/exynos4/firmware/mfc_fw.bin:system/vendor/firmware/mfc_fw.bin

# FIMC-IS firmware
PRODUCT_COPY_FILES += \
	device/samsung/exynos4/firmware/fimc_is_fw.bin:system/vendor/firmware/fimc_is_fw.bin \
	device/samsung/exynos4/firmware/setfile.bin:system/vendor/firmware/setfile.bin

# These are the hardware-specific features
PRODUCT_COPY_FILES += \
	frameworks/native/data/etc/tablet_core_hardware.xml:system/etc/permissions/tablet_core_hardware.xml \
	frameworks/native/data/etc/android.hardware.touchscreen.multitouch.jazzhand.xml:system/etc/permissions/android.hardware.touchscreen.multitouch.jazzhand.xml \
	frameworks/native/data/etc/android.hardware.wifi.xml:system/etc/permissions/android.hardware.wifi.xml \
	frameworks/av/media/libstagefright/data/media_codecs_google_audio.xml:system/etc/media_codecs_google_audio.xml \
	frameworks/av/media/libstagefright/data/media_codecs_google_telephony.xml:system/etc/media_codecs_google_telephony.xml \
	frameworks/av/media/libstagefright/data/media_codecs_google_video.xml:system/etc/media_codecs_google_video.xml \
	frameworks/native/data/etc/android.hardware.camera.flash-autofocus.xml:system/etc/permissions/android.hardware.camera.flash-autofocus.xml \
	frameworks/native/data/etc/android.hardware.camera.front.xml:system/etc/permissions/android.hardware.camera.front.xml \
	frameworks/native/data/etc/android.software.sip.voip.xml:system/etc/permissions/android.software.sip.voip.xml \
	frameworks/native/data/etc/android.hardware.usb.accessory.xml:system/etc/permissions/android.hardware.usb.accessory.xml \
	frameworks/native/data/etc/android.hardware.usb.host.xml:system/etc/permissions/android.hardware.usb.host.xml \
	frameworks/native/data/etc/android.hardware.location.gps.xml:system/etc/permissions/android.hardware.location.gps.xml \
	frameworks/native/data/etc/android.hardware.sensor.accelerometer.xml:system/etc/permissions/android.hardware.sensor.accelerometer.xml \
	frameworks/native/data/etc/android.hardware.sensor.barometer.xml:system/etc/permissions/android.hardware.sensor.barometer.xml \
	frameworks/native/data/etc/android.hardware.sensor.light.xml:system/etc/permissions/android.hardware.sensor.light.xml \
	frameworks/native/data/etc/android.hardware.audio.low_latency.xml:system/etc/permissions/android.hardware.audio.low_latency.xml \
	frameworks/native/data/etc/android.hardware.telephony.cdma.xml:system/etc/permissions/android.hardware.telephony.cdma.xml \
	frameworks/native/data/etc/android.hardware.telephony.gsm.xml:system/etc/permissions/android.hardware.telephony.gsm.xml \
	frameworks/native/data/etc/android.hardware.ethernet.xml:system/etc/permissions/android.hardware.ethernet.xml

# Bluetooth
PRODUCT_COPY_FILES += \
	frameworks/native/data/etc/android.hardware.bluetooth.xml:system/etc/permissions/android.hardware.bluetooth.xml \
	frameworks/native/data/etc/android.hardware.bluetooth_le.xml:system/etc/permissions/android.hardware.bluetooth_le.xml

# Need AppWidget permission to prevent from Launcher's crash.
PRODUCT_COPY_FILES += \
	frameworks/native/data/etc/android.software.app_widgets.xml:system/etc/permissions/android.software.app_widgets.xml

# 3G
PRODUCT_COPY_FILES += $(LOCAL_PATH)/spn-conf.xml:system/etc/spn-conf.xml

# The OpenGL ES API level that is natively supported by this device.
# This is a 16.16 fixed point number
PRODUCT_PROPERTY_OVERRIDES := \
	ro.opengles.version=131072

PRODUCT_PROPERTY_OVERRIDES += \
	hwui.render_dirty_regions=false

PRODUCT_PROPERTY_OVERRIDES += \
	wifi.interface=wlan0 \
	wifi.supplicant_scan_interval=15

# Set default USB interface
PRODUCT_DEFAULT_PROPERTY_OVERRIDES += \
	persist.sys.usb.config=mtp
#or persist.sys.usb.config=mass_storage

# Widevine DRM
PRODUCT_PROPERTY_OVERRIDES += \
	drm.service.enabled=true

# setup dalvik vm configs.
$(call inherit-product, frameworks/native/build/tablet-7in-hdpi-1024-dalvik-heap.mk)

# we have enough storage space to hold precise GC data
PRODUCT_TAGS += dalvik.gc.type-precise

# Screen size is "normal", density is "hdpi"
PRODUCT_AAPT_CONFIG := normal large xlarge hdpi xhdpi
PRODUCT_AAPT_PREF_CONFIG := hdpi

PRODUCT_CHARACTERISTICS := tablet,nosdcard

