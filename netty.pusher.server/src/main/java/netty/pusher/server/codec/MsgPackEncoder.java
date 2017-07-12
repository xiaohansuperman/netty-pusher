package netty.pusher.server.codec;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import netty.pusher.exception.MsgPackEncoderException;
import netty.pusher.protocol.constant.ProtocolConst;
import netty.pusher.protocol.core.ProtocolData;
import netty.pusher.server.context.ProcessContext;
import netty.pusher.server.core.tcp.PusherServerHandler;
import netty.pusher.server.util.ChannelBufferUtil;

public class MsgPackEncoder extends OneToOneEncoder{

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
		if(msg == null){
			Exception exception = new NullPointerException("要进行编码的协议为NULL");
			ProcessContext context = ProcessContext.createContext(exception, channel);
			PusherServerHandler.write(context);
			throw exception;
		}
		
		if(msg instanceof ProtocolData){
			ProtocolData protocolData = (ProtocolData) msg;
			byte[] data = protocolData.toBytes();
			ChannelBuffer channelBuffer = ChannelBufferUtil.byteToBuffer(ProtocolConst.P_START_TAG,data,ProtocolConst.P_END_TAG);
			return channelBuffer;
		}
		
		Exception exception = new MsgPackEncoderException();
		ProcessContext context = ProcessContext.createContext(exception, channel);
		PusherServerHandler.write(context);
		throw new MsgPackEncoderException();
	}

}
