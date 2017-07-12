package netty.pusher.exception;

public class ProtocolException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProtocolException() {
		super("协议错误");
	}
	
	public ProtocolException(String message){
		super(message);
	}
	
}
