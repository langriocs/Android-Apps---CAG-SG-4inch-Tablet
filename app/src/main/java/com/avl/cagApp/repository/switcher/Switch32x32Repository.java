package com.avl.cagApp.repository.switcher;

import com.avl.cagApp.libs.TCPClient;
import com.avl.cagApp.repository.tv.ITVListener;

import java.util.List;
import java.util.stream.Collectors;

public class Switch32x32Repository implements ISwitchRepository {

    private volatile ISwitchListener listener;
    private final TCPClient tcpClient;

    public Switch32x32Repository() {
        this.tcpClient = createClient();
    }

    private TCPClient createClient() {
        return new TCPClient("cag", new TCPClient.OnMessageReceived() {
            @Override
            public void onMessageReceived(String message) {
                if (message == null || message.trim().isEmpty()) {
                    return;
                }

                handleMessage(message);

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

    private void handleMessage(String message) {

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

    }

    @Override
    public void routeAV(Integer selectedInput, List<Integer> selectedOutput) {
        String selOutput = selectedOutput.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        tcpClient.sendMessage("s in " + selectedInput.toString() + " av out " + selOutput +"! \r");
    }


    @Override
    public void setListener(ISwitchListener listener) {

    }

    @Override
    public void cleanup() {

    }
}
