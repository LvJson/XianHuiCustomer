package com.maibo.lys.xianhuicustomer.myactivity;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.SaveCallback;
import com.maibo.lys.xianhuicustomer.R;
import com.maibo.lys.xianhuicustomer.myfragment.HomePagerRecyclerFragment;
import com.maibo.lys.xianhuicustomer.myfragment.MineFragment;
import com.maibo.lys.xianhuicustomer.myfragment.OrderFormFragment;
import com.maibo.lys.xianhuicustomer.myutils.Util;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity1 extends FragmentActivity implements View.OnClickListener{
//
//    private FragmentManager fragmentManager;
//    private FragmentTransaction transaction;
    @Bind(R.id.ll_msg)
    LinearLayout ll_msg;
    @Bind(R.id.ll_man)
    LinearLayout ll_man;
    @Bind(R.id.ll_setting)
    LinearLayout ll_setting;
    @Bind(R.id.imageview_msg)
    ImageView imageview_msg;
    @Bind(R.id.imageview_man)
    ImageView imageview_man;
    @Bind(R.id.imageview_setting)
    ImageView imageview_setting;
    @Bind(R.id.textview_msg)
    TextView textview_msg;
    @Bind(R.id.textview_man)
    TextView textview_man;
    @Bind(R.id.textview_setting)
    TextView textview_setting;
    @Bind(R.id.ll_tab)
    LinearLayout ll_tab;
    SharedPreferences sp;
    Fragment homeFragment;
    Fragment sortFragment;
    Fragment personFragment;
    Fragment mFragments[];
    private int mIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        ButterKnife.bind(this);
        initData();
        registPushService();
        initFragment();
        setTabHeightAndWidth();
        initListener();
    }

    /**
     * 获取基本数据
     */
    private void initData() {
        sp=getSharedPreferences("BaseDatas",MODE_PRIVATE);
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        ll_msg.setOnClickListener(this);
        ll_man.setOnClickListener(this);
        ll_setting.setOnClickListener(this);
    }
    private void initFragment() {
        homeFragment = new HomePagerRecyclerFragment();
        sortFragment = new OrderFormFragment();
        personFragment = new MineFragment();
        //添加到数组
        mFragments = new Fragment[]{homeFragment, sortFragment, personFragment};
        //开启事务
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //添加首页
        ft.add(R.id.content, homeFragment).commit();
        //默认设置为第1个
        mIndex = 0;
        setIndexSelected(0);
    }
    private void setIndexSelected(int index) {
        if (mIndex == index) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        //隐藏
        ft.hide(mFragments[mIndex]);
        //判断是否添加
        if (!mFragments[index].isAdded()) {
            ft.add(R.id.content, mFragments[index]).show(mFragments[index]);
        } else {
            ft.show(mFragments[index]);
        }
        ft.commit();
        //再次赋值
        mIndex = index;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_msg:
                setIndexSelected(0);
                selectImageResouse(imageview_msg,imageview_man,imageview_setting,0);
                selectTextColor(textview_msg,textview_man,textview_setting);
                break;
            case R.id.ll_man:
                setIndexSelected(1);
                selectImageResouse(imageview_msg,imageview_man,imageview_setting,1);
                selectTextColor(textview_man,textview_msg,textview_setting);
                break;
            case R.id.ll_setting:
                setIndexSelected(2);
                selectImageResouse(imageview_msg,imageview_man,imageview_setting,2);
                selectTextColor(textview_setting,textview_man,textview_msg);
                break;
        }
    }
    /**
     * 动态设置底部导航栏的宽和高
     */
    private void setTabHeightAndWidth() {
        int viewHeight= (Util.getScreenHeight(this)-getStatusBarHeight())/35;
        LinearLayout.LayoutParams params_msg= (LinearLayout.LayoutParams) imageview_msg.getLayoutParams();
        params_msg.height=(int)(viewHeight*1.6);
        params_msg.width=(int)(viewHeight*1.6);
        imageview_msg.setLayoutParams(params_msg);

        LinearLayout.LayoutParams params_man= (LinearLayout.LayoutParams) imageview_man.getLayoutParams();
        params_man.height=(int)(viewHeight*1.6);
        params_man.width=(int)(viewHeight*1.6);
        imageview_man.setLayoutParams(params_man);

        LinearLayout.LayoutParams params_setting= (LinearLayout.LayoutParams) imageview_setting.getLayoutParams();
        params_setting.height=(int)(viewHeight*1.6);
        params_setting.width=(int)(viewHeight*1.6);
        imageview_setting.setLayoutParams(params_setting);
    }

    /**
     * 导航栏文字颜色选择器
     */
    private void selectTextColor(TextView a,TextView b,TextView c) {
        a.setTextColor(getResources().getColor(R.color.colorLightYellow));
        b.setTextColor(getResources().getColor(R.color.gray_weixin));
        c.setTextColor(getResources().getColor(R.color.gray_weixin));
    }

    /**
     * 导航栏按钮选择器
     */
    private void selectImageResouse(ImageView a,ImageView b,ImageView c,int d) {
        if (d==0){
            a.setImageResource(R.mipmap.msg_yes);
            b.setImageResource(R.mipmap.setting_no);
            c.setImageResource(R.mipmap.man_no);
        }else if (d==1){
            a.setImageResource(R.mipmap.msg_no);
            b.setImageResource(R.mipmap.setting_yes);
            c.setImageResource(R.mipmap.man_no);
        }else if (d==2){
            a.setImageResource(R.mipmap.msg_no);
            b.setImageResource(R.mipmap.setting_no);
            c.setImageResource(R.mipmap.man_yes);
        }

    }
    /**
     * 获取状态栏高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 注册Leancloud推送服务
     */
    private void registPushService() {
        // 设置默认打开的 Activity
        PushService.setDefaultPushCallback(this, MainActivity.class);
        //订阅频道
        //在登录成功后注册频道，只要在保存 Installation 之前调用 PushService.subscribe 方法
        PushService.subscribe(this, sp.getString("guid", null), MainActivity.class);
        // 保存 installation 到服务器
        AVInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                AVInstallation.getCurrentInstallation().saveInBackground();
                String installationId = AVInstallation.getCurrentInstallation().getInstallationId();
            }
        });
    }
}
