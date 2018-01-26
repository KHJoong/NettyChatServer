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
	
	MessageManagement messageManagement;
	UserManagement userManagement;
	
	
	@Override
	public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception{
		
		Map<String, Object> result = new HashMap<>();
		
		Channel channel = ctx.channel();
		
		Map<String, Object> data = null;
		try {
			data = objectMapper.readValue(msg, new TypeReference<Map<String, Object>>() {
			});
		} catch (JsonParseException | JsonMappingException e) {
			e.printStackTrace();
		}
		
		messageManagement.execute(channel, data, result);
		
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
