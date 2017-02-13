package com.maibo.lys.xianhuicustomer.myactivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maibo.lys.xianhuicustomer.R;
import com.maibo.lys.xianhuicustomer.constants.PublicString;
import com.maibo.lys.xianhuicustomer.constants.WebAddress;
import com.maibo.lys.xianhuicustomer.myutils.PhoneFormatCheckUtils;
import com.maibo.lys.xianhuicustomer.myutils.Util;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import okhttp3.Call;

/**
 * Created by LYS on 2017/2/6.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.ll_head)
    LinearLayout ll_head;
    @Bind(R.id.ll_left)
    LinearLayout ll_left;
    @Bind(R.id.tv_center)
    TextView tv_center;
    @Bind(R.id.tv_right)
    TextView tv_right;
    @Bind(R.id.et_phone)
    EditText et_phone;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.et_code)
    EditText et_code;
    @Bind(R.id.tv_get_code)
    TextView tv_get_code;
    @Bind(R.id.tv_register)
    TextView tv_register;
    String phoneNumber="";
    String peopleType="customer";
    String codeType="sms";
    String password="";

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    //上传密码
                    String sign=Util.getSign(phoneNumber,peopleType,password,PublicString.PUBLICK_KEY);
                    uploadingPassword(phoneNumber,peopleType,password,sign);
                    break;
            }
        }
    };

    /**
     * 上传密码到服务器
     */
    private void uploadingPassword(String username,String type,String password,String sign) {
        OkHttpUtils
                .post()
                .url(WebAddress.CHANGE_LOGIN_PASSWORD)
                .addParams("username",username)
                .addParams("type",type)
                .addParams("password",password)
                .addParams("sign",sign)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast(R.string.net_errow);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JsonObject jsOb=new JsonParser().parse(response).getAsJsonObject();
                        String status=jsOb.get("status").getAsString();
                        String message=jsOb.get("message").getAsString();
                        if (status.equals("error")) showToast(message);
                    }
                });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        uploadingPassword("18300601712","customer","456789",Util.getSign("18300601712","customer","456789",PublicString.PUBLICK_KEY));
        initBar();
        initListener();
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        tv_get_code.setOnClickListener(this);
        tv_register.setOnClickListener(this);
    }

    /**
     * 初始化ToolBar
     */
    private void initBar() {
        ViewGroup.LayoutParams params = ll_head.getLayoutParams();
        params.height = ((Util.getScreenHeight(this) - getStatusBarHeight()) / 35) * 2;
        ll_head.setLayoutParams(params);
        ll_head.setBackgroundColor(Color.WHITE);
        tv_center.setText("注册");
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_get_code:
                //获取验证码
                phoneNumber=et_phone.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNumber)){
                    showToast("电话号码不能为空");
                }else{
                    if (PhoneFormatCheckUtils.isPhoneLegal(phoneNumber)){
                        tv_get_code.setClickable(false);
                        MyCountDownTimer mc = new MyCountDownTimer(60000, 1000);
                        mc.start();
                        String sign=Util.getSign(phoneNumber,peopleType, PublicString.PUBLICK_KEY);
                        showShortDialog();
                        getIdentifyingCode(phoneNumber,peopleType,codeType,sign);
                    }else{
                        showToast("错误的号码");
                    }
                }
                break;
            case R.id.tv_register:
                //开始注册
                password=et_password.getText().toString().trim();
                String code=et_code.getText().toString().trim();
                String sign2=Util.getSign(phoneNumber,peopleType,code,PublicString.PUBLICK_KEY);
                String strName[]={"手机号码","密码","验证码"};
                String strData[]={phoneNumber,password,code};
                String bufferStr=Util.getNullString(strName,strData);
                if (TextUtils.isEmpty(phoneNumber)){
                    showToast("请先获取验证码");
                    return;
                }
                if (TextUtils.isEmpty(bufferStr)){
                    if (PhoneFormatCheckUtils.isPhoneLegal(phoneNumber)){
                        showShortDialog();
                        startRegister(phoneNumber,peopleType,code,sign2);
                    }else{
                        showToast("错误的号码");
                    }
                }else{
                    showToast(bufferStr+" 不能为空");
                }
                break;
        }
    }

    /**
     * 获取验证码
     * @param phoneNumber
     * @param peopleType
     * @param codeType
     * @param sign
     */
    private void getIdentifyingCode(String phoneNumber,String peopleType,String codeType,String sign) {
        OkHttpUtils
                .post()
                .url(WebAddress.REGISTER_SMS_GOT)
                .addParams("mobile",phoneNumber)
                .addParams("type",peopleType)
                .addParams("sms_type",codeType)
                .addParams("sign",sign)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast(R.string.net_errow);
                        dismissShortDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("111:",response);
                        JsonObject jsOb=new JsonParser().parse(response).getAsJsonObject();
                        String status=jsOb.get("status").getAsString();
                        String message=jsOb.get("message").getAsString();
                        showToast(message);
                        dismissShortDialog();
                    }
                });
    }

    /**
     * 开始注册
     * @param mobile
     * @param type
     * @param sms_code
     * @param sign
     */
    private void startRegister(String mobile,String type,String sms_code,String sign) {
        OkHttpUtils
                .post()
                .url(WebAddress.REGISTER_SMS_VERIFY)
                .addParams("mobile",mobile)
                .addParams("type",type)
                .addParams("sms_code",sms_code)
                .addParams("sign",sign)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast(R.string.net_errow);
                        dismissShortDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JsonObject jsOb=new JsonParser().parse(response).getAsJsonObject();
                        String status=jsOb.get("status").getAsString();
                        String message=jsOb.get("message").getAsString();
                        if (status.equals("ok")){
                            finish();
                            showToast(message);
                            //注册成功后将密码上传到服务器
                            Message msg=Message.obtain();
                            msg.what=0;
                            handler.sendMessage(msg);
                        }else{
                            showToast(message);
                        }
                        dismissShortDialog();
                    }
                });

    }

    class MyCountDownTimer extends CountDownTimer {
        /**
         * @param millisInFuture    表示以毫秒为单位 倒计时的总数
         *                          <p>
         *                          例如 millisInFuture=1000 表示1秒
         * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick 方法
         *                          <p>
         *                          例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            tv_get_code.setClickable(true);
            tv_get_code.setText("获取验证码");
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tv_get_code.setText("" + millisUntilFinished / 1000 + "秒");
        }
    }
}
