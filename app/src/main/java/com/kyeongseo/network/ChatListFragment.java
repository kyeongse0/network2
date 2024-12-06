package com.kyeongseo.network;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ChatListFragment extends Fragment {

    private ArrayAdapter<String> adapter;
    private ArrayList<String> chatRooms = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);

        ListView chatRoomListView = view.findViewById(R.id.chatRoomListView);

        // 채팅방 리스트 초기화
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, chatRooms);
        chatRoomListView.setAdapter(adapter);

        // 채팅방 클릭 시 채팅 화면으로 이동
        chatRoomListView.setOnItemClickListener((parent, view1, position, id) -> {
            Intent intent = new Intent(getContext(), ChatActivity.class);
            intent.putExtra("chatRoomName", chatRooms.get(position));
            startActivity(intent);
        });

        return view;
    }

    // 서버에서 새로운 채팅방 추가 시 호출 (예시)
    public void addChatRoom(String chatRoomName) {
        if (!chatRooms.contains(chatRoomName)) {
            chatRooms.add(chatRoomName);
            adapter.notifyDataSetChanged();
        }
    }
}