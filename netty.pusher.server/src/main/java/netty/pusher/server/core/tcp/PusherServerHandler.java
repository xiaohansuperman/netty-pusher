package netty.pusher.server.core.tcp;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import netty.pusher.protocol.core.ProtocolData;
import netty.pusher.protocol.enums.ProtocolType;
import netty.pusher.protocol.protocol.HeartBeatProtocol;
import netty.pusher.protocol.protocol.Protocol;
import netty.pusher.protocol.protocol.RequestProtocol;
import netty.pusher.protocol.protocol.ResponseProtocol;
import netty.pusher.server.manager.ChannelManager;
import netty.pusher.server.service.ProcessCenter;

public class PusherServerHandler extends SimpleChannelUpstreamHandler{
	
	private final ProcessCenter processCenter = new ProcessCenter();
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		
		ProtocolData protocolData = (ProtocolData) e.getMessage();
		Protocol protocol = protocolData.getProtocol();
		//根据不同的需要可以进行处理，如果数量太多，这里可以用线程池进行替代(区分工作线程和业务线程)
		if(protocol instanceof RequestProtocol){
			RequestProtocol requestProtocol = (RequestProtocol) protocol;
			processCenter.process(requestProtocol);
		}else if(protocol instanceof HeartBeatProtocol){
			HeartBeatProtocol heartBeatProtocol = (HeartBeatProtocol) protocol;
			processCenter.process(heartBeatProtocol);
		}
		
		Channel channel = e.getChannel();
		ChannelManager.addChannel(channel);
		
		ProtocolData protocolData2 = new ProtocolData(ProtocolType.Response);
		ResponseProtocol protocol2 = new ResponseProtocol();
		protocol2.setName("服务端回复一条Response");
		protocolData2.setProtocol(protocol2);
		
		channel.write(protocolData2);
	}
	
}
