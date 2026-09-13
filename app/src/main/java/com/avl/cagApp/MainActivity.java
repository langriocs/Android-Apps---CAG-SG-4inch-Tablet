package com.avl.cagApp;

import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.lifecycle.ViewModelProvider;

import com.avl.cagApp.fragments.CustomAlertDialog;
import com.avl.cagApp.libs.MyLibUtil;
import com.avl.cagApp.model.vo.ControlDevice;
import com.avl.cagApp.model.vo.RoomDevice;
import com.avl.cagApp.viewmodel.ShareViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Hide system bars for a truly full screen immersive experience
        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController != null) {
            windowInsetsController.setSystemBarsBehavior(
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            );
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            return insets;
        });

        ShareViewModel viewModel = new ViewModelProvider(this).get(ShareViewModel.class);
        viewModel.getControlRoomDevices().observe(this, data -> {
            if (data == null) {
                showAlert();
            }
        });

        viewModel.setIspFourInchPanel(isFourInchPanel());

        final String ipAddress = "192.168.1.10";
//        final String ipAddress = MyLibUtil.getIPAddress(true);
        viewModel.fetchControlDeviceByIpAddress(ipAddress);

    }

    private boolean isFourInchPanel() {
        DisplayMetrics dm = getResources().getDisplayMetrics();

        return dm.widthPixels == 480 && dm.heightPixels == 480;
    }

    private void showAlert() {
        CustomAlertDialog alertScreenDialog = new CustomAlertDialog();
        alertScreenDialog.setTitle("Changi Airport Group");
        alertScreenDialog.setMessage("Device IP Address not found! Please contact the admin.");
        alertScreenDialog.show(getSupportFragmentManager(), "alert dialog");
    }
}