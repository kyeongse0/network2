package com.kyeongseo.network;

import java.io.Serializable;

public class Chat implements Serializable {
    private String name; // 채팅 상대방 이름
    private String lastMessage; // 마지막 메시지

    public Chat(String name, String lastMessage) {
        this.name = name;
        this.lastMessage = lastMessage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}