package com.avl.cagApp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avl.cagApp.model.ControlSwitchItem;
import com.avl.cagApp.repository.switcher.ISwitchListener;
import com.avl.cagApp.repository.switcher.Switch32x32Repository;

import java.util.List;
import java.util.stream.Collectors;

public class MasterPanelViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private final MutableLiveData<Boolean> isSystemInitialized = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isSwitcherConnected = new MutableLiveData<>(false);
    private Switch32x32Repository switchRepo;

    public MasterPanelViewModel() {
        switchRepo = new Switch32x32Repository();
        setupSwitchListener();
    }

    // Switch setup
    private void setupSwitchListener() {
        switchRepo.setListener(new ISwitchListener() {
            @Override
            public void onConnected() {
                isSwitcherConnected.postValue(true);
            }

            @Override
            public void onDisconnected() {
                isSwitcherConnected.postValue(false);
            }
        });
    }

    public void routeAV(int selectedInput, List<Integer> selectedOutput) {
        switchRepo.routeAV(selectedInput, selectedOutput);
    }

    public void switchControl(ControlSwitchItem controlSwitchItem) {

    }
}