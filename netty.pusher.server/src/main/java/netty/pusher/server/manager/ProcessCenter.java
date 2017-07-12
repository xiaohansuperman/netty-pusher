package netty.pusher.server.manager;

import org.apache.log4j.Logger;

import netty.pusher.exception.ProcessException;
import netty.pusher.protocol.protocol.HeartBeatProtocol;
import netty.pusher.protocol.protocol.RequestProtocol;
import netty.pusher.server.handler.HeartBeatHandler;
import netty.pusher.server.handler.RequestHandler;
import netty.pusher.util.async.AsyncInvoker;
import netty.pusher.util.async.AsyncTask;
import netty.pusher.util.async.IAsyncHandler;

public class ProcessCenter {
	
	private static final Logger logger = Logger.getLogger(ProcessCenter.class);
	private AsyncInvoker invoker = AsyncInvoker.getInstance();
	
	public void process(RequestProtocol requestProtocol){
		IAsyncHandler requestHandler = new RequestHandler(requestProtocol);
		AsyncTask requestTask = new AsyncTask(requestHandler);
		try {
			invoker.addTask(requestTask);
		} catch (ProcessException e) {
			logger.error("添加任务出错",e);
		}
	}
	
	public void process(HeartBeatProtocol heartBeatProtocol){
		IAsyncHandler heartBeatHandler = new HeartBeatHandler(heartBeatProtocol);
		AsyncTask heartBeatTask = new AsyncTask(heartBeatHandler);
		try {
			invoker.addTask(heartBeatTask);
		} catch (ProcessException e) {
			logger.error("添加任务出错",e);
		}
	}
}
