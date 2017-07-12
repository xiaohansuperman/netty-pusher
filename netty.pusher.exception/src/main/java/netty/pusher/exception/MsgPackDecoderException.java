package netty.pusher.exception;

public class MsgPackDecoderException extends Exception{
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1L;
	
	public MsgPackDecoderException(){
		super("MsgPackDecoder 解码失败");
	}
	
	public MsgPackDecoderException(String message){
		super(message);
	}
	
}
