package com.p2psample.connection;

import com.p2psample.connection.events.ConnectionEvent;
import com.p2psample.connection.events.DiscoveryEvent;
import com.p2psample.nearby.NearBySource;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Purushottam Gupta on 2/5/2018.
 */

public class ConnectionPresenter implements ConnectionContract.Presenter {
    private final ConnectionContract.View connectionView;

    public ConnectionPresenter(ConnectionContract.View connectionView) {
        this.connectionView = checkNotNull(connectionView);
        this.connectionView.setPresenter(this);
    }

    @Override
    public void start() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void stop() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void findConnection(String packageName) {
        NearBySource.getInstance().findConnection(packageName);
    }

    @Override
    public void shareConnection(String endpointName, String packageName) {
        NearBySource.getInstance().shareConnection(endpointName, packageName);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEndPointFound(DiscoveryEvent.OnEndPointFound event) {
        connectionView.addEndPoint(event.getEndpoint());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEndPointLost(DiscoveryEvent.OnEndPointLost event) {
        connectionView.removeEndPoint(event.getEndPointId());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConnectionSuccess(ConnectionEvent.OnConnectionSuccess event) {
        connectionView.startMessaging();
    }
}
