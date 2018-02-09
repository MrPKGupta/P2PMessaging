package com.p2psample.messaging.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.p2psample.R;
import com.p2psample.messaging.model.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Purushottam Gupta on 2/6/2018.
 */

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Message> messages = new ArrayList<>();
    private static final int TYPE_SENT = 0;
    private static final int TYPE_RECEIVED = 1;

    @Override
    public int getItemCount() {
        if (messages == null)
            return 0;
        return messages.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_SENT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_sent, parent, false);
                return new SentMessageHolder(view);
            case TYPE_RECEIVED:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_received, parent, false);
                return new ReceivedMessageHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (messages != null) {
            Message message = messages.get(position);
            if (message != null) {
                return message.getType();
            }
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);
        if (message != null) {
            switch (message.getType()) {
                case TYPE_SENT:
                    ((SentMessageHolder) holder).tvMessage.setText(message.getText());
                    break;
                case TYPE_RECEIVED:
                    ((ReceivedMessageHolder) holder).tvMessage.setText(message.getText());
                    break;
            }
        }
    }

    static class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView tvMessage;

        SentMessageHolder(View view) {
            super(view);
            tvMessage = view.findViewById(R.id.tvMessage);
        }
    }

    static class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView tvMessage;

        ReceivedMessageHolder(View view) {
            super(view);
            tvMessage = view.findViewById(R.id.tvMessage);
        }
    }

    public void addMessage(Message message) {
        messages.add(message);
        notifyItemInserted(messages.size());
    }
}
