package com.maibo.lys.xianhuicustomer.myadapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.maibo.lys.xianhuicustomer.R;
import com.maibo.lys.xianhuicustomer.myapplication.MyApplication;
import com.maibo.lys.xianhuicustomer.myentity.Ad;
import com.maibo.lys.xianhuicustomer.myentity.Project;
import com.maibo.lys.xianhuicustomer.myinterface.HomeProjectItemClickListener;
import com.maibo.lys.xianhuicustomer.myview.HorizontalListView;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by LYS on 2017/2/4.
 */

public class HomePagerRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<Ad> list;
    private List<Project> proList;
    private Context context;
    private int currentItem;
    private Activity mActivity;
    private ScheduledExecutorService executor;
    private ViewPager viewPager;
    HomeProjectItemClickListener listener;


    public HomePagerRecyclerAdapter(Activity mActivity, Context context, List<Ad> list, List<Project> proList) {
        mLayoutInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.mActivity = mActivity;
        this.proList = proList;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new ViewPagerHolder(mLayoutInflater.inflate(R.layout.style_viewpager, parent, false));
        } else if (viewType == 1) {
            return new TextSwitchViewHolder(mLayoutInflater.inflate(R.layout.style_tip_view, parent, false));
        } else if (viewType == 2) {
            return new HorizonListViewHolder(mLayoutInflater.inflate(R.layout.style_horizon_list, parent, false));
        } else {
            return new ProjectDataListViewHolder(mLayoutInflater.inflate(R.layout.style_vertail_adapter, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ProjectDataListViewHolder) {
            Picasso.with(context).load(proList.get(position - 3).getImages_url()[0]).into(((ProjectDataListViewHolder) holder).cir_img);
            ((ProjectDataListViewHolder) holder).tv_pro_name.setText(proList.get(position - 3).getFullname());
            ((ProjectDataListViewHolder) holder).tv_pro_intrduce.setText(proList.get(position - 3).getSummary());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(position, proList.get(position - 3));
                }
            });
        }else if (holder instanceof TextSwitchViewHolder){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyApplication.showToast(context,textSwitchItem+"");
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return proList.size() + 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else if (position == 1) {
            return 1;
        } else if (position == 2) {
            return 2;
        } else {
            return 3;
        }

    }

    /**
     * 轮播图
     */
    public class ViewPagerHolder extends RecyclerView.ViewHolder {
        LinearLayout dot_layout;
//        TextView tv_intro;

        ViewPagerHolder(View view) {
            super(view);
            viewPager = (ViewPager) view.findViewById(R.id.viewPager);
//            viewPager.setNestedpParent((ViewGroup) viewPager.getParent());
            dot_layout = (LinearLayout) view.findViewById(R.id.dot_layout);
//            tv_intro = (TextView) view.findViewById(R.id.tv_intro);
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    currentItem = position;
                    updateIntroAndDot(viewPager, dot_layout);
                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            initDots(dot_layout);
            viewPager.setAdapter(new MyPagerAdapter(context, list));
            updateIntroAndDot(viewPager, dot_layout);
        }
    }

    /**
     * 初始化dot
     */
    private void initDots(LinearLayout dot_layout) {
        for (int i = 0; i < list.size(); i++) {
            View view = new View(context);
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
    private void updateIntroAndDot(ViewPager viewPager, LinearLayout dot_layout) {
        int currentPage = viewPager.getCurrentItem() % list.size();
//        tv_intro.setText(list.get(currentPage).getIntro());

        for (int i = 0; i < dot_layout.getChildCount(); i++) {
            dot_layout.getChildAt(i).setEnabled(i == currentPage);
        }
    }

    /**
     * 开始自动播放
     */
    public void startAutoScroll() {
        stopAutoScroll();
        executor = Executors.newSingleThreadScheduledExecutor();
        Runnable command = new Runnable() {
            @Override
            public void run() {
                selectNextItem();
            }

            private void selectNextItem() {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (viewPager != null)
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

    /**
     * 停止自动播放
     */
    public void stopAutoScroll() {
        if (executor != null) {
            executor.shutdownNow();
        }
    }

    /**
     * 上下滚动的文字
     */
    TextSwitcher textSwitcher;
    int i=0;
    String items[];
    int textSwitchItem;
    public class TextSwitchViewHolder extends RecyclerView.ViewHolder {


        TextSwitchViewHolder(View view) {
            super(view);
            textSwitcher = (TextSwitcher) view.findViewById(R.id.text_switcher);
            textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
                @Override
                public View makeView() {
                    TextView textView=new TextView(context);
                    FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    textView.setLayoutParams(params);
                    textView.setGravity(Gravity.CENTER);
                    textView.setLines(1);
                    textView.setEllipsize(TextUtils.TruncateAt.END);
                    textView.setTextSize(16);
                    textView.setTextColor(context.getResources().getColor(R.color.all_text_color));
                    return textView;
                }
            });

            textSwitcher.setInAnimation(context, R.anim.slide_in_bottom);
            textSwitcher.setOutAnimation(context, R.anim.slide_out_top);
            items = new String[] { "新春特别活动，楚舆狂歌套限时出售，先到者先得之，迟来者不候哦，快点加入我们吧！", "三周年红发效果图放出！", "冬至趣味活动开启，一起来吃冬至宴席。" };
            Message msg = mHandler.obtainMessage(1);
            msg.what = i;
            mHandler.sendMessage(msg);

        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            textSwitchItem=i % items.length;
            textSwitcher.setText(items[i % items.length]);
            i++;
            Message msgg = mHandler.obtainMessage(1);
            msgg.what = i;
            mHandler.sendMessageDelayed(msgg, 3000);
        }
    };

    /**
     * 横向ListView
     */
    public class HorizonListViewHolder extends RecyclerView.ViewHolder {
        HorizontalListView horizon_listview;

        HorizonListViewHolder(View view) {
            super(view);
            horizon_listview = (HorizontalListView) view.findViewById(R.id.horizon_listview);
            horizon_listview.setNestedpParent((ViewGroup) horizon_listview.getParent());
            horizon_listview.setAdapter(new HorizontalListAdapter(context));
        }
    }

    /**
     * 剩余数据的填充
     */
    public class ProjectDataListViewHolder extends RecyclerView.ViewHolder {
        CircleImageView cir_img;
        TextView tv_pro_name, tv_pro_intrduce;

        ProjectDataListViewHolder(View view) {
            super(view);
            cir_img = (CircleImageView) view.findViewById(R.id.cir_img);
            tv_pro_name = (TextView) view.findViewById(R.id.tv_pro_name);
            tv_pro_intrduce = (TextView) view.findViewById(R.id.tv_pro_intrduce);
        }
    }

    public void setOnMyItemClickListenre(HomeProjectItemClickListener listenre) {
        this.listener = listenre;
    }
}
