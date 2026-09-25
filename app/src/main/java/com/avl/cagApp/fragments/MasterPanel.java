package com.avl.cagApp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avl.cagApp.R;
import com.avl.cagApp.adapter.ControlSwitchAdapter;
import com.avl.cagApp.adapter.DisplayOutputAdapter;
import com.avl.cagApp.model.ControlSwitchItem;
import com.avl.cagApp.model.DisplayOutputItem;
import com.avl.cagApp.viewmodel.MasterPanelViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MasterPanel extends Fragment {

    private MasterPanelViewModel mViewModel;
    private RecyclerView rvOutput;
    private RecyclerView rvControl;
    private List<DisplayOutputItem> displayOutputItems;
    private List<ControlSwitchItem> controlSwitchItems;
    private Integer buttonInputSelected = 0;

    private final Map<Integer, View> inputButtons = new HashMap<>();

    private View layoutVideo;
    private View layoutControl;
    private View btnVideo;
    private View btnControl;
    private View btnVideoCtrl;
    private View btnControlCtrl;

    public static MasterPanel newInstance() {
        return new MasterPanel();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(requireActivity()).get(MasterPanelViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_master_panel, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupInputButtons(view);
        setupOutputDisplay(view);
        setupControlSwitch(view);
        setupModeNavigation(view);
        observeViewModel();
    }

    private void setSelectedInput(Integer selectedInput) {
        buttonInputSelected = selectedInput;

        inputButtons.forEach((key, buttonView) -> {
            boolean isSelected = (Objects.equals(key, selectedInput));
            buttonView.setSelected(isSelected);
        });
    }

    private void setupInputButtons(View view) {
        inputButtons.put(1, view.findViewById(R.id.btn_hall1_usb1));
        inputButtons.put(2, view.findViewById(R.id.btn_hall1_usb2));
        inputButtons.put(3, view.findViewById(R.id.btn_hall1_wireless));
        inputButtons.put(4, view.findViewById(R.id.btn_hall2_usb1));
        inputButtons.put(5, view.findViewById(R.id.btn_hall2_usb2));
        inputButtons.put(6, view.findViewById(R.id.btn_hall2_wireless));
        inputButtons.put(7, view.findViewById(R.id.btn_cag_usb1));
        inputButtons.put(8, view.findViewById(R.id.btn_cag_usb2));
        inputButtons.put(9, view.findViewById(R.id.btn_cag_usb3));
        inputButtons.put(10, view.findViewById(R.id.btn_cag_wireless1));
        inputButtons.put(11, view.findViewById(R.id.btn_cag_wireless2));
        inputButtons.put(12, view.findViewById(R.id.btn_cag_starhub));
        inputButtons.put(13, view.findViewById(R.id.btn_cag_apple_tv));
        inputButtons.put(14, view.findViewById(R.id.btn_cag_cctv));

        inputButtons.forEach((key, buttonView) -> {
            if (buttonView != null) {
                buttonView.setOnClickListener(view1 -> setSelectedInput(key));
            }
        });
    }

    private void setupModeNavigation(View view) {
        layoutVideo = view.findViewById(R.id.layout_master_video);
        layoutControl = view.findViewById(R.id.layout_master_control);

        btnVideo = view.findViewById(R.id.btn_video);
        btnControl = view.findViewById(R.id.btn_control);

        if (btnVideo != null) {
            btnVideo.setOnClickListener(v -> showVideoLayout());
        }
        if (btnVideoCtrl != null) {
            btnVideoCtrl.setOnClickListener(v -> showVideoLayout());
        }

        if (btnControl != null) {
            btnControl.setOnClickListener(v -> showControlLayout());
        }
        if (btnControlCtrl != null) {
            btnControlCtrl.setOnClickListener(v -> showControlLayout());
        }

        showVideoLayout();
    }

    private void showVideoLayout() {
        if (layoutVideo != null) {
            layoutVideo.setVisibility(View.VISIBLE);
        }
        if (layoutControl != null) {
            layoutControl.setVisibility(View.GONE);
        }

        updateModeButtonsHighlight(true);
    }

    private void showControlLayout() {
        if (layoutVideo != null) {
            layoutVideo.setVisibility(View.GONE);
        }
        if (layoutControl != null) {
            layoutControl.setVisibility(View.VISIBLE);
        }

        updateModeButtonsHighlight(false);
    }

    private void updateModeButtonsHighlight(boolean isVideoMode) {
        if (btnVideo != null) {
            btnVideo.setSelected(isVideoMode);
            btnVideo.setBackgroundResource(isVideoMode ? R.drawable.bg_rounded_card_selected : R.drawable.bg_rounded_card);
        }
        if (btnVideoCtrl != null) {
            btnVideoCtrl.setSelected(isVideoMode);
            btnVideoCtrl.setBackgroundResource(isVideoMode ? R.drawable.bg_rounded_card_selected : R.drawable.bg_rounded_card);
        }

        if (btnControl != null) {
            btnControl.setSelected(!isVideoMode);
            btnControl.setBackgroundResource(!isVideoMode ? R.drawable.bg_rounded_card_selected : R.drawable.bg_rounded_card);
        }
        if (btnControlCtrl != null) {
            btnControlCtrl.setSelected(!isVideoMode);
            btnControlCtrl.setBackgroundResource(!isVideoMode ? R.drawable.bg_rounded_card_selected : R.drawable.bg_rounded_card);
        }
    }

    private void setupOutputDisplay(View view) {
        rvOutput = view.findViewById(R.id.rv_display_output);
        displayOutputItems = new ArrayList<>();
        displayOutputItems.add(new DisplayOutputItem("Hall 1 LED Wall IN 1", R.drawable.ic_display, 1));
        displayOutputItems.add(new DisplayOutputItem("Hall 1 LED Wall IN 2", R.drawable.ic_display, 2));
        displayOutputItems.add(new DisplayOutputItem("Hall 2 LED Wall IN 1", R.drawable.ic_display, 3));
        displayOutputItems.add(new DisplayOutputItem("Hall 2 LED Wall IN 2", R.drawable.ic_display, 4));
        displayOutputItems.add(new DisplayOutputItem("Hall 2 LED Wall IN 1", R.drawable.ic_display, 5));
        displayOutputItems.add(new DisplayOutputItem("Hall 2 LED Wall IN 2", R.drawable.ic_display,6));
        displayOutputItems.add(new DisplayOutputItem("Briefing Room 1 & 2", R.drawable.ic_display,7));
        displayOutputItems.add(new DisplayOutputItem("CAG OPS Rm Front TV Left", R.drawable.ic_display,9));
        displayOutputItems.add(new DisplayOutputItem("CAG OPS Rm Front TV Right", R.drawable.ic_display,10));
        displayOutputItems.add(new DisplayOutputItem("CAG OPS Rm Side TV Left", R.drawable.ic_display, 11));
        displayOutputItems.add(new DisplayOutputItem("CAG OPS Rm Side TV Right", R.drawable.ic_display, 12));
        displayOutputItems.add(new DisplayOutputItem("Board Room", R.drawable.ic_display, 13));
        displayOutputItems.add(new DisplayOutputItem("Training Room", R.drawable.ic_display, 14));
        displayOutputItems.add(new DisplayOutputItem("CARE OPS Room", R.drawable.ic_display, 15));
        displayOutputItems.add(new DisplayOutputItem("Airline Room 1", R.drawable.ic_display, 16));
        displayOutputItems.add(new DisplayOutputItem("Airline Room 2", R.drawable.ic_display, 17));
        displayOutputItems.add(new DisplayOutputItem("CAG Meeting Room", R.drawable.ic_display, 18));
        displayOutputItems.add(new DisplayOutputItem("PMA Holding Room", R.drawable.ic_display,19));
        displayOutputItems.add(new DisplayOutputItem("Police OPS Room", R.drawable.ic_display,20));
        displayOutputItems.add(new DisplayOutputItem("CID OPS Room", R.drawable.ic_display,21));

        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(),8, GridLayoutManager.VERTICAL,false);

        rvOutput.setLayoutManager(layoutManager);

        DisplayOutputAdapter adapter = new DisplayOutputAdapter(displayOutputItems, item -> {

            if(item == null) {
                return;
            }

            if (buttonInputSelected == null || buttonInputSelected == 0) {
                return;
            }

            List<Integer> output = new ArrayList<>();
            output.add(item.getPortNumber());
            // on Selected item
            mViewModel.routeAV( buttonInputSelected, output);
        });

        rvOutput.setAdapter(adapter);
    }

    private void setupControlSwitch(View view) {
        rvControl = view.findViewById(R.id.rv_control_switch);
        controlSwitchItems = new ArrayList<>();
        controlSwitchItems.add(new ControlSwitchItem("HALL 1 LED Wall", false, 1));
        controlSwitchItems.add(new ControlSwitchItem("Briefing Room 1", false, 2));
        controlSwitchItems.add(new ControlSwitchItem("Training Room LED", false, 3));
        controlSwitchItems.add(new ControlSwitchItem("CAG Meeting Room", false, 4));
        controlSwitchItems.add(new ControlSwitchItem("Airline Room 1", false,5));
        controlSwitchItems.add(new ControlSwitchItem("HALL 2 Front LED WALL", false, 6));
        controlSwitchItems.add(new ControlSwitchItem("Briefing Room 2", false, 7));
        controlSwitchItems.add(new ControlSwitchItem("CAG OPS Room Front Left", false, 8));
        controlSwitchItems.add(new ControlSwitchItem("PMA Holding Room", false, 9));
        controlSwitchItems.add(new ControlSwitchItem("Airline Room 2", false, 10));
        controlSwitchItems.add(new ControlSwitchItem("HALL 2 Side LED WALL", false, 11));
        controlSwitchItems.add(new ControlSwitchItem("Boardroom Front LED", false, 12));
        controlSwitchItems.add(new ControlSwitchItem("CAG OPS Room Front Right", false, 13));
        controlSwitchItems.add(new ControlSwitchItem("Police OPS Room", false, 14));
        controlSwitchItems.add(new ControlSwitchItem("CARE OPS Room Front", false, 15));
        controlSwitchItems.add(new ControlSwitchItem("Temp", false, 0));
        controlSwitchItems.add(new ControlSwitchItem("Boardroom Side LED", false, 16));
        controlSwitchItems.add(new ControlSwitchItem("CAG OPS Room Side Left", false, 17));
        controlSwitchItems.add(new ControlSwitchItem("CID OPS Room", false, 18));
        controlSwitchItems.add(new ControlSwitchItem("CARE OPS Room Side", false, 19));

        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(),5, GridLayoutManager.VERTICAL,false);
        rvControl.setLayoutManager(layoutManager);

        ControlSwitchAdapter adapter = new ControlSwitchAdapter(controlSwitchItems, item -> {
            mViewModel.switchControl(item);
        });
        rvControl.setAdapter(adapter);
    }

    private void observeViewModel() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (rvOutput != null) {
            rvOutput.setAdapter(null);
        }
        rvOutput = null;
        inputButtons.clear();
        displayOutputItems.clear();
    }
}
