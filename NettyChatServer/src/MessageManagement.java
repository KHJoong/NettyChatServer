import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import io.netty.channel.Channel;

// Ŭ���̾�Ʈ�� ���� ��û�� � ó���� �䱸�ϴ��� �м��ϰ� �� ��û�� �°� �������ϴ� ������ �մϴ�.
public class MessageManagement {

	ObjectMapper objectMapper = new ObjectMapper();
	
	UserManagement userManagement;
	RoomManagement roomManagement;
	
	// Ŭ���̾�Ʈ�� ���� �޽����� ��û�� ���� ������ ����� �����صδ� �Լ��Դϴ�.
	public void execute(Channel channel, Map<String, Object> receivedData, Map<String, Object> sendData) throws Exception {
		
		userManagement = new UserManagement();
		roomManagement = new RoomManagement();
		
		System.out.println("MessageManagement:execute:channel:"+channel);
		System.out.println("MessageManagement:execute:receivedData"+receivedData);
		System.out.println("MessageManagement:execute:sendData:"+sendData);
		
		String type = (String) receivedData.get("type");
		
		
		switch (type) {
			// ��Ƽ�� Ŭ���̾�Ʈ�� ����Ǵ� ���¸� �����մϴ�.
			case "join" :
				userManagement.join(channel, type, receivedData);
				break;
//			case "create_room" :
//				roomManagement.create(channel, type, sendData);
//				break;
			// Ŭ���̾�Ʈ�� �濡 ������ �ǹ��մϴ�.
			case "enter_room" :				
				roomManagement.enter(channel, type, receivedData, sendData);
				break;
			// Ŭ���̾�Ʈ�� ���� �������� �ǹ��մϴ�.
			case "exit_room" :
				roomManagement.exit(channel, type, sendData);
				break;
			// Ŭ���̾�Ʈ�� �濡 �ִ� �ٸ� Ŭ���̾�Ʈ���� �޽����� �������� �ǹ��մϴ�.
			case "send_room" :
				roomManagement.send(channel, type, receivedData, sendData);
				break;
		}
		
	}
	
	// �ٸ� Ŭ���̾�Ʈ���� �޽����� �����ϱ����� ���˴ϴ�.
	void sendMessage(Channel channel, Map<String, Object> sendData) throws Exception{
		channel.writeAndFlush(objectMapper.writeValueAsString(sendData)+System.lineSeparator());
	}
	
}
