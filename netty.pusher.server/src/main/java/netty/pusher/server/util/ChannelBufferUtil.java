package netty.pusher.server.util;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

public class ChannelBufferUtil {
	
	private ChannelBufferUtil(){
		
	}
	
	
	public static ChannelBuffer byteToBuffer(byte[]... array){
		return ChannelBuffers.copiedBuffer(array);
	}
}
