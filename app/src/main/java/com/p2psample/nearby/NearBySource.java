package com.p2psample.nearby;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;
import com.google.android.gms.nearby.connection.ConnectionsClient;
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate;
import com.google.android.gms.nearby.connection.Strategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.p2psample.P2PApplication;
import com.p2psample.connection.events.ConnectionEvent;
import com.p2psample.connection.events.DiscoveryEvent;
import com.p2psample.messaging.events.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by Purushottam Gupta on 2/6/2018.
 */

public class NearBySource {
    private static final String TAG = NearBySource.class.getSimpleName();
    private static NearBySource nearBySource;
    private ConnectionsClient connectionsClient;
    private static final Strategy STRATEGY = Strategy.P2P_STAR;
    private static final long ADVERTISING_DURATION = 30000;
    private static final String SERVICE_ID = "com.p2psample.SERVICE_ID";
    private String connectedEndPointId;

    private final PayloadCallback payloadCallback =
            new PayloadCallback() {
                @Override
                public void onPayloadReceived(String endpointId, Payload payload) {
                    EventBus.getDefault().post(new MessageEvent(new String(payload.asBytes(), UTF_8)));
                }

                @Override
                public void onPayloadTransferUpdate(String endpointId, PayloadTransferUpdate update) {

                }
            };

    private final ConnectionLifecycleCallback connectionLifecycleCallback =
            new ConnectionLifecycleCallback() {
                @Override
                public void onConnectionInitiated(String endpointId, ConnectionInfo connectionInfo) {
                    Log.d(TAG, String.format("onConnectionInitiated(endpointId=%s, endpointName=%s)",
                            endpointId, connectionInfo.getEndpointName()));
                    connectionsClient.acceptConnection(endpointId, payloadCallback);
                }

                @Override
                public void onConnectionResult(String endpointId, ConnectionResolution result) {
                    if (result.getStatus().isSuccess()) {
                        Log.i(TAG, "onConnectionResult: connection successful");
                        connectionsClient.stopDiscovery();
                        connectionsClient.stopAdvertising();
                        connectedEndPointId = endpointId;
                        EventBus.getDefault().post(new ConnectionEvent.OnConnectionSuccess());
                    } else {
                        Log.i(TAG, "onConnectionResult: connection failed");
                    }
                }

                @Override
                public void onDisconnected(String endpointId) {
                    Log.i(TAG, "onDisconnected: disconnected from the endPoint " + endpointId);
                }
            };

    private final EndpointDiscoveryCallback endpointDiscoveryCallback =
            new EndpointDiscoveryCallback() {
                @Override
                public void onEndpointFound(String endpointId, DiscoveredEndpointInfo info) {
                    Log.i(TAG, "onEndpointFound: endpoint found " + endpointId);
                    EventBus.getDefault().post(new DiscoveryEvent.OnEndPointFound(
                            new Endpoint(endpointId, info.getEndpointName())));
                }

                @Override
                public void onEndpointLost(String endpointId) {
                    Log.i(TAG, "onEndpointLost: endpoint lost " + endpointId);
                    EventBus.getDefault().post(new DiscoveryEvent.OnEndPointLost(endpointId));
                }
            };

    private NearBySource() {
        connectionsClient = Nearby.getConnectionsClient(P2PApplication.getInstance().getApplicationContext());
    }

    public static NearBySource getInstance() {
        if (nearBySource == null) {
            nearBySource = new NearBySource();
        }
        return nearBySource;
    }

    public void requestConnection(Endpoint endpoint) {
        connectionsClient.requestConnection(endpoint.getName(), endpoint.getId(), connectionLifecycleCallback);
    }

    public void findConnection(String packageName) {
        connectionsClient.startDiscovery(packageName, endpointDiscoveryCallback,
                new DiscoveryOptions(STRATEGY));
    }

    public void shareConnection(String endpointName, String packageName) {
        connectionsClient.startAdvertising(endpointName, packageName, connectionLifecycleCallback,
                new AdvertisingOptions(STRATEGY));
    }

    public void send(Payload payload) {
        connectionsClient
                .sendPayload(connectedEndPointId, payload)
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "sendPayload() failed.");
                            }
                        });
    }
}
