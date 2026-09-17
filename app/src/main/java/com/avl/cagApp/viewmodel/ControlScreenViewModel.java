package com.avl.cagApp.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avl.cagApp.libs.TCPClient;

public class ControlScreenViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isSystemInitialized = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isSwitcherConnected = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isTVConnected = new MutableLiveData<>(false);

    private TCPClient switcherClient;
    private TCPClient tvClient;

    private TCPClient.OnMessageReceived switcherOnMessageReceivedListener = message -> {
        if (message == null) {
            return;
        }

        Log.d("Switcher Message Received", message);
    };
    private TCPClient.OnMessageReceived tvOnMessageReceivedListener = message -> {
        if (message == null) {
            return;
        }
        Log.d("TV Message Received", message);
    };

    public ControlScreenViewModel () {
        switcherClient = createClient(isSwitcherConnected, switcherOnMessageReceivedListener );
        tvClient = createClient(isTVConnected, tvOnMessageReceivedListener );
    }

    private TCPClient createClient(MutableLiveData<Boolean> connectionState, TCPClient.OnMessageReceived onMessageReceived ) {
        return new TCPClient("cag",  onMessageReceived, new TCPClient.OnConnectionStatusChanged() {
            @Override
            public void onConnected() {
                connectionState.postValue(true);
            }

            @Override
            public void onDisconnected() {
                connectionState.postValue(false);
            }
        });
    }

    public LiveData<Boolean> getIsSystemInitialized() { return isSystemInitialized; }
    public void setSystemInitialized(boolean initialized) { isSystemInitialized.postValue(initialized); }

    public LiveData<Boolean> getIsSwitcherConnected() {
        return isSwitcherConnected;
    }

    public LiveData<Boolean> getIsTVConnected() {
        return isTVConnected;
    }

    public void connectSwitcher(String ip, int port) {
        switcherClient.connect(ip, port);
    }
    public void connectTV(String ip, int port) {
        tvClient.connect(ip, port);
    }

     public void sendToSwitcher(String message) {
        switcherClient.sendMessage(message);
    }

    public void sendToTV(String message) {
        tvClient.sendMessage(message);
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        switcherClient.stopClient();
        tvClient.stopClient();
    }
}
