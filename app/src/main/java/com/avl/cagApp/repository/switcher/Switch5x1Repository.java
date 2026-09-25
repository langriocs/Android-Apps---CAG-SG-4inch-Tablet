package com.avl.cagApp.repository.switcher;

import com.avl.cagApp.libs.TCPClient;
import com.avl.cagApp.repository.tv.ITVListener;

import java.util.List;

public class Switch5x1Repository implements ISwitchRepository {

    private volatile ISwitchListener listener;
    private final TCPClient tcpClient;

    public Switch5x1Repository() {
        tcpClient = createClient();
    }

    private TCPClient createClient() {
        return new TCPClient("cag", new TCPClient.OnMessageReceived() {
            @Override
            public void onMessageReceived(String message) {
                if (message == null || message.trim().isEmpty()) {
                    return;
                }

            }
        }, new TCPClient.OnConnectionStatusChanged() {
            @Override
            public void onConnected() {
                ISwitchListener currentListener = listener;
                if (currentListener != null) {
                    currentListener.onConnected();
                }
            }

            @Override
            public void onDisconnected() {
                ISwitchListener currentListener = listener;
                if (currentListener != null) {
                    currentListener.onDisconnected();
                }
            }
        });
    }

    @Override
    public void connect(String ip, int port) {
        tcpClient.connect(ip, port);
    }

    @Override
    public void disconnect() {
        tcpClient.stopClient();
    }

    @Override
    public void routeInputSourceTo(Switch5x1Output output) {
        tcpClient.sendMessage("s input source " + output.getValue() + "\r");
    }

    @Override
    public void routeAV(Integer selectedInput, List<Integer> selectedOutput) {

    }

    @Override
    public void setListener(ISwitchListener switchListener) {
        this.listener = switchListener;
    }

    @Override
    public void cleanup() {
        this.listener = null;
        tcpClient.cleanup();
    }
}
