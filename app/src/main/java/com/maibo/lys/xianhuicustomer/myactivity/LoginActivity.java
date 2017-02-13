package com.maibo.lys.xianhuicustomer.myactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maibo.lys.xianhuicustomer.R;
import com.maibo.lys.xianhuicustomer.constants.PublicString;
import com.maibo.lys.xianhuicustomer.constants.WebAddress;
import com.maibo.lys.xianhuicustomer.myapplication.MyApplication;
import com.maibo.lys.xianhuicustomer.myutils.Util;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import okhttp3.Call;

/**
 * Created by LYS on 2017/2/6.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.ll_head)
    LinearLayout ll_head;
    @Bind(R.id.ll_left)
    LinearLayout ll_left;
    @Bind(R.id.tv_center)
    TextView tv_center;
    @Bind(R.id.tv_right)
    TextView tv_right;
    @Bind(R.id.iv_icon)
    ImageView iv_icon;
    @Bind(R.id.activity_login_et_username)
    EditText numberView;
    @Bind(R.id.activity_login_et_password)
    EditText passwordView;
    @Bind(R.id.btn_register)
    TextView btnRegister;
    @Bind(R.id.btn_login)
    TextView btnLogin;
    @Bind(R.id.tv_change_shop)
    LinearLayout tv_change_shop;
    @Bind(R.id.ll_logig_interface)
    LinearLayout ll_logig_interface;
    SharedPreferences sp;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    startActivity(MainActivity1.class);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sp = getSharedPreferences("BaseDatas", MODE_PRIVATE);
        if (sp.getBoolean("isLogined", false)) {

            if (MyApplication.TAG== 0) {
                //显示启动页
                ll_logig_interface.setVisibility(View.GONE);
                tv_change_shop.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(MainActivity1.class);
                        finish();
                    }
                }, 2000);
                MyApplication.TAG = 1;
            } else {
                startActivity(MainActivity1.class);
                finish();
            }
        } else {
            initBar();
            setHeightAndWidth();
            initListener();
        }
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    /**
     * 初始化ToolBar
     */
    private void initBar() {
        ViewGroup.LayoutParams params = ll_head.getLayoutParams();
        params.height = ((Util.getScreenHeight(this) - getStatusBarHeight()) / 35) * 2;
        ll_head.setLayoutParams(params);
        ll_head.setBackgroundColor(Color.WHITE);
        tv_center.setText("登录");
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SplashActivity.class));
                finish();
            }
        });
    }

    /**
     * 适配界面
     */
    private void setHeightAndWidth() {
        View views[] = {iv_icon, numberView, passwordView, btnRegister, btnLogin};
        int height[] = {viewHeight * 1 / 6, viewHeight * 20 / 255, viewHeight * 20 / 255, viewHeight * 20 / 255, viewHeight * 20 / 255};
        int widths[] = null;
        setViewHeightAndWidth(views, height, widths);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                startActivity(RegisterActivity.class);
                break;
            case R.id.btn_login:
                String phoneNumber = numberView.getText().toString().trim();
                String password = passwordView.getText().toString().trim();
                String strName[] = {"电话号码", "密码"};
                String strDatas[] = {phoneNumber, password};
                String result = Util.getNullString(strName, strDatas);
                if (TextUtils.isEmpty(result)) {
                    //登录客户端
                    showLongDialog();
                    LoginMobile(phoneNumber, password, PublicString.PEOPLE_TYPE);
                } else {
                    showToast(result + " 不能为空");
                }
                break;
        }
    }

    /**
     * 登录客户端
     *
     * @param mobile
     * @param password
     * @param type
     */
    private void LoginMobile(String mobile, String password, String type) {
        OkHttpUtils
                .post()
                .url(WebAddress.LOGIN_MOBILE)
                .addParams("mobile", mobile)
                .addParams("password", password)
                .addParams("type", type)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast(R.string.net_errow);
                        dismissLongDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JsonObject jsOb = new JsonParser().parse(response).getAsJsonObject();
                        String status = jsOb.get("status").getAsString();
                        String message = jsOb.get("message").getAsString();
                        if (status.equals("ok")) {
                            showToast(message);
                            analysisAndSaveDatas(jsOb);
                            Message msg = Message.obtain();
                            msg.what = 0;
                            handler.sendMessage(msg);
                        } else {
                            showToast(message);
                        }
                        dismissLongDialog();
                    }
                });
    }

    /**
     * 解析登录数据并保存到sp文件中
     *
     * @param jsOb
     */
    private void analysisAndSaveDatas(JsonObject jsOb) {
        JsonObject data = jsOb.get("data").getAsJsonObject();
        String token = "";
        String user_id = "";
        String user_name = "";
        String display_name = "";
        String org_id = "";
        String org_name = "";
        String user_type = "";
        String avator_url = "";
        String mobile = "";
        String init_login_password = "";
        String guid = "";
        if (!data.get("token").isJsonNull())
            token = data.get("token").getAsString();
        if (!data.get("user_id").isJsonNull())
            user_id = data.get("user_id").getAsString();
        if (!data.get("user_name").isJsonNull())
            user_name = data.get("user_name").getAsString();
        if (!data.get("display_name").isJsonNull())
            display_name = data.get("display_name").getAsString();
        if (!data.get("org_id").isJsonNull())
            org_id = data.get("org_id").getAsString();
        if (!data.get("org_name").isJsonNull())
            org_name = data.get("org_name").getAsString();
        if (!data.get("user_type").isJsonNull())
            user_type = data.get("user_type").getAsString();
        if (!data.get("avator_url").isJsonNull())
            avator_url = data.get("avator_url").getAsString();
        if (!data.get("mobile").isJsonNull())
            mobile = data.get("mobile").getAsString();
        if (!data.get("init_login_password").isJsonNull())
            init_login_password = data.get("init_login_password").getAsString();
        if (!data.get("guid").isJsonNull())
            guid = data.get("guid").getAsString();
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isLogined", true);
        editor.putString("token", token);
        editor.putString("user_id", user_id);
        editor.putString("user_name", user_name);
        editor.putString("display_name", display_name);
        editor.putString("org_id", org_id);
        editor.putString("org_name", org_name);
        editor.putString("user_type", user_type);
        editor.putString("avator_url", avator_url);
        editor.putString("mobile", mobile);
        editor.putString("init_login_password", init_login_password);
        editor.putString("guid", guid);
        editor.commit();
    }
}
