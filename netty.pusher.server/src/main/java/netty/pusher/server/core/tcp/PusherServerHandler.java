package netty.pusher.server.core.tcp;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import netty.pusher.exception.ProcessException;
import netty.pusher.protocol.core.ProtocolData;
import netty.pusher.server.context.ProcessContext;
import netty.pusher.server.manager.ChannelManager;
import netty.pusher.server.manager.ProcessCenter;

public class PusherServerHandler extends SimpleChannelUpstreamHandler{
	
	private final ProcessCenter processCenter = new ProcessCenter();
	private static final Logger logger = Logger.getLogger(PusherServerHandler.class);
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws ProcessException {
		
		ProtocolData protocolData = (ProtocolData) e.getMessage();
		Channel channel = e.getChannel();
		ProcessContext context = new ProcessContext();
		context.setChannel(channel);
		context.setProtocolData(protocolData);
		processCenter.process(context);
	}
	
	
	@Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) {
		ChannelManager.addChannel(e.getChannel());
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		logger.error("unexpected exception from downstream remoteAddress(" + e.getChannel().getRemoteAddress().toString() + ")",e.getCause());
	}
	
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e){
		logger.info("channel is closed:" + e.getChannel().getRemoteAddress().toString());
		e.getChannel().close();
	}
	
	public static void write(ProcessContext context){
		Channel channel = context.getChannel();
		if(channel != null && channel.isOpen() && channel.isConnected()){
			channel.write(context.getProtocolData());
		}
	}
	
	
	
}
