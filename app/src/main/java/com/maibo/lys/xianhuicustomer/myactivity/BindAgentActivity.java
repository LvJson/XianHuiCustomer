package com.maibo.lys.xianhuicustomer.myactivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maibo.lys.xianhuicustomer.R;
import com.maibo.lys.xianhuicustomer.constants.PublicString;
import com.maibo.lys.xianhuicustomer.constants.WebAddress;
import com.maibo.lys.xianhuicustomer.myadapter.BindInformationAdapter;
import com.maibo.lys.xianhuicustomer.myentity.BindInfomation;
import com.maibo.lys.xianhuicustomer.myentity.Card;
import com.maibo.lys.xianhuicustomer.myentity.CourseCard;
import com.maibo.lys.xianhuicustomer.myutils.Util;
import com.maibo.lys.xianhuicustomer.myview.AllDividerDecoration;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by LYS on 2017/2/6.
 */

public class BindAgentActivity extends BaseActivity {
    @Bind(R.id.ll_head)
    LinearLayout ll_head;
    @Bind(R.id.ll_left)
    LinearLayout ll_left;
    @Bind(R.id.tv_center)
    TextView tv_center;
    @Bind(R.id.tv_right)
    TextView tv_right;
    @Bind(R.id.rv_bind_agent)
    RecyclerView rv_bind_agent;
    String agent_id;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    BindInfomation bindInfomation = (BindInfomation) msg.obj;
                    rv_bind_agent.setAdapter(new BindInformationAdapter(getApplicationContext()
                            , bindInfomation.getVipcard_list(), bindInfomation.getCourseCards_list(), bindInfomation));
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_agent);
        initView();
        initBar();
        Intent intent = getIntent();
        agent_id = intent.getStringExtra("agent_id");
        initDatas(agent_id);
    }

    /**
     * 初始化view
     */
    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rv_bind_agent.setLayoutManager(manager);
        rv_bind_agent.addItemDecoration(new AllDividerDecoration(this));
    }

    /**
     * 获取注册后相关数据
     *
     * @param agent_id
     */
    private void initDatas(String agent_id) {
        String sign = Util.getSign(mobile, type, agent_id, PublicString.PUBLICK_KEY);
        String strName[] = {"手机号", "用户类型", "门店ID"};
        String strDatas[] = {mobile, type, agent_id};
        String result = Util.getNullString(strName, strDatas);
        if (TextUtils.isEmpty(result)) {
            getBindingInfo(mobile, type, agent_id, sign);
        } else {
            showToast(result + " 不能为空");
        }
    }

    /**
     * 获取PC端注册后返回数据
     *
     * @param mobile
     * @param type
     * @param agent_id
     * @param sign
     */
    private void getBindingInfo(String mobile, String type, String agent_id, String sign) {

        OkHttpUtils
                .post()
                .url(WebAddress.BINDING_INFO)
                .addParams("mobile", mobile)
                .addParams("type", type)
                .addParams("agent_id", agent_id)
                .addParams("sign", sign)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast(R.string.net_errow);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("BindAgentActivity", response);

                        JsonObject jsOb = new JsonParser().parse(response).getAsJsonObject();
                        String status = jsOb.get("status").getAsString();
                        String message = jsOb.get("message").getAsString();
                        if (status.equals("ok")) {
                            analysisJsonDatas(jsOb);

                        } else {
                            showToast(message);
                        }
                    }
                });
    }

    /**
     * 解析PC端注册后返回数据
     *
     * @param jsOb
     */
    private void analysisJsonDatas(JsonObject jsOb) {
        List<Card> vipCards = new ArrayList<>();
        List<CourseCard> courseCards = new ArrayList<>();
        JsonObject data = jsOb.get("data").getAsJsonObject();
        String agent_id = "";
        String agent_name = "";
        String user_id = "";
        String display_name = "";
        String org_id = "";
        String org_name = "";
        if (!data.get("agent_id").isJsonNull())
            agent_id = data.get("agent_id").getAsString();
        if (!data.get("agent_name").isJsonNull())
            agent_name = data.get("agent_name").getAsString();
        if (!data.get("user_id").isJsonNull())
            user_id = data.get("user_id").getAsString();
        if (!data.get("display_name").isJsonNull())
            display_name = data.get("display_name").getAsString();
        if (!data.get("org_id").isJsonNull())
            org_id = data.get("org_id").getAsString();
        if (!data.get("org_name").isJsonNull())
            org_name = data.get("org_name").getAsString();
        if (data.get("vipcard_list").isJsonArray()) {
            JsonArray vipcard = data.get("vipcard_list").getAsJsonArray();
            for (JsonElement je : vipcard) {
                JsonObject jo = je.getAsJsonObject();
                String card_num = "";
                String amount = "";
                String card_name = "";
                if (!jo.get("card_num").isJsonNull())
                    card_num = jo.get("card_num").getAsString();
                if (!jo.get("amount").isJsonNull())
                    amount = jo.get("amount").getAsString();
                if (!jo.get("card_name").isJsonNull())
                    card_name = jo.get("card_name").getAsString();
                vipCards.add(new Card(card_num, amount, card_name));
            }
        }
        if (data.get("coursecard_list").isJsonArray()) {
            JsonArray coursecard = data.get("coursecard_list").getAsJsonArray();
            for (JsonElement je : coursecard) {
                JsonObject jo = je.getAsJsonObject();
                String card_num = "";
                String amount = "";
                String times = "";
                String card_name = "";
                if (!jo.get("card_num").isJsonNull())
                    card_num = jo.get("card_num").getAsString();
                if (!jo.get("amount").isJsonNull())
                    amount = jo.get("amount").getAsString();
                if (!jo.get("times").isJsonNull())
                    times = jo.get("times").getAsString();
                if (!jo.get("card_name").isJsonNull())
                    card_name = jo.get("card_name").getAsString();
                courseCards.add(new CourseCard(card_num, amount, card_name, times));
            }
        }
        Message msg = Message.obtain();
        msg.what = 0;
        msg.obj = new BindInfomation(agent_id, agent_name, user_id, display_name, org_id, org_name, vipCards, courseCards);
        handler.sendMessage(msg);
    }

    /**
     * 初始化ToolBar
     */
    private void initBar() {
        ViewGroup.LayoutParams params = ll_head.getLayoutParams();
        params.height = ((Util.getScreenHeight(this) - getStatusBarHeight()) / 35) * 2;
        ll_head.setLayoutParams(params);
        ll_head.setBackgroundColor(Color.WHITE);
        tv_center.setText("绑定");
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_right.setText("确定");
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //绑定
                String sign = Util.getSign(mobile, type, agent_id, PublicString.PUBLICK_KEY);
                showShortDialog();
                bindingAgent(mobile, type, agent_id, sign);
            }
        });
    }

    /**
     * 手机注册绑定
     *
     * @param mobile
     * @param type
     * @param agent_id
     * @param sign
     */
    private void bindingAgent(String mobile, String type, String agent_id, String sign) {
        OkHttpUtils
                .post()
                .url(WebAddress.BINDING)
                .addParams("mobile", mobile)
                .addParams("type", type)
                .addParams("agent_id", agent_id)
                .addParams("sign", sign)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast(R.string.net_errow);
                        dismissShortDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("BindAgent", response);
                        JsonObject jsOb = new JsonParser().parse(response).getAsJsonObject();
                        String status = jsOb.get("status").getAsString();
                        String message = jsOb.get("message").getAsString();
                        showToast(message);
                        dismissShortDialog();
                    }
                });
    }

    /**
     * 绑定账号
     */
    @OnClick(R.id.tv_right)
    public void bindData() {
        //绑定账号
    }
}
