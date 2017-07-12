package netty.pusher.client.handler;

import netty.pusher.protocol.protocol.ResponseProtocol;

public class ResponseHandler implements IHandler{
	
	private ResponseProtocol protocol;
	
	public ResponseHandler(ResponseProtocol protocol){
		this.protocol = protocol;
	}

	public Object run() throws Throwable {
		System.out.println(protocol.getMessage());
		return null;
	}
	
}
