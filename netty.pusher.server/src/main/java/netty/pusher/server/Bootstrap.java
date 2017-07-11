package netty.pusher.server;

import org.apache.log4j.Logger;

import netty.pusher.server.core.PushRunner;
import netty.pusher.server.core.tcp.PusherServer;

public final class Bootstrap {
	
	private static final Logger logger = Logger.getLogger(Bootstrap.class);
	
	public static void startServer(){
		
		logger.info("starting pusher server");
		PusherServer pusherServer = new PusherServer();
		Thread serverThread = new Thread(pusherServer);
		serverThread.setDaemon(true);
		serverThread.start();
		logger.info("pusher server start success");
		
		logger.info("starting push service");
		//这里用单线程或者多线程需要自己去处理
		PushRunner pushRunner = new PushRunner();
		Thread pushThread = new Thread(pushRunner);
		pushThread.setDaemon(true);
		pushThread.start();
		logger.info("push service start success");
		
	}
	
	public static void main(String[] args){
		
		startServer();
		
		while(true){
			try {
				Thread.sleep(1000 * 60 * 60);
			} catch (InterruptedException e) {
				logger.error("服务端线程意外中断：",e);
			}
		}
	}
	
}
