package netty.pusher.server.core.tcp;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

public class PusherServer implements Runnable{
	
	private final ServerBootstrap bootstrap = new ServerBootstrap();
	private final PusherServerHandler pusherServerHandler = new PusherServerHandler();
	private final PusherServerPipelineFactory pusherServerPipelineFactory = new PusherServerPipelineFactory(pusherServerHandler, 1024 * 1024 * 4);
	private static final int workCount = 64;
	private static final boolean tcpNoDelay =true;
	private static final String listenIP = "127.0.0.1";
	private static final int listenPort = 8080;
	private static final Logger logger = Logger.getLogger(PusherServer.class);
	
	@Override
	public void run() {
		try {
			bootstrap.setFactory(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool(),workCount));
			bootstrap.setPipelineFactory(pusherServerPipelineFactory);
			bootstrap.setOption("child.tcpNoDelay", tcpNoDelay);
			bootstrap.setOption("child.receiveBufferSize", 1024 * 1024 * 4);
			bootstrap.setOption("child.sendBufferSize", 1024 * 1024 * 4);
			
			InetSocketAddress socketAddress = new InetSocketAddress(listenIP,listenPort);
			bootstrap.bind(socketAddress);
		} catch (Exception e) {
			logger.error("初始化服务端出错", e);
			System.exit(1);
		}
	}
	
}
