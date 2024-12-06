package com.kyeongseo.network;
import java.util.ArrayList;
import java.util.List;

public class ChatRoomManager {
    private static List<ChatRoom> chatRooms = new ArrayList<>();

    // 채팅방 목록 가져오기
    public static List<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    // 채팅방 추가 또는 업데이트
    public static void addOrUpdateChatRoom(String participantName, String lastMessage) {
        for (ChatRoom chatRoom : chatRooms) {
            if (chatRoom.getParticipantName().equals(participantName)) {
                // 기존 채팅방 업데이트
                chatRoom.setLastMessage(lastMessage);
                chatRoom.setTimestamp(System.currentTimeMillis());
                return;
            }
        }
        // 새로운 채팅방 추가
        chatRooms.add(new ChatRoom(participantName, lastMessage, System.currentTimeMillis()));
    }
}