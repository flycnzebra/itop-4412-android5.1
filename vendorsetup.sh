FLYZEBRA_ROOT=vendor/flyzebra

#init device.mk
cp -rvf $FLYZEBRA_ROOT/device/samsung/itop4412 ./device/samsung/

#copy framework
cp -rvf $FLYZEBRA_ROOT/frameworks ./

#copy system
cp -rvf $FLYZEBRA_ROOT/system/core ./system/