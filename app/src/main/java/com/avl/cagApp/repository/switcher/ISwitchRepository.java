package com.avl.cagApp.repository.switcher;

import com.avl.cagApp.repository.tv.ITVListener;

import java.util.List;

public interface ISwitchRepository {

    void connect(String ip, int port);
    void disconnect();
    void routeInputSourceTo(Switch5x1Output output);
    void routeAV(Integer selectedInput, List<Integer> selectedOutput);
    void setListener(ISwitchListener listener);
    void cleanup();
}
