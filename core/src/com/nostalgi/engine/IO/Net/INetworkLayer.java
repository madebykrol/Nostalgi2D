package com.nostalgi.engine.IO.Net;

/**
 * Created by ksdkrol on 2016-10-20.
 */

public interface INetworkLayer {
    
    void replicateInput(OnlineSession session, InputSnapshot snapshot);

    void replicateActor(OnlineSession session, ActorSnapshot snapshot);

    void onRecieveActorSnapshot(OnlineSession session, INetworkDataReceiveCallback<ActorSnapshot> callback);

}
