package com.scweb.scgateway.exception;

public class StompGatewayException extends RuntimeException{

    public StompGatewayException(String message) {
        super(message);
    }

    public StompGatewayException(String message, Throwable cause) {
        super(message, cause);
    }

}
