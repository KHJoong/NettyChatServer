import java.util.Map;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.util.internal.StringUtil;

// 클라이언트가 네티와 연결될 때, 나갈 때의 행동을 정의하는 클래스입니다. 
public class UserManagement {
	
	public void join(Channel channel, String type, Map<String, Object> receivedData) throws Exception{
		
		String userId = (String) receivedData.get("userId");
		// key : Channel ID / value : userId 
		NettyChatServer.channelIdUserIdRepo.getChannelIdUserIdMap().put(channel.id(), userId);
		// key : userId / value : channel 정보
		NettyChatServer.userIdChannelIdRepo.getuserIdChannelIdMap().put(userId, channel);
				
		System.out.println("UserManagement:join:channel.id:"+channel.id()+" / userId:"+userId);
		System.out.println("UserManagement:join:channel.id:"+NettyChatServer.channelIdUserIdRepo.getChannelIdUserIdMap().keySet());
		System.out.println("UserManagement:join:channel.id:"+NettyChatServer.userIdChannelIdRepo.getuserIdChannelIdMap().keySet());
		
	}
	
	public void exit(Channel channel) {
		// 연결 종료되는 클라이언트의 channel id를 담고 있습니다. 
		ChannelId channelId = channel.id();
		Map<ChannelId, String> channelIdUserIdMap = NettyChatServer.channelIdUserIdRepo.getChannelIdUserIdMap();
		// 연결 종료되는 클라이언트의 userId를 담고 있습니다. 
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
