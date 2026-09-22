package com.avl.cagApp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avl.cagApp.R;
import com.avl.cagApp.viewmodel.MasterPanelViewModel;

public class MasterPanel extends Fragment {

    private MasterPanelViewModel mViewModel;

    public static MasterPanel newInstance() {
        return new MasterPanel();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_master_panel, container, false);
    }



}