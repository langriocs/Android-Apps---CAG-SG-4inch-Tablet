package com.avl.cagApp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avl.cagApp.R;
import com.avl.cagApp.viewmodel.ShareViewModel;
import com.google.android.material.button.MaterialButton;

public class SplashScreen extends Fragment {

    private TextView tvRoomName;
    private MaterialButton btnPressStart;

    private boolean isFourPanel = false;

    public static SplashScreen newInstance() {
        return new SplashScreen();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvRoomName = view.findViewById(R.id.room_name_tv);

        btnPressStart = view.findViewById(R.id.btn_press_start);

        if (btnPressStart == null) {
            return;
        }

        btnPressStart.setOnClickListener(v -> {
            proceedToNextScreen(v);
        });

        view.setOnClickListener(v -> {
            proceedToNextScreen(v);
        });

        ShareViewModel shareViewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);
        shareViewModel.getControlDevice().observe(getViewLifecycleOwner(), deviceInfo -> {
            if (deviceInfo != null) {
                tvRoomName.setText(deviceInfo.getRoomName());
            }
        });

        isFourPanel = shareViewModel.isFourInchPanel();
    }

    private void proceedToNextScreen(View v) {
        if (isFourPanel) {
            Navigation.findNavController(v).navigate(R.id.action_splashScreen_to_controlScreen);
        } else {
            Navigation.findNavController(v).navigate(R.id.action_splashScreen1_to_controlScreen1);
        }
    }

}