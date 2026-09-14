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
    private MaterialButton btn_usb_1;
    private MaterialButton btn_usb_2;
    private MaterialButton btn_wifi_1;
    private MaterialButton btn_wifi_2;
    private MaterialButton btn_power_off_front;
    private MaterialButton btn_power_on_front;
    private MaterialButton btn_power_off_side;
    private MaterialButton btn_power_on_side;


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
        btn_usb_1 = view.findViewById(R.id.btn_usb_1);
        btn_usb_2 = view.findViewById(R.id.btn_usb_2);
        btn_wifi_1 = view.findViewById(R.id.btn_wifi_1);
        btn_wifi_2 = view.findViewById(R.id.btn_wifi_2);
        btn_power_off_front = view.findViewById(R.id.btn_power_off_front);
        btn_power_on_front = view.findViewById(R.id.btn_power_on_front);
        btn_power_off_side = view.findViewById(R.id.btn_power_off_side);
        btn_power_on_side = view.findViewById(R.id.btn_power_on_side);

        if (tvRoomName == null) {

        }
        if (btn_usb_1 != null) {
            btn_usb_1.setOnClickListener(v -> {

            });
        }
        if (btn_usb_2 != null) {
            btn_usb_2.setOnClickListener(v -> {

            });
        }
        if (btn_wifi_1 != null) {
            btn_wifi_1.setOnClickListener(v -> {

            });
        }
        if (btn_wifi_2 != null) {
            btn_wifi_2.setOnClickListener(v -> {

            });
        }
        if (btn_power_off_front != null) {
            btn_power_off_front.setOnClickListener(v -> {

            });
        }
        if (btn_power_on_front != null) {
            btn_power_on_front.setOnClickListener(v -> {

            });
        }
        if (btn_power_off_side != null) {
            btn_power_off_side.setOnClickListener(v -> {

            });
        }
        if (btn_power_on_side != null) {
            btn_power_on_side.setOnClickListener(v -> {

            });
        }

    }
}