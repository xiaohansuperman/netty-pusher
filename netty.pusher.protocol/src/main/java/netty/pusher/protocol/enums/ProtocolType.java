package netty.pusher.protocol.enums;

import netty.pusher.protocol.protocol.ExceptionProtocol;
import netty.pusher.protocol.protocol.HeartBeatProtocol;
import netty.pusher.protocol.protocol.PushProtocol;
import netty.pusher.protocol.protocol.RequestProtocol;
import netty.pusher.protocol.protocol.ResponseProtocol;


public enum ProtocolType {
	
    Response(1),
    Request(2),
    Exception(3),
    HeartBeat(4),
    Pusher(5);
    
    private final int num;

    public int getNum() {
        return num;
    }

    private ProtocolType(int num) {
        this.num = num;
    }

    public static ProtocolType getProtocolType(int num) throws java.lang.Exception {
        for (ProtocolType type : ProtocolType.values()) {
            if (type.getNum() == num) {
                return type;
            }
        }
        throw new Exception("末知的ProtocolType:" + num);
    }
    
	/**
	 * 
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static ProtocolType getProtocolType(Class<?> clazz) throws Exception {
		if(clazz == RequestProtocol.class){
			return ProtocolType.Request;
		} else if(clazz == ResponseProtocol.class){
			return ProtocolType.Response;
		} else if(clazz == ExceptionProtocol.class){
			return ProtocolType.Exception;
		} else if(clazz == HeartBeatProtocol.class){
			return ProtocolType.HeartBeat;
		} else if(clazz == PushProtocol.class){
			return ProtocolType.Pusher;
		}
		throw new Exception("末知的ProtocolType:" + clazz.getName());
	}
	
	/**
	 * 
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static Class<?> getProtocolClass(ProtocolType type) throws Exception {
		if(type == ProtocolType.Request){
			return RequestProtocol.class;
		} else if(type == ProtocolType.Response) {
			return ResponseProtocol.class;
		} else if(type == ProtocolType.Exception){
			return ExceptionProtocol.class;
		} else if(type == ProtocolType.HeartBeat){
			return HeartBeatProtocol.class;
		} else if(type == ProtocolType.Pusher){
			return PushProtocol.class;
		}
		throw new Exception("末知的ProtocolType:" + type);
	}
	
	public static ProtocolType getProtocolType(Object obj) {
        if(obj instanceof RequestProtocol) {
    		return ProtocolType.Request;
    	} else if(obj instanceof ResponseProtocol) {
    		return ProtocolType.Response;
    	} else if(obj instanceof HeartBeatProtocol) {
    		return ProtocolType.HeartBeat;
    	} else if(obj instanceof PushProtocol) {
    		return ProtocolType.Pusher;
    	}else {
    		return ProtocolType.Exception;
    	}
	}
	
}
