package com.p2psample.messaging.events;

/**
 * Created by Purushottam Gupta on 2/6/2018.
 */

public class MessageEvent {
    private String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
