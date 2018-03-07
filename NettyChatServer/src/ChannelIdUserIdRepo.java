import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelId;

public class ChannelIdUserIdRepo {

	private final Map<ChannelId, String> channelIdUserIdMap = new ConcurrentHashMap<>();
	
	public Map<ChannelId, String> getChannelIdUserIdMap(){
		return channelIdUserIdMap;
	}
	
}
