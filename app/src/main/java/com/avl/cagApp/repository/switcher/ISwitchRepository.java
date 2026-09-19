package com.avl.cagApp.repository.switcher;

import com.avl.cagApp.repository.tv.ITVListener;

public interface ISwitchRepository {

    void connect(String ip, int port);
    void disconnect();
    void routeInputSourceTo(Switch5x1Output output);
    void setListener(ISwitchListener listener);
    void cleanup();
}
