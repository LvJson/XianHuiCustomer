package com.maibo.lys.xianhuicustomer.myadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maibo.lys.xianhuicustomer.R;
import com.maibo.lys.xianhuicustomer.myentity.BindInfomation;
import com.maibo.lys.xianhuicustomer.myentity.Card;
import com.maibo.lys.xianhuicustomer.myentity.CourseCard;
import com.maibo.lys.xianhuicustomer.myentity.RecyclerEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LYS on 2017/2/8.
 */

public class BindInformationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Card> vipCard;
    private List<CourseCard> courseCards;
    private BindInfomation bInfo;
    private List<RecyclerEntity> datas=new ArrayList<>();
    private LayoutInflater mLayoutInflater;

    public BindInformationAdapter(Context context, List<Card> vipCard, List<CourseCard> courseCards,BindInfomation bInfo){
        this.context=context;
        this.vipCard=vipCard;
        this.courseCards=courseCards;
        this.bInfo=bInfo;
        mLayoutInflater=LayoutInflater.from(context);
        if (vipCard.size()!=0){
            datas.add(new RecyclerEntity("会员卡","",0));
            for (Card card:vipCard){
                datas.add(new RecyclerEntity(card.getCard_name(),card.getAmount(),1));
            }
        }
        if (courseCards.size()!=0){
            datas.add(new RecyclerEntity("疗程卡","",0));
            for (CourseCard cc:courseCards){
                datas.add(new RecyclerEntity(cc.getCard_name(),cc.getAmount(),1));
            }
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==-1){
            return new headViewHolder(mLayoutInflater.inflate(R.layout.style_bind_head,parent,false));
        }else if (viewType==0){
            return new cardTypeViewHolder(mLayoutInflater.inflate(R.layout.style_one_text,parent,false));
        }else {
            return new mainViewHolder(mLayoutInflater.inflate(R.layout.style_two_text,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof headViewHolder){
            ((headViewHolder) holder).tv_name.setText(bInfo.getDisplay_name());
            ((headViewHolder) holder).tv_agent_name.setText(bInfo.getAgent_name());
            ((headViewHolder) holder).tv_org_name.setText(bInfo.getOrg_name());
        }else if (holder instanceof cardTypeViewHolder){
            ((cardTypeViewHolder) holder).tv_one.setText(datas.get(position-1).getOne());
        }else if (holder instanceof mainViewHolder){
            ((mainViewHolder) holder).tv_name.setText(datas.get(position-1).getOne());
            ((mainViewHolder) holder).tv_amount.setText(datas.get(position-1).getTwo());
        }


    }

    @Override
    public int getItemCount() {
        return datas.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return -1;
        }else{
            return datas.get(position-1).getType();
        }
    }

    class headViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name,tv_agent_name,tv_org_name;
        headViewHolder(View view){
            super(view);
            tv_name= (TextView) view.findViewById(R.id.tv_name);
            tv_agent_name= (TextView) view.findViewById(R.id.tv_agent_name);
            tv_org_name= (TextView) view.findViewById(R.id.tv_org_name);
        }
    }
    class cardTypeViewHolder extends RecyclerView.ViewHolder{
        TextView tv_one;
        cardTypeViewHolder(View view){
            super(view);
            tv_one= (TextView) view.findViewById(R.id.tv_one);
        }
    }
    class mainViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name,tv_amount;
        mainViewHolder(View view){
            super(view);
            tv_name= (TextView) view.findViewById(R.id.tv_name);
            tv_amount= (TextView) view.findViewById(R.id.tv_amount);
        }
    }
}
