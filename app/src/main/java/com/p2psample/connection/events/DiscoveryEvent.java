package com.p2psample.connection.events;

import com.p2psample.nearby.Endpoint;

/**
 * Created by Purushottam Gupta on 2/6/2018.
 */

public class DiscoveryEvent {

    public static class OnEndPointFound {
        Endpoint endpoint;

        public OnEndPointFound(Endpoint endpoint) {
            this.endpoint = endpoint;
        }

        public Endpoint getEndpoint() {
            return endpoint;
        }
    }

    public static class OnEndPointLost {
        String endPointId;

        public OnEndPointLost(String endPointId) {
            this.endPointId = endPointId;
        }

        public String getEndPointId() {
            return endPointId;
        }
    }
}
