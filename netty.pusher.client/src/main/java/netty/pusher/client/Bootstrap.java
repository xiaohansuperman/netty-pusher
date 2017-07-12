package netty.pusher.client;

import java.io.IOException;

import org.apache.log4j.Logger;

import netty.pusher.client.core.PushClient;
import netty.pusher.protocol.protocol.RequestProtocol;

public class Bootstrap {
	
	private static final Logger logger = Logger.getLogger(Bootstrap.class);
	
	public static void main(String [] args){
		PushClient client = new PushClient();
    	
    	String message = "客户端发来一条Request";
        RequestProtocol protocol = new RequestProtocol();
        protocol.setMessage(message);
        try {
			client.send(protocol);
		} catch (IOException e1) {
			logger.error("发送数据出错",e1);
		}
    	
    	while(true){
    		try {
    			client.receive();
				Thread.sleep(1000 * 1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Throwable e) {
				logger.error("接收数据出错",e);
				return;
			}
    	}
	}
	
}
