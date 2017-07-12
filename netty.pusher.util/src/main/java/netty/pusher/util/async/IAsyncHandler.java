package netty.pusher.util.async;

public interface IAsyncHandler {
	
	public abstract Object run() throws Throwable;
		  
	public abstract void messageReceived(Object paramObject);
	  
	public abstract void exceptionCaught(Throwable paramThrowable);
	
}
