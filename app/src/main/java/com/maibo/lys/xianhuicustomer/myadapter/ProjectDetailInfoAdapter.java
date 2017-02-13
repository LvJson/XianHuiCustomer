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

public class ProjectDetailInfoAdapter extends BaseAdapter {
    Context context;
    List<LittleProject> llPro;
    Double totalPri;
    String realPri;

    public ProjectDetailInfoAdapter(Context context, List<LittleProject> llPro, Double totalPri, String realPri) {
        this.context = context;
        this.llPro = llPro;
        this.totalPri = totalPri;
        this.realPri = realPri;
    }

    @Override
    public int getCount() {
        return llPro.size()+2;
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
        View view = View.inflate(context, R.layout.style_project_price,null);
        TextView tv_name= (TextView) view.findViewById(R.id.tv_name);
        TextView tv_times= (TextView) view.findViewById(R.id.tv_time);
        TextView tv_price= (TextView) view.findViewById(R.id.tv_price);
        if (position==llPro.size()){
            tv_times.setText("总价值");
            tv_price.setText(totalPri+"元");
        }else if (position==llPro.size()+1){
            tv_times.setText("优惠价");
            tv_price.setText(realPri+"元");
        }else{
            tv_name.setText(llPro.get(position).getName());
            tv_times.setText(llPro.get(position).getQty()+"次");
            tv_price.setText(llPro.get(position).getPrice()+"元");
        }
        return view;
    }
}
