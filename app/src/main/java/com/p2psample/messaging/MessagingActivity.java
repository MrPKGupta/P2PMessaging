package com.p2psample.messaging;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.p2psample.R;
import com.p2psample.util.ActivityUtils;

public class MessagingActivity extends AppCompatActivity {
    private MessagingPresenter messagingPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        MessagingFragment messagingFragment =
                (MessagingFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (messagingFragment == null) {
            messagingFragment = MessagingFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), messagingFragment, R.id.contentFrame);
        }

        messagingPresenter = new MessagingPresenter(messagingFragment);
    }
}
