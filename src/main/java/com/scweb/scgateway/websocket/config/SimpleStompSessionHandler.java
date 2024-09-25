package com.scweb.scgateway.websocket.config;

import com.scweb.scgateway.exception.StompGatewayException;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.Objects;

public class SimpleStompSessionHandler extends StompSessionHandlerAdapter {

    private final String serverUrl;
    private final SimpMessagingTemplate messagingTemplate;
    private StompSession stompSession;

    public SimpleStompSessionHandler(String serverUrl, SimpMessagingTemplate messagingTemplate) {
        this.serverUrl = serverUrl;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        this.stompSession = session;
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        session.send(headers, payload);
        throw new StompGatewayException("Notifications transport or message handling failures.", exception);
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        var destination = Objects.requireNonNull(headers.getDestination());
        var message = MessageBuilder.withPayload(payload)
                .setHeader(MessageHeaders.CONTENT_TYPE,
                        Objects.requireNonNull(headers.getContentType()).toString());
        messagingTemplate.convertAndSend(destination, message.build());
    }

    public void sendMessage(String destinationPath, Object payload) {
        if (stompSession == null)
            throw new StompGatewayException("STOMP Session is not available.");
        stompSession.send(destinationPath, payload);
    }

    public void sendMessage(Message<?> message) {
        if (stompSession == null)
            throw new StompGatewayException("STOMP Session is not available.");

        var accessor = StompHeaderAccessor.wrap(message);
        var destination = accessor.getDestination();
        if (destination == null)
            throw new StompGatewayException("STOMP Destination is not available.");

        var stompHeaders = new StompHeaders();
        stompHeaders.setDestination(destination);

        var contentType = accessor.getContentType();
        stompHeaders.setContentType(Objects.requireNonNullElse(contentType, MimeTypeUtils.APPLICATION_JSON));

        var contentLength = accessor.getContentLength();
        if (contentLength != null) stompHeaders.setContentLength(contentLength);

        stompSession.send(stompHeaders, message.getPayload());
    }

    public void subscribe(String destinationPath) {
        if (stompSession == null)
            throw new StompGatewayException("STOMP Session is not available.");
        stompSession.subscribe(destinationPath, this);
    }

    public void createClient() {
        WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.connectAsync(serverUrl, this);
    }

}
