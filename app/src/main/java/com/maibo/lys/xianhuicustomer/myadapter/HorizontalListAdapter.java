package com.maibo.lys.xianhuicustomer.myadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.maibo.lys.xianhuicustomer.R;

/**
 * Created by LYS on 2017/1/17.
 */

public class HorizontalListAdapter extends BaseAdapter {
    Context mContext;
    public HorizontalListAdapter(Context mContext){
        this.mContext=mContext;
    }
    @Override
    public int getCount() {
        return 10;
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
        View view=View.inflate(mContext, R.layout.style_horizontal_adapter,null);
        return view;
    }
}
