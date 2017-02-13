package com.maibo.lys.xianhuicustomer.myactivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maibo.lys.xianhuicustomer.R;
import com.maibo.lys.xianhuicustomer.myutils.Util;

import butterknife.Bind;

import static com.maibo.lys.xianhuicustomer.R.drawable.order_text_full_bg;

/**
 * Created by LYS on 2017/2/4.
 */

public class OrderEvaluateActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.order_start0)
    ImageView order_start0;
    @Bind(R.id.order_start1)
    ImageView order_start1;
    @Bind(R.id.order_start2)
    ImageView order_start2;
    @Bind(R.id.order_start3)
    ImageView order_start3;
    @Bind(R.id.order_start4)
    ImageView order_start4;

    @Bind(R.id.buy_start0)
    ImageView buy_start0;
    @Bind(R.id.buy_start1)
    ImageView buy_start1;
    @Bind(R.id.buy_start2)
    ImageView buy_start2;
    @Bind(R.id.buy_start3)
    ImageView buy_start3;
    @Bind(R.id.buy_start4)
    ImageView buy_start4;

    @Bind(R.id.buy_choose0)
    TextView buy_choose0;
    @Bind(R.id.buy_choose1)
    TextView buy_choose1;
    @Bind(R.id.buy_choose2)
    TextView buy_choose2;
    @Bind(R.id.buy_choose3)
    TextView buy_choose3;

    @Bind(R.id.ll_head)
    LinearLayout ll_head;
    @Bind(R.id.ll_left)
    LinearLayout ll_left;
    @Bind(R.id.tv_center)
    TextView tv_center;
    @Bind(R.id.tv_right)
    TextView tv_right;

    @Bind(R.id.tv_go_evaluate)
    TextView tv_go_evaluate;

    private int start_solid = R.drawable.start_solid;
    private int start_empty = R.drawable.start_empty;
    private int order_text_empty_bg=R.drawable.order_text_bg;
    private int order_text_fulls_bg= order_text_full_bg;
    private int order_total_start = 1;
    private int buy_total_start = 1;
    private boolean isNeedChange=false;
    private boolean isChoose0 = false;
    private boolean isChoose1 = false;
    private boolean isChoose2 = false;
    private boolean isChoose3 = false;
    private boolean isChoose4 = false;

    private boolean isBChoose0 = false;
    private boolean isBChoose1 = false;
    private boolean isBChoose2 = false;
    private boolean isBChoose3 = false;
    private boolean isBChoose4 = false;

    private boolean isCheck0=false;
    private boolean isCheck1=false;
    private boolean isCheck2=false;
    private boolean isCheck3=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_evaluate);
        initBar();
        initListener();
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        order_start0.setOnClickListener(this);
        order_start1.setOnClickListener(this);
        order_start2.setOnClickListener(this);
        order_start3.setOnClickListener(this);
        order_start4.setOnClickListener(this);
        buy_start0.setOnClickListener(this);
        buy_start1.setOnClickListener(this);
        buy_start2.setOnClickListener(this);
        buy_start3.setOnClickListener(this);
        buy_start4.setOnClickListener(this);
        buy_choose0.setOnClickListener(this);
        buy_choose1.setOnClickListener(this);
        buy_choose2.setOnClickListener(this);
        buy_choose3.setOnClickListener(this);
        tv_go_evaluate.setOnClickListener(this);
        tv_right.setOnClickListener(this);


    }
    /**
     * 初始化ToolBar
     */
    private void initBar() {
        ViewGroup.LayoutParams params = ll_head.getLayoutParams();
        params.height = ((Util.getScreenHeight(this) - getStatusBarHeight()) / 35) * 2;
        ll_head.setLayoutParams(params);
        ll_head.setBackgroundColor(Color.WHITE);
        tv_center.setText("评价");
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_right.setText("提交");
        tv_right.setTextColor(getResources().getColor(R.color.orderTextColor));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_start0:
                order_start1.setImageResource(start_empty);
                order_start2.setImageResource(start_empty);
                order_start3.setImageResource(start_empty);
                order_start4.setImageResource(start_empty);
                isChoose1 = false;
                isChoose2 = false;
                isChoose3 = false;
                isChoose4 = false;
                order_total_start = 1;
                break;
            case R.id.order_start1:
                if (!isChoose1) {
                    order_start0.setImageResource(start_solid);
                    order_start1.setImageResource(start_solid);
                    isChoose1 = true;
                    order_total_start = 2;
                } else {
                    order_start1.setImageResource(start_empty);
                    order_start2.setImageResource(start_empty);
                    order_start3.setImageResource(start_empty);
                    order_start4.setImageResource(start_empty);
                    isChoose1 = false;
                    isChoose2 = false;
                    isChoose3 = false;
                    isChoose4 = false;
                    order_total_start = 1;
                }
                break;
            case R.id.order_start2:
                if (!isChoose2) {
                    order_start0.setImageResource(start_solid);
                    order_start1.setImageResource(start_solid);
                    order_start2.setImageResource(start_solid);
                    isChoose1 = true;
                    isChoose2 = true;
                    order_total_start = 3;
                } else {
                    order_start2.setImageResource(start_empty);
                    order_start3.setImageResource(start_empty);
                    order_start4.setImageResource(start_empty);
                    isChoose2 = false;
                    isChoose3 = false;
                    isChoose4 = false;
                    order_total_start = 2;
                }
                break;
            case R.id.order_start3:
                if (!isChoose3) {
                    order_start0.setImageResource(start_solid);
                    order_start1.setImageResource(start_solid);
                    order_start2.setImageResource(start_solid);
                    order_start3.setImageResource(start_solid);
                    isChoose1 = true;
                    isChoose2 = true;
                    isChoose3 = true;
                    order_total_start = 4;
                } else {
                    order_start3.setImageResource(start_empty);
                    order_start4.setImageResource(start_empty);
                    isChoose3 = false;
                    isChoose4 = false;
                    order_total_start = 3;
                }
                break;
            case R.id.order_start4:
                if (!isChoose4) {
                    order_start0.setImageResource(start_solid);
                    order_start1.setImageResource(start_solid);
                    order_start2.setImageResource(start_solid);
                    order_start3.setImageResource(start_solid);
                    order_start4.setImageResource(start_solid);
                    isChoose1 = true;
                    isChoose2 = true;
                    isChoose3 = true;
                    isChoose4 = true;
                    order_total_start = 5;

                } else {
                    order_start4.setImageResource(start_empty);
                    isChoose4 = false;
                    order_total_start = 4;
                }
                break;
            //////////////////////////////////////////////////////////////////////
            case R.id.buy_start0:
                buy_start1.setImageResource(start_empty);
                buy_start2.setImageResource(start_empty);
                buy_start3.setImageResource(start_empty);
                buy_start4.setImageResource(start_empty);
                isBChoose1 = false;
                isBChoose2 = false;
                isBChoose3 = false;
                isBChoose4 = false;
                buy_total_start = 1;
                changeBuyText();
                break;
            case R.id.buy_start1:
                if (!isBChoose1) {
                    buy_start0.setImageResource(start_solid);
                    buy_start1.setImageResource(start_solid);
                    isBChoose1 = true;
                    buy_total_start = 2;
                } else {
                    buy_start1.setImageResource(start_empty);
                    buy_start2.setImageResource(start_empty);
                    buy_start3.setImageResource(start_empty);
                    buy_start4.setImageResource(start_empty);
                    isBChoose1 = false;
                    isBChoose2 = false;
                    isBChoose3 = false;
                    isBChoose4 = false;
                    buy_total_start = 1;
                }
                changeBuyText();
                break;
            case R.id.buy_start2:
                if (!isBChoose2) {
                    buy_start0.setImageResource(start_solid);
                    buy_start1.setImageResource(start_solid);
                    buy_start2.setImageResource(start_solid);
                    isBChoose1 = true;
                    isBChoose2 = true;
                    buy_total_start = 3;
                } else {
                    buy_start2.setImageResource(start_empty);
                    buy_start3.setImageResource(start_empty);
                    buy_start4.setImageResource(start_empty);
                    isBChoose2 = false;
                    isBChoose3 = false;
                    isBChoose4 = false;
                    buy_total_start = 2;
                }
                changeBuyText();
                break;
            case R.id.buy_start3:
                if (!isBChoose3) {
                    buy_start0.setImageResource(start_solid);
                    buy_start1.setImageResource(start_solid);
                    buy_start2.setImageResource(start_solid);
                    buy_start3.setImageResource(start_solid);
                    isBChoose1 = true;
                    isBChoose2 = true;
                    isBChoose3 = true;
                    buy_total_start = 4;
                } else {
                    buy_start3.setImageResource(start_empty);
                    buy_start4.setImageResource(start_empty);
                    isBChoose3 = false;
                    isBChoose4 = false;
                    buy_total_start = 3;
                }
                changeBuyText();
                break;
            case R.id.buy_start4:
                if (!isBChoose4) {
                    buy_start0.setImageResource(start_solid);
                    buy_start1.setImageResource(start_solid);
                    buy_start2.setImageResource(start_solid);
                    buy_start3.setImageResource(start_solid);
                    buy_start4.setImageResource(start_solid);
                    isBChoose1 = true;
                    isBChoose2 = true;
                    isBChoose3 = true;
                    isBChoose4 = true;
                    buy_total_start = 5;

                } else {
                    buy_start4.setImageResource(start_empty);
                    isBChoose4 = false;
                    buy_total_start = 4;
                }
                changeBuyText();
                break;
            case R.id.buy_choose0:
                if (!isCheck0){
                    isCheck0=true;
                    buy_choose0.setBackgroundResource(order_text_fulls_bg);
                    buy_choose0.setTextColor(Color.WHITE);
                }else{
                    isCheck0=false;
                    buy_choose0.setBackgroundResource(order_text_empty_bg);
                    buy_choose0.setTextColor(getResources().getColor(R.color.orderTextColor));
                }
                break;
            case R.id.buy_choose1:
                if (!isCheck1){
                    isCheck1=true;
                    buy_choose1.setBackgroundResource(order_text_fulls_bg);
                    buy_choose1.setTextColor(Color.WHITE);
                }else{
                    isCheck1=false;
                    buy_choose1.setBackgroundResource(order_text_empty_bg);
                    buy_choose1.setTextColor(getResources().getColor(R.color.orderTextColor));
                }
                break;
            case R.id.buy_choose2:
                if (!isCheck2){
                    isCheck2=true;
                    buy_choose2.setBackgroundResource(order_text_fulls_bg);
                    buy_choose2.setTextColor(Color.WHITE);
                }else{
                    isCheck2=false;
                    buy_choose2.setBackgroundResource(order_text_empty_bg);
                    buy_choose2.setTextColor(getResources().getColor(R.color.orderTextColor));
                }
                break;
            case R.id.buy_choose3:
                if (!isCheck3){
                    isCheck3=true;
                    buy_choose3.setBackgroundResource(order_text_fulls_bg);
                    buy_choose3.setTextColor(Color.WHITE);
                }else{
                    isCheck3=false;
                    buy_choose3.setBackgroundResource(order_text_empty_bg);
                    buy_choose3.setTextColor(getResources().getColor(R.color.orderTextColor));
                }
                break;
            case R.id.tv_go_evaluate:
                //1)判断是否已提交评价
                //TODO

                //2)评价成功后，方可跳转到评价界面


                break;
            case R.id.tv_right:
                //开始提交订单
                //TODO

                break;
        }
    }

    /**
     * 动态修改购买意愿下的文字内容
     */
    private void changeBuyText() {
        if (buy_total_start>2){
            buy_choose0.setText("技术好");
            buy_choose1.setText("环境好");
            buy_choose2.setText("价格合理");
            buy_choose3.setText("顾问不错");
            buy_choose0.setBackgroundResource(order_text_empty_bg);
            buy_choose1.setBackgroundResource(order_text_empty_bg);
            buy_choose2.setBackgroundResource(order_text_empty_bg);
            buy_choose3.setBackgroundResource(order_text_empty_bg);
            buy_choose0.setTextColor(getResources().getColor(R.color.orderTextColor));
            buy_choose1.setTextColor(getResources().getColor(R.color.orderTextColor));
            buy_choose2.setTextColor(getResources().getColor(R.color.orderTextColor));
            buy_choose3.setTextColor(getResources().getColor(R.color.orderTextColor));
            isCheck0=false;
            isCheck1=false;
            isCheck2=false;
            isCheck3=false;

        }else{
            buy_choose0.setText("技术好差");
            buy_choose1.setText("环境好差");
            buy_choose2.setText("价格坑");
            buy_choose3.setText("顾问挫");
            buy_choose0.setBackgroundResource(order_text_empty_bg);
            buy_choose1.setBackgroundResource(order_text_empty_bg);
            buy_choose2.setBackgroundResource(order_text_empty_bg);
            buy_choose3.setBackgroundResource(order_text_empty_bg);
            buy_choose0.setTextColor(getResources().getColor(R.color.orderTextColor));
            buy_choose1.setTextColor(getResources().getColor(R.color.orderTextColor));
            buy_choose2.setTextColor(getResources().getColor(R.color.orderTextColor));
            buy_choose3.setTextColor(getResources().getColor(R.color.orderTextColor));
            isCheck0=false;
            isCheck1=false;
            isCheck2=false;
            isCheck3=false;
        }
    }
}
