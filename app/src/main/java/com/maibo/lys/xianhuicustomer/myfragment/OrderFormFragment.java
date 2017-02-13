package com.maibo.lys.xianhuicustomer.myfragment;

import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maibo.lys.xianhuicustomer.R;
import com.maibo.lys.xianhuicustomer.myactivity.MainActivity1;
import com.maibo.lys.xianhuicustomer.myutils.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by LYS on 2017/1/13.
 */

public class OrderFormFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.ll_head)
    LinearLayout ll_head;
    @Bind(R.id.ll_left)
    LinearLayout ll_left;
    @Bind(R.id.tv_center)
    TextView tv_center;

    @Bind(R.id.tab)
    LinearLayout tab;
    @Bind(R.id.tv_all)
    TextView tv_all;
    @Bind(R.id.tv_no_evaluate)
    TextView tv_no_evaluate;
    @Bind(R.id.tv_wait_lottery)
    TextView tv_wait_lotter;
    @Bind(R.id.iv_over)
    ImageView iv_over;
    @Bind(R.id.vp_mywork)
    ViewPager vp_mywork;

    private View rootView;
    private AllOrdersFragment mAllOrderFragment;
    private NoAssessFragment mNoAssessFragment;
    private WaitLotteryFragment mWaitLotteryFragment;
    List<Fragment> fragmentList;

    private int bmpw = 0; // 游标宽度
    private int offset = 0;// // 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_order_form, null);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        currIndex = 0;
        ButterKnife.bind(this, rootView);
        initBar();
        initView();
        return rootView;
    }

    /**
     * 初始化View
     */
    private void initView() {
        mAllOrderFragment = new AllOrdersFragment();
        mNoAssessFragment = new NoAssessFragment();
        mWaitLotteryFragment = new WaitLotteryFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(mAllOrderFragment);
        fragmentList.add(mNoAssessFragment);
        fragmentList.add(mWaitLotteryFragment);
        tv_all.setOnClickListener(this);
        tv_no_evaluate.setOnClickListener(this);
        tv_wait_lotter.setOnClickListener(this);
        initCursorPos();
        vp_mywork.setOffscreenPageLimit(3);
        vp_mywork.setAdapter(new MyFragmentAdapters(getChildFragmentManager()));
        vp_mywork.setOnPageChangeListener(new MyPagerChangerListener());

    }

    /**
     * 初始化标题栏
     */
    private void initBar() {
        ViewGroup.LayoutParams params = ll_head.getLayoutParams();
        MainActivity1 parentActivity = (MainActivity1) getActivity();
        params.height = ((Util.getScreenHeight(getActivity()) - parentActivity.getStatusBarHeight()) / 35) * 2;
        ll_head.setLayoutParams(params);
        tv_center.setText("订单");
        ll_left.setVisibility(View.INVISIBLE);
        ll_head.setBackgroundColor(Color.WHITE);
    }

    //初始化指示器位置
    public void initCursorPos() {
        // 初始化动画
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        tv_all.measure(0, 0);
        iv_over.getLayoutParams().width = screenWidth / 3;
        bmpw = screenWidth / 3;// 获取图片宽度

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / 3 - bmpw);// 计算偏移量

        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        iv_over.setImageMatrix(matrix);// 设置动画初始位置
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_all:
                changeView(0);
                break;
            case R.id.tv_no_evaluate:
                changeView(1);
                break;
            case R.id.tv_wait_lottery:
                changeView(2);
                break;
        }
    }

    //ViewPager适配器
    class MyFragmentAdapters extends FragmentStatePagerAdapter {
        public MyFragmentAdapters(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        /**
         * 每次更新完成ViewPager的内容后，调用该接口，此处复写主要是为了让导航按钮上层的覆盖层能够动态的移动
         */
        @Override
        public Parcelable saveState() {
            return null;
        }
    }

    /**
     * ViewPager监听器
     */
    class MyPagerChangerListener implements ViewPager.OnPageChangeListener {

        int one = bmpw;// 页卡1 -> 页卡2 偏移量
        int two = one * 2;// 页卡1 -> 页卡3 偏移量

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Animation animation = null;
            switch (position) {
                case 0:
                    if (currIndex == 1) {
                        animation = new TranslateAnimation(one, 0, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, 0, 0, 0);
                    }
                    break;
                case 1:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, one, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, one, 0, 0);
                    }
                    break;
                case 2:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, two, 0, 0);
                    } else if (currIndex == 1) {
                        animation = new TranslateAnimation(one, two, 0, 0);
                    }
                    break;
            }
            currIndex = position;
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(300);
            iv_over.startAnimation(animation);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    /**
     * 手动设置ViewPager要显示的视图
     *
     * @param desTab
     */
    private void changeView(int desTab) {
        vp_mywork.setCurrentItem(desTab, true);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
