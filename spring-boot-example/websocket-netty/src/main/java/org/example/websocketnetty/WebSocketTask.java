package org.example.websocketnetty;

import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class WebSocketTask {

    @Scheduled(cron = "0/5 * * * * ?")
    public void sendMessageToClient() {
        String message = "这是来自服务端的消息：" + DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now());
        WebSocketServerHandler.sendToAllClient(message);
    }
}
