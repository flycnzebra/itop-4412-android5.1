FLYZEBRA_ROOT=vendor/flyzebra

#init device.mk
cp -rvf $FLYZEBRA_ROOT/device/samsung/itop4412 ./device/samsung/

#copy framework
cp -rvf $FLYZEBRA_ROOT/frameworks ./
cp -vf ./frameworks/base/Android._ ./frameworks/base/Android.mk
rm -vf ./frameworks/base/Android._
cp -vf ./frameworks/base/services/core/jni/Android._ ./frameworks/base/services/core/jni/Android.mk
rm -vf ./frameworks/base/services/core/jni/Android._
#copy system
cp -rvf $FLYZEBRA_ROOT/system/core ./system/

#copy selinux
cp -vf $FLYZEBRA_ROOT/external/sepolicy/service_contexts ./external/sepolicy/service_contexts

#copy hardware include
cp -rvf $FLYZEBRA_ROOT/hardware/libhardware/include ./hardware/libhardware/