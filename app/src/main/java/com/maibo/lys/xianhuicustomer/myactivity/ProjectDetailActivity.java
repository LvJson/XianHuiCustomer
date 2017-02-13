package com.maibo.lys.xianhuicustomer.myactivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maibo.lys.xianhuicustomer.R;
import com.maibo.lys.xianhuicustomer.constants.PublicString;
import com.maibo.lys.xianhuicustomer.constants.WebAddress;
import com.maibo.lys.xianhuicustomer.myadapter.ProjectDetailInfoAdapter;
import com.maibo.lys.xianhuicustomer.myadapter.ProjectNoteAdapter;
import com.maibo.lys.xianhuicustomer.myadapter.ViewPagerAdapter;
import com.maibo.lys.xianhuicustomer.myentity.LittleProject;
import com.maibo.lys.xianhuicustomer.myentity.Project;
import com.maibo.lys.xianhuicustomer.myutils.NetWorkUtils;
import com.maibo.lys.xianhuicustomer.myutils.Util;
import com.maibo.lys.xianhuicustomer.myview.MyListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by LYS on 2017/2/9.
 */

public class ProjectDetailActivity extends BaseActivity{
    @Bind(R.id.ll_head)
    LinearLayout ll_head;
    @Bind(R.id.ll_left)
    LinearLayout ll_left;
    @Bind(R.id.tv_center)
    TextView tv_center;
    @Bind(R.id.tv_right)
    TextView tv_right;

    @Bind(R.id.view_pager)
    ViewPager view_pager;
    @Bind(R.id.tv_indicator)
    TextView tv_indicator;
    @Bind(R.id.tv_intrduce)
    TextView tv_intrduce;
    @Bind(R.id.tv_real_price)
    TextView tv_real_price;
    @Bind(R.id.tv_door_price)
    TextView tv_door_price;
    @Bind(R.id.tv_appointment)
    TextView tv_appointment;
    @Bind(R.id.tv_look_detail)
    TextView tv_look_detail;
    @Bind(R.id.frame)
    FrameLayout frame;
    @Bind(R.id.scroll_view)
    ScrollView scroll_view;
    @Bind(R.id.in_no_datas)
    LinearLayout in_no_datas;
    @Bind(R.id.in_loading_error)
    LinearLayout in_loading_error;

