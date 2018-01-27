import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelId;

// channel id에 해당하는 user의 id 정보를 담아두기 위한 클래스입니다.
public class ChannelIdUserIdRepo {

	private final Map<ChannelId, String> channelIdUserIdMap = new ConcurrentHashMap<>();
	
	public Map<ChannelId, String> getChannelIdUserIdMap(){
		return channelIdUserIdMap;
	}
	
}
