FLYZEBRA_ROOT=vendor/flyzebra

#解压迅为文件
#tar -zxvf $FLYZEBRA_ROOT/itop4412_android5.1.tar.gz

#copy framework
cp -rvf $FLYZEBRA_ROOT/frameworks ./
mv -v ./frameworks/base/Android._ ./frameworks/base/Android.mk
mv -v ./frameworks/base/services/core/jni/Android._ ./frameworks/base/services/core/jni/Android.mk
#copy system
cp -rvf $FLYZEBRA_ROOT/system/core ./system/

#copy hardware include
cp -rvf $FLYZEBRA_ROOT/hardware/libhardware/include ./hardware/libhardware/

#copy selinux
cp -vf $FLYZEBRA_ROOT/external/sepolicy/service_contexts ./external/sepolicy/service_contexts

#copy tinyalsa
cp -vf $FLYZEBRA_ROOT/external/tinyalsa/Android._ ./external/tinyalsa/Android.mk

#copy libnl-header
#cp -rvf $FLYZEBRA_ROOT/external/libnl-headers ./external/
#mv -v ./external/libnl-headers/Android._ ./external/libnl-headers/Android.mk

#edit wpa
#mv -v external/wpa_supplicant_8/Android.mk external/wpa_supplicant_8/Android._
#mv -v external/wpa_supplicant_8/wpa_supplicant/Android.mk external/wpa_supplicant_8/wpa_supplicant/Android._
#mv -v external/wpa_supplicant_8/hostapd/Android.mk external/wpa_supplicant_8/hostapd/Android._
#mv -v external/wpa_supplicant_8/hs20/client/Android.mk external/wpa_supplicant_8/hs20/client/Android._
