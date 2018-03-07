import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import io.netty.channel.Channel;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RoomManagement {

	UserManagement userManagement = new UserManagement();
	MessageManagement messageManagement = new MessageManagement();
	JedisPool jPool = new JedisPool("localhost", 6379);
	
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
		String userId = (String) receivedData.get("userId");
		String currentRoom = (String) receivedData.get("currentRoom");
		System.out.println("RoomManageMent:send:currentRoom:"+currentRoom);
		
		Jedis j = jPool.getResource();
		double time = System.currentTimeMillis();
		String jsonData = new ObjectMapper().writeValueAsString(sendData);
		j.zadd(currentRoom, time, jsonData);
		System.out.println(j.zrange(currentRoom, 0, -1));
		
		int numOfMem = NettyChatServer.roomIdUserIdRepo.getRoomIdUserIdMap().getCollection(currentRoom).size();		
		String[] toUser = (String[]) NettyChatServer.roomIdUserIdRepo.getRoomIdUserIdMap().getCollection(currentRoom).toArray(new String[numOfMem]);
		for(int i=0 ; i< numOfMem; i++) {
			System.out.println("RoomManageMent:send:toUser[]:"+toUser[i]);
			if(!toUser[i].equals(userId)) {
				Channel toChannel = NettyChatServer.userIdChannelIdRepo.getuserIdChannelIdMap().get(toUser[i]);
				System.out.println("RoomManageMent:send:toChannel:"+toChannel);
				messageManagement.sendMessage(toChannel, sendData);
			}
		}
	}
	
}
