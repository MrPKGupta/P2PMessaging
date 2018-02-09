package com.p2psample.messaging;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.p2psample.R;
import com.p2psample.messaging.adapter.MessageAdapter;
import com.p2psample.messaging.model.Message;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Purushottam Gupta on 2/5/2018.
 */

public class MessagingFragment extends Fragment implements MessagingContract.View {
    private MessagingContract.Presenter presenter;
    private MessageAdapter messageAdapter;

    public static MessagingFragment newInstance() {
        return new MessagingFragment();
    }

    @Override
    public void setPresenter(@NonNull MessagingContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_messaging, container, false);

        ImageButton btnSend = root.findViewById(R.id.btnSend);
        final EditText etMessage = root.findViewById(R.id.etMessage);
        RecyclerView rvMessage = root.findViewById(R.id.rvMessage);
        rvMessage.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMessage.setHasFixedSize(true);

        if (messageAdapter == null) {
            messageAdapter = new MessageAdapter();
        }
        rvMessage.setAdapter(messageAdapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = etMessage.getText().toString().trim();
                messageAdapter.addMessage(new Message(messageText, 0));
                presenter.sendMessage(messageText);
                etMessage.setText("");
            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    public void onReceivedMessage(String message) {
        messageAdapter.addMessage(new Message(message, 1));
    }
}
