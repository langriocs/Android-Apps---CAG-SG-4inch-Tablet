package com.avl.cagApp.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avl.avlibrary.repository.switcher.ISwitchListener;
import com.avl.avlibrary.repository.switcher.ISwitchRepository;
import com.avl.avlibrary.repository.switcher.Switch5x1Output;
import com.avl.avlibrary.repository.switcher.Switch5x1Repository;
import com.avl.avlibrary.repository.tv.ITVListener;
import com.avl.avlibrary.repository.tv.ITVRepository;
import com.avl.avlibrary.repository.tv.LGTVRepository;
import com.avl.avlibrary.repository.tv.TVPowerState;
import com.avl.cagApp.model.vo.ControlDevice;

import java.util.HashMap;
import java.util.Map;

public class ControlScreenViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isSystemInitialized = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isSwitcherConnected = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isTVConnected = new MutableLiveData<>(false);
    private final MutableLiveData<TVPowerState> tvState = new MutableLiveData<>(TVPowerState.OFF);
    private final MutableLiveData<Boolean> isUsbCSelected = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isWirelessSelected = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isMSSeleccted = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> dspMuted = new MutableLiveData<>(false);
    private final MutableLiveData<String> tvMessage = new MutableLiveData<>("");
    private final MutableLiveData<Integer> dspVolume = new MutableLiveData<>(50);
    private final ITVRepository tvRepository;
    private final ISwitchRepository switchRepository;
//    private final IDSPRepository dspRepository;


    private Switch5x1Output selectedSource;

    public ControlScreenViewModel () {
//        Police OPS Room
//        switchRepository = connectSwitcher("192.168.1.71", 8000);
//        tvRepository = connectTV("192.168.1.72", 9761);

//      PMA Holding
//        switchRepository = connectSwitcher("192.168.1.61", 8000);
//        tvRepository = connectTV("192.168.1.62", 9761);
//        CID OPS
//        switchRepository = connectSwitcher("192.168.1.81", 8000);
//        tvRepository = connectTV("192.168.1.82", 9761);
//      AIRLINE Rm 1
//        switchRepository = connectSwitcher("192.168.1.11", 8000);
//        tvRepository = connectTV("192.168.1.12", 9761);
//        AIRLINE Rm 2
        switchRepository = connectSwitcher("192.168.1.21", 8000);
        tvRepository = connectTV("192.168.1.22", 9761);


    }

    public LiveData<Boolean> getIsSystemInitialized() { return isSystemInitialized; }
    public void setSystemInitialized(boolean initialized) { isSystemInitialized.postValue(initialized); }

    public LiveData<Boolean> getIsSwitcherConnected() {
        return isSwitcherConnected;
    }

    public LiveData<Boolean> getIsTVConnected() {
        return isTVConnected;
    }

    public LiveData<TVPowerState> getTvPowerState() {
        return tvState;
    }

    public LiveData<Boolean> getIsUsbCSelected() {
        return isUsbCSelected;
    }

    public void setUsbCSelected(boolean selected) {
        isUsbCSelected.postValue(selected);
    }

    public LiveData<Boolean> getIsWirelessSelected() {
        return isWirelessSelected;
    }

    public void setWirelessSelected(boolean selected) {
        isWirelessSelected.postValue(selected);
    }

    public LiveData<Boolean> getIsMSSelected() {
        return isMSSeleccted;
    }

    public LiveData<Boolean> getDSPMuted() {
        return dspMuted;
    }

    public LiveData<String> getTVMessage() {
        return tvMessage;
    }

    public LiveData<Integer> getDSPVolume() {
        return dspVolume;
    }

    private ISwitchRepository connectSwitcher(String ip, int port) {
        ISwitchRepository switchRepository = new Switch5x1Repository();
        switchRepository.setListener(new ISwitchListener() {

            @Override
            public void onConnected(String ip) {
                isSwitcherConnected.postValue(true);
                Log.d("cagSwitch", "Switcher connected " + ip);
            }

            @Override
            public void onDisconnected(String ip) {
                isSwitcherConnected.postValue(false);
                Log.d("cag", "Switcher disconnected " + ip);
            }
        });

        switchRepository.connect(ip, port);

        return switchRepository;

    }

    public ITVRepository connectTV(String ip, int port) {
        ITVRepository tv = new LGTVRepository();
        tv.setListener(new ITVListener() {
            @Override
            public void onConnected(String ip) {
                Log.d("cagTV", "TV connected " + ip);
                isTVConnected.postValue(true);
            }

            @Override
            public void onDisconnected(String ip) {
                isTVConnected.postValue(false);
            }

            @Override
            public void onPowerStateChanged(TVPowerState state) {
                tvState.postValue(state);
            }

            @Override
            public void onVolumeChanged(int volume) {
                dspVolume.postValue(volume);
            }

            @Override
            public void onMuteChanged(boolean state) {
                dspMuted.postValue(state);
            }

            @Override
            public void onError(String message) {

            }
        });
        tv.connect(ip, port);

        return tv;
    }

//    public void connectDSP(String ip, int port) {
//        dspRepository.connect(ip, port);
//        dspRepository.setChannel(DSPChannel.CH_1);
//        dspRepository.setVolume(50);
//        dspRepository.setMute(0);
//        dspVolume.postValue(50);
//        dspMuted.postValue(false);
//    }
//    public void connectSwitcher(String ip, int port) {
//        switchRepository.connect(ip, port);
//    }



    public void turnOnTV() {
        tvRepository.turnOn();
    }

    public void turnOffTV() {
        tvRepository.turnOff();
        tvRepository.turnOff();
    }

    public void changeMute(boolean isMute) {
//        dspRepository.setMute(isMute ? 1 : 0);
//        dspMuted.postValue(isMute);
        tvRepository.setMute(isMute);
        dspMuted.postValue(isMute);
    }

    public void changeVolume(int volume) {
//        dspRepository.setVolume(volume);
//        dspVolume.postValue(volume);
        tvRepository.setVolume(volume);
    }

    public void disconnectSwitcher() {
        switchRepository.disconnect();
    }
    public void routeInputSourceToUSB() {
        isUsbCSelected.postValue(true);
        isWirelessSelected.postValue(false);
        isMSSeleccted.postValue(false);
        switchRepository.routeInputSourceTo(selectedSource);
    }

    public void routeInputSourceToWireless() {
        isUsbCSelected.postValue(false);
        isWirelessSelected.postValue(true);
        isMSSeleccted.postValue(false);
        switchRepository.routeInputSourceTo(Switch5x1Output.HDMI_4);
    }

    public void routeInputSourceToMS() {
        isUsbCSelected.postValue(false);
        isWirelessSelected.postValue(false);
        isMSSeleccted.postValue(true);
        switchRepository.routeInputSourceTo(Switch5x1Output.HDMI_3);
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        tvRepository.cleanup();
        switchRepository.cleanup();
    }

    public void setSelectedControlDevice(ControlDevice controlDevice) {
        if (controlDevice.getIpAdd().equals("192.168.1.60")) { // if Room is PMA Holding
            selectedSource = Switch5x1Output.HDMI_3;
        } else {
            selectedSource = Switch5x1Output.USB_1;
        }
    }



}
