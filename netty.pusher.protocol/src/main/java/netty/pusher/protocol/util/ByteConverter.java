package netty.pusher.protocol.util;

public class ByteConverter {

    /**
     * byte array to int (little endian)
     * @param buf
     * @return
     */
    public static int bytesToIntLittleEndian(byte buf[]) {
        return buf[0] & 0xff
                | ((buf[1] << 8) & 0xff00)
                | ((buf[2] << 16) & 0xff0000)
                | ((buf[3] << 24) & 0xff000000);
    }
    
    
    public static short byteToShortLittleEndian(byte[] buffer){
        short int16 = 0;
        int16 = (short) (buffer[0] & 0xff);
        int16 |= ((short) buffer[1] << 8) & 0xff00;
        return int16;
    }

    
	public static short bytesToShortLittleEndian(byte[] buf, int offset) {
		return (short) (((buf[offset] << 8) & 0xff00) | (buf[offset + 1] & 0xff));
	}
	
	public static short bytesToShortBigEndian(byte[] buf, int offset) {
		return (short) (buf[offset] & 0xff | ((buf[offset + 1] << 8) & 0xff00));
	}
    
    
    /**
     * int to byte array (little endian)
     * @param n
     * @return
     */
    public static byte[] intToBytesLittleEndian(int n) {
        byte[] buf = new byte[4];
        buf[0] = (byte) (0xff & n);
        buf[1] = (byte) ((0xff00 & n) >> 8);
        buf[2] = (byte) ((0xff0000 & n) >> 16);
        buf[3] = (byte) ((0xff000000 & n) >> 24);
        return buf;
    }

    /**
     * byte array to int (big endian)
     * @param buf
     * @return
     */
    public static int bytesToIntBigEndian(byte[] buf) {
        return ((buf[0] << 24) & 0xff000000)
                | ((buf[1] << 16) & 0xff0000)
                | ((buf[2] << 8) & 0xff00)
                | (buf[3] & 0xff);
    }

    /**
     * int to byte array (big endian)
     * @param n
     * @return
     */
    public static byte[] intToBytesBigEndian(int n) {
        byte[] buf = new byte[4];
        buf[0] = (byte) ((0xff000000 & n) >> 24);
        buf[1] = (byte) ((0xff0000 & n) >> 16);
        buf[2] = (byte) ((0xff00 & n) >> 8);
        buf[3] = (byte) (0xff & n);
        return buf;
    }
    
    public static byte[] shortToBytesBigEndian(short n) {
		byte[] buf = new byte[2];
		for(int i=0; i<buf.length; i++) {
			buf[i] = (byte) (n >> (8 * i));
		}
		return buf;
	}
    
    public static byte[] shortToBytesLittleEndian(short n) {
		byte[] buf = new byte[2];
		for(int i=0; i<buf.length; i++) {
			buf[buf.length - i - 1] = (byte) (n >> (8 * i));
		}
		return buf;
	}
   
    public static int bytesToIntLittleEndian(byte buf[], int offset) {
        return buf[offset] & 0xff
                | ((buf[offset + 1] << 8) & 0xff00)
                | ((buf[offset + 2] << 16) & 0xff0000)
                | ((buf[offset + 3] << 24) & 0xff000000);
    }

    public static int bytesToIntBigEndian(byte[] buf, int offset) {
        return ((buf[offset] << 24) & 0xff000000)
                | ((buf[offset + 1] << 16) & 0xff0000)
                | ((buf[offset + 2] << 8) & 0xff00)
                | (buf[offset + 3] & 0xff);
    }
}
