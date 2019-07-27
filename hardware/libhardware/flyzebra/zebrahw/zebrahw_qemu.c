#define  LOG_TAG  "zebrahw_qemu"                                                                                 
#include <cutils/log.h>
#include <cutils/sockets.h>                                                                                        
#include <sys/types.h>                                                                                             
#include <sys/stat.h>                                                                                              
#include <fcntl.h>
#include <malloc.h>
#include <hardware/zebrahw.h>                                                                                                

#define   ZEBRAHW_DEBUG   1                                                                                      

#if ZEBRAHW_DEBUG
#  define D(...)   ALOGD(__VA_ARGS__)                                                                              
#else
#  define D(...)   ((void)0)                                                                                       
#endif                                                                                                             

static int fd = 0;                                                                                                 

static int zebrahw__read(char* buffer, int length)                                                               
{   
    int retval;                                                                                                    
    
    D("ZEBRA HW - read()for %d bytes called", length);                                                           
    
    retval = read(fd, buffer, length);    
    D("read data from driver: %s", buffer);

    return retval;
}
   
static int zebrahw__write(char* buffer, int length)
{
    int retval;

    D("ZEBRA HW - write()for %d bytes called", length);

    retval = write(fd, buffer, length);
    D("write data to driver: %s", buffer);

    return retval;
}

static int zebrahw__close(void)
{
    if (fd != -1) {
        if (!close(fd)) {
            return 0;
        }
    }

    return -1;
}

static int zebrahw__test(int value)
{
    return value;
}

static int open_zebrahw(const struct hw_module_t* module, char const* name,
        struct hw_device_t** device)
{
    struct zebrahw_device_t *dev = malloc(sizeof(struct zebrahw_device_t));
    if (!dev) {
        D("ZEBRA HW failed to malloc memory !!!");
        return -1;
    }

    memset(dev, 0, sizeof(*dev));

    dev->common.tag = HARDWARE_DEVICE_TAG;
    dev->common.version = 0;
    dev->common.module = (struct hw_module_t*)module;
    dev->read = zebrahw__read;
    dev->write = zebrahw__write;
    dev->close = zebrahw__close;
    dev->test = zebrahw__test;

    *device = (struct hw_device_t*) dev;

    fd = open("/dev/flyzebra", O_RDWR);
    if (fd < 0) {
        D("failed to open /dev/flyzebra!");
        return 0;
    }

    D("ZEBRA HW has been initialized");

    return 0;
}

static struct hw_module_methods_t zebrahw_module_methods = {
    .open = open_zebrahw,
};

struct hw_module_t HAL_MODULE_INFO_SYM = {
    .tag = HARDWARE_MODULE_TAG,
    .version_major = 1,
    .version_minor = 0,
    .id = ZEBRAHW_HARDWARE_MODULE_ID,
    .name = "Zebra HW Module",
    .author = "Zebra inc.",
    .methods = &zebrahw_module_methods,
};