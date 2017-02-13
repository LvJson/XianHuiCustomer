package com.maibo.lys.xianhuicustomer.myadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.maibo.lys.xianhuicustomer.R;
import com.maibo.lys.xianhuicustomer.myentity.LittleProject;

import java.util.List;

/**
 * Created by LYS on 2017/2/10.
 */

public class ProjectNoteAdapter extends BaseAdapter {
    Context context;
    List<LittleProject> llPro;
    public ProjectNoteAdapter(Context context, List<LittleProject> llPro){
        this.context=context;
        this.llPro=llPro;
    }
    @Override
    public int getCount() {
        return llPro.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=View.inflate(context, R.layout.style_project_note,null);
        TextView tv_title= (TextView) view.findViewById(R.id.tv_title);
        TextView tv_content= (TextView) view.findViewById(R.id.tv_content);
        tv_title.setText(llPro.get(position).getTitle());
        tv_content.setText(llPro.get(position).getContent());
        return view;
    }
}
