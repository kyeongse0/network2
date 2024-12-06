package com.kyeongseo.network;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PostDetailActivity extends AppCompatActivity {

    private TextView tvPostDetailContent;
    private ImageView ivPostDetailImage;
    private Button btnChatWithAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        tvPostDetailContent = findViewById(R.id.tvPostDetailContent);
        ivPostDetailImage = findViewById(R.id.ivPostDetailImage);
        btnChatWithAuthor = findViewById(R.id.btnChatWithAuthor);

        // 게시글 데이터 받기
        Intent intent = getIntent();
        String postContent = intent.getStringExtra("postContent");
        String postImageUri = intent.getStringExtra("postImageUri"); // 이미지 URI 전달받음
        String authorName = intent.getStringExtra("authorName"); // 작성자 정보

        tvPostDetailContent.setText(postContent);

        if (postImageUri != null) {
            ivPostDetailImage.setImageURI(Uri.parse(postImageUri)); // 이미지 설정
        }

        // 채팅하기 버튼 클릭 이벤트
        btnChatWithAuthor.setOnClickListener(v -> {
            Intent chatIntent = new Intent(PostDetailActivity.this, ChatActivity.class);
            chatIntent.putExtra("chatPartner", authorName); // 작성자 이름 전달
            startActivity(chatIntent);
        });
    }
}