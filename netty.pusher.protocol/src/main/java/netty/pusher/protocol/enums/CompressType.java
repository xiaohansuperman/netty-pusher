package netty.pusher.protocol.enums;

public enum CompressType {
	
	 /**
     *  不进行压缩
     */
    UnCompress(1);
    
    private final int num;

    public int getNum() {
        return num;
    }

    private CompressType(int num) {
        this.num = num;
    }

    public static CompressType getCompressType(int num) throws Exception {
        for (CompressType type : CompressType.values()) {
            if (type.getNum() == num) {
                return type;
            }
        }
    	throw new Exception("ĩ֪��ѹ����ʽ");
    }
	
}
