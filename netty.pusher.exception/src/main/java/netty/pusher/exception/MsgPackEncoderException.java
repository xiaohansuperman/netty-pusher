package netty.pusher.exception;

public class MsgPackEncoderException extends Exception{
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1L;
	
	public MsgPackEncoderException(){
		super("MsgPackEncoder 编码失败");
	}
	
	public MsgPackEncoderException(String message){
		super(message);
	}
	
}
