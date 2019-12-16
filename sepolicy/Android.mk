BOARD_SEPOLICY_DIRS := \
	vendor/flyzebra/sepolicy

BOARD_SEPOLICY_UNION := \
	vendor/flyzebra/sepolicy/file_contexts \
	vendor/flyzebra/sepolicy/genfs_contexts \
	vendor/flyzebra/sepolicy/property_contexts \
	vendor/flyzebra/sepolicy/property.te \
	vendor/flyzebra/sepolicy/adbd.te \
	vendor/flyzebra/sepolicy/app.te \
	vendor/flyzebra/sepolicy/device.te \
	vendor/flyzebra/sepolicy/domain.te \
	vendor/flyzebra/sepolicy/file.te \
	vendor/flyzebra/sepolicy/mediaserver.te \
	vendor/flyzebra/sepolicy/surfaceflinger.te \
	vendor/flyzebra/sepolicy/system_server.te \
	vendor/flyzebra/sepolicy/init.te \
	vendor/flyzebra/sepolicy/kernel.te \
	vendor/flyzebra/sepolicy/shell.te \
	vendor/flyzebra/sepolicy/servicemanager.te \
	vendor/flyzebra/sepolicy/netd.te \
	vendor/flyzebra/sepolicy/healthd.te \
	vendor/flyzebra/sepolicy/zygote.te \
	vendor/flyzebra/sepolicy/installd.te \
	vendor/flyzebra/sepolicy/sdcardd.te \
	vendor/flyzebra/sepolicy/debuggerd.te \
	vendor/flyzebra/sepolicy/unlabeled.te \
	vendor/flyzebra/sepolicy/bootanim.te \
	vendor/flyzebra/sepolicy/adjlowmem.te \
	vendor/flyzebra/sepolicy/service_contexts \
	vendor/flyzebra/sepolicy/service.te