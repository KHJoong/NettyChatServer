import java.util.Map;
import java.util.UUID;

import io.netty.channel.Channel;

public class RoomManagement {

	UserManagement userManagement;
	
	UserIdChannelIdRepo userIdChannelIdRepo;
	ChannelIdUserIdRepo channelIdUserIdRepo;
	UserIdRoomIdRepo userIdRoomIdRepo;
	RoomIdUserIdRepo roomIdUserIdRepo;
	
	public void create(Channel channel, String type, Map<String, Object> receivedData) throws Exception{
		String userId = channelIdUserIdRepo.getChannelIdUserIdMap().get(channel.id());
		
		String roomId = UUID.randomUUID().toString();
		
		roomIdUserIdRepo.getRoomIdUserIdMap().put(roomId, userId);
		userIdRoomIdRepo.getUserIdRoomIdMap().put(userId, roomId);		
	}
	
	public void enter(Channel channel, String type, Map<String, Object> receivedData, Map<String, Object> sendData) throws Exception{
		String userId = channelIdUserIdRepo.getChannelIdUserIdMap().get(channel.id());
		
		String roomId = (String)receivedData.get("roomId");
		
		roomIdUserIdRepo.getRoomIdUserIdMap().put(roomId, userId);
		userIdRoomIdRepo.getUserIdRoomIdMap().put(userId, roomId);
	}
	
	
	public void exit(Channel channel, String type, Map<String, Object> sendData) throws Exception{
		String userId = channelIdUserIdRepo.getChannelIdUserIdMap().get(channel.id());
		
		String roomId = userIdRoomIdRepo.getUserIdRoomIdMap().get(userId);
		
		roomIdUserIdRepo.getRoomIdUserIdMap().remove(roomId, userId);
		userIdRoomIdRepo.getUserIdRoomIdMap().remove(userId);
	}
	
	
	public void send(Channel channel, String type, Map<String, Object> receivedData, Map<String, Object> sendData) throws Exception{
		String userId = channelIdUserIdRepo.getChannelIdUserIdMap().get(channel.id());
		
		sendData.put("type", type);
		sendData.put("userId", userId);
		sendData.put("content", receivedData.get("content"));
		
		String roomId = userIdRoomIdRepo.getUserIdRoomIdMap().get(userId);
		
		roomIdUserIdRepo.getRoomIdUserIdMap().getCollection(roomId).parallelStream().forEach(otherUserId ->{
			Channel otherChannel = userIdChannelIdRepo.getuserIdChannelIdMap().get(otherUserId);
			
			if(!otherChannel.isActive()) {
				userManagement.exit(otherChannel);
				return;
			}
		});
	}
	
}
