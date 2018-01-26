import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class NettyChatServer {

	public static void main(String[] args) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap serverBootStrap = new ServerBootstrap();
			serverBootStrap.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>() {
				
				
				@Override
				public void initChannel(SocketChannel ch) {
					ChannelPipeline serverChannelPipeline = ch.pipeline();
					
					serverChannelPipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
					serverChannelPipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
					// ���ӵ� Ŭ���̾�Ʈ�κ��� ���ŵ� �����͸� ó���� �ڵ鷯�� �����ϴ� �κ�
					serverChannelPipeline.addLast(new MsgHandler());
				}
			});
			
			// ��Ʈ��Ʈ�� Ŭ������ bind method�� ������ ��Ʈ�� ������
			ChannelFuture serverChannelFuture = serverBootStrap.bind(8888).sync();
			
			serverChannelFuture.channel().closeFuture().sync();
			
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
		
	}
	
}


