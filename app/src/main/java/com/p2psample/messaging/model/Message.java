package com.p2psample.messaging.model;

/**
 * Created by Purushottam Gupta on 2/6/2018.
 */

public class Message {
    private String text;
    private int type;

    public Message(String text, int type) {
        this.text = text;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }
}
