package netty.pusher.server.handler;

import netty.pusher.protocol.protocol.RequestProtocol;
import netty.pusher.util.async.IAsyncHandler;

public class RequestHandler implements IAsyncHandler{

	private RequestProtocol requestProtocol;
	
	public RequestHandler(RequestProtocol requestProtocol) {
		this.requestProtocol = requestProtocol;
	}
	
	@Override
	public Object run() throws Throwable {
		System.out.println(requestProtocol.getName());
		return null;
	}

	@Override
	public void messageReceived(Object paramObject) {
		
		
	}

	@Override
	public void exceptionCaught(Throwable paramThrowable) {
		
		
	}

}
