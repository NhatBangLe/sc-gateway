package com.scweb.scgateway.websocket.chat;

import com.scweb.scgateway.websocket.config.StompSessionManagement;
import com.scweb.scgateway.websocket.annotation.StompRoute;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Hidden
@Controller
@RequiredArgsConstructor
@StompRoute(id = "chat-service", url = "ws://localhost:8080/chat")
public class ChatController {

    private final String serverId = "chat-service";
    private final StompSessionManagement sessionManagement;

    @MessageMapping("/conversation/{conversationId}")
    public void transferMessage(Message<?> message) {
        sessionManagement.sendMessage(serverId, message);
    }

//    @MessageMapping("/conversation/{conversationId}")
//    public void transferMessage(@DestinationVariable Long conversationId, @Payload Object message) {
//        sessionManagement.sendMessage(serverId, "/sc/conversation/" + conversationId, message);
//    }

    @MessageMapping("/user/{userId}/connected")
    public void handleUserHasConnected(@DestinationVariable String userId) {
        sessionManagement.sendMessage(serverId, "/sc/user/" + userId + "/connected");
    }

    @MessageMapping("/user/{userId}/disconnected")
    public void handleUserHasDisconnected(@DestinationVariable String userId) {
        sessionManagement.sendMessage(serverId, "/sc/user/" + userId + "/disconnected");
    }

    @SubscribeMapping("/conversation/{conversationId}")
    public void subscribeConversation(@DestinationVariable Long conversationId) {
        sessionManagement.subscribe(serverId, "/topic/conversation/" + conversationId);
    }

    @SubscribeMapping("/user/{userId}")
    public void subscribeNotification(@DestinationVariable String userId) {
        sessionManagement.subscribe(serverId, "/topic/user/" + userId);
    }

}
