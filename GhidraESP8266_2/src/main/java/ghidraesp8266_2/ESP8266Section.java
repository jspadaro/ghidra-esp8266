package ghidraesp8266_2;

import java.io.IOException;

import ghidra.app.util.bin.BinaryReader;
import ghidra.app.util.bin.StructConverter;
import ghidra.program.model.data.DataType;
import ghidra.program.model.data.Structure;
import ghidra.program.model.data.StructureDataType;
import ghidra.util.Msg;
import ghidra.util.exception.DuplicateNameException;

public class ESP8266Section implements StructConverter {
	private int offset;
	private int size;
	private byte[] content;
	
    // Figure out v1 vs v2 segment based on header
	public ESP8266Section(BinaryReader reader, ESP8266Header userheader) throws IOException {
        if(userheader.getVersion() == 1) {
            Msg.info(this, "Loading v1 header section");
            this.initV1(reader);
        } else if(userheader.getVersion() == 2) {
            // V2 = calculate offset for loading
            // Some scripts have IROM MAP START + flashing_addr + 8
            // Not sure what flashing_addr should be, but this seems right for a Tuya TWE1S although odd that entry point is IROM_MAP_START+4
            Msg.info(this, "Loading v2 header section");
            this.initV2(reader, ESP8266Constants.IROM_MAP_START + 8);
        } else {
            Msg.error(this, "Header is neither v1 nor v2?  Should have been caught as an exception already.");
        }
    }

	public void initV1(BinaryReader reader) throws IOException {
        // Segments in ESP8266 are <4 byte offset><4 byte size><segment data>
        long fileOffset = reader.getPointerIndex();
		offset = reader.readNextInt();
		size = reader.readNextInt();
		Msg.info(this, String.format("Segment Length: %x Load offset: %x File Offset: %x", size, offset, fileOffset));
		content = reader.readNextByteArray(size);
	}
    
    // Primarily for "v2" header that has 0x0 for offset
	private void initV2(BinaryReader reader, int calculatedOffset) throws IOException {
        // Segments in ESP8266 are <4 byte offset><4 byte size><segment data>
        long fileOffset = reader.getPointerIndex();
		offset = reader.readNextInt();
        if(offset != 0) {
            // V2 will still have offset, but it should be 0
            throw new IOException(String.format("Expected v2 header to have 0 offset, but offset here was: %s", offset));
        } else {
            // use the one from the args
            offset = calculatedOffset;
        }
		size = reader.readNextInt();
		Msg.info(this, String.format("Segment Length: %x Load offset: %x File Offset: %x", size, offset, fileOffset));
		content = reader.readNextByteArray(size);
    }
    
	@Override
	public DataType toDataType() throws DuplicateNameException, IOException {
		Structure structure = new StructureDataType("data_block", 0);
		//structure.add(DWORD, 1, "offset", "Starting offset of the section");
		//structure.add(DWORD, 1, "size", "Size of the section");
		structure.add(BYTE, size, "content", "Contents of the section");
		return structure;
	}

	public int getOffset() {
		return offset;
	}

	public int getSize() {
		return size;
	}

	public byte[] getContent() {
		Msg.info(this, String.format("Section starts with %02x %02x %02x", content[0], content[1], content[2]));
		return content;
	}

	public String getName() {
		// Rules based on ranges
		if(offset == ESP8266Constants.SEGMENT_USER_CODE_BASE)
			return ".user_code";
		else if(offset == ESP8266Constants.SEGMENT_USER_DATA_BASE)
			return ".user_data";
		else if(offset <= ESP8266Constants.SEGMENT_DATA_END)
			return ".data";
		else if(offset > ESP8266Constants.SEGMENT_CODE_BASE)
			return ".code";
		else
			return ".unknown";
	}
	public int getType() {
		// Rules based on ranges
		if(offset == ESP8266Constants.SEGMENT_USER_CODE_BASE)
			return ESP8266Constants.SECTION_TYPE_CODE;
		else if(offset == ESP8266Constants.SEGMENT_USER_DATA_BASE)
			return ESP8266Constants.SECTION_TYPE_DATA;
		else if(offset <= ESP8266Constants.SEGMENT_DATA_END)
			return ESP8266Constants.SECTION_TYPE_DATA;
		else if(offset > ESP8266Constants.SEGMENT_CODE_BASE)
			return ESP8266Constants.SECTION_TYPE_CODE;
		else
			return ESP8266Constants.SECTION_TYPE_DATA;
	}
}
