package netty.pusher.server.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.netty.channel.Channel;

public class ChannelManager {
	
	private static final Map<Integer,Channel> allChannel = new ConcurrentHashMap<Integer,Channel>();
	
	private ChannelManager(){
		
	}
	
	public static Map<Integer,Channel> getAllChannel(){
		final Map<Integer,Channel> channelMap = new ConcurrentHashMap<Integer,Channel>();
		if(!allChannel.isEmpty()){
			channelMap.putAll(allChannel);
		}
		return channelMap;
	}
	
	public static void addChannel(Channel channel){
		allChannel.put(channel.getId(), channel);
	}
}
