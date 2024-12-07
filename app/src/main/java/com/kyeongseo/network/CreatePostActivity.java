package com.kyeongseo.network;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class CreatePostActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText etPostContent; // 글 내용 입력 필드


    private EditText etPostTitle; // 제목 입력 필드
    private EditText etPostAuthor; // 작성자 입력 필드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        etPostTitle = findViewById(R.id.etPostTitle); // 제목 입력 필드 연결
        etPostAuthor = findViewById(R.id.etPostAuthor); // 작성자 입력 필드 연결
        etPostContent = findViewById(R.id.etPostContent);



        ImageButton btnSubmitPost = findViewById(R.id.btnSubmitPost);


        btnSubmitPost.setOnClickListener(v -> submitPost());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void submitPost() {
        String postTitle = etPostTitle.getText().toString().trim();
        String postAuthor = etPostAuthor.getText().toString().trim();
        String postContent = etPostContent.getText().toString().trim();

        if (postTitle.isEmpty() || postAuthor.isEmpty() || postContent.isEmpty()) {
            // 제목, 작성자 또는 내용이 비어 있을 경우 처리
            Toast.makeText(this, "제목, 작성자, 내용을 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        Data inputData = new Data.Builder()
                .putString("postTitle", postTitle)
                .putString("postAuthor", postAuthor)
                .putString("postContent", postContent)

                .build();

        OneTimeWorkRequest postRequest = new OneTimeWorkRequest.Builder(PostWorker.class)
                .setInputData(inputData)
                .build();

        WorkManager.getInstance(this).enqueue(postRequest);
        finish();
    }
}