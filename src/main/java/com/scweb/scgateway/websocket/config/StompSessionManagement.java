package com.scweb.scgateway.websocket.config;

import com.scweb.scgateway.exception.StompGatewayException;
import com.scweb.scgateway.websocket.annotation.StompRoute;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class StompSessionManagement {

    private final SimpMessagingTemplate messagingTemplate;
    private final ApplicationContext applicationContext;
    private final Map<String, SimpleStompSessionHandler> handlers = new HashMap<>();

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        var classes = applicationContext.getBeansWithAnnotation(StompRoute.class).values();
        classes.stream()
                .map(o -> o.getClass().getAnnotation(StompRoute.class))
                .forEach(route -> {
                    var handler = new SimpleStompSessionHandler(route.url(), messagingTemplate);
                    handler.createClient();
                    handlers.put(route.id(), handler);
                });
    }

    public void sendMessage(String serverId, String destinationPath)
            throws IllegalArgumentException, StompGatewayException {
        sendMessage(serverId, destinationPath, null);
    }

    public void sendMessage(String serverId, String destinationPath, @Nullable Object payload)
            throws IllegalArgumentException, StompGatewayException {
        validateServerId(serverId);
        Assert.notNull(destinationPath, "destinationPath must not be null.");

        handlers.get(serverId).sendMessage(destinationPath, payload);
    }

    public void sendMessage(String serverId, Message<?> message)
            throws IllegalArgumentException, StompGatewayException {
        validateServerId(serverId);
        Assert.notNull(message, "message must not be null.");

        handlers.get(serverId).sendMessage(message);
    }

    public void subscribe(String serverId, String destinationPath)
            throws IllegalArgumentException, StompGatewayException {
        validateServerId(serverId);
        Assert.notNull(destinationPath, "destinationPath must not be null.");

        handlers.get(serverId).subscribe(destinationPath);
    }

    private void validateServerId(String serverId) throws IllegalArgumentException, StompGatewayException {
        Assert.notNull(serverId, "serverId must not be null.");
        if (!handlers.containsKey(serverId))
            throw new StompGatewayException("serverId: " + serverId + " not found.");
    }

}
