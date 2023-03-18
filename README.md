# Ghidra-ESP8266

This is hank/ghidra-esp8266 updated for Ghidra 10.2.3.  It also fixes some
issues I had when trying to load a tuyasmart-based ESP8266 binary.

### Pre-built Zip

- Added pre-built distribution ZIP to built/
- Can just use this or rebuild yourself below

### Build instructions

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
