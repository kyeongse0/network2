package com.kyeongseo.network;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_SENT = 1; // 내가 보낸 메시지
    private static final int VIEW_TYPE_RECEIVED = 2; // 내가 받은 메시지

    private List<String> messages;

    public MessageAdapter(List<String> messages) {
        this.messages = messages;
    }

    @Override
    public int getItemViewType(int position) {
        // 메시지가 "나 :"로 시작하면 송신 메시지로 간주
        if (messages.get(position).startsWith("나 :")) {
            return VIEW_TYPE_SENT;
        } else {
            return VIEW_TYPE_RECEIVED;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SENT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageViewHolder(view);
        } else { // VIEW_TYPE_RECEIVED
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String message = messages.get(position);

        if (holder instanceof SentMessageViewHolder) {
            ((SentMessageViewHolder) holder).messageBody.setText(message.substring(3)); // "나 :" 제거 후 표시
        } else if (holder instanceof ReceivedMessageViewHolder) {
            ((ReceivedMessageViewHolder) holder).messageBody.setText(message.substring(4)); // "상대 :" 제거 후 표시
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    // 송신 메시지 ViewHolder
    static class SentMessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageBody;

        SentMessageViewHolder(View itemView) {
            super(itemView);
            messageBody = itemView.findViewById(R.id.text_message_body);
        }
    }

    // 수신 메시지 ViewHolder
    static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageBody;

        ReceivedMessageViewHolder(View itemView) {
            super(itemView);
            messageBody = itemView.findViewById(R.id.text_message_body);
        }
    }
}