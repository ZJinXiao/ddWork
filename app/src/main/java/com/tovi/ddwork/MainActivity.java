package com.tovi.ddwork;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.accessibility.AccessibilityManager;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.tovi.ddwork.service.Service;

public class MainActivity extends AppCompatActivity implements AccessibilityManager.AccessibilityStateChangeListener {

    private Switch state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        state = (Switch) findViewById(R.id.state);
        state.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Service.start(MainActivity.this, 1 * 60);
                    Util.wakelock(MainActivity.this);
                } else {
                    Service.stop(MainActivity.this);
                    Util.dormancy();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 更新服务状态
        updateServiceStatus();
    }

    @Override
    public void onAccessibilityStateChanged(boolean enabled) {
        // 更新服务状态
        updateServiceStatus();
    }

    /**
     * 更新当前 WorkService 显示状态
     */
    private void updateServiceStatus() {
        state.setChecked(Service.isRunning(this));
    }
}
