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
        viewModel.setIspFourInchPanel(isFourInchPanel());



    }

    private boolean isFourInchPanel() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return dm.widthPixels == 480 && dm.heightPixels == 480;
    }


}