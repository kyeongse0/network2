package com.kyeongseo.network;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private Context context;
    private ArrayList<ChatMessage> messages;

    public ChatAdapter(Context context, ArrayList<ChatMessage> messages) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat_message, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatMessage message = messages.get(position);

        holder.messageText.setText(message.getText());

        if (message.isSentByMe()) {
            holder.messageText.setBackgroundResource(R.color.white);
            holder.messageText.setGravity(View.TEXT_ALIGNMENT_VIEW_END);
        } else {
            holder.messageText.setBackgroundResource(R.color.white);
            holder.messageText.setGravity(View.TEXT_ALIGNMENT_VIEW_START);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.tvMessage);
        }
    }
}