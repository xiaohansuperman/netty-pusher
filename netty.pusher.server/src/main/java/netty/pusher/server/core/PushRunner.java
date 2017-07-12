package netty.pusher.server.core;

import org.apache.log4j.Logger;

import netty.pusher.exception.ProcessException;
import netty.pusher.server.handler.PushHandler;
import netty.pusher.util.async.AsyncInvoker;
import netty.pusher.util.async.AsyncTask;
import netty.pusher.util.async.IAsyncHandler;

public class PushRunner implements Runnable{
	
	private AsyncInvoker invoker = AsyncInvoker.getInstance();
	private static final Logger logger = Logger.getLogger(PushRunner.class);
	
	@Override
	public void run() {
		IAsyncHandler pushHandler = new PushHandler();
		AsyncTask pushTask = new AsyncTask(pushHandler);
		try {
			invoker.addTask(pushTask);
		} catch (ProcessException e) {
			logger.error("添加任务出错",e);
		}
	}
	
}
