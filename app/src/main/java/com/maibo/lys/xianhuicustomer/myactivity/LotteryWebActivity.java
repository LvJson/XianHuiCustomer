package com.maibo.lys.xianhuicustomer.myactivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.maibo.lys.xianhuicustomer.R;
import com.maibo.lys.xianhuicustomer.constants.WebAddress;
import com.maibo.lys.xianhuicustomer.myutils.Util;

import butterknife.Bind;

/**
 * Created by LYS on 2016/12/16.
 */

public class LotteryWebActivity extends BaseActivity {

    @Bind(R.id.webview)
    WebView webView;
    @Bind(R.id.pro_bar)
    ProgressBar pro_bar;

    @Bind(R.id.ll_head)
    LinearLayout ll_head;
    @Bind(R.id.ll_left)
    LinearLayout ll_left;
    @Bind(R.id.tv_center)
    TextView tv_center;
    @Bind(R.id.tv_right)
    TextView tv_right;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery_web);
        initView();
        initBar();
    }

    private void initView() {
        adapterLitterBar(ll_head);
        initWebView();

    }
    /**
     * 初始化ToolBar
     */
    private void initBar() {
        ViewGroup.LayoutParams params = ll_head.getLayoutParams();
        params.height = ((Util.getScreenHeight(this) - getStatusBarHeight()) / 35) * 2;
        ll_head.setLayoutParams(params);
        ll_head.setBackgroundColor(Color.WHITE);
        tv_center.setText("抽奖");
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 初始化webview
     */
    private void initWebView() {
        //为了支持JavaScript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

//        settings.setTextZoom(55);//可缩放字体，控制字体大小范围[1,100]
//        settings.setTextSize(WebSettings.TextSize.SMALLER);
        //优先使用缓存
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//LOAD_NO_CACHE:不使用缓存

        webView.loadUrl(WebAddress.LOTTERY);
        //控制网页打开位置1、返回值为true时在当前应用中打开 2、为false时在浏览器中打开
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        //监听webview中内容加载进度
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    // 网页加载完成
                    pro_bar.setVisibility(View.GONE);

                } else {
                    // 加载中
                    pro_bar.setVisibility(View.VISIBLE);
                    pro_bar.setProgress(newProgress);
                }

            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
