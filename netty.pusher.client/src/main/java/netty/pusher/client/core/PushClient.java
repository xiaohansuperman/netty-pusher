package netty.pusher.client.core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import netty.pusher.client.handler.ExceptionHandler;
import netty.pusher.client.handler.HeartBeatHandler;
import netty.pusher.client.handler.PushHandler;
import netty.pusher.client.handler.ResponseHandler;
import netty.pusher.client.util.CByteArrayOutputStream;
import netty.pusher.protocol.constant.ProtocolConst;
import netty.pusher.protocol.core.ProtocolData;
import netty.pusher.protocol.enums.ProtocolType;
import netty.pusher.protocol.protocol.ExceptionProtocol;
import netty.pusher.protocol.protocol.HeartBeatProtocol;
import netty.pusher.protocol.protocol.Protocol;
import netty.pusher.protocol.protocol.PushProtocol;
import netty.pusher.protocol.protocol.RequestProtocol;
import netty.pusher.protocol.protocol.ResponseProtocol;

public class PushClient {
	
	private SocketChannel channel;
	private ByteBuffer receiveBuffer, sendBuffer;
	private int index = 0;
	private CByteArrayOutputStream receiveData = new CByteArrayOutputStream();
	/**
	 * 建立连接
	 */
    public PushClient(){
    	try {
			channel = SocketChannel.open();
			channel.configureBlocking(false);
	        channel.socket().setSendBufferSize(1024 * 4);
	        channel.socket().setReceiveBufferSize(1024 * 4);
	        receiveBuffer = ByteBuffer.allocate(1024 * 4);
	        sendBuffer = ByteBuffer.allocate(1024 * 4);
	        InetSocketAddress isa = new InetSocketAddress("127.0.0.1", 8080);
	        channel.connect(isa);
	        
	        long begin = System.currentTimeMillis();
			while(true) {
				if((System.currentTimeMillis() - begin) > 2000) {
					channel.close();
					throw new Exception("connect to " + isa + " timeout：2000ms" );
				}
				channel.finishConnect();
				if(channel.isConnected()) {
					break;
				} else {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
	        
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
       
    }
    
    /**
     * 发送数据
     * @param p
     * @throws IOException 
     * @throws Exception 
     */
    public void send(Protocol protocol) throws IOException{
    	
		ProtocolData data = null; 
        byte[] p = null;
		if(protocol instanceof RequestProtocol){
			data = new ProtocolData(ProtocolType.Request,protocol);
		}else if(protocol instanceof HeartBeatProtocol){
			data = new ProtocolData(ProtocolType.HeartBeat,protocol);
		}else{
			throw new ProtocolException("发送的协议类型错误");
		}
		
		p = data.toBytes();
        sendBuffer.clear();
		sendBuffer.put(ProtocolConst.P_START_TAG);
		sendBuffer.put(p);
		sendBuffer.put(ProtocolConst.P_END_TAG);
		sendBuffer.flip();
        
		
		while(sendBuffer.hasRemaining()) {
			try {
				channel.write(sendBuffer);
			} catch (IOException e) {
				throw e;	
			}
		}
    }
    
    /**
     * 接收数据
     * @throws Throwable 
     */
    public void receive() throws Throwable{
		receiveBuffer.clear();
 		int re = channel.read(receiveBuffer);
 		if (re < 0) {
			System.err.println("server is close.");
		}
 		receiveBuffer.flip();
   	 	if (receiveBuffer.remaining() == 0) {
            return;
        }
   	 	while (receiveBuffer.remaining() > 0) {
   	 		byte b = receiveBuffer.get();
   	 		receiveData.write(b);
   	 		if (b == ProtocolConst.P_END_TAG[index]) {
   	 			index++;
	   	 		if (index == ProtocolConst.P_END_TAG.length) {
	   	 			byte[] pak = receiveData.toByteArray(ProtocolConst.P_START_TAG.length, receiveData.size() - ProtocolConst.P_END_TAG.length - ProtocolConst.P_START_TAG.length);
	   	 			ProtocolData protocolData = ProtocolData.fromBytes(pak);
	   	 			Protocol protocol = protocolData.getProtocol();
	   	 			if(protocol instanceof ResponseProtocol){
	   	 				ResponseProtocol responseProtocol = (ResponseProtocol) protocol;
	   	 				ResponseHandler responseHandler = new ResponseHandler(responseProtocol);
	   	 				responseHandler.run();
	   	 			}else if(protocol instanceof PushProtocol){
	   	 				PushProtocol pushProtocol = (PushProtocol) protocol;
	   	 				PushHandler pushHandler = new PushHandler(pushProtocol);
	   	 				pushHandler.run();
	   	 			}else if(protocol instanceof HeartBeatProtocol){
	   	 				HeartBeatProtocol heartBeatProtocol = (HeartBeatProtocol) protocol;
	   	 				HeartBeatHandler heartBeatHandler = new HeartBeatHandler(heartBeatProtocol);
	   	 				heartBeatHandler.run();
	   	 			}else if(protocol instanceof ExceptionProtocol){
	   	 				ExceptionProtocol exceptionProtocol = (ExceptionProtocol) protocol;
	   	 				ExceptionHandler exceptionHandler = new ExceptionHandler(exceptionProtocol);
	   	 				exceptionHandler.run();
	   	 			}
		   	 		index = 0;
                    receiveData.reset();
                    continue;
	   	 		}
   	 		}else{
	   	 		if(b == ProtocolConst.P_END_TAG[0]) {
            		index = 1;
            	} else {
            		index = 0;
            	}
   	 		}
   	 	}
		
    }
    
}
