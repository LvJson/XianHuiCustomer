package com.maibo.lys.xianhuicustomer.myfragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.maibo.lys.xianhuicustomer.R;
import com.maibo.lys.xianhuicustomer.constants.PublicString;
import com.maibo.lys.xianhuicustomer.constants.WebAddress;
import com.maibo.lys.xianhuicustomer.myactivity.MainActivity1;
import com.maibo.lys.xianhuicustomer.myactivity.MyCaptureActivity;
import com.maibo.lys.xianhuicustomer.myactivity.ProjectDetailActivity;
import com.maibo.lys.xianhuicustomer.myadapter.HomePagerRecyclerAdapter;
import com.maibo.lys.xianhuicustomer.myapplication.MyApplication;
import com.maibo.lys.xianhuicustomer.myentity.Ad;
import com.maibo.lys.xianhuicustomer.myentity.Project;
import com.maibo.lys.xianhuicustomer.myinterface.HomeProjectItemClickListener;
import com.maibo.lys.xianhuicustomer.myutils.Util;
import com.maibo.lys.xianhuicustomer.myview.AllDividerDecoration;
import com.maibo.lys.xianhuicustomer.myview.MyProgressDialog;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import okhttp3.Call;

/**
 * Created by LYS on 2017/2/4.
 */

public class HomePagerRecyclerFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.ll_head)
    LinearLayout ll_head;
    @Bind(R.id.ll_left)
    LinearLayout ll_left;
    @Bind(R.id.tv_center)
    TextView tv_center;
    @Bind(R.id.tv_right)
    TextView tv_right;
    @Bind(R.id.home_recycler)
    XRecyclerView home_recycler;
    @Bind(R.id.tv_handover)
    TextView tv_handover;

    MyProgressDialog shortDialog;

    private View rootView;
    int tag = 0;
    LinearLayoutManager manager;
    List<Ad> list;
    HomePagerRecyclerAdapter recyclerAdapter;
    List<Project> projects;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    projects = (List<Project>) msg.obj;
                    home_recycler.setAdapter(recyclerAdapter = new HomePagerRecyclerAdapter(getActivity(), getContext(), list, projects));
                    recyclerAdapter.startAutoScroll(); // activity激活时候自动播放
                    tag = 1;
                    recyclerAdapter.setOnMyItemClickListenre(new HomeProjectItemClickListener() {
                        @Override
                        public void onItemClick(int position, Project project) {
                            Log.e("project_id1", project.getProject_id() + "  agentId1:" + project.getAgent_id());
                            Intent intent = new Intent(getActivity(), ProjectDetailActivity.class);
                            intent.putExtra("project_id", project.getProject_id());
                            intent.putExtra("agent_id", project.getAgent_id());
                            startActivity(intent);
                        }
                    });
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home_page_recylcer, null);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        ButterKnife.bind(this, rootView);
        initBar();
        initView();
        initData();
        initListener();
        return rootView;
    }

    /**
     * 初始化view
     */
    private void initView() {
        manager = new LinearLayoutManager(getContext());
        home_recycler.setLayoutManager(manager);
        home_recycler.addItemDecoration(new AllDividerDecoration(getContext()));
//        home_recycler.addItemDecoration(new RecycleViewDivider(getContext(),LinearLayout.VERTICAL,50,getResources().getColor(R.color.bar_color)));
//        home_recycler.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayout.VERTICAL));
        home_recycler.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        home_recycler.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        DefaultItemAnimator animator = new DefaultItemAnimator() {
            @Override
            public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
                return true;
            }
        };
        home_recycler.setItemAnimator(animator);
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        ll_left.setOnClickListener(this);
        tv_handover.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        home_recycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        home_recycler.refreshComplete();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        home_recycler.loadMoreComplete();
