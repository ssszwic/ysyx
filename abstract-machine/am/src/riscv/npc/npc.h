#ifndef __NPC_H__
#define __NPC_H__

#define DEVICE_BASE 0xa0000000
#define MMIO_BASE 0xa0000000

// device
#define SERIAL_PORT     (DEVICE_BASE + 0x00003f8)
#define RTC_ADDR        (DEVICE_BASE + 0x0000048)
#define KBD_ADDR        (DEVICE_BASE + 0x0000080) // different form nemu
#define VGACTL_ADDR     (DEVICE_BASE + 0x0000100)
#define FB_ADDR         (MMIO_BASE   + 0x1000000)

#endif