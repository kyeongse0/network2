package com.kyeongseo.network;

public class Post {
    private String title;       // 게시글 제목
    private String author;      // 작성자
    private String content;     // 게시글 내용
    private String imageUri;    // 이미지 URI (선택 사항)

    // 생성자
    public Post(String title, String author, String content, String imageUri) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.imageUri = imageUri;
    }

    // Getter 메서드
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getImageUri() {
        return imageUri;
    }

    // Setter 메서드 (필요 시 추가)
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}