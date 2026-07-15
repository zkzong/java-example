package org.example.websocketnetty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WebSocketNettyServer {

    @Value("${websocket.port:8081}")
    private int port;

    @Value("${websocket.path:/ws}")
    private String websocketPath;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public void start() {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new WebSocketServerInitializer(websocketPath));

            ChannelFuture future = bootstrap.bind(port).sync();
            log.info("Netty WebSocket服务端启动成功，端口：{}，路径：{}", port, websocketPath);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("Netty WebSocket服务端启动异常", e);
            Thread.currentThread().interrupt();
        } finally {
            destroy();
        }
    }

    @PreDestroy
    public void destroy() {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
        log.info("Netty WebSocket服务端已关闭");
    }
}
