LOCAL_PATH := $(call my-dir)
    
# HAL module implemenation, not prelinked and stored in
# hw/<GPS_HARDWARE_MODULE_ID>.<ro.hardware>.so
include $(CLEAR_VARS)
LOCAL_MODULE_PATH := $(TARGET_OUT_SHARED_LIBRARIES)/hw
LOCAL_CFLAGS += $(common_flags)
LOCAL_LDLIBS += -llog
LOCAL_C_INCLUDES := hardware/libhardware
LOCAL_SHARED_LIBRARIES := liblog libcutils libhardware
LOCAL_SRC_FILES := zebrahw_qemu.c
LOCAL_MODULE := zebrahw.$(TARGET_BOOTLOADER_BOARD_NAME)
LOCAL_MODULE_TAGS := optional
include $(BUILD_SHARED_LIBRARY)