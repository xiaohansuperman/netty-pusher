package netty.pusher.server.handler;

import java.net.ProtocolException;

import netty.pusher.protocol.core.ProtocolData;
import netty.pusher.protocol.enums.ProtocolType;
import netty.pusher.protocol.protocol.ExceptionProtocol;
import netty.pusher.protocol.protocol.HeartBeatProtocol;
import netty.pusher.protocol.protocol.Protocol;
import netty.pusher.protocol.protocol.RequestProtocol;
import netty.pusher.protocol.protocol.ResponseProtocol;
import netty.pusher.protocol.util.ExceptionHelper;
import netty.pusher.server.context.ProcessContext;
import netty.pusher.server.core.tcp.PusherServerHandler;
import netty.pusher.util.async.IAsyncHandler;

public class ProcessHandler implements IAsyncHandler{
	
	private ProcessContext context;
	
	public ProcessHandler(ProcessContext context){
		this.context = context;
	}

	@Override
	public Object run() throws Throwable {
		ProtocolData protocolData = context.getProtocolData();
		Protocol protocol = protocolData.getProtocol();
		if(protocol instanceof RequestProtocol){
			RequestProtocol requestProtocol = (RequestProtocol) protocol;
			System.out.println(requestProtocol.getMessage());
			protocolData = new ProtocolData(ProtocolType.Response);
			ResponseProtocol responseProtocol = new ResponseProtocol();
			responseProtocol.setMessage("服务端发送来一条response:回复");
			protocolData.setProtocol(responseProtocol);
		}else if(protocol instanceof HeartBeatProtocol){
			HeartBeatProtocol heartBeatProtocol1 = (HeartBeatProtocol) protocol;
			System.out.println(heartBeatProtocol1.getMessage());
			protocolData = new ProtocolData(ProtocolType.HeartBeat);
			HeartBeatProtocol heartBeatProtocol = new HeartBeatProtocol();
			heartBeatProtocol.setMessage("服务端发送来一条heartBeat:心跳");
			protocolData.setProtocol(heartBeatProtocol);
		}else{
			throw new ProtocolException("发送的协议有误：" + protocol.getClass().getSimpleName());
		}
		return protocolData;
	}

	@Override
	public void messageReceived(Object obj) {
		ProtocolData protocolData = (ProtocolData) obj;
		context.setProtocolData(protocolData);
		PusherServerHandler.write(context);
	}

	@Override
	public void exceptionCaught(Throwable throwable) {
		ExceptionProtocol protocol = new ExceptionProtocol();
		String errorMessage = ExceptionHelper.getStackTrace(throwable);
		protocol.setMessage(errorMessage);
		ProtocolData protocolData = new ProtocolData(ProtocolType.Exception);
		protocolData.setProtocol(protocol);
		context.setProtocolData(protocolData);
		PusherServerHandler.write(context);
	}
	
}
