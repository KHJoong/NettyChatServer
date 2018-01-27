import java.util.Map;

import io.netty.channel.Channel;

public class RoomManagement {

	UserManagement userManagement = new UserManagement();
	MessageManagement messageManagement = new MessageManagement();
	
//	UserIdChannelIdRepo userIdChannelIdRepo;
//	ChannelIdUserIdRepo channelIdUserIdRepo;
//	UserIdRoomIdRepo userIdRoomIdRepo;
//	RoomIdUserIdRepo roomIdUserIdRepo;
	
	public void create(Channel channel, String type, Map<String, Object> receivedData) throws Exception{
		String userId = NettyChatServer.channelIdUserIdRepo.getChannelIdUserIdMap().get(channel.id());
		
		String roomId = (String)receivedData.get("roomId");
		
		NettyChatServer.roomIdUserIdRepo.getRoomIdUserIdMap().put(roomId, userId);
		NettyChatServer.userIdRoomIdRepo.getUserIdRoomIdMap().put(userId, roomId);		
		
		System.out.println("RoomManageMent:create:"+channel+"/"+type+"/"+receivedData);
	}
	
	public void enter(Channel channel, String type, Map<String, Object> receivedData, Map<String, Object> sendData) throws Exception{
		try {
			System.out.println("RoomManageMent:enter:"+channel);
			System.out.println("RoomManageMent:enter:"+type);
			System.out.println("RoomManageMent:enter:"+receivedData);
			System.out.println("RoomManageMent:enter:"+sendData);
		} catch(NullPointerException e) {
			System.out.println("null");
		}
		
		String userId = (String) receivedData.get("userId");
		String roomId = (String) receivedData.get("roomId");
		
		System.out.println("RoomManageMent:enter:userId:"+userId);
		System.out.println("RoomManageMent:enter:roomId:"+roomId);
		
		if(NettyChatServer.roomIdUserIdRepo.getRoomIdUserIdMap().getCollection(roomId)==null || !NettyChatServer.roomIdUserIdRepo.getRoomIdUserIdMap().getCollection(roomId).contains(userId)) {			
			NettyChatServer.roomIdUserIdRepo.getRoomIdUserIdMap().put(roomId, userId);
			NettyChatServer.userIdRoomIdRepo.getUserIdRoomIdMap().put(userId, roomId);
			System.out.println("RoomManageMent:enter:RoomUser:"+NettyChatServer.roomIdUserIdRepo.getRoomIdUserIdMap().getCollection(roomId));
			System.out.println("RoomManageMent:enter:UserRoom:"+NettyChatServer.userIdRoomIdRepo.getUserIdRoomIdMap().keySet());
		}
		
	}
	
	
	public void exit(Channel channel, String type, Map<String, Object> sendData) throws Exception{
		String userId = NettyChatServer.channelIdUserIdRepo.getChannelIdUserIdMap().get(channel.id());
		
		String roomId = NettyChatServer.userIdRoomIdRepo.getUserIdRoomIdMap().get(userId);
		
		NettyChatServer.roomIdUserIdRepo.getRoomIdUserIdMap().remove(roomId, userId);
		NettyChatServer.userIdRoomIdRepo.getUserIdRoomIdMap().remove(userId);
		
		System.out.println("RoomManageMent:exit:"+channel+"/"+type+"/"+sendData);
	}
	
	
	public void send(Channel channel, String type, Map<String, Object> receivedData, Map<String, Object> sendData) throws Exception{
		
		String userId = (String) receivedData.get("userId");
		String currentRoom = (String) receivedData.get("currentRoom");
		
		Channel toChannel = NettyChatServer.userIdChannelIdRepo.getuserIdChannelIdMap().get(userId);
		
		int numOfMem = NettyChatServer.roomIdUserIdRepo.getRoomIdUserIdMap().getCollection(currentRoom).size();
		
		String[] toUser = (String[]) NettyChatServer.roomIdUserIdRepo.getRoomIdUserIdMap().getCollection(currentRoom).toArray(new String[numOfMem]);
		for(int i=0 ; i< numOfMem; i++) {
			
		}
		
//		String userId = NettyChatServer.channelIdUserIdRepo.getChannelIdUserIdMap().get(channel.id());
//		
//		sendData.put("type", type);
//		sendData.put("userId", userId);
//		sendData.put("content", receivedData.get("content"));
//		
//		String roomId = NettyChatServer.userIdRoomIdRepo.getUserIdRoomIdMap().get(userId);
		
//		NettyChatServer.roomIdUserIdRepo.getRoomIdUserIdMap().getCollection(roomId).parallelStream().forEach(otherUserId ->{
//			Channel otherChannel = NettyChatServer.userIdChannelIdRepo.getuserIdChannelIdMap().get(otherUserId);
//			
//			if(!otherChannel.isActive()) {
//				userManagement.exit(otherChannel);
//				return;
//			}
//			
//			try {
//				messageManagement.sendMessage(channel, sendData);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		});
	}
	
}
