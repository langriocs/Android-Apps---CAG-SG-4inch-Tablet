package com.avl.cagApp.repository.tv;

import com.avl.cagApp.model.TVPowerState;

public interface ITVListener {

    void onConnected();
    void onDisconnected();
    void onPowerStateChanged(TVPowerState state);
    void onVolumeChanged(int volume);
    void onMuteChanged(boolean isMuted);
    void onError(String message);
}
