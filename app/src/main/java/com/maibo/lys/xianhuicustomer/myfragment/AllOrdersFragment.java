package com.maibo.lys.xianhuicustomer.myfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.maibo.lys.xianhuicustomer.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LYS on 2017/1/13.
 */

public class AllOrdersFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_all_orders,null);
        ButterKnife.bind(this,view);
        return view;
    }
    @OnClick(R.id.tv1)
    public void show1(){
        Toast.makeText(getContext(),"one",Toast.LENGTH_LONG).show();
    }
    @OnClick(R.id.tv2)
    public void show2(){
        Toast.makeText(getContext(),"two",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
