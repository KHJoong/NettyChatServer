import java.util.Map;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.util.internal.StringUtil;

public class UserManagement {

	ChannelIdUserIdRepo channelIdUserIdRepo;
	UserIdChannelIdRepo userIdChannelIdRepo;
	RoomIdUserIdRepo roomIdUserIdRepo;
	UserIdRoomIdRepo userIdRoomIdRepo;
	
	public void join(Channel channel, String type, Map<String, Object> receivedData, Map<String, Object> sendData) throws Exception{
		
		String userId = (String) receivedData.get("userId");
		
		channelIdUserIdRepo.getChannelIdUserIdMap().put(channel.id(), userId);
		userIdChannelIdRepo.getuserIdChannelIdMap().put(userId, channel);
		
	}
	
	public void exit(Channel channel) {
		ChannelId channelId = channel.id();
		Map<ChannelId, String> channelIdUserIdMap = channelIdUserIdRepo.getChannelIdUserIdMap();
		String userId = channelIdUserIdMap.get(channelId);
		
		if(!StringUtil.isNullOrEmpty(userId)) {
			userIdChannelIdRepo.getuserIdChannelIdMap().remove(userId);
			
			String rooomId = userIdRoomIdRepo.getUserIdRoomIdMap().get(userId);
			
			if(!StringUtil.isNullOrEmpty(rooomId)) {
				roomIdUserIdRepo.getRoomIdUserIdMap().remove(rooomId, userId);
				userIdRoomIdRepo.getUserIdRoomIdMap().remove(userId);
			}
			
		}
	}
	
}
