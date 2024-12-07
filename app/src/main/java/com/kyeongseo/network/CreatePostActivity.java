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
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class CreatePostActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText etPostContent; // 글 내용 입력 필드
    private ImageView ivPostImage; // 이미지 미리보기
    private Uri selectedImageUri; // 선택된 이미지 URI

    private EditText etPostTitle; // 제목 입력 필드
    private EditText etPostAuthor; // 작성자 입력 필드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        etPostTitle = findViewById(R.id.etPostTitle); // 제목 입력 필드 연결
        etPostAuthor = findViewById(R.id.etPostAuthor); // 작성자 입력 필드 연결
        etPostContent = findViewById(R.id.etPostContent);
        ivPostImage = findViewById(R.id.ivPostImage);

        Button btnSelectImage = findViewById(R.id.btnSelectImage);
        Button btnSubmitPost = findViewById(R.id.btnSubmitPost);

        btnSelectImage.setOnClickListener(v -> openImagePicker());
        btnSubmitPost.setOnClickListener(v -> submitPost());
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST); // 이미지 선택 요청 실행
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData(); // 선택된 이미지 URI 저장

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                ivPostImage.setImageBitmap(bitmap); // 선택한 이미지를 미리보기로 표시
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "이미지 로드에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        }
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
                .putString("imageUri", selectedImageUri != null ? selectedImageUri.toString() : null)
                .build();

        OneTimeWorkRequest postRequest = new OneTimeWorkRequest.Builder(PostWorker.class)
                .setInputData(inputData)
                .build();

        WorkManager.getInstance(this).enqueue(postRequest);
        finish();
    }
}