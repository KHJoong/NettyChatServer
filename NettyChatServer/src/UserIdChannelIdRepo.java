import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;

// user가 연결되어 있는 channel의 정보를 담고 있는 클래스입니다.
// channel이라고 해야하는데 클래스 이름 중간에 ChannelId라고 ..........
// 수정하기 귀찮아서 일단 둡니다.
public class UserIdChannelIdRepo {

	UserManagement userManagement;
	
	private final Map<String, Channel> userIdChannelIdMap = new ConcurrentHashMap<>();
	
	public Map<String, Channel> getuserIdChannelIdMap() {
		return userIdChannelIdMap;
	}
	
	public void writeAndFlush(String returnMessage) throws Exception {
		userIdChannelIdMap.values().parallelStream().forEach(channel -> {
			if(!channel.isActive()) {
				userManagement.exit(channel);
				channel.close();
				return;
			}
		});
	}
	
}
