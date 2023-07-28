package com.tencent.shadow.sample.plugin;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tencent.shadow.sample.plugin.utils.LogUtils;

public class TestActivityOrientationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_orientation);

        try {
            Intent intent = getIntent();
            int orientation = intent.getIntExtra("orientation",-1);
            setRequestedOrientation(orientation);
        }catch (Exception e){
            Toast.makeText(TestActivityOrientationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            LogUtils.logE(e.getMessage());
        }
    }

    public void setOrientation(View view) {
        int orientation = getRequestedOrientation();
        if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
