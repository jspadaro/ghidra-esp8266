package ghidraesp8266_2;

public final class ESP8266Constants {
	/** "Flash" magic value */
	public final static byte FLASH_MAGIC = (byte) 0xe9;
    /** "IROM" Magic value, if boot mode is 2, from:
    * https://github.com/espressif/ESP8266_NONOS_SDK/blob/158bb7a53f16cfff21b35fb4ec66fa15261f5a4a/tools/gen_appbin.py
    * **/
	public final static byte IROM_MAGIC = (byte) 0xea;
    
    public final static int IROM_MAP_START = 0x40200000;
    public final static int IROM_MAP_END = 0x40300000;
    public final static int SEGMENT_USER_CODE_BASE = 0x40100000;
    public final static int SEGMENT_USER_DATA_BASE = 0x3FFE8000;
    public final static int SEGMENT_DATA_END = 0x3FFFFFFF;
    public final static int SEGMENT_CODE_BASE = 0x40100000;
    public final static int SECTION_TYPE_CODE = 1;
    public final static int SECTION_TYPE_DATA = 2;
}
