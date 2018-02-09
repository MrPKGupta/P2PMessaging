package com.p2psample.messaging;

import com.google.android.gms.nearby.connection.Payload;
import com.p2psample.messaging.events.MessageEvent;
import com.p2psample.nearby.NearBySource;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by Purushottam Gupta on 2/5/2018.
 */

public class MessagingPresenter implements MessagingContract.Presenter {
    private final MessagingContract.View messagingView;

    public MessagingPresenter(MessagingContract.View messagingView) {
        this.messagingView = checkNotNull(messagingView);
        this.messagingView.setPresenter(this);
    }

    @Override
    public void start() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void stop() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessaging(MessageEvent event) {
        messagingView.onReceivedMessage(event.getMessage());
    }

    @Override
    public void sendMessage(String message) {
        NearBySource.getInstance().send(Payload.fromBytes(message.getBytes(UTF_8)));
    }
}
