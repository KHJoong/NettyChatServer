import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelId;

// channel id�� �ش��ϴ� user�� id ������ ��Ƶα� ���� Ŭ�����Դϴ�.
public class ChannelIdUserIdRepo {

	private final Map<ChannelId, String> channelIdUserIdMap = new ConcurrentHashMap<>();
	
	public Map<ChannelId, String> getChannelIdUserIdMap(){
		return channelIdUserIdMap;
	}
	
}
