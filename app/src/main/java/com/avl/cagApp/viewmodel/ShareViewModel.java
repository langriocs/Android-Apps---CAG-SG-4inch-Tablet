package com.avl.cagApp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.avl.cagApp.model.vo.ControlDevice;
import com.avl.cagApp.model.vo.ControlRoomDevices;
import com.avl.cagApp.model.vo.RoomDevice;
import com.avl.cagApp.repository.room.IDeviceRoomRepository;
import com.avl.cagApp.repository.room.impl.DeviceRoomRepository;

import java.util.List;

public class ShareViewModel extends AndroidViewModel {
    private final IDeviceRoomRepository deviceRoomRepo;
    private final MutableLiveData<String> ipAddressQuery = new MutableLiveData<>();
    private final LiveData<ControlRoomDevices> controlRoomDevices;
    private boolean isFourInchPanel = false;
    private List<RoomDevice> selectedRoomDevices;
    private ControlDevice selectedControlDevice;

    public ShareViewModel(@NonNull Application application) {
        super(application);
        deviceRoomRepo = new DeviceRoomRepository(application);
        controlRoomDevices = Transformations.switchMap(ipAddressQuery, deviceRoomRepo::fetchControlDeviceWithRoomDevicesByIpAddress);
    }

    public LiveData<ControlRoomDevices> getControlRoomDevices() {
        return controlRoomDevices;
    }

    public void fetchControlDeviceByIpAddress(String ipAddress) {
        ipAddressQuery.setValue(ipAddress);
    }

    public void setIspFourInchPanel(boolean fourInchPanel) {
        isFourInchPanel = fourInchPanel;
    }
    public boolean isFourInchPanel() {
        return isFourInchPanel;
    }

    public void setSelectedRoomDevices(List<RoomDevice> roomDevices) {
        this.selectedRoomDevices = roomDevices;
    }
    public List<RoomDevice> getSelectedRoomDevices() {
        return selectedRoomDevices;
    }

    public ControlDevice getSelectedControlDevice() {
        return selectedControlDevice;
    }
    public void setSelectedControlDevice(ControlDevice selectedControlDevice) {
        this.selectedControlDevice = selectedControlDevice;
    }
}
