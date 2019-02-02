package com.clxk.h.sdustcamp.base;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

import com.clxk.h.sdustcamp.Constans;

import butterknife.ButterKnife;


/**
 * Created by Chook_lxk on 18/12/27
 * @function Activity基类 添加6.0权限的动态获取
 */
public class MyBaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 权限判断方法
     * @param permissions
     * @return
     */
    public boolean hasPermission(String... permissions) {
        for(String permission : permissions) {
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 权限请求方法
     * @param code
     * @param permissions
     */
    public void requestPermission(int code, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constans.CALENDAR_CODE:
                doCalendarPermission();
                break;
            case Constans.CALL_PHONE_CODE:
                doCallPhonePermission();
                break;
            case Constans.CAMERA_CODE:
                doCameraPermission();
                break;
            case Constans.LOCATION_CODE:
                doLocationPermission();
                break;
            case Constans.MICROPHONE_CODE:
                doMicrophonePermission();
                break;
            case Constans.READ_EXTERNAL_CODE:
                doReadExternalPermission();
                break;
            case Constans.SMS_CODE:
                doSmsPermission();
                break;
            case Constans.WRITE_EXTERNAL_CODE:
                doWriteExternalPermission();
                break;
        }
    }

    /**
     * 默认的日历权限处理
     */
    public void doCalendarPermission(){}

    /**
     * 默认的打电话权限处理
     */
    public void doCallPhonePermission(){}

    /**
     * 默认的相册权限处理
     */
    public void doCameraPermission(){}

    /**
     * 默认的位置获取权限处理
     */
    public void doLocationPermission(){}

    /**
     * 默认的麦克风权限处理
     */
    public void doMicrophonePermission(){}

    /**
     * 默认的读SD权限处理
     */
    public void doReadExternalPermission(){}

    /**
     * 默认的写SD权限处理
     */
    public void doWriteExternalPermission(){}

    /**
     * 默认的短信权限处理
     */
    public void doSmsPermission(){}
}