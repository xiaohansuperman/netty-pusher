package netty.pusher.protocol.constant;

/**
 * ProtocolConst
 *
 */
public class ProtocolConst {

    /**
     * 初始标记
     */
    public static final byte[] P_START_TAG = new byte[]{18, 17, 13, 10, 9};
    
    /**
     * 结束标记
     */
    public static final byte[] P_END_TAG = new byte[]{9, 10, 13, 17, 18};
    
}
