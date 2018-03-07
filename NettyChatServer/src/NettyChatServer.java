import java.util.List;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class NettyChatServer {

	static ChannelIdUserIdRepo channelIdUserIdRepo = new ChannelIdUserIdRepo();
	static UserIdChannelIdRepo userIdChannelIdRepo = new UserIdChannelIdRepo();
	static RoomIdUserIdRepo roomIdUserIdRepo = new RoomIdUserIdRepo();
	static UserIdRoomIdRepo userIdRoomIdRepo = new UserIdRoomIdRepo();
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("Netty Chat Server Start");
		
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap serverBootStrap = new ServerBootstrap();
			serverBootStrap.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>() {
				
				@Override
				public void initChannel(SocketChannel ch) {
					
					System.out.println("NettyChatServer:initChannel:"+ch);
					
					ChannelPipeline serverChannelPipeline = ch.pipeline();
					
					serverChannelPipeline.addLast(new ByteToMessageDecoder() {
			            @Override
			            public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
			                out.add(in.readBytes(in.readableBytes()));
			            }
			        });
					
					serverChannelPipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
					serverChannelPipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
					serverChannelPipeline.addLast(new MsgHandler());
				}
			});
			
			ChannelFuture serverChannelFuture = serverBootStrap.bind(8888).sync();
			
			serverChannelFuture.channel().closeFuture().sync();
			
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
		
	}
	
}


