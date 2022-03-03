package com.u1.gocashm.util.model;

import java.io.Serializable;

public class MessageInfo implements Serializable {
    public static final int TYPE_SEND = 1; // 发送
    public static final int TYPE_RECEIVE = 2; // 接收

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    //    @SerializedName("id")
    private String messageNumber;
    private String messageContent;
    private int messageType;
    private String messageTime;

    public String getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(String messageNumber) {
        this.messageNumber = messageNumber;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }
}
