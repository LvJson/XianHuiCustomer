package com.maibo.lys.xianhuicustomer.myapplication;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.avos.avoscloud.AVOSCloud;
import com.karumi.dexter.Dexter;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * Created by LYS on 2017/2/8.
 */

public class MyApplication extends Application {
    // 专用ID和KEY
    private final String APP_ID  = "wciU4iW0lEVmc9EJ9WzmhyGw-gzGzoHsz";
    private final String APP_KEY = "eXUtSMYSxVCJhE4IHOiGWabv";
    public static int TAG=0;
    @Override
    public void onCreate() {
        super.onCreate();
        TAG=0;
        AVOSCloud.initialize(this,APP_ID, APP_KEY);
        ZXingLibrary.initDisplayOpinion(this);
        Dexter.initialize(getApplicationContext());

    }


    public static void showToast(Context context, String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }
}
