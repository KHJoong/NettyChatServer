import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import io.netty.channel.Channel;

public class MessageManagement {

	ObjectMapper objectMapper = new ObjectMapper();
	
	UserManagement userManagement;
	RoomManagement roomManagement;
	
	public void execute(Channel channel, Map<String, Object> receivedData, Map<String, Object> sendData) throws Exception {
		
		userManagement = new UserManagement();
		roomManagement = new RoomManagement();
		
		System.out.println("MessageManagement:execute:channel:"+channel);
		System.out.println("MessageManagement:execute:receivedData"+receivedData);
		System.out.println("MessageManagement:execute:sendData:"+sendData);
		
		String type = (String) receivedData.get("type");
		
		
		switch (type) {
			case "join" :
				userManagement.join(channel, type, receivedData);
				break;
//			case "create_room" :
//				roomManagement.create(channel, type, sendData);
//				break;
			case "enter_room" :				
				roomManagement.enter(channel, type, receivedData, sendData);
				break;
			case "exit_room" :
				roomManagement.exit(channel, type, sendData);
				break;
			case "send_room" :
				roomManagement.send(channel, type, receivedData, sendData);
				break;
		}
		
	}
	
	void sendMessage(Channel channel, Map<String, Object> sendData) throws Exception{
		channel.writeAndFlush(objectMapper.writeValueAsString(sendData)+System.lineSeparator());
	}
	
}
