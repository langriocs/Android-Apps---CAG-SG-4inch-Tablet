package com.avl.cagApp.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avl.cagApp.viewmodel.ControlScreen1ViewModel;
import com.avl.cagApp.R;

public class ControlScreen1 extends Fragment {

    private ControlScreen1ViewModel mViewModel;

    public static ControlScreen1 newInstance() {
        return new ControlScreen1();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_control_screen1, container, false);
    }

}