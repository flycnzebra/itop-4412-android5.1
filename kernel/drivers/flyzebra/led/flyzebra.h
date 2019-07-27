#ifndef _FLYZEBRA_H_
#define _FLYZEBRA_H_

#ifndef DEVICE_NAME
#define DEVICE_NAME "flyzebra"
#endif

#ifndef DEVICE_MINOR_NUM
#define DEVICE_MINOR_NUM 1
#endif

#ifndef DEV_MAJOR
#define DEV_MAJOR 0
#endif

#ifndef DEV_MINOR
#define DEV_MINOR 0
#endif

#ifndef REGDEV_SIZE
#define REGDEV_SIZE 3000
#endif

struct reg_dev
{
	char *data;
	unsigned long size;
	
	struct cdev cdev;
};
#endif
