package org.example.websocketnetty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Component
public class WebSocketNettyServer implements SmartLifecycle {

    @Value("${websocket.port:8081}")
    private int port;

    @Value("${websocket.path:/ws}")
    private String websocketPath;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    private final AtomicBoolean running = new AtomicBoolean(false);

    @Override
    public void start() {
        if (!running.compareAndSet(false, true)) {
            return;
        }
        // start() 内部会阻塞在 closeFuture().sync()，放到独立线程避免卡住容器生命周期
        Thread serverThread = new Thread(this::doStart, "websocket-netty-server");
        serverThread.setDaemon(true);
        serverThread.start();
    }

    @Override
    public void stop() {
        if (!running.compareAndSet(true, false)) {
            return;
        }
        destroy();
    }

    @Override
    public boolean isRunning() {
        return running.get();
    }

    /**
     * 相位越大越晚启动、越早停止，保证 Netty 在其它组件就绪后启动、在它们销毁前关闭。
     */
    @Override
    public int getPhase() {
        return Integer.MAX_VALUE;
    }

    private void doStart() {
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

    public void destroy() {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
        running.set(false);
        log.info("Netty WebSocket服务端已关闭");
    }
}