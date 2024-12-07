package com.kyeongseo.network;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class PostDetailActivity extends AppCompatActivity {

    private TextView tvPostDetailTitle;   // 글 제목
    private TextView tvPostDetailAuthor;  // 작성자
    private TextView tvPostDetailContent; // 글 내용
    private ImageView ivPostDetailImage;  // 이미지
    private Button btnChatWithAuthor;     // 채팅 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        tvPostDetailTitle = findViewById(R.id.tvPostDetailTitle);
        tvPostDetailAuthor = findViewById(R.id.tvPostDetailAuthor);
        tvPostDetailContent = findViewById(R.id.tvPostDetailContent);
        ivPostDetailImage = findViewById(R.id.ivPostDetailImage);
        btnChatWithAuthor = findViewById(R.id.btnChatWithAuthor);

        // 게시글 데이터 받기
        Intent intent = getIntent();

        String postTitle = intent.getStringExtra("postTitle");       // 제목
        String postAuthor = intent.getStringExtra("postAuthor");     // 작성자
        String postContent = intent.getStringExtra("postContent");   // 내용
        String postImageUri = intent.getStringExtra("postImageUri"); // 이미지 URI

        // 데이터 설정
        tvPostDetailTitle.setText(postTitle);
        tvPostDetailAuthor.setText("작성자: " + postAuthor);
        tvPostDetailContent.setText(postContent);

        // 채팅하기 버튼 클릭 이벤트
        btnChatWithAuthor.setOnClickListener(v -> {
            Intent chatIntent = new Intent(PostDetailActivity.this, ChatActivity.class);
            chatIntent.putExtra("chatPartner", postAuthor); // 작성자 이름 전달
            startActivity(chatIntent);
        });

        // 서버에서 이미지 가져오기
        new Thread(() -> fetchImageFromServer("/path/to/image.jpg")).start(); // 이미지 경로 지정
    }

    private void fetchImageFromServer(String imagePath) {
        try (Socket socket = new Socket("172.30.1.23", 9999)) {
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = new BufferedInputStream(socket.getInputStream());

            // 서버에 이미지 요청 보내기
            outputStream.write(("GET_IMAGE " + imagePath + "\n").getBytes());
            outputStream.flush();

            // 서버로부터 이미지 데이터 수신
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            // UI에 이미지 표시
            runOnUiThread(() -> ivPostDetailImage.setImageBitmap(bitmap));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}