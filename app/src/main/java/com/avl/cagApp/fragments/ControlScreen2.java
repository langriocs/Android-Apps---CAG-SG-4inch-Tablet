package com.avl.cagApp.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avl.cagApp.R;
import com.avl.cagApp.viewmodel.ControlScreen2ViewModel;

public class ControlScreen2 extends Fragment {

    private ControlScreen2ViewModel mViewModel;

    public static ControlScreen2 newInstance() {
        return new ControlScreen2();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_control_screen2, container, false);
    }


}