package netty.pusher.exception;

public class TimeOutException extends Exception{
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1L;
	
	public TimeOutException(){
		super("处理超时");
	}
	
	public TimeOutException(String message){
		super(message);
	}
	
}
