package netty.pusher.client.handler;

import netty.pusher.protocol.protocol.HeartBeatProtocol;

public class HeartBeatHandler implements IHandler{
	
	private HeartBeatProtocol protocol;
	
	public HeartBeatHandler(HeartBeatProtocol protocol){
		this.protocol = protocol;
	}

	public Object run() throws Throwable {
		System.out.println(protocol.getMessage());
		return null;
	}
	
}
