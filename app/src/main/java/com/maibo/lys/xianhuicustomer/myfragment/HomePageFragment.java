package com.maibo.lys.xianhuicustomer.myfragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.maibo.lys.xianhuicustomer.R;
import com.maibo.lys.xianhuicustomer.myactivity.MainActivity1;
import com.maibo.lys.xianhuicustomer.myadapter.HomeVertailAdapter;
import com.maibo.lys.xianhuicustomer.myadapter.HorizontalListAdapter;
import com.maibo.lys.xianhuicustomer.myadapter.MyPagerAdapter;
import com.maibo.lys.xianhuicustomer.myentity.Ad;
import com.maibo.lys.xianhuicustomer.myutils.Util;
import com.maibo.lys.xianhuicustomer.myview.HorizontalListView;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by LYS on 2017/1/13.
 */

public class HomePageFragment extends Fragment {

    @Bind(R.id.ll_head)
    LinearLayout ll_head;
    @Bind(R.id.ll_left)
    LinearLayout ll_left;
    @Bind(R.id.tv_center)
    TextView tv_center;
    private View rootView;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.tv_intro)
    TextView tv_intro;
    @Bind(R.id.dot_layout)
    LinearLayout dot_layout;
    @Bind(R.id.horizon_listview)
    HorizontalListView horizon_listview;
    @Bind(R.id.my_listview)
    ListView my_listview;
    @Bind(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    private ArrayList<Ad> list = new ArrayList<Ad>();
    private ScheduledExecutorService executor;
    private int currentItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home_page, null);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        ButterKnife.bind(this, rootView);
        initBar();
        initView();
        initListener();
        initData();
        return rootView;
    }

    /**
     * 初始化基本界面
     */
    private void initView() {
        horizon_listview.setAdapter(new HorizontalListAdapter(getContext()));
        my_listview.setAdapter(new HomeVertailAdapter(getContext()));
    }

    /**
     * 初始化ToolBar
     */
    private void initBar() {
        ViewGroup.LayoutParams params = ll_head.getLayoutParams();
        MainActivity1 parentActivity = (MainActivity1) getActivity();
        params.height = ((Util.getScreenHeight(getActivity()) - parentActivity.getStatusBarHeight()) / 35) * 2;
        ll_head.setLayoutParams(params);
        tv_center.setText("首页");
        ll_left.setVisibility(View.INVISIBLE);
        ll_head.setBackgroundColor(Color.WHITE);
    }

    /**
     * 初始化viewPager监听器
     */
    private void initListener() {
        refreshLayout.setOverScrollRefreshShow(false);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefreshing();
                    }
                },2000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefreshing();
                    }
                },2000);
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentItem = position;
                updateIntroAndDot();
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        list.clear();
        list.add(new Ad(R.mipmap.a, "Text one"));
        list.add(new Ad(R.mipmap.b, "Text two"));
        list.add(new Ad(R.mipmap.c, "Text three"));
        list.add(new Ad(R.mipmap.d, "Text four"));
        list.add(new Ad(R.mipmap.e, "Text five"));

        initDots();

        viewPager.setAdapter(new MyPagerAdapter(getActivity(), list));

        updateIntroAndDot();
    }

    /**
     * 初始化dot
     */
    private void initDots() {
        for (int i = 0; i < list.size(); i++) {
            View view = new View(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            params.setMargins(5, 0, 5, 0);
            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.selector_dot);
            dot_layout.addView(view);
        }
    }

    /**
     * 更新文本
     */
    private void updateIntroAndDot() {
        int currentPage = viewPager.getCurrentItem() % list.size();
        tv_intro.setText(list.get(currentPage).getIntro());

        for (int i = 0; i < dot_layout.getChildCount(); i++) {
            dot_layout.getChildAt(i).setEnabled(i == currentPage);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        startAutoScroll(); // activity激活时候自动播放
    }

    @Override
    public void onPause() {
        super.onPause();

        stopAutoScroll(); // activity暂停时候停止自动播放
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    private void startAutoScroll() {
        stopAutoScroll();

        executor = Executors.newSingleThreadScheduledExecutor();
        Runnable command = new Runnable() {
            @Override
            public void run() {
                selectNextItem();
            }

            private void selectNextItem() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewPager.setCurrentItem(++currentItem);
                    }
                });
            }
        };
        int delay = 2;
        int period = 3;
        TimeUnit timeUnit = TimeUnit.SECONDS;
        executor.scheduleAtFixedRate(command, delay, period, timeUnit);
    }

    private void stopAutoScroll() {
        if (executor != null) {
            executor.shutdownNow();
        }
    }

}
