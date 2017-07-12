package netty.pusher.server.handler;

import netty.pusher.protocol.protocol.HeartBeatProtocol;
import netty.pusher.util.async.IAsyncHandler;

public class HeartBeatHandler implements IAsyncHandler{
	
	private HeartBeatProtocol heartBeatProtocol;
	
	public HeartBeatHandler(HeartBeatProtocol heartBeatProtocol) {
		this.heartBeatProtocol = heartBeatProtocol;
	}
	
	@Override
	public Object run() throws Throwable {
		System.out.println(heartBeatProtocol.getName());
		return null;
	}

	@Override
	public void messageReceived(Object paramObject) {
		
		
	}

	@Override
	public void exceptionCaught(Throwable paramThrowable) {
		
		
	}
	
}
