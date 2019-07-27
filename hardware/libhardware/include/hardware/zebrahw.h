#ifndef ANDROID_ZEBRAHW_INTERFACE_H
#define ANDROID_ZEBRAHW_INTERFACE_H

#include <stdint.h>
#include <sys/cdefs.h>
#include <sys/types.h>

#include <hardware/hardware.h>
    
__BEGIN_DECLS

#define ZEBRAHW_HARDWARE_MODULE_ID "zebrahw"

struct zebrahw_device_t {
    struct hw_device_t common;

    int (*read)(char* buffer, int length);
    int (*write)(char* buffer, int length);
    int (*close)(void);
    int (*test)(int value);
};

__END_DECLS

#endif // ANDROID_ZEBRAHW_INTERFACE_H