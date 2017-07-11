package netty.pusher.server.core.tcp;

import static org.jboss.netty.buffer.ChannelBuffers.directBuffer;
import static org.jboss.netty.channel.Channels.pipeline;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;

import netty.pusher.protocol.constant.ProtocolConst;
import netty.pusher.server.codec.MsgPackDecoder;
import netty.pusher.server.codec.MsgPackEncoder;

public class PusherServerPipelineFactory implements ChannelPipelineFactory{
	
	private final ChannelHandler handler;
	private final int frameMaxLength;

    public PusherServerPipelineFactory(ChannelHandler handler, int frameMaxLength) {
    	this.handler = handler;
    	this.frameMaxLength = frameMaxLength;
    }

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = pipeline();
		
		ChannelBuffer buf = directBuffer(ProtocolConst.P_END_TAG.length);
		buf.writeBytes(ProtocolConst.P_END_TAG);
		//解决粘包问题
		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(this.frameMaxLength, true, buf));
		//解决序列化问题
		pipeline.addLast("msgPackDecoder",new MsgPackDecoder());
		//编码
		pipeline.addLast("msgPackEncoder",new MsgPackEncoder());
		pipeline.addLast("handler", handler);

		return pipeline;
	}
	
}
