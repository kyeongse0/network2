package com.kyeongseo.network;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView chatRecyclerView; // 메시지를 표시하는 RecyclerView
    private EditText etMessageInput; // 메시지 입력 필드
    private Button btnSendMessage; // 메시지 전송 버튼

    private ArrayList<ChatMessage> chatMessages; // 채팅 메시지 리스트
    private ChatAdapter chatAdapter; // RecyclerView 어댑터

    private String chatPartner; // 채팅 상대 이름
    private String clientId; // 현재 사용자 ID

    private Socket socket; // 소켓 연결 객체
    private PrintWriter output; // 서버로 메시지 전송
    private BufferedReader input; // 서버로부터 메시지 수신

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        etMessageInput = findViewById(R.id.etMessageInput);
        btnSendMessage = findViewById(R.id.btnSendMessage);

        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(this, chatMessages);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setAdapter(chatAdapter);

        // 채팅 상대 정보 및 사용자 ID 가져오기
        chatPartner = getIntent().getStringExtra("chatPartner");
        clientId = getIntent().getStringExtra("clientId");

        connectToServer();

        btnSendMessage.setOnClickListener(v -> sendMessage());
    }

    private void connectToServer() {
        new Thread(() -> {
            try {
                socket = new Socket("172.30.1.23", 9999); // 서버 IP와 포트 설정
                output = new PrintWriter(socket.getOutputStream(), true);
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                output.println(clientId); // 사용자 ID 전송

                String message;
                while ((message = input.readLine()) != null) {
                    String finalMessage = message;
                    runOnUiThread(() -> {
                        chatMessages.add(new ChatMessage(finalMessage, false)); // 수신한 메시지 추가
                        chatAdapter.notifyDataSetChanged();
                        chatRecyclerView.smoothScrollToPosition(chatMessages.size() - 1); // 최신 메시지로 스크롤
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void sendMessage() {
        String messageText = etMessageInput.getText().toString().trim();

        if (!messageText.isEmpty() && output != null) {
            new Thread(() -> {
                output.println("CHAT " + chatPartner + " " + messageText); // 서버로 메시지 전송
            }).start();

            runOnUiThread(() -> {
                chatMessages.add(new ChatMessage(messageText, true)); // 내가 보낸 메시지 추가
                chatAdapter.notifyDataSetChanged();
                etMessageInput.setText(""); // 입력 필드 초기화
                chatRecyclerView.smoothScrollToPosition(chatMessages.size() - 1); // 최신 메시지로 스크롤
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close(); // 액티비티 종료 시 소켓 닫기
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}