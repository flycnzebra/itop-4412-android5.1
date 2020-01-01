FLYZEBRA_ROOT=vendor/flyzebra

#xunwei
#tar -zxvf $FLYZEBRA_ROOT/itop4412_android5.1.tar.gz

#init.rc
cp -rvf $FLYZEBRA_ROOT/device ./

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