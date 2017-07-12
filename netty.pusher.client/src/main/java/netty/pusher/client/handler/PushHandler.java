package netty.pusher.client.handler;

import netty.pusher.protocol.protocol.PushProtocol;

public class PushHandler implements IHandler{
	
	private PushProtocol protocol;
	
	public PushHandler(PushProtocol protocol){
		this.protocol = protocol;
	}

	public Object run() throws Throwable {
		System.out.println(protocol.getMessage());
		return null;
	}
	
}
