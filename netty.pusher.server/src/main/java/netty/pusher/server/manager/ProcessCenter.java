package netty.pusher.server.manager;

import netty.pusher.exception.ProcessException;
import netty.pusher.server.context.ProcessContext;
import netty.pusher.server.handler.ProcessHandler;
import netty.pusher.util.async.AsyncInvoker;
import netty.pusher.util.async.AsyncTask;
import netty.pusher.util.async.IAsyncHandler;

public class ProcessCenter {
	
	private AsyncInvoker invoker = AsyncInvoker.getInstance();
	
	
	public void process(ProcessContext context) throws ProcessException{
		IAsyncHandler processHandler = new ProcessHandler(context);
		AsyncTask requestTask = new AsyncTask(processHandler);
		invoker.addTask(requestTask);
	}
	
}
