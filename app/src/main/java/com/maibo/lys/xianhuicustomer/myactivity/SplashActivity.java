package com.maibo.lys.xianhuicustomer.myactivity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener;
import com.maibo.lys.xianhuicustomer.R;
import com.maibo.lys.xianhuicustomer.myapplication.MyApplication;
import com.maibo.lys.xianhuicustomer.myinterface.SampleMultiplePermissionListener;
import com.maibo.lys.xianhuicustomer.myutils.NetWorkUtils;

import butterknife.Bind;

/**
 * @desc 启动屏
 * Created by devilwwj on 16/1/23.
 */
public class SplashActivity extends BaseActivity {
    SharedPreferences sp;
    @Bind(R.id.imageView2)
    ImageView imageView2;
    @Bind(android.R.id.content)
    ViewGroup rootView;
    private MultiplePermissionsListener allPermissionsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //检查网络连接状态
        if (NetWorkUtils.isNetworkConnected(this)) {
            //网络连接正常
            //获取相关权限
//            if (Dexter.isRequestOngoing()) {
//                return;
//            }
//            Dexter.checkPermissions(allPermissionsListener, Manifest.permission.CAMERA,
//                    Manifest.permission.READ_EXTERNAL_STORAGE);
            afterGetPermission();

        } else {
            setContentView(R.layout.activity_splash);
            MyApplication.showToast(getApplicationContext(), "网络断开连接！");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 2000);

        }

    }

    /**
     * 在权限获取成功后
     */
    private void afterGetPermission() {
        //判断是否登录成功
        sp = getSharedPreferences("BaseDatas", MODE_PRIVATE);
        Boolean isSuccess = sp.getBoolean("isLogined", false);
        // 如果不是第一次启动app，则正常显示启动屏
        if (!isSuccess) {
            setContentView(R.layout.activity_splash);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    enterHomeActivity();
                }
            }, 2000);
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }


    private void enterHomeActivity() {
        Intent intent = new Intent(this, WelcomeGuideActivity.class);
        startActivity(intent);
        finish();
    }


    /**
     * 注册权限监听器
     */
    private void createPermissionListeners() {
//        PermissionListener feedbackViewPermissionListener = new SamplePermissionListener(this);
        MultiplePermissionsListener feedbackViewMultiplePermissionListener =
                new SampleMultiplePermissionListener(this);
        //多个权限
        if (allPermissionsListener == null) {
            allPermissionsListener =
                    new CompositeMultiplePermissionsListener(feedbackViewMultiplePermissionListener,
                            SnackbarOnAnyDeniedMultiplePermissionsListener.Builder.with(rootView,
                                    "请在设置中开启权限")
                                    .withOpenSettingsButton("设置")
                                    .build());
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void showPermissionRationale(final PermissionToken token) {
        new AlertDialog.Builder(this).setTitle("提醒")
                .setMessage("请开启该应用所需权限!")
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        token.cancelPermissionRequest();
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        token.continuePermissionRequest();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        token.cancelPermissionRequest();
                    }
                })
                .show();
    }

    /**
     * 获取权限成功
     *
     * @param permission
     */
    public void showPermissionGranted(String permission, int number) {

        if (number == 2) {
            afterGetPermission();
        }

    }

    /**
     * 获取权限失败
     *
     * @param permission
     * @param isPermanentlyDenied
     */
    public void showPermissionDenied(String permission, boolean isPermanentlyDenied) {

    }
}