//                        home_recycler.setNoMore(true);
                        Toast.makeText(getContext(), "已加载全部", Toast.LENGTH_LONG).show();
                    }
                }, 2000);
            }
        });
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
        ll_left.setVisibility(View.VISIBLE);
        ll_head.setBackgroundColor(Color.WHITE);
    }

    /**
     * 初始化图片数据
     */
    private void initData() {
        list = new ArrayList<>();
        list.clear();
        list.add(new Ad(R.mipmap.a, "Text one"));
        list.add(new Ad(R.mipmap.b, "Text two"));
        list.add(new Ad(R.mipmap.c, "Text three"));
        list.add(new Ad(R.mipmap.d, "Text four"));
        list.add(new Ad(R.mipmap.e, "Text five"));
        shortDialog = new MyProgressDialog(getActivity());
        shortDialog.show();
        getProjectList(Util.getSign(PublicString.PUBLICK_KEY), "1");

    }

    /**
     * 获取项目列表
     *
     * @param sign
     * @param pagerNumber
     */
    private void getProjectList(String sign, String pagerNumber) {
        OkHttpUtils
                .post()
                .url(WebAddress.GET_PROJECT_LIST)
                .addParams("sign", sign)
                .addParams("pagerNumber", pagerNumber)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        shortDialog.dismiss();
                        Toast.makeText(getContext(), R.string.net_errow, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JsonObject jsOb = new JsonParser().parse(response).getAsJsonObject();
                        String status = jsOb.get("status").getAsString();
                        String message = jsOb.get("message").getAsString();
                        if (status.equals("ok")) {
                            analysisProjectList(jsOb);
                        } else {
                            MyApplication.showToast(getContext(), message);
                        }
                        shortDialog.dismiss();
                    }
                });
    }

    /**
     * 解析项目列表数据
     *
     * @param jsOb
     */
    private void analysisProjectList(JsonObject jsOb) {
        List<Project> project = new ArrayList<Project>();
        if (jsOb.get("data").isJsonArray()) {
            JsonArray data = jsOb.get("data").getAsJsonArray();
            String agent_id = "";
            String project_id = "";
            String fullname = "";
            String type = "";
            String retail_price = "";
            String summary = "";
            String disabled = "";
            String create_time = "";
            String last_modify_time = "";
            String images_url[] = null;
            for (JsonElement je : data) {
                JsonObject jo = je.getAsJsonObject();
                if (!jo.get("agent_id").isJsonNull())
                    agent_id = jo.get("agent_id").getAsString();
                if (!jo.get("project_id").isJsonNull())
                    project_id = jo.get("project_id").getAsString();
                if (!jo.get("fullname").isJsonNull())
                    fullname = jo.get("fullname").getAsString();
                if (!jo.get("type").isJsonNull())
                    type = jo.get("type").getAsString();
                if (!jo.get("retail_price").isJsonNull())
                    retail_price = jo.get("retail_price").getAsString();
                if (!jo.get("summary").isJsonNull())
                    summary = jo.get("summary").getAsString();
                if (!jo.get("disabled").isJsonNull())
                    disabled = jo.get("disabled").getAsString();
                if (!jo.get("create_time").isJsonNull())
                    create_time = jo.get("create_time").getAsString();
                if (!jo.get("last_modify_time").isJsonNull())
                    last_modify_time = jo.get("last_modify_time").getAsString();
                if (jo.get("images_url").isJsonArray()) {
                    JsonArray json_img = jo.get("images_url").getAsJsonArray();
                    int num = json_img.size();
                    images_url = new String[num];
                    for (int i = 0; i < num; i++) {
                        images_url[i] = json_img.get(i).getAsString();
                    }
                }
                project.add(new Project(agent_id, project_id, fullname, type, retail_price,
                        summary, disabled, create_time, last_modify_time, images_url));
            }
        }
        Message msg = Message.obtain();
        msg.what = 0;
        msg.obj = project;
        handler.sendMessage(msg);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (tag != 0 && recyclerAdapter != null)
            recyclerAdapter.startAutoScroll(); // activity激活时候自动播放
    }

    @Override
    public void onPause() {
        super.onPause();
        tag = 1;
        if (recyclerAdapter != null)
            recyclerAdapter.stopAutoScroll(); // activity暂停时候停止自动播放
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                //跳转到消息界面

                break;
            case R.id.tv_handover:
                //跳转到切换门店界面

                break;
            case R.id.tv_right:
                //跳转到扫一扫新增界面
                PermissionGen.needPermission(HomePagerRecyclerFragment.this, 100,
                        new String[]{
                                Manifest.permission.CAMERA
                        }
                );
                break;
        }
    }

    /**
     * 以下三个代码块为权限处理所用
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 100)
    public void doSomething() {
        //权限已获得
        // //权限已获得
        Intent intent = new Intent(getActivity(), MyCaptureActivity.class);
        startActivityForResult(intent, 1);
    }

    @PermissionFail(requestCode = 100)
    public void doFailSomething() {
        MyApplication.showToast(getContext(), "请设置权限");
    }

    /**
     * 处理二维码扫描结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        Log.e("Home","onactivity");
        if (requestCode == 1) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    //扫描结果
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    //TODO

                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {

                }
            }
        }
    }
}
