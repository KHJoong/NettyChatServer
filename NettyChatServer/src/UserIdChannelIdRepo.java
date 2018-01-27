import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;

// user�� ����Ǿ� �ִ� channel�� ������ ��� �ִ� Ŭ�����Դϴ�.
// channel�̶�� �ؾ��ϴµ� Ŭ���� �̸� �߰��� ChannelId��� ..........
// �����ϱ� �����Ƽ� �ϴ� �Ӵϴ�.
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
