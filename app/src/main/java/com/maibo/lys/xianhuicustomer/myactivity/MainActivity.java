package com.maibo.lys.xianhuicustomer.myactivity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.maibo.lys.xianhuicustomer.R;
import com.maibo.lys.xianhuicustomer.myfragment.HomePageFragment;
import com.maibo.lys.xianhuicustomer.myfragment.MineFragment;
import com.maibo.lys.xianhuicustomer.myfragment.OrderFormFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity implements View.OnClickListener{

    //Fragment数组界面
    private Class mFragmentArray[] = {HomePageFragment.class, OrderFormFragment.class,
            MineFragment.class};
    //存放图片数组
    private int mImageArray[] = {R.drawable.tab_home_btn,
            R.drawable.tab_order_btn, R.drawable.tab_mine_btn};
    //选修卡文字
    private String mTextArray[] = {"首页", "订单", "我的"};
    LayoutInflater mLayoutInflater;


    @Bind(android.R.id.tabhost)
    FragmentTabHost mTabHost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();

    }

    /**
     * 初始化view
     */
    private void initView() {
        initTabAndFragment();
    }

    /**
     * 初始化TabLayout
     */
    private void initTabAndFragment() {
        mLayoutInflater = LayoutInflater.from(this);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        // 得到fragment的个数
        int count = mFragmentArray.length;
        for (int i = 0; i < count; i++) {
            // 给每个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextArray[i])
                    .setIndicator(getTabItemView(i));
            // 将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, mFragmentArray[i], null);
            // 设置Tab按钮的背景
            mTabHost.setTag(i);
//            mTabHost.getTabWidget().getChildAt(i)
//                    .setBackgroundResource(R.drawable.selector_tab_background);
        }
        //设置首次进入时选中的Fragment
        mTabHost.setCurrentTab(0);
    }

    /**
     * 给每个Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = mLayoutInflater.inflate(R.layout.tab_item_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageArray[index]);
        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTextArray[index]);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }
    /**
     * 获取状态栏高度
     * @return
     */
    public  int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result =  getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
