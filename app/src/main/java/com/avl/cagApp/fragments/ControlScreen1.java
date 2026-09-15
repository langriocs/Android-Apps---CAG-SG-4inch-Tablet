package com.avl.cagApp.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avl.cagApp.viewmodel.ControlScreen1ViewModel;
import com.avl.cagApp.R;
import com.google.android.material.button.MaterialButton;

public class ControlScreen1 extends Fragment {

    private ControlScreen1ViewModel mViewModel;

    private TextView tvRoomName;
    private MaterialButton btnUSB1;
    private MaterialButton btnUSB2;
    private MaterialButton btnWIFI1;
    private MaterialButton btnWIFI2;
    private MaterialButton btnPowerOffFront;
    private MaterialButton btnPowerOnFront;
    private MaterialButton btnPowerOffSide;
    private MaterialButton btnPowerOnSide;


    public static ControlScreen1 newInstance() {
        return new ControlScreen1();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_control_screen1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(ControlScreen1ViewModel.class);

        tvRoomName = view.findViewById(R.id.room_name_tv);
        btnUSB1 = view.findViewById(R.id.btn_usb_1);
        btnUSB2 = view.findViewById(R.id.btn_usb_2);
        btnWIFI1 = view.findViewById(R.id.btn_wifi_1);
        btnWIFI2 = view.findViewById(R.id.btn_wifi_2);
        btnPowerOffFront = view.findViewById(R.id.btn_power_off_front);
        btnPowerOnFront = view.findViewById(R.id.btn_power_on_front);
        btnPowerOffSide = view.findViewById(R.id.btn_power_off_side);
        btnPowerOnSide = view.findViewById(R.id.btn_power_on_side);

        btnUSB1.setOnClickListener(v -> {

        });
        btnUSB2.setOnClickListener(v -> {

        });
        btnWIFI1.setOnClickListener(v -> {

        });
        btnWIFI2.setOnClickListener(v -> {

        });
        btnPowerOffFront.setOnClickListener(v -> {

        });
        btnPowerOnFront.setOnClickListener(v -> {

        });
        btnPowerOffSide.setOnClickListener(v -> {

        });
        btnPowerOnSide.setOnClickListener(v -> {

        });



    }
}