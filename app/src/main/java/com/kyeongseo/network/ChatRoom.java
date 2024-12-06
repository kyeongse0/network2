package com.kyeongseo.network;
import java.io.Serializable;

public class ChatRoom implements Serializable {
    private String participantName; // 채팅 상대방 이름
    private String lastMessage; // 마지막 메시지
    private long timestamp; // 마지막 메시지 시간

    public ChatRoom(String participantName, String lastMessage, long timestamp) {
        this.participantName = participantName;
        this.lastMessage = lastMessage;
        this.timestamp = timestamp;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}