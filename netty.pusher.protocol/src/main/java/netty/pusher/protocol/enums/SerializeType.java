package netty.pusher.protocol.enums;

public enum SerializeType {
	
	MsgPack(1);
	
	private final int num;

    public int getNum() {
        return num;
    }

    private SerializeType(int num) {
        this.num = num;
    }

    public static SerializeType getSerializeType(int num) {
        for (SerializeType type : SerializeType.values()) {
            if (type.getNum() == num) {
                return type;
            }
        }
        return null;
    }
	
}
