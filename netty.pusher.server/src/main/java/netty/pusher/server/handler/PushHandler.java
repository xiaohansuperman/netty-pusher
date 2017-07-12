package netty.pusher.server.handler;

import java.util.Map;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;

import netty.pusher.protocol.core.ProtocolData;
import netty.pusher.protocol.enums.ProtocolType;
import netty.pusher.protocol.protocol.PushProtocol;
import netty.pusher.server.context.ProcessContext;
import netty.pusher.server.core.tcp.PusherServerHandler;
import netty.pusher.server.manager.ChannelManager;
import netty.pusher.util.async.IAsyncHandler;

public class PushHandler implements IAsyncHandler{
	
	private static final Logger logger = Logger.getLogger(PushHandler.class);
	
	public PushHandler() {
		
	}

	@Override
	public Object run() throws Throwable {
		while(true){
			ProcessContext context = new ProcessContext();
			ProtocolData protocolData = new ProtocolData(ProtocolType.Pusher);
			PushProtocol protocol = new PushProtocol();
			protocol.setMessage("服务端推送一条Push:推送");
			protocolData.setProtocol(protocol);
			Map<Integer,Channel> channelMap = ChannelManager.getAllChannel();
			for(Integer channelID : channelMap.keySet()){
				Channel channel = channelMap.get(channelID);
				if(channel.isConnected() && channel.isConnected()){
					context.setProtocolData(protocolData);
					context.setChannel(channel);
					PusherServerHandler.write(context);
				}
			}
			try {
				Thread.sleep(1000 * 5);
			} catch (InterruptedException e) {
				logger.error("服务端线程意外中断：",e);
			}
		}
	}

	@Override
	public void messageReceived(Object paramObject) {
		
	}

	@Override
	public void exceptionCaught(Throwable paramThrowable) {
		
	}
	
}
