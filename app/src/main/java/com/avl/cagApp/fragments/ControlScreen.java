package com.avl.cagApp.fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avl.cagApp.R;
import com.avl.cagApp.viewmodel.ControlScreenViewModel;
import com.avl.cagApp.viewmodel.ShareViewModel;
import com.google.android.material.button.MaterialButton;

public class ControlScreen extends Fragment {

    private ControlScreenViewModel mViewModel;
    private ShareViewModel mShareModel;
    private CountDownTimer warmupTimer;
    private int volNum = 32;
    boolean isTVConnected = false;
    boolean isSwitcherConnected = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(ControlScreenViewModel.class);
        mShareModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_control_screen1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvRoomName = view.findViewById(R.id.room_name_tv);
        MaterialButton btnUSB1 = view.findViewById(R.id.btn_usb_1);
        MaterialButton btnUSB2 = view.findViewById(R.id.btn_usb_2);
        MaterialButton btnPowerOff = view.findViewById(R.id.btn_power_off);
        MaterialButton btnPowerOn = view.findViewById(R.id.btn_power_on);
        MaterialButton btnVolumeUp = view.findViewById(R.id.btn_vol_up);
        MaterialButton btnVolumeDown = view.findViewById(R.id.btn_vol_down);
        MaterialButton btnExit = view.findViewById(R.id.btn_exit);
        ImageView switchLedIcon = view.findViewById(R.id.switchLedIcon);
        ImageView tvLedIcon = view.findViewById(R.id.tvLedIcon);
        TextView tvLedSwitchDesc = view.findViewById(R.id.switchLedIcon_tv);
        TextView tvLedTVDesc = view.findViewById(R.id.tvLedDesc1_tv);

        btnUSB1.setOnClickListener(v -> {
            if (!isSwitcherConnected) {
                return;
            }
            mViewModel.sendToSwitcher("s input source 1");
        });

        btnUSB2.setOnClickListener(v -> {
            if (!isSwitcherConnected) {
                return;
            }
            mViewModel.sendToSwitcher("s input source 4");
        } );

        btnPowerOff.setOnClickListener(v -> {
            if (!isTVConnected) {
                return;
            }

            mViewModel.sendToTV("ka 00 00\r");
        });
        
        btnPowerOn.setOnClickListener(v -> {
            if (!isTVConnected) {
                return;
            }

            mViewModel.sendToTV("ka 00 01\r");
        });
        
        btnVolumeUp.setOnClickListener(v -> {
            if (!isTVConnected) {
                return;
            }
            if (volNum == 64) {
                return;
            }

            volNum = volNum + 1;
            String strVolNum = (volNum < 10) ? "0" + volNum : String.valueOf(volNum);
            mViewModel.sendToTV("kf 00 " + strVolNum + "\r");

        });
        
        btnVolumeDown.setOnClickListener(v -> {
            if (!isTVConnected) {
                return;
            }
            if (volNum == 0) {
                return;
            }

            volNum = volNum - 1;
            String strVolNum = (volNum < 10) ? "0" + volNum : String.valueOf(volNum);
            mViewModel.sendToTV("kf 00 " + strVolNum + "\r");

        });

        btnExit.setOnClickListener(v -> {
            performShutdown();
            Navigation.findNavController(v).navigate(R.id.action_controlScreen_to_splashScreen);
        });

        // Warmup UI components
        View layoutWarmup = view.findViewById(R.id.layoutWarmup);
        TextView txtWarmupCountdown = view.findViewById(R.id.txtWarmupCountdown);

        mShareModel.getControlRoomDevices().observe(getViewLifecycleOwner(), controlRoomDevices -> {
            if (controlRoomDevices != null) {
                tvRoomName.setText(controlRoomDevices.controlDevice.getRoomName());
                if (controlRoomDevices.roomDevices != null) {
                    controlRoomDevices.roomDevices.forEach(roomDevice -> {
                        if (roomDevice.getDeviceName().equals("Switch")) {
                            mViewModel.connectSwitcher(roomDevice.getDeviceIpAddress(), roomDevice.getDevicePort());
                        } else if (roomDevice.getDeviceName().equals("TV")) {
                            mViewModel.connectTV(roomDevice.getDeviceIpAddress(), roomDevice.getDevicePort());
                        }
                    });
                }
            }
        });

        mViewModel.getIsSystemInitialized().observe(getViewLifecycleOwner(), isInitialized -> {
            if (!isInitialized) {
                startWarmup(layoutWarmup, txtWarmupCountdown);

            } else {
                layoutWarmup.setVisibility(View.GONE);
            }
        });

        mViewModel.getIsSwitcherConnected().observe(getViewLifecycleOwner(), isConnected -> {
            setLEDConnectionStatus(switchLedIcon, tvLedSwitchDesc, isConnected);
            String statDesc = "Switch is " + (isConnected ? "online" : "offline");
            tvLedSwitchDesc.setText(statDesc);
            isSwitcherConnected = isConnected;
        });

        mViewModel.getIsTVConnected().observe(getViewLifecycleOwner(), isConnected -> {
            setLEDConnectionStatus(tvLedIcon, tvLedTVDesc, isConnected);
            String statDesc = "TV is " + (isConnected ? "online" : "offline");
            tvLedTVDesc.setText(statDesc);
            isTVConnected = isConnected;
        });
    }

    private void startWarmup(View layoutWarmup, TextView txtWarmupCountdown) {
        layoutWarmup.setVisibility(View.VISIBLE);

        if (warmupTimer != null) {
            warmupTimer.cancel();
        }

        warmupTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String secUntilFinished = (millisUntilFinished / 1000) + "s";
                txtWarmupCountdown.setText(secUntilFinished);
            }

            @Override
            public void onFinish() {
                mViewModel.setSystemInitialized(true);
            }
        }.start();
    }

    private void setLEDConnectionStatus(ImageView ledIcon,TextView tvDesc, boolean status) {
//        int color = ContextCompat.getColor(requireContext(), R.color.led_off);
        int color;

        if(status) {
            color = ContextCompat.getColor(requireContext(), R.color.led_connected);
        } else {
            color = ContextCompat.getColor(requireContext(), R.color.led_disconnected);
        }

        ledIcon.setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }

    private void performShutdown() {
        if (warmupTimer != null) {
            warmupTimer.cancel();
        }

        // Execute the shutdown sequence in the ViewModel (includes recursive TV checks)

        // Final reset logic
        mViewModel.setSystemInitialized(false);
    }
}