package com.nostalgi.engine.IO.Net;

/**
 * Created by ksdkrol on 2016-10-20.
 */

public interface INetworkLayer {
    
    void replicateInput(PlayerSession session, InputSnapshot snapshot);

    void replicateActor(PlayerSession session, ActorSnapshot snapshot);

    void onRecieveActorSnapshot(PlayerSession session, INetworkDataReceiveCallback<ActorSnapshot> callback);

}
