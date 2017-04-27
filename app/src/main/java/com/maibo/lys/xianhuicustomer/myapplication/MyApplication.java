package com.maibo.lys.xianhuicustomer.myapplication;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.webkit.WebSettings;
import android.widget.Toast;

import com.avos.avoscloud.AVOSCloud;
import com.karumi.dexter.Dexter;
import com.maibo.lys.xianhuicustomer.myutils.UserAgentInterceptor;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

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

        //OKhttp初始化配置
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new UserAgentInterceptor(getUserAgent()))
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);


    }


    public static void showToast(Context context, String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }

    /**
     * 用于获取当前手机相关信息
     * @return
     */
    private  String getUserAgent() {
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(getApplicationContext());
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
