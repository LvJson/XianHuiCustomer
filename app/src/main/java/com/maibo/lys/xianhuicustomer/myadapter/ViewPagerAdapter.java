package com.maibo.lys.xianhuicustomer.myadapter;

/**
 * Created by LYS on 2017/2/9.
 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.maibo.lys.xianhuicustomer.R;
import com.squareup.picasso.Picasso;

/**
 * Created by LYS on 2017/1/17.
 */

public class ViewPagerAdapter extends PagerAdapter {

    Context context;
    String[] str_url;
    int height;
    int width;
    public ViewPagerAdapter(Context context, String[] str_url,int height,int width){
        this.context=context;
        this.str_url=str_url;
        this.height=height;
        this.width=width;
    }
    /**
     * 返回多少page
     */
    @Override
    public int getCount() {
        return str_url.length;
    }

    /**
     * true: 表示不去创建，使用缓存  false:去重新创建
     * view： 当前滑动的view
     * object：将要进入的新创建的view，由instantiateItem方法创建
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 类似于BaseAdapger的getView方法
     * 用了将数据设置给view
     * 由于它最多就3个界面，不需要viewHolder
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(context, R.layout.adapter_ad, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
//        Picasso.with(context).load(str_url[position]).into(imageView);
        Picasso.with(context)
                .load(str_url[position])
                .resize(width, height)
                .centerCrop()
                .into(imageView);
        container.addView(view);//一定不能少，将view加入到viewPager中
        return view;
    }

    /**
     * 销毁page
     * position： 当前需要消耗第几个page
     * object:当前需要消耗的page
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//			super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}