    @Bind(R.id.listview_detail)
    MyListView listview_detail;
    @Bind(R.id.listview_note)
    MyListView listview_note;
    List<Project> project_list;
    String intent_project_id,intent_agent_id,intent_sign;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    project_list = (List<Project>) msg.obj;
                    fullData(project_list);
                    break;
            }
        }
    };

    /**
     * 填充数据
     * @param project_list
     */
    private void fullData(List<Project> project_list) {
        Project project=project_list.get(0);
        final int size=project_list.get(0).getImages_url().length;
        view_pager.setAdapter(new ViewPagerAdapter(getApplicationContext(),project_list.get(0).getImages_url(),
                screenHeight*2/7, Util.getScreenWidth(getApplicationContext())));
        view_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tv_indicator.setText(position+1+"/"+size);
            }
            @Override
            public void onPageSelected(int position) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tv_intrduce.setText(project_list.get(0).getSummary());
        tv_real_price.setText("￥"+project.getRetail_price());
        double total_price=0.00;
        for (int i=0;i<project.getProject_detail().size();i++){
            LittleProject lp=project.getProject_detail().get(i);
            double a=Double.parseDouble(lp.getPrice());
            total_price+=a;
        }
        tv_door_price.setText("门市价 ￥"+total_price);
        listview_detail.setAdapter(new ProjectDetailInfoAdapter(getApplicationContext(),
                project.getProject_detail(),total_price,project.getRetail_price()));
        listview_note.setAdapter(new ProjectNoteAdapter(getApplicationContext(),project.getProject_note()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);
        initBar();
        initView();
        initDatas();

    }

    /**
     * 初始化数据
     */
    private void initDatas() {
        Intent intent = getIntent();
        intent_project_id = intent.getStringExtra("project_id");
        intent_agent_id = intent.getStringExtra("agent_id");
        intent_sign = Util.getSign(new String[]{intent_agent_id, intent_project_id, PublicString.PUBLICK_KEY});
        //判断网络连接状态
        if (NetWorkUtils.isNetworkConnected(this)){
            showShortDialog();
            getProjectDetailDatas(intent_sign, intent_project_id, intent_agent_id);
        }else{
            scroll_view.setVisibility(View.GONE);
            in_loading_error.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化view
     */
    private void initView() {
        setSingleViewHeightAndWidth(frame,screenHeight*2/7,Util.getScreenWidth(this));
    }
    /**
     * 初始化ToolBar
     */
    private void initBar() {
        ViewGroup.LayoutParams params = ll_head.getLayoutParams();
        params.height = ((Util.getScreenHeight(this) - getStatusBarHeight()) / 35) * 2;
        ll_head.setLayoutParams(params);
        ll_head.setBackgroundColor(Color.WHITE);
        tv_center.setText("项目详情");
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 获取项目详情
     *
     * @param sign
     * @param project_id
     */
    private void getProjectDetailDatas(String sign, String project_id, String agent_id) {
        OkHttpUtils
                .post()
                .url(WebAddress.GET_PROJECT_DETAIL)
                .addParams("sign", sign)
                .addParams("project_id", project_id)
                .addParams("agent_id", agent_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast(R.string.net_errow);
                        dismissShortDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JsonObject jsOb = new JsonParser().parse(response).getAsJsonObject();
                        String status = jsOb.get("status").getAsString();
                        String message = jsOb.get("message").getAsString();
                        if (status.equals("ok")) {
                            analysisProjectDetail(jsOb);

                        } else {
                            showToast(message);
                        }
                        dismissShortDialog();
                    }
                });
    }

    /**
     * 解析项目详情
     *
     * @param jsOb
     */
    private void analysisProjectDetail(JsonObject jsOb) {
        JsonObject data = jsOb.get("data").getAsJsonObject();
        List<Project> project = new ArrayList<Project>();
        String project_id = "";
        String project_code = "";
        String org_id = "";
        String fullname = "";
        String type = "";
        String retail_price = "";
        String summary = "";
        String description = "";
        String disabled = "";
        String sync_time = "";
        String creator = "";
        String create_time = "";
        String modifier = "";
        String last_modify_time = "";
        String agent_id = "";
        String images_url[] = null;
        List<LittleProject> project_detail = new ArrayList<LittleProject>();
        List<LittleProject> project_note = new ArrayList<LittleProject>();
        if (!data.get("project_id").isJsonNull()) project_id = data.get("project_id").getAsString();
        if (!data.get("project_code").isJsonNull())
            project_code = data.get("project_code").getAsString();
        if (!data.get("org_id").isJsonNull()) org_id = data.get("org_id").getAsString();
        if (!data.get("fullname").isJsonNull()) fullname = data.get("fullname").getAsString();
        if (!data.get("type").isJsonNull()) type = data.get("type").getAsString();
        if (!data.get("retail_price").isJsonNull())
            retail_price = data.get("retail_price").getAsString();
        if (!data.get("summary").isJsonNull()) summary = data.get("summary").getAsString();
        if (!data.get("description").isJsonNull())
            description = data.get("description").getAsString();
        if (!data.get("disabled").isJsonNull()) disabled = data.get("disabled").getAsString();
        if (!data.get("sync_time").isJsonNull()) sync_time = data.get("sync_time").getAsString();
        if (!data.get("creator").isJsonNull()) creator = data.get("creator").getAsString();
        if (!data.get("create_time").isJsonNull())
            create_time = data.get("create_time").getAsString();
        if (!data.get("modifier").isJsonNull()) modifier = data.get("modifier").getAsString();
        if (!data.get("last_modify_time").isJsonNull())
            last_modify_time = data.get("last_modify_time").getAsString();
        if (!data.get("agent_id").isJsonNull()) agent_id = data.get("agent_id").getAsString();
        if (data.get("images_url").isJsonArray()) {
            JsonArray imgUrl = data.get("images_url").getAsJsonArray();
            int num = imgUrl.size();
            images_url = new String[num];
            for (int i = 0; i < num; i++) {
                images_url[i] = imgUrl.get(i).getAsString();
            }
        }
        if (data.get("project_detail").isJsonArray()) {
            String project_ids = "";
            String line = "";
            String name = "";
            String price = "";
            String qty = "";
            String unit = "";
            JsonArray pro_det = data.get("project_detail").getAsJsonArray();
            for (JsonElement je : pro_det) {
                JsonObject jo = je.getAsJsonObject();
                if (!jo.get("project_id").isJsonNull())
                    project_ids = jo.get("project_id").getAsString();
                if (!jo.get("line").isJsonNull()) line = jo.get("line").getAsString();
                if (!jo.get("name").isJsonNull()) name = jo.get("name").getAsString();
                if (!jo.get("price").isJsonNull()) price = jo.get("price").getAsString();
                if (!jo.get("qty").isJsonNull()) qty = jo.get("qty").getAsString();
                if (!jo.get("unit").isJsonNull()) unit = jo.get("unit").getAsString();
                project_detail.add(new LittleProject(project_ids, line, name, price, qty, unit));
            }
        }
        if (data.get("project_note").isJsonArray()) {
            String project_idss = "";
            String line = "";
            String title = "";
            String content = "";
            JsonArray pro_note = data.get("project_note").getAsJsonArray();
            for (JsonElement je : pro_note) {
                JsonObject jo = je.getAsJsonObject();
                if (!jo.get("project_id").isJsonNull())
                    project_idss = jo.get("project_id").getAsString();
                if (!jo.get("line").isJsonNull()) line = jo.get("line").getAsString();
                if (!jo.get("title").isJsonNull()) title = jo.get("title").getAsString();
                if (!jo.get("content").isJsonNull()) content = jo.get("content").getAsString();
                project_note.add(new LittleProject(project_idss, line, title, content));
            }

        }
        project.add(new Project(project_id, agent_id, project_code, org_id, type, retail_price, summary,
                description, disabled, sync_time, creator, create_time, modifier, last_modify_time, images_url,
                fullname, project_detail, project_note));
        Message msg = Message.obtain();
        msg.what = 0;
        msg.obj = project;
        handler.sendMessage(msg);
    }

    @OnClick(R.id.tv_look_detail)
    public void toDetailPager(){
        Intent intent=new Intent(this,PictureAndWordDetail.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("project",project_list.get(0));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 重新加载
     * @param view
     */
    public void loadingMore(View view){
        //判断网络连接状态
        if (NetWorkUtils.isNetworkConnected(this)){
            showShortDialog();
            getProjectDetailDatas(intent_sign, intent_project_id, intent_agent_id);
        }else{
            scroll_view.setVisibility(View.GONE);
            in_loading_error.setVisibility(View.VISIBLE);
        }
    }
}
