import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MsgHandler extends SimpleChannelInboundHandler<String>{
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	MessageManagement messageManagement = new MessageManagement();
	UserManagement userManagement = new UserManagement();
	
	@Override
	public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception{
		
		System.out.println("MsgHandler:channelRead0:msg:"+msg);		
		
		Map<String, Object> sendData = new HashMap<>();

		Channel channel = ctx.channel();

		Map<String, Object> receivedData;
		try {

			sendData = receivedData = objectMapper.readValue(msg, new TypeReference<Map<String, Object>>() {
			});

		} catch (JsonParseException | JsonMappingException e) {

			e.printStackTrace();
			return;

		}
		System.out.println("MsgHandler:channelRead0:channel:"+channel);
		System.out.println("MsgHandler:channelRead0:receivedData:"+receivedData);
		System.out.println("MsgHandler:channelRead0:sendData:"+sendData);
		messageManagement.execute(channel, receivedData, sendData);
		
		
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		
		userManagement.exit(ctx.channel());
		
		ctx.close();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
	}
	
}
