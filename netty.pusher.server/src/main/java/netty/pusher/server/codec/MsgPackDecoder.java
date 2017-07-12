package netty.pusher.server.codec;

import netty.pusher.exception.MsgPackDecoderException;
import netty.pusher.protocol.constant.ProtocolConst;
import netty.pusher.protocol.core.ProtocolData;
import netty.pusher.protocol.util.ProtocolHelper;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class MsgPackDecoder extends FrameDecoder{
	
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,ChannelBuffer buffer) throws Exception {
		int readableBytes = buffer.readableBytes();
		if(readableBytes < ProtocolConst.P_START_TAG.length){
			throw new MsgPackDecoderException("MsgPackDecoder 解码失败，可读长度小于初始标记长度，当前长度：" + readableBytes);
		}
		byte[] reciveByte = new byte[readableBytes];
		buffer.readBytes(reciveByte,0,reciveByte.length);
		byte[] headDelimiter = new byte[ProtocolConst.P_START_TAG.length];
		System.arraycopy(reciveByte, 0, headDelimiter, 0, ProtocolConst.P_START_TAG.length);
		
		if(ProtocolHelper.checkHeadDelimiter(headDelimiter)) {
			byte[] requestBuffer = new byte[reciveByte.length - ProtocolConst.P_START_TAG.length];
			System.arraycopy(reciveByte, ProtocolConst.P_START_TAG.length, requestBuffer, 0, (reciveByte.length - ProtocolConst.P_START_TAG.length));
			ProtocolData protocol = ProtocolData.fromBytes(requestBuffer);
			return protocol;
		}
		
		String p_start_tag = "";  
		for(int i = 0; i < headDelimiter.length ; i++){
			p_start_tag += headDelimiter[i] + " ";
		}
		
		throw new MsgPackDecoderException("MsgPackDecoder 解码失败，初始标记不正确，当前错误初始标记为：" + p_start_tag);
	}


}
