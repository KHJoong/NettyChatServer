import java.util.Map;

import io.netty.channel.Channel;

// 채팅의 방을 관리하기 위해 만들어진 클래스입니다.
// 방에 들어가고 나가고, 방 사람들에게 채팅을 보내는 부분을 담고 있습니다.
public class RoomManagement {

	UserManagement userManagement = new UserManagement();
	MessageManagement messageManagement = new MessageManagement();
	
//	public void create(Channel channel, String type, Map<String, Object> receivedData) throws Exception{
//		String userId = NettyChatServer.channelIdUserIdRepo.getChannelIdUserIdMap().get(channel.id());
//		
//		String roomId = (String)receivedData.get("roomId");
//		
//		NettyChatServer.roomIdUserIdRepo.getRoomIdUserIdMap().put(roomId, userId);
//		NettyChatServer.userIdRoomIdRepo.getUserIdRoomIdMap().put(userId, roomId);		
//		
//		System.out.println("RoomManageMent:create:"+channel+"/"+type+"/"+receivedData);
//	}
	
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
		// 요청을 보낸 클라이언트가 누구인지 담고 있는 변수입니다.
		String userId = (String) receivedData.get("userId");
		// 메시지를 보낼 방이 어디인지 담고 있는 변수입니다.
		String currentRoom = (String) receivedData.get("currentRoom");
		System.out.println("RoomManageMent:send:currentRoom:"+currentRoom);
		// 그 방에 몇  명이 있는지 담고 있는 변수입니다.
		int numOfMem = NettyChatServer.roomIdUserIdRepo.getRoomIdUserIdMap().getCollection(currentRoom).size();		
		// multivaluemap(key 하나에 value 여러개)의 value들을 변수에 담아두는 부분입니다.
		String[] toUser = (String[]) NettyChatServer.roomIdUserIdRepo.getRoomIdUserIdMap().getCollection(currentRoom).toArray(new String[numOfMem]);
		// 메시지를 보낸 클라이언트를 제외한 나머지 클라이언트에게 메시지를 보내기 위한 loop문 입니다.
		for(int i=0 ; i< numOfMem; i++) {
			System.out.println("RoomManageMent:send:toUser[]:"+toUser[i]);
			if(!toUser[i].equals(userId)) {
				// 메시지를 받을 클라이언트의 채널 정보를 담고 있는 변수입니다.
				Channel toChannel = NettyChatServer.userIdChannelIdRepo.getuserIdChannelIdMap().get(toUser[i]);
				System.out.println("RoomManageMent:send:toChannel:"+toChannel);
				messageManagement.sendMessage(toChannel, sendData);
			}
		}
	}
	
}
