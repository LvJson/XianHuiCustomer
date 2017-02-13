package com.maibo.lys.xianhuicustomer.myactivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.maibo.lys.xianhuicustomer.R;
import com.maibo.lys.xianhuicustomer.myadapter.ProjectDetailInfoAdapter;
import com.maibo.lys.xianhuicustomer.myadapter.ProjectNoteAdapter;
import com.maibo.lys.xianhuicustomer.myentity.LittleProject;
import com.maibo.lys.xianhuicustomer.myentity.Project;
import com.maibo.lys.xianhuicustomer.myutils.Util;

import butterknife.Bind;

/**
 * Created by LYS on 2017/2/10.
 */

public class PictureAndWordDetail extends BaseActivity {
    @Bind(R.id.ll_head)
    LinearLayout ll_head;
    @Bind(R.id.ll_left)
    LinearLayout ll_left;
    @Bind(R.id.tv_center)
    TextView tv_center;
    @Bind(R.id.tv_right)
    TextView tv_right;
    @Bind(R.id.tv_content)
    TextView tv_content;
    @Bind(R.id.listview_detail)
    ListView listview_detail;
    @Bind(R.id.listview_note)
    ListView listview_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_and_word_detail);
        initBar();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        Project project= (Project) bundle.get("project");
        tv_content.setText(project.getDescription());
        double total_price=0.00;
        for (int i=0;i<project.getProject_detail().size();i++){
            LittleProject lp=project.getProject_detail().get(i);
            double a=Double.parseDouble(lp.getPrice());
            total_price+=a;
        }
        listview_detail.setAdapter(new ProjectDetailInfoAdapter(this,project.getProject_detail(),total_price,project.getRetail_price()));
        listview_note.setAdapter(new ProjectNoteAdapter(this,project.getProject_note()));
    }

    /**
     * 初始化ToolBar
     */
    private void initBar() {
        ViewGroup.LayoutParams params = ll_head.getLayoutParams();
        params.height = ((Util.getScreenHeight(this) - getStatusBarHeight()) / 35) * 2;
        ll_head.setLayoutParams(params);
        ll_head.setBackgroundColor(Color.WHITE);
        tv_center.setText("图文详情");
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
