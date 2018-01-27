import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


// 네티와 클라이언트의 연결 시작, 데이터 받아들이기, 연결 끝나는 순간을 정의해주는 클래스입니다.
// SimpleChannelInboundHandler를 상속합니다.
public class MsgHandler extends SimpleChannelInboundHandler<String>{
	// 클라이언트로부터 받아온 String 메시지를 HashMap에 담기 위해 사용합니다.
	ObjectMapper objectMapper = new ObjectMapper();
	//클라이언트가 보낸 메시지가 어떤 요청을 하고, 그 요청에 따라 메시지를 전송하는 역할을 합니다.
	MessageManagement messageManagement = new MessageManagement();
	// 네티와 클라이언트의 연결과 연결 해제 시의 행동을 정의합니다.
	UserManagement userManagement = new UserManagement();
	
	@Override
	public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception{
		
		System.out.println("MsgHandler:channelRead0:msg:"+msg);		
		
		Map<String, Object> sendData = new HashMap<>();

		Channel channel = ctx.channel();

		Map<String, Object> receivedData;
		try {
			// 클라이언트로부터 들어온 msg 변수를 hashmap에 json 형식으로 담습니다.
			// sendData는 나중에 요청에 따라 보낼 데이터를 따로 생성할 필요가 있을때를 위해 생성해둡니다.
			// receivedData는 클라이언트의 요청을 보관하는 변수입니다. 변화 없이 유지시킵니다.
			sendData = receivedData = objectMapper.readValue(msg, new TypeReference<Map<String, Object>>() {
			});

		} catch (JsonParseException | JsonMappingException e) {

			e.printStackTrace();
			return;

		}
		System.out.println("MsgHandler:channelRead0:channel:"+channel);
		System.out.println("MsgHandler:channelRead0:receivedData:"+receivedData);
		System.out.println("MsgHandler:channelRead0:sendData:"+sendData);
		// 네티가 받은 데이터를 메시지 관리하는 클래스로 전달해 요청에 맞게 처리합니다.
		messageManagement.execute(channel, receivedData, sendData);
		
		
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// 유저와 네티의 연결이 해제되었을 때 행동입니다.
		userManagement.exit(ctx.channel());
		
		ctx.close();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
	}
	
}
