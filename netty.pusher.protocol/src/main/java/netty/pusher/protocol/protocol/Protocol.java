package netty.pusher.protocol.protocol;

import org.msgpack.annotation.Message;

@Message
public class Protocol {
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
