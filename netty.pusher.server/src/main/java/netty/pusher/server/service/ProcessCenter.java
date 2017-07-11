package netty.pusher.server.service;

import netty.pusher.protocol.protocol.HeartBeatProtocol;
import netty.pusher.protocol.protocol.RequestProtocol;

public class ProcessCenter {
	
	public void process(RequestProtocol requestProtocol){
		System.out.println(requestProtocol.getName());
	}
	
	public void process(HeartBeatProtocol heartBeatProtocol){
		System.out.println(heartBeatProtocol.getName());
	}
}
