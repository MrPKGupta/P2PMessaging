package com.p2psample.connection;

import com.p2psample.BasePresenter;
import com.p2psample.BaseView;
import com.p2psample.nearby.Endpoint;

/**
 * Created by Purushottam Gupta on 2/5/2018.
 */

public class ConnectionContract {

    interface View extends BaseView<Presenter> {
        void addEndPoint(Endpoint endpoint);

        void removeEndPoint(String endPointId);

        void startMessaging();
    }

    interface Presenter extends BasePresenter {
        void findConnection(String packageName);

        void shareConnection(String endpointName, String packageName);
    }
}
