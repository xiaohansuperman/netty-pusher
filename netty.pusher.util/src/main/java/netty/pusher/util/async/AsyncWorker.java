package netty.pusher.util.async;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

public class AsyncWorker extends Thread{
	
	private final TransferQueue<AsyncTask> taskQueue = new LinkedTransferQueue<AsyncTask>();
	
	private volatile boolean isStop = false;
	
	private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	
	public AsyncWorker(){
		
	}
	
	public void addTask(AsyncTask processTask){
		taskQueue.add(processTask);
	}
	
	@Override
	public void run() {
		while(!isStop){
			execNoTimeLimitTask();
		}
	}
	
	private void execNoTimeLimitTask(){
		try {
			AsyncTask task = taskQueue.poll(1500, TimeUnit.MILLISECONDS);
			if(task != null){
				final IAsyncHandler handler = task.getAsyncHandler();
				final CountDownLatch cdl = new CountDownLatch(1);
				executor.execute(new Runnable() {
					public void run() {
						try {
							handler.run();
						} catch (Throwable e) {
							handler.exceptionCaught(e);
						}finally {
							cdl.countDown();
						}
					}
				});
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	
}
