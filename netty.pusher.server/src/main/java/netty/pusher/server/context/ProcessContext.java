package netty.pusher.server.context;


import org.jboss.netty.channel.Channel;

import netty.pusher.protocol.core.ProtocolData;
import netty.pusher.protocol.enums.ProtocolType;
import netty.pusher.protocol.protocol.ExceptionProtocol;
import netty.pusher.protocol.util.ExceptionHelper;

public class ProcessContext {
	
	private ProtocolData protocolData;
	
	private Channel channel;

	public ProtocolData getProtocolData() {
		return protocolData;
	}

	public void setProtocolData(ProtocolData protocolData) {
		this.protocolData = protocolData;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	public static ProcessContext createContext(Throwable throwable,Channel channel){
		ProcessContext context = new ProcessContext();
		ExceptionProtocol protocol = new ExceptionProtocol();
		String errorMessage = ExceptionHelper.getStackTrace(throwable);
		protocol.setMessage(errorMessage);
		ProtocolData protocolData = new ProtocolData(ProtocolType.Exception);
		protocolData.setProtocol(protocol);
		context.setProtocolData(protocolData);
		context.setChannel(channel);
		return context;
	}
	
	
}
