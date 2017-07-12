package netty.pusher.protocol.protocol;

import org.msgpack.annotation.Message;

@Message
public class Protocol {
	
	private String Message;

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

}
