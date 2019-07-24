#Add busybox into system/bin
PRODUCT_COPY_FILES += vendor/flyzebra/system/bin/busybox:system/bin/busybox
#enable adb connect 
PRODUCT_PROPERTY_OVERRIDES += service.adb.tcp.port=5555