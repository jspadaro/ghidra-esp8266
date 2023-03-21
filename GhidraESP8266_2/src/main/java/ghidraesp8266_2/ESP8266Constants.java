package ghidraesp8266_2;

public final class ESP8266Constants {
    // ESP8266 Magic value
	public final static byte ESP_IMAGE_MAGIC = (byte) 0xe9;
    // Same thing but for slightly-different "v2" header
	public final static byte ESP_IMAGE_V2_MAGIC = (byte) 0xea;
    
    public final static int IROM_MAP_START = 0x40200000;
    public final static int IROM_MAP_END = 0x40300000;
    public final static int SEGMENT_USER_CODE_BASE = 0x40100000;
    public final static int SEGMENT_USER_DATA_BASE = 0x3FFE8000;
    public final static int SEGMENT_DATA_END = 0x3FFFFFFF;
    public final static int SEGMENT_CODE_BASE = 0x40100000;
    public final static int SECTION_TYPE_CODE = 1;
    public final static int SECTION_TYPE_DATA = 2;
}
