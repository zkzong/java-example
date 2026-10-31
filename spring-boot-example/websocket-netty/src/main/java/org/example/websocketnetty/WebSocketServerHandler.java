package org.example.websocketnetty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ChannelHandler.Sharable
public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static final ConcurrentHashMap<String, ChannelHandlerContext> channelMap = new ConcurrentHashMap<>();

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        String channelId = ctx.channel().id().asLongText();
        channelMap.put(channelId, ctx);
        log.info("客户端连接建立：{}，当前在线数：{}", channelId, channelMap.size());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        String channelId = ctx.channel().id().asLongText();
        channelMap.remove(channelId);
        log.info("客户端连接断开：{}，当前在线数：{}", channelId, channelMap.size());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        String channelId = ctx.channel().id().asLongText();
        log.info("收到来自客户端：{}的消息：{}", channelId, msg.text());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                log.info("客户端读空闲超时，关闭连接：{}", ctx.channel().id().asLongText());
                ctx.channel().close();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("WebSocket异常：{}", ctx.channel().id().asLongText(), cause);
        ctx.channel().close();
    }

    public static void sendToAllClient(String message) {
        channelMap.forEach((channelId, ctx) -> {
            try {
                ctx.channel().writeAndFlush(new TextWebSocketFrame(message));
            } catch (Exception e) {
                log.error("发送消息失败：{}", channelId, e);
            }
        });
    }
}
