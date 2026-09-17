package com.avl.cagApp.fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
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

        return inflater.inflate(R.layout.fragment_control_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvRoomName = view.findViewById(R.id.txtRoomName);
        ConstraintLayout btnSourceUsbC = view.findViewById(R.id.btnSourceUsbC);
        ConstraintLayout btnSourceWireless = view.findViewById(R.id.btnSourceWireless);
        ConstraintLayout btnMute = view.findViewById(R.id.btnMute);
        MaterialButton btnPower = view.findViewById(R.id.btnPower);

        btnSourceUsbC.setOnClickListener(v -> {
//            if (!isSwitcherConnected) {
//                return;
//            }
            mViewModel.sendToSwitcher("s input source 1");
            Boolean current = mViewModel.getIsUsbCSelected().getValue();
            mViewModel.setUsbCSelected(current == null || !current);
        });

        btnSourceWireless.setOnClickListener(v -> {
//            if (!isSwitcherConnected) {
//                return;
//            }
            mViewModel.sendToSwitcher("s input source 4");
            Boolean current = mViewModel.getIsWirelessSelected().getValue();
            mViewModel.setWirelessSelected(current == null || !current);
        } );

        btnPower.setOnClickListener(v -> {
            Boolean currentState = mViewModel.getIsPowerOn().getValue();
            mViewModel.setPowerOn(currentState == null || !currentState);
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

        mViewModel.getIsPowerOn().observe(getViewLifecycleOwner(), isPowerOn -> {
            int color = isPowerOn ? 0xFF4CAF50 : 0xFFFF0000; // Green : Red
            btnPower.setIconTint(android.content.res.ColorStateList.valueOf(color));
            btnPower.setBackgroundResource(isPowerOn ? R.drawable.bg_rounded_card_green : R.drawable.bg_rounded_card_red);
            btnPower.setText(isPowerOn ? "Turn display on" : "Turn display off");
        });

        mViewModel.getIsUsbCSelected().observe(getViewLifecycleOwner(), isSelected -> {
            btnSourceUsbC.setBackgroundResource(isSelected ? R.drawable.bg_rounded_card_selected : R.drawable.bg_rounded_card);
        });

        mViewModel.getIsWirelessSelected().observe(getViewLifecycleOwner(), isSelected -> {
            btnSourceWireless.setBackgroundResource(isSelected ? R.drawable.bg_rounded_card_selected : R.drawable.bg_rounded_card);
        });

        mViewModel.getIsSystemInitialized().observe(getViewLifecycleOwner(), isInitialized -> {
            if (!isInitialized) {
                startWarmup(layoutWarmup, txtWarmupCountdown);

            } else {
                layoutWarmup.setVisibility(View.GONE);
            }
        });

//        mViewModel.getIsSwitcherConnected().observe(getViewLifecycleOwner(), isConnected -> {
//            setLEDConnectionStatus(switchLedIcon, tvLedSwitchDesc, isConnected);
//            String statDesc = "Switch is " + (isConnected ? "online" : "offline");
//            tvLedSwitchDesc.setText(statDesc);
//            isSwitcherConnected = isConnected;
//        });
//
//        mViewModel.getIsTVConnected().observe(getViewLifecycleOwner(), isConnected -> {
//            setLEDConnectionStatus(tvLedIcon, tvLedTVDesc, isConnected);
//            String statDesc = "TV is " + (isConnected ? "online" : "offline");
//            tvLedTVDesc.setText(statDesc);
//            isTVConnected = isConnected;
//        });
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