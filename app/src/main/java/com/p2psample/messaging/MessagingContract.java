package com.p2psample.messaging;

import com.p2psample.BasePresenter;
import com.p2psample.BaseView;

/**
 * Created by Purushottam Gupta on 2/5/2018.
 */

public class MessagingContract {

    interface View extends BaseView<Presenter> {
        void onReceivedMessage(String message);
    }

    interface Presenter extends BasePresenter {
        void sendMessage(String message);
    }
}
