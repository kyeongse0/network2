package com.kyeongseo.network;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class BoardFragment extends Fragment {

    private RecyclerView postRecyclerView;
    private PostAdapter adapter; // RecyclerView 어댑터
    private ArrayList<Post> posts = new ArrayList<>(); // 게시글 데이터 리스트

    private PrintWriter output; // 서버로 메시지 전송
    private BufferedReader input; // 서버로부터 메시지 수신
    private Socket socket; // 소켓 연결 객체

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board, container, false);

        postRecyclerView = view.findViewById(R.id.postRecyclerView);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new PostAdapter(getContext(), posts, post -> {
            // 게시글 클릭 시 상세보기로 이동
            Intent intent = new Intent(getContext(), PostDetailActivity.class);
            intent.putExtra("postContent", post.getContent());
            intent.putExtra("postImageUri", post.getImageUri());
            startActivity(intent);
        });

        postRecyclerView.setAdapter(adapter);

        Button btnCreatePost = view.findViewById(R.id.btnCreatePost);

        btnCreatePost.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CreatePostActivity.class);
            startActivity(intent); // CreatePostActivity로 이동
        });

        connectToServer(); // 서버와 연결 및 실시간 업데이트 시작

        return view;
    }

    private void connectToServer() {
        new Thread(() -> { // 네트워크 작업을 별도 스레드에서 실행
            try {
                if (socket == null || socket.isClosed()) { // 소켓이 없거나 닫혀 있으면 새로 생성
                    socket = new Socket("172.30.1.23", 9999); // 서버 IP와 포트 설정
                    output = new PrintWriter(socket.getOutputStream(), true);
                    input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    String message;
                    while ((message = input.readLine()) != null) {
                        if (message.startsWith("새 게시글: ")) { // 새 게시글 알림 처리
                            String[] parts = message.substring(7).split("::"); // "내용::이미지 URI" 형식으로 분리
                            String content = parts[0];
                            String imageUri = parts.length > 1 ? parts[1] : null;

                            getActivity().runOnUiThread(() -> {
                                posts.add(new Post(content, imageUri)); // 게시글 추가
                                adapter.notifyDataSetChanged(); // UI 업데이트
                            });
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close(); // Fragment 종료 시 소켓 닫기
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}