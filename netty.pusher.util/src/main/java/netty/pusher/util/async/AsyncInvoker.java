package netty.pusher.util.async;

import java.util.concurrent.atomic.AtomicInteger;

import netty.pusher.exception.ProcessException;

public class AsyncInvoker {
	
	private static final AsyncInvoker invoker = new AsyncInvoker();
	
	private final AtomicInteger count = new AtomicInteger(0);
	
	private AsyncWorker[] workers = null;
	
	
	private AsyncInvoker(){
		workers = new AsyncWorker[Runtime.getRuntime().availableProcessors()];
		for(int i = 0; i < workers.length;i++){
			workers[i] = new AsyncWorker();
			workers[i].setDaemon(true);
			workers[i].start();
		}
	}
	
	public static AsyncInvoker getInstance(){
		return invoker;
	}
	
	public void addTask(AsyncTask asyncTask) throws ProcessException{
		if(count.get() > 2000){
			throw new ProcessException("处理队列满了");
		}
		
		int idx = count.get() % workers.length;
		workers[idx].addTask(asyncTask);
		count.incrementAndGet();
	}
	
	
}
