# Ghidra-ESP8266

This is hank/ghidra-esp8266 updated for Ghidra 10.2.3.  It also fixes some
issues I had when trying to load a tuyasmart-based ESP8266 binary (TYWE1S)

I think it's mostly correct now.  Major change was supporting the v2 (0xea magic) header.

### Prerequisites

- Tested with Ghidra 10.2.3
- Requires Tensilica Xtensa plugin: https://github.com/Ebiroll/ghidra-xtensa
  - This fork *appears* more up-to-date than the original
  - The prebuilt zips are 3 years old as of this writing, you'll have to build yourself

### Install Xtensa Support

- This isn't this plugin, you need it 

### Easy Install - Pre-built Zip

Download Extension:
- [Pre-built distribution Zip](GhidraESP8266_extension.zip)

In Ghidra:
- File -> Install Extensions
- Click the + button, locate the zip file
- Restart Ghidra and try it out

### Build Yourself - Instructions

Some info from [this issue](https://github.com/hank/ghidra-esp8266/issues/4)

Install OpenJDK as you would for Ghidra itself, get Ghidra running.

Get updated gradle from gradle.org and run like this:
```
/opt/gradle/gradle-8.0.2/bin/gradle --stacktrace -PGHIDRA_INSTALL_DIR=../../../ghidra/ghidra_10.2.3_PUBLIC
```

Git clone the project into a new folder, change dir
```
git clone git@github.com:jspadaro/ghidra-esp8266.git
cd ghidra-esp8266/GhidraESP8266_2
gradle -PGHIDRA_INSTALL_DIR=/path_to.../ghidra_10.2.3_PUBLIC/
```

In Ghidra:
- File -> Install Extensions
- Click the + button, locate the zip file:
  - `GhidraESP8266_2/dist/ghidra_10.0.2_PUBLIC_...ESP8266_2.zip`
- Restart Ghidra and try it out

Debug output can be viewed in Ghidra user log.  Main window, button at bottom
right.

### Notes

- Original version wasn't quite parsing a version 1 ESP8266 dump correctly
  - 0x1000 had 0xea magic, not 0xe9
- Also suspect implementation isn't complete in general:
  - Doesn't currently support user2.bin at 0x81000 or 0x101000 either
  - Partition information at https://github.com/espressif/ESP8266_NONOS_SDK/blob/master/documents/EN/%20Partition%20Table.md
- esptool `bin_img.py` `LoadFirmwareImage()` has most useful info:
  - https://github.com/espressif/esptool/blob/01bd2269e11213834a26dbd3b53aa06fc7ff745d/esptool/bin_image.py#L38
  - Focused on `ESP8266ROMFirmwareImage()` because that's the chip I'm looking
    at
  - Adding other ROMs should be a matter of walking through that and fixing up
    the loader section analysis
- Magic: Appears that 0xe9 = v1, 0xea = v2 formats
  - Looks like a single flash image can have both (one for boot, one for user)
  - Boot segment is at 0x0, user is 0x1000
  - Unintuitively, at least one Tuya Smart device I have has v2 format for user,
    but v1 for boot
  - This Ida plugin is also helpful for reference: https://gist.github.com/lordneon/95383a4e0de8638a5f500a40e17ec8a6
