#MediaTek mt66xx
$(call inherit-product, hardware/mediatek/config/$(strip $(BOARD_CONNECTIVITY_MODULE))/product_package.mk)
# MFC firmware
PRODUCT_COPY_FILES += \
	    device/samsung/common/libwireless/mtk_hif_sdio.ko:system/lib/modules/mtk_hif_sdio.ko \
        device/samsung/common/libwireless/mtk_stp_uart.ko:system/lib/modules/mtk_stp_uart.ko \
        device/samsung/common/libwireless/mtk_stp_wmt.ko:system/lib/modules/mtk_stp_wmt.ko \
        device/samsung/common/libwireless/mtk_wmt_wifi.ko:system/lib/modules/mtk_wmt_wifi.ko \
        device/samsung/common/libwireless/wlan_mt6620.ko:system/lib/modules/wlan_mt6620.ko \
        device/samsung/common/libwireless/mtk_stp_gps.ko:system/lib/modules/mtk_stp_gps.ko \
        device/samsung/common/libwireless/mtk_stp_bt.ko:system/lib/modules/mtk_stp_bt.ko \
        device/samsung/common/libwireless/bt_uhid.ko:system/lib/modules/bt_uhid.ko \
        hardware/mediatek/config/combo_mt66xx/init.combo_mt66xx.rc:root/init.connectivity.rc \

#Add busybox into system/bin
PRODUCT_COPY_FILES += vendor/flyzebra/system/bin/busybox:system/bin/busybox
#ratd so
PRODUCT_COPY_FILES += vendor/flyzebra/system/lib/lib-mpd.so:system/lib/lib-mpd.so
#enable adb connect 
PRODUCT_PROPERTY_OVERRIDES += service.adb.tcp.port=5555

# include product packages
#ZebraService
PRODUCT_PACKAGES += zebrahw.itop4412

PRODUCT_PACKAGES += FileManager
PRODUCT_PACKAGES += FlyLauncher
PRODUCT_PACKAGES += FlySystemUI
#PRODUCT_PACKAGES += com.google.android.gsf
#PRODUCT_PACKAGES += com.google.android.gms
#PRODUCT_PACKAGES += com.android.vending
PRODUCT_PACKAGES += com.android.chrome
PRODUCT_PACKAGES += NewMpApp
PRODUCT_PACKAGES += ratd 
PRODUCT_PACKAGES += GaodemapAuto 
PRODUCT_PACKAGES += BaidumapAuto 
