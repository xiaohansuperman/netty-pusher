package netty.pusher.server.core;

import netty.pusher.server.service.PushService;

public class PushRunner implements Runnable{
	
	private final PushService pushService = new PushService();
	
	@Override
	public void run() {
		pushService.push();
	}
	
}
