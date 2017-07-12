package netty.pusher.exception;

public class ProcessException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProcessException() {
		super("处理异常");
	}
	
	public ProcessException(String message){
		super(message);
	}
}
