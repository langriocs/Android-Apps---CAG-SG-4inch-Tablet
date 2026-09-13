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
    private LiveData<ControlDevice> controlDevice;
    private LiveData<ControlRoomDevices> controlRoomDevices;
    private boolean isFourInchPanel = false;

    public ShareViewModel(@NonNull Application application) {
        super(application);
        deviceRoomRepo = new DeviceRoomRepository(application);
        controlRoomDevices = Transformations.switchMap(ipAddressQuery, deviceRoomRepo::fetchControlDeviceWithRoomDevicesByIpAddress);
        controlDevice = Transformations.switchMap(ipAddressQuery, deviceRoomRepo::fetchControlDeviceByIpAddress);
    }

    public LiveData<ControlDevice> getControlDevice() {
        return controlDevice;
    }
    public LiveData<ControlRoomDevices> getControlRoomDevices() {
        return controlRoomDevices;
    }

    public void fetchControlDeviceByIpAddress(String ipAddress) {
        ipAddressQuery.setValue(ipAddress);
    }

    public void fetchControlDeviceWithRoomDevicesByIpAddress(String ipAddress) {
        ipAddressQuery.setValue(ipAddress);
    }

    public void saveControlDevice(ControlDevice controlDevice) {
        deviceRoomRepo.saveControlDevice(controlDevice);
    }

    public void saveRoomDevices(List<RoomDevice> roomDevices) {
        deviceRoomRepo.saveRoomDevices(roomDevices);
    }

    public void saveControlRoomDevices(ControlDevice controlDevice, List<RoomDevice> roomDevices) {
        deviceRoomRepo.saveControlRoomDevices(controlDevice, roomDevices);
    }

    public void setIspFourInchPanel(boolean fourInchPanel) {
        isFourInchPanel = fourInchPanel;
    }
    public boolean isFourInchPanel() {
        return isFourInchPanel;
    }
}
