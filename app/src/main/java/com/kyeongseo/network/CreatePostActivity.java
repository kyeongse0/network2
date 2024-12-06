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

import java.io.IOException;

public class CreatePostActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText etPostContent; // 글 내용 입력 필드
    private ImageView ivPostImage; // 이미지 미리보기
    private Uri selectedImageUri; // 선택된 이미지 URI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        etPostContent = findViewById(R.id.etPostContent);
        ivPostImage = findViewById(R.id.ivPostImage);
        Button btnSelectImage = findViewById(R.id.btnSelectImage);
        Button btnSubmitPost = findViewById(R.id.btnSubmitPost);

        // 이미지 선택 버튼 클릭 이벤트
        btnSelectImage.setOnClickListener(v -> openImagePicker());

        // 글 등록 버튼 클릭 이벤트
        btnSubmitPost.setOnClickListener(v -> submitPost());
    }

    // 이미지 선택 창 열기
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                ivPostImage.setImageBitmap(bitmap); // 선택한 이미지를 미리보기로 표시
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 게시글 서버로 전송
    private void submitPost() {
        String postContent = etPostContent.getText().toString().trim();

        if (postContent.isEmpty() || selectedImageUri == null) {
            // 글 내용 또는 이미지가 없을 경우 처리 (예: 토스트 메시지)
            return;
        }

        // WorkManager를 통해 서버로 게시글 전송 작업 실행
        Data inputData = new Data.Builder()
                .putString("postContent", postContent)
                .putString("imageUri", selectedImageUri.toString())
                .build();

        OneTimeWorkRequest postRequest = new OneTimeWorkRequest.Builder(PostWorker.class)
                .setInputData(inputData)
                .build();

        WorkManager.getInstance(this).enqueue(postRequest);

        finish(); // 완료 후 액티비티 종료
    }
}