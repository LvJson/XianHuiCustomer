package com.maibo.lys.xianhuicustomer.myfragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maibo.lys.xianhuicustomer.R;
import com.maibo.lys.xianhuicustomer.myactivity.LotteryWebActivity;
import com.maibo.lys.xianhuicustomer.myactivity.OrderEvaluateActivity;
import com.maibo.lys.xianhuicustomer.myactivity.SplashActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LYS on 2017/1/13.
 */

public class MineFragment extends Fragment implements View.OnClickListener{
    @Bind(R.id.tv_quit_app)
    TextView tv_quit_app;
    @Bind(R.id.tv_invite)
    TextView tv_invite;

    private View rootView;
    SharedPreferences sp;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        if(rootView==null){
            rootView=inflater.inflate(R.layout.fragment_mine, null);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        ButterKnife.bind(this,rootView);
        initView();
        initListener();

        return rootView;
    }

    /**
     * 初始化view
     */
    private void initView() {
        sp=getActivity().getSharedPreferences("BaseDatas", Context.MODE_PRIVATE);
    }

    private void initListener() {
        tv_quit_app.setOnClickListener(this);
        tv_invite.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    @OnClick(R.id.tv_evaluate)
    public void evaluate(View view){
        startActivity(new Intent(getActivity(), OrderEvaluateActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_quit_app:
                quitApp();
                break;
            case R.id.tv_invite:
                startActivity(new Intent(getActivity(), LotteryWebActivity.class));
                break;
        }
    }

    /**
     * 退出应用
     */
    private void quitApp() {
        AlertDialog ad = new AlertDialog.Builder(getActivity()).create();
        ad.setTitle("闲惠");
        ad.setMessage("确定退出应用吗？");
        ad.setButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sp.edit().clear().commit();
                getActivity().finish();
                startActivity(new Intent(getActivity(), SplashActivity.class));
            }
        });
        ad.setButton2("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        ad.show();
    }
}
