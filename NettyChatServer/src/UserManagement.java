import java.util.Map;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.util.internal.StringUtil;

public class UserManagement {
	
	public void join(Channel channel, String type, Map<String, Object> receivedData) throws Exception{
		
		String userId = (String) receivedData.get("userId");
		NettyChatServer.channelIdUserIdRepo.getChannelIdUserIdMap().put(channel.id(), userId);
		NettyChatServer.userIdChannelIdRepo.getuserIdChannelIdMap().put(userId, channel);
				
		System.out.println("UserManagement:join:channel.id:"+channel.id()+" / userId:"+userId);
		System.out.println("UserManagement:join:channel.id:"+NettyChatServer.channelIdUserIdRepo.getChannelIdUserIdMap().keySet());
		System.out.println("UserManagement:join:channel.id:"+NettyChatServer.userIdChannelIdRepo.getuserIdChannelIdMap().keySet());
		
	}
	
	public void exit(Channel channel) {
		ChannelId channelId = channel.id();
		Map<ChannelId, String> channelIdUserIdMap = NettyChatServer.channelIdUserIdRepo.getChannelIdUserIdMap();
		String userId = channelIdUserIdMap.get(channelId);
		
		System.out.println("UserManagement:exit"+channel.id()+"/"+userId);
		
		if(!StringUtil.isNullOrEmpty(userId)) {
			NettyChatServer.userIdChannelIdRepo.getuserIdChannelIdMap().remove(userId);
			
			String roomId = NettyChatServer.userIdRoomIdRepo.getUserIdRoomIdMap().get(userId);
			
			System.out.println("UserManagement:exit"+roomId);
			
			if(!StringUtil.isNullOrEmpty(roomId)) {
				NettyChatServer.roomIdUserIdRepo.getRoomIdUserIdMap().remove(roomId, userId);
				NettyChatServer.userIdRoomIdRepo.getUserIdRoomIdMap().remove(userId);
				
				System.out.println("UserManagement:exit:secondIf");				
			}
			
		}
	}
	
}
