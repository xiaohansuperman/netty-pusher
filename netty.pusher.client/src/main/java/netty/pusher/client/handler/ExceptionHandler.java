package netty.pusher.client.handler;

import netty.pusher.protocol.protocol.ExceptionProtocol;

public class ExceptionHandler implements IHandler{

	public ExceptionProtocol protocol;
	
	public ExceptionHandler(ExceptionProtocol protocol){
		this.protocol = protocol;
	}
	
	public Object run() throws Throwable {
		System.out.println(protocol.getMessage());
		return null;
	}
	
}
