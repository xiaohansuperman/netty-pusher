package netty.pusher.protocol.util;

import netty.pusher.protocol.constant.ProtocolConst;

public class ProtocolHelper {
	
	private ProtocolHelper(){
		
	}
	
	public static boolean checkHeadDelimiter(byte[] buf){
		if(buf.length == ProtocolConst.P_START_TAG.length){
			for(int i=0; i<buf.length; i++) {
				if(buf[i] != ProtocolConst.P_START_TAG[i]) {
					return false;
				}
			}
			return true;
		} else{
			return false;
		}
	}
	
}
