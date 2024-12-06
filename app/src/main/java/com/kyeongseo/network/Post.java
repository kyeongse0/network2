package com.kyeongseo.network;

import java.io.Serializable;
public class Post {
    private String content; // 게시글 내용
    private String imageUri; // 이미지 URI

    public Post(String content, String imageUri) {
        this.content = content;
        this.imageUri = imageUri;
    }

    public String getContent() {
        return content;
    }

    public String getImageUri() {
        return imageUri;
    }
}