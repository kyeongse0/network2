package com.kyeongseo.network;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyeongseo.network.Post;
import com.kyeongseo.network.R;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private Context context;
    private ArrayList<Post> posts; // 게시글 데이터 리스트
    private OnItemClickListener listener; // 클릭 이벤트 리스너

    public interface OnItemClickListener {
        void onItemClick(Post post);
    }

    public PostAdapter(Context context, ArrayList<Post> posts, OnItemClickListener listener) {
        this.context = context;
        this.posts = posts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);

        // 제목 설정
        holder.postTitleTextView.setText(post.getTitle());

        // 작성자 설정
        holder.postAuthorTextView.setText("작성자: " + post.getAuthor());

        // 내용 설정
        holder.postContentTextView.setText(post.getContent());


        // 이미지 URI를 ImageView에 설정
        if (post.getImageUri() != null) {
            holder.postImageView.setImageURI(Uri.parse(post.getImageUri()));
        } else {
            holder.postImageView.setImageResource(android.R.color.darker_gray); // 기본 이미지
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(post)); // 클릭 이벤트 전달
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {

        ImageView postImageView;
        TextView postTitleTextView;
        TextView postAuthorTextView;
        TextView postContentTextView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            postImageView = itemView.findViewById(R.id.postImageView);
            postTitleTextView = itemView.findViewById(R.id.postTitleTextView);
            postAuthorTextView = itemView.findViewById(R.id.postAuthorTextView);
            postContentTextView = itemView.findViewById(R.id.postContentTextView);
        }
    }
}