import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import io.netty.channel.Channel;

// 클라이언트가 보낸 요청이 어떤 처리를 요구하는지 분석하고 그 요청에 맞게 재전송하는 역할을 합니다.
public class MessageManagement {

	ObjectMapper objectMapper = new ObjectMapper();
	
	UserManagement userManagement;
	RoomManagement roomManagement;
	
	// 클라이언트가 보낸 메시지의 요청에 따라 수행할 방법을 구분해두는 함수입니다.
	public void execute(Channel channel, Map<String, Object> receivedData, Map<String, Object> sendData) throws Exception {
		
		userManagement = new UserManagement();
		roomManagement = new RoomManagement();
		
		System.out.println("MessageManagement:execute:channel:"+channel);
		System.out.println("MessageManagement:execute:receivedData"+receivedData);
		System.out.println("MessageManagement:execute:sendData:"+sendData);
		
		String type = (String) receivedData.get("type");
		
		
		switch (type) {
			// 네티와 클라이언트가 연결되는 상태를 유지합니다.
			case "join" :
				userManagement.join(channel, type, receivedData);
				break;
//			case "create_room" :
//				roomManagement.create(channel, type, sendData);
//				break;
			// 클라이언트가 방에 들어갔음을 의미합니다.
			case "enter_room" :				
				roomManagement.enter(channel, type, receivedData, sendData);
				break;
			// 클라이언트가 방을 나갔음을 의미합니다.
			case "exit_room" :
				roomManagement.exit(channel, type, sendData);
				break;
			// 클라이언트가 방에 있는 다른 클라이언트에게 메시지를 전송함을 의미합니다.
			case "send_room" :
				roomManagement.send(channel, type, receivedData, sendData);
				break;
		}
		
	}
	
	// 다른 클라이언트에게 메시지를 전송하기위해 사용됩니다.
	void sendMessage(Channel channel, Map<String, Object> sendData) throws Exception{
		channel.writeAndFlush(objectMapper.writeValueAsString(sendData)+System.lineSeparator());
	}
	
}
